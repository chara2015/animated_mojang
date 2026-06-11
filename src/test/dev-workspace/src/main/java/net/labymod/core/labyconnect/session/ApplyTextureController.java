package net.labymod.core.labyconnect.session;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.client.session.model.MojangTexture;
import net.labymod.api.client.session.model.MojangTextureChangedResponse;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.mojang.texture.SkinPolicy;
import net.labymod.api.notification.Notification;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.thirdparty.LabySentry;
import net.labymod.api.util.FileDialogs;
import net.labymod.api.util.I18n;
import net.labymod.api.util.Pair;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/ApplyTextureController.class */
@Singleton
@Referenceable
public class ApplyTextureController {
    private static final Logging LOGGER = Logging.getLogger();
    private final NotificationController notificationController;
    private final DefaultLabyConnect labyConnect;
    private final LabyAPI labyAPI;
    private final TextureRepository textureRepository;
    private final MinecraftServices minecraftServices;
    private final GameImageProvider gameImageProvider;
    private final FileDialogs fileDialogs;
    private MojangTextureChangedResponse userProfile;
    private boolean requestedUserProfile = false;

    @Inject
    public ApplyTextureController(NotificationController notificationController, LabyConnect labyConnect, LabyAPI labyAPI, TextureRepository textureRepository, MinecraftServices minecraftServices, GameImageProvider gameImageProvider, FileDialogs fileDialogs) {
        this.notificationController = notificationController;
        this.labyConnect = (DefaultLabyConnect) labyConnect;
        this.labyAPI = labyAPI;
        this.textureRepository = textureRepository;
        this.minecraftServices = minecraftServices;
        this.gameImageProvider = gameImageProvider;
        this.fileDialogs = fileDialogs;
        this.labyAPI.eventBus().registerListener(this);
    }

    public void getProfileAsync(Consumer<MojangTextureChangedResponse> consumer) {
        if (this.requestedUserProfile) {
            consumer.accept(this.userProfile);
        } else {
            this.requestedUserProfile = true;
            Task.builder(() -> {
                Response<MojangTextureChangedResponse> profileResponse = this.minecraftServices.getProfile();
                profileResponse.ifPresent(response -> {
                    this.userProfile = (MojangTextureChangedResponse) profileResponse.get();
                    ThreadSafe.executeOnRenderThread(() -> {
                        consumer.accept(response);
                    });
                });
                if (profileResponse.hasException()) {
                    profileResponse.exception().printStackTrace();
                    consumer.accept(null);
                }
            }).build().execute();
        }
    }

    public void setCapeAsync(String capeId, Consumer<MojangTextureChangedResponse> consumer) {
        Task.builder(() -> {
            Response<MojangTextureChangedResponse> profileResponse;
            if (capeId == null) {
                profileResponse = this.minecraftServices.hideCape();
            } else {
                profileResponse = this.minecraftServices.showCape(capeId);
            }
            Response<MojangTextureChangedResponse> response = profileResponse;
            profileResponse.ifPresent(response2 -> {
                this.userProfile = (MojangTextureChangedResponse) response.get();
                consumer.accept(response2);
                this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.customization.textures.cape.title", new Component[0])).text(Component.translatable("labymod.activity.customization.textures.cape.change.success", new Component[0])).build());
            });
            if (profileResponse.hasException()) {
                profileResponse.exception().printStackTrace();
                this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.customization.textures.cape.title", new Component[0])).text(Component.translatable("labymod.activity.customization.textures.cape.change.success", new Component[0]).append(Component.text(" " + profileResponse.getStatusCode()))).build());
            }
        }).build().execute();
    }

    public void requestUploadSkin(Icon previewIcon, Runnable callback) {
        Notification notification = Notification.builder().title(Component.translatable("labymod.activity.skins.apply.title", new Component[0])).text(Component.translatable("labymod.activity.skins.apply.description", new Component[0])).icon(previewIcon).addButton(Notification.NotificationButton.of(Component.translatable("labymod.ui.button.apply", new Component[0]), callback)).duration(300000L).build();
        this.notificationController.push(notification);
    }

    public void browseSkinFile(Consumer<Path> callback) {
        try {
            this.fileDialogs.open(I18n.translate("labymod.activity.skins.choose.title", new Object[0]), Paths.get("/", new String[0]), I18n.translate("labymod.activity.skins.choose.type", new Object[0]), new String[]{"png"}, false, paths -> {
                callback.accept(paths.length == 1 ? paths[0] : null);
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.notificationController.push(Notification.builder().title(Component.text("Error")).text(Component.text("File chooser not supported yet: " + e.getClass().getSimpleName())).build());
            callback.accept(null);
        }
    }

    public void browseSkinFile(MinecraftServices.SkinVariant variant) {
        browseSkinFile(path -> {
            if (path == null) {
                return;
            }
            uploadSkinFileAsync(path, variant);
        });
    }

    public void uploadSkinFileAsync(Path path) {
        uploadSkinFileAsync(path, null);
    }

    public void uploadSkinFileAsync(Path path, @Nullable MinecraftServices.SkinVariant variant) {
        Task.builder(() -> {
            try {
                uploadSkinFile(path, variant);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).build().execute();
    }

    public Pair<ResourceLocation, GameImage> loadSkinFile(Path path, MinecraftServices.SkinVariant variant) throws IOException {
        InputStream inputStream = IOUtil.newInputStream(path);
        GameImage image = this.gameImageProvider.getImage(inputStream);
        if (isInvalidSkin(image)) {
            return null;
        }
        GameImage imageLegal = SkinPolicy.applyPolicy(image);
        ResourceLocation resourceLocation = ResourceLocation.create(this.labyAPI.getNamespace(this), "skins/upload");
        if (variant == null) {
            variant = SkinPolicy.guessVariant(image);
        }
        resourceLocation.metadata().set("variant", variant.getId());
        this.textureRepository.releaseTexture(resourceLocation);
        imageLegal.uploadTextureAt(resourceLocation);
        return Pair.of(resourceLocation, imageLegal);
    }

    public void uploadSkinFile(Path path, @Nullable MinecraftServices.SkinVariant variant) throws IOException {
        Pair<ResourceLocation, GameImage> skin = loadSkinFile(path, variant);
        if (skin == null || skin.getSecond() == null) {
            return;
        }
        GameImage image = skin.getSecond();
        if (variant == null) {
            variant = SkinPolicy.guessVariant(image);
        }
        uploadSkinAsync(variant, new MinecraftServices.SkinPayload(image));
    }

    public void uploadSkinAsync(MinecraftServices.SkinVariant variant, MinecraftServices.SkinPayload payload, ResultCallback<MojangTextureChangedResponse> result) {
        Task.builder(() -> {
            result.accept((Result) uploadSkin(variant, payload));
        }).build().execute();
    }

    public void uploadSkinAsync(MinecraftServices.SkinVariant variant, MinecraftServices.SkinPayload payload) {
        Task.builder(() -> {
            uploadSkin(variant, payload);
        }).build().execute();
    }

    public Result<MojangTextureChangedResponse> uploadSkin(MinecraftServices.SkinVariant variant, MinecraftServices.SkinPayload payload) {
        try {
            GameImage image = getGameImage(payload);
            if (image == null) {
                return Result.ofException(new IllegalStateException("No image could be created from the payload"));
            }
            if (isInvalidSkin(image)) {
                return Result.ofException(new IllegalStateException("This image does not have the correct skin format."));
            }
            MinecraftServices.SkinPayload payload2 = new MinecraftServices.SkinPayload(image);
            Response<MojangTextureChangedResponse> result = this.minecraftServices.changeSkin(variant, payload2);
            if (result.hasException()) {
                this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.skins.apply.title", new Component[0])).text(Component.translatable("labymod.activity.skins.apply.error", new Component[0])).build());
                return result;
            }
            MojangTextureChangedResponse response = result.get();
            if (response.getSkins().length > 0) {
                LabyConnectSession session = this.labyConnect.getSession();
                if (session != null) {
                    session.sendTextureUpdated(response);
                }
                this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.skins.apply.title", new Component[0])).text(Component.translatable("labymod.activity.skins.apply.success", new Component[0])).build());
                return result;
            }
            this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.skins.apply.title", new Component[0])).text(Component.translatable("labymod.activity.skins.apply.error", new Component[0])).build());
            return Result.ofException(new IllegalStateException("No skins found"));
        } catch (Exception exception) {
            LabySentry.capture(exception);
            LOGGER.error("An error occurred while uploading a skin", exception);
            this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.skins.apply.title", new Component[0])).text(Component.translatable("labymod.activity.skins.apply.error", new Component[0])).build());
            return Result.ofException(exception);
        }
    }

    public void applySkinTexture(UUID uniqueId, MojangTexture texture) {
        ThreadSafe.executeOnRenderThread(() -> {
            MojangTextureService textureService = Laby.references().mojangTextureService();
            textureService.applySkinTexture(uniqueId, texture.getVariant(), texture.getUrl());
        });
    }

    public void applyCapeTexture(UUID uniqueId, MojangTexture texture) {
        ThreadSafe.executeOnRenderThread(() -> {
            MojangTextureService textureService = Laby.references().mojangTextureService();
            textureService.applyTexture(uniqueId, MojangTextureType.CAPE, texture == null ? null : texture.getUrl());
        });
    }

    @Subscribe
    public void onSessionUpdate(SessionUpdateEvent event) {
        this.userProfile = null;
        this.requestedUserProfile = false;
    }

    private boolean isInvalidSkin(GameImage image) {
        if (!SkinPolicy.isValidFormat(image)) {
            this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.skins.apply.title", new Component[0])).text(Component.translatable("labymod.activity.skins.apply.wrong_format", new Component[0])).build());
            return true;
        }
        return false;
    }

    @Nullable
    private GameImage getGameImage(MinecraftServices.SkinPayload payload) throws IOException {
        if (payload.hasGameImage()) {
            return payload.getGameImage();
        }
        String url = payload.getUrl();
        if (url == null) {
            return null;
        }
        CompletableTextureImage target = new CompletableTextureImage(new TextureImage(Textures.WHITE));
        this.textureRepository.executeTextureLoader(url, target);
        return target.getCompleted().getGameImage();
    }
}
