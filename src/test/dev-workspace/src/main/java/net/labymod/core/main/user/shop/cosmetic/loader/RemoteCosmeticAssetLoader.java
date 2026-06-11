package net.labymod.core.main.user.shop.cosmetic.loader;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import net.laby.lib.bedrock.geometry.GeometryFile;
import net.laby.lib.cosmetics.AnimationService;
import net.laby.lib.cosmetics.GeometryService;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.render.model.DefaultModelService;
import net.labymod.core.main.user.shop.cosmetic.CosmeticAssets;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.item.geometry.BedrockModelLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/loader/RemoteCosmeticAssetLoader.class */
public class RemoteCosmeticAssetLoader implements CosmeticAssetLoader {
    private static final Logging LOGGER = Logging.getLogger();
    private final AnimationService animationService;
    private final GeometryService geometryService;
    private final DefaultModelService modelService;

    public RemoteCosmeticAssetLoader(AnimationService animationService, GeometryService geometryService, DefaultModelService modelService) {
        this.animationService = animationService;
        this.geometryService = geometryService;
        this.modelService = modelService;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.loader.CosmeticAssetLoader
    public CompletableFuture<CosmeticAssets> loadAssets(CosmeticDefinition definition) {
        CompletableFuture<CosmeticAssets> future = new CompletableFuture<>();
        int identifier = definition.id();
        LabyExecutors.submitBackgroundTask(() -> {
            try {
                Collection<ModelAnimation> animations = loadAnimations(identifier);
                GeometryFile geometryFile = loadGeometry(identifier);
                if (geometryFile == null) {
                    LOGGER.warn("Failed to load geometry for cosmetic {}", Integer.valueOf(identifier));
                    definition.markFailed();
                    future.completeExceptionally(new RuntimeException("Failed to load geometry for cosmetic " + identifier));
                    return;
                }
                ThreadSafe.executeOnRenderThread(() -> {
                    CosmeticAssets assets;
                    try {
                        BedrockModelLoader loader = BedrockModelLoader.create(geometryFile, true);
                        if (animations != null) {
                            assets = CosmeticAssets.ofAnimated(loader.getModel(), animations, loader.getEffects(), loader.hasOutlineParts());
                        } else {
                            assets = CosmeticAssets.ofModel(loader.getModel(), loader.getEffects(), loader.hasOutlineParts());
                        }
                        definition.setAssets(assets);
                        future.complete(assets);
                    } catch (Exception e) {
                        LOGGER.error("Failed to initialize cosmetic {}: {}", Integer.valueOf(identifier), e.getMessage());
                        definition.markFailed();
                        future.completeExceptionally(e);
                    }
                });
            } catch (Exception exception) {
                LOGGER.error("Failed to load cosmetic {}: {}", Integer.valueOf(identifier), exception.getMessage());
                definition.markFailed();
                future.completeExceptionally(exception);
            }
        });
        return future;
    }

    private Collection<ModelAnimation> loadAnimations(int identifier) {
        return (Collection) this.animationService.getAnimation(identifier).fold(animationFile -> {
            return this.modelService.loadBlockBenchAnimations(animationFile, AnimationMeta.defaults());
        }, error -> {
            LOGGER.warn("Failed to load animation for cosmetic {}: {}", Integer.valueOf(identifier), error.message());
            return null;
        });
    }

    private GeometryFile loadGeometry(int identifier) {
        return (GeometryFile) this.geometryService.getGeometry(identifier).fold(Function.identity(), error -> {
            LOGGER.warn("Failed to load geometry for cosmetic {}: {}", Integer.valueOf(identifier), error.message());
            return null;
        });
    }
}
