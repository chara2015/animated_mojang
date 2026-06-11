package net.labymod.core.main.user.shop.emote;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.laby.lib.bedrock.animation.AnimationFile;
import net.laby.lib.bedrock.geometry.GeometryFile;
import net.laby.lib.core.result.Result;
import net.laby.lib.emote.Emote;
import net.laby.lib.http.HttpError;
import net.laby.lib.http.StreamingHttpResponse;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.texture.TextureDetails;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.render.model.DefaultModelService;
import net.labymod.core.main.user.shop.AnimationContainer;
import net.labymod.core.main.user.shop.JsonBodyDeserializer;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationMeta;
import net.labymod.core.main.user.shop.emote.exception.EmoteException;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.core.main.user.shop.item.texture.Ratio;
import net.labymod.core.main.util.LabyLibHttpClients;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/emote/EmoteLoader.class */
@Singleton
@Referenceable
public class EmoteLoader {
    private static final Logging LOGGER = Logging.getLogger();
    private static final String EMOTE_TEXTURE_PATH = "emotes/%s/texture";
    private final DefaultModelService modelService;
    private final Resources resources;
    private final net.laby.lib.emote.EmoteService emoteService;

    @Inject
    public EmoteLoader(ModelService modelService, Resources resources, LabyLibHttpClients httpClients) {
        this.modelService = (DefaultModelService) modelService;
        this.resources = resources;
        this.emoteService = new net.laby.lib.emote.EmoteService(httpClients.createValidatedHttpClient(JsonBodyDeserializer.defaultGson()));
        this.emoteService.setLogUnknownFields(Laby.labyAPI().labyModLoader().isExplicitLabyModDevelopmentEnvironment());
    }

    public void loadEmotes(Int2ObjectOpenHashMap<EmoteItem> emotes) {
        List<EmoteItem> items = loadEmoteIndex();
        List<Future<?>> tasks = new ArrayList<>(items.size());
        for (EmoteItem emote : items) {
            tasks.add(LabyExecutors.submitBackgroundTask(() -> {
                loadEmote(emote, emotes);
            }));
        }
        awaitAndReportErrors(tasks);
    }

    private void loadEmote(EmoteItem emote, Int2ObjectOpenHashMap<EmoteItem> emotes) {
        int emoteId = emote.getId();
        emote.setAnimationContainer(loadAnimations(emoteId));
        if (emote.hasProps()) {
            emote.setPropsModel(loadPropsModel(emoteId));
            loadPropsTexture(emote, emoteId);
        }
        if (emote.hasPlayerModel()) {
            emote.setSteveModel(loadPlayerModel(emoteId, false));
            emote.setAlexModel(loadPlayerModel(emoteId, true));
        }
        ThreadSafe.executeOnRenderThread(() -> {
            emotes.put(emoteId, emote);
        });
    }

    private void loadPropsTexture(EmoteItem emote, int emoteId) {
        String texturePath = String.format(Locale.ROOT, EMOTE_TEXTURE_PATH, Integer.valueOf(emoteId));
        Ratio ratio = emote.getTextureRatio();
        if (ratio == null) {
            byte[] textureData = loadTextureData(emoteId);
            ResourceLocation location = ResourceLocation.create("labymod", texturePath + ".png");
            CompletableResourceLocation completableLocation = this.resources.textureRepository().getOrRegisterTexture(TextureDetails.builder(location).withImageData(textureData).build());
            completableLocation.addCompletableListener(() -> {
                emote.setPropsTextureLocation(completableLocation.getCompleted());
            });
            return;
        }
        byte[] animatedTextureData = loadTextureData(emoteId);
        AnimatedResourceLocation animatedLocation = this.resources.resourceLocationFactory().createAnimated("labymod", texturePath, new ByteArrayInputStream(animatedTextureData), ratio.getWidth(), ratio.getHeight(), emote.getTextureAnimationDelay());
        emote.setAnimatedPropsTextureLocation(animatedLocation);
    }

    private List<EmoteItem> loadEmoteIndex() {
        return (List) this.emoteService.getIndex((context, result) -> {
            LOGGER.warn("Skipping {}: {}", context, result.errors());
        }).fold(index -> {
            List<EmoteItem> items = new ArrayList<>();
            for (Map.Entry<Integer, Emote> entry : index.emotes().entrySet()) {
                items.add(new EmoteItem(entry.getValue(), null));
            }
            return items;
        }, error -> {
            LOGGER.warn("Failed to load emote index: {}", error.message());
            return Collections.emptyList();
        });
    }

    private AnimationContainer loadAnimations(int emoteId) {
        AnimationFile animationFile = (AnimationFile) unwrap(this.emoteService.getAnimation(emoteId), emoteId, "animation");
        Collection<ModelAnimation> animations = this.modelService.loadBlockBenchAnimations(animationFile, EmoteAnimationMeta.withDefaults());
        if (animations == null || animations.isEmpty()) {
            throw new EmoteException(emoteId, "Animation file for emote " + emoteId + " does not contain animations", "No animations");
        }
        AnimationContainer container = new AnimationContainer(animations);
        if (container.hasTriggerAnimations()) {
            for (ModelAnimation animation : container.getAnimations()) {
                animation.addMeta(EmoteAnimationMeta.TRIGGER_EMOTE, true);
            }
        }
        for (ModelAnimation animation2 : container.getTriggerAnimations(AnimationTrigger.NONE)) {
            animation2.addMeta(EmoteAnimationMeta.START_ANIMATION, true);
        }
        return container;
    }

    private Model loadPropsModel(int emoteId) {
        return this.modelService.loadBlockBenchModel((GeometryFile) unwrap(this.emoteService.getGeometry(emoteId), emoteId, "props"));
    }

    private Model loadPlayerModel(int emoteId, boolean slim) {
        Result<GeometryFile, HttpError> steveModel;
        if (slim) {
            steveModel = this.emoteService.getAlexModel(emoteId);
        } else {
            steveModel = this.emoteService.getSteveModel(emoteId);
        }
        Result<GeometryFile, HttpError> result = steveModel;
        return this.modelService.loadBlockBenchModel((GeometryFile) unwrap(result, emoteId, "player model"));
    }

    private byte[] loadTextureData(int emoteId) {
        try {
            StreamingHttpResponse response = (StreamingHttpResponse) unwrap(this.emoteService.getTexture(emoteId), emoteId, ItemMetadata.TEXTURE_KEY);
            try {
                byte[] allBytes = response.body().readAllBytes();
                if (response != null) {
                    response.close();
                }
                return allBytes;
            } catch (Throwable th) {
                if (response != null) {
                    try {
                        response.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (EmoteException e) {
            throw e;
        } catch (Exception e2) {
            throw new EmoteException(emoteId, e2.getMessage(), e2.getMessage());
        }
    }

    private <T> T unwrap(Result<T, HttpError> result, int i, String str) {
        return (T) result.fold(Function.identity(), error -> {
            throw new EmoteException(i, "Failed to load " + str + " of emote " + i + ": " + error.message(), error.message());
        });
    }

    private void awaitAndReportErrors(List<Future<?>> tasks) {
        Throwable cause;
        Map<String, IntList> errorMap = new HashMap<>();
        for (Future<?> task : tasks) {
            try {
                task.get();
            } catch (Exception exception) {
                Throwable cause2 = exception;
                while (true) {
                    cause = cause2;
                    if (cause.getCause() == null) {
                        break;
                    } else {
                        cause2 = cause.getCause();
                    }
                }
                if (cause instanceof EmoteException) {
                    EmoteException emoteException = (EmoteException) cause;
                    errorMap.computeIfAbsent(emoteException.getReason(), s -> {
                        return new IntArrayList();
                    }).add(emoteException.getEmoteId());
                } else {
                    LOGGER.warn("Unable to load emote: {}", cause.getMessage());
                }
            }
        }
        for (Map.Entry<String, IntList> entry : errorMap.entrySet()) {
            IntList ids = entry.getValue();
            boolean multiple = ids.size() > 1;
            Logging logging = LOGGER;
            Object[] objArr = new Object[4];
            objArr[0] = multiple ? "some emotes" : "emote";
            objArr[1] = entry.getKey();
            objArr[2] = multiple ? "Emotes" : "Emote";
            objArr[3] = StringUtil.join((Collection<?>) ids, (CharSequence) ", ");
            logging.warn("Unable to load {}. (Cause: {}, {}: {})", objArr);
        }
    }
}
