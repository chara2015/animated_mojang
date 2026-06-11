package net.labymod.core.main.user.shop.cosmetic.texture;

import java.io.ByteArrayInputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import net.laby.lib.core.result.Result;
import net.laby.lib.cosmetics.Cosmetic;
import net.laby.lib.cosmetics.CosmeticOptions;
import net.laby.lib.cosmetics.FrameAspectRatio;
import net.laby.lib.cosmetics.TextureService;
import net.laby.lib.http.StreamingHttpResponse;
import net.labymod.api.Namespaces;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.state.CosmeticState;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.core.main.user.shop.item.model.TextureDetails;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/texture/CosmeticTextureService.class */
public class CosmeticTextureService {
    private static final Logging LOGGER = Logging.getLogger();
    private final TextureService textureService;
    private final TextureRepository textureRepository;
    private final Resources resources;
    private final MojangTextureService mojangTextureService;

    @Nullable
    private final TextureUploadThrottler uploadThrottler;

    public CosmeticTextureService(TextureService textureService, TextureRepository textureRepository, Resources resources, MojangTextureService mojangTextureService) {
        this(textureService, textureRepository, resources, mojangTextureService, null);
    }

    public CosmeticTextureService(TextureService textureService, TextureRepository textureRepository, Resources resources, MojangTextureService mojangTextureService, @Nullable TextureUploadThrottler uploadThrottler) {
        this.textureService = textureService;
        this.textureRepository = textureRepository;
        this.resources = resources;
        this.mojangTextureService = mojangTextureService;
        this.uploadThrottler = uploadThrottler;
    }

    @Nullable
    public CompletableResourceLocation loadTexture(CosmeticDefinition definition, CosmeticState state, ItemMetadata metadata, Player player) {
        Cosmetic cosmetic = definition.cosmetic();
        if (cosmetic == null) {
            return null;
        }
        boolean mojangBound = metadata.isMojangBound();
        boolean forcedPlayerSkin = metadata.isForcedPlayerSkin(mojangBound);
        if (mojangBound || forcedPlayerSkin) {
            return loadMojangBoundTexture(player, forcedPlayerSkin ? null : metadata.getSkinTexture());
        }
        if (metadata.isTypeAndMojangBound()) {
            return loadTypeAndMojangTexture(cosmetic, state, metadata, player);
        }
        return loadTypeBoundTexture(cosmetic, state, metadata, player);
    }

    @Nullable
    private CompletableResourceLocation loadTypeBoundTexture(Cosmetic cosmetic, CosmeticState state, ItemMetadata metadata, Player player) {
        TextureContext ctx = buildTextureContext(cosmetic, metadata, player);
        if (ctx == null) {
            return null;
        }
        FrameAspectRatio ratio = cosmetic.frameAspectRatio();
        boolean animated = ratio != null;
        int frameDelay = animated ? fetchFrameDelay(cosmetic, ctx) : 0;
        return fetchTexture(cosmetic, ctx, response -> {
            if (animated) {
                return registerAnimatedTexture(response, ctx.texturePath, ratio, state, frameDelay);
            }
            ResourceLocation location = ResourceLocation.create("labymod", ctx.texturePath + ".png");
            return registerTexture(response, location, state);
        });
    }

    private int fetchFrameDelay(Cosmetic cosmetic, TextureContext ctx) {
        return ((Integer) this.textureService.getTextureMeta(cosmetic, ctx.options, ctx.playerUuid).fold((v0) -> {
            return v0.getFrameDelay();
        }, error -> {
            return 50;
        })).intValue();
    }

    private CompletableResourceLocation loadMojangBoundTexture(Player player, @Nullable TextureDetails skinTextureDetails) {
        UUID textureUuid = skinTextureDetails == null ? null : skinTextureDetails.getUuid();
        if (textureUuid == null) {
            CompletableResourceLocation completable = new CompletableResourceLocation(null);
            completable.executeCompletableListeners(player.skinTexture());
            return completable;
        }
        return this.mojangTextureService.getTexture(textureUuid, MojangTextureType.SKIN);
    }

    @Nullable
    private CompletableResourceLocation loadTypeAndMojangTexture(Cosmetic cosmetic, CosmeticState state, ItemMetadata metadata, Player player) {
        TextureContext ctx = buildTextureContext(cosmetic, metadata, player);
        if (ctx == null) {
            return null;
        }
        TextureDetails skinTexture = metadata.getSkinTexture();
        UUID skinUuid = skinTexture == null ? ctx.playerUuid : skinTexture.getUuid();
        return fetchTexture(cosmetic, ctx, response -> {
            return registerAndComposite(response, ctx.texturePath, skinUuid);
        });
    }

    @Nullable
    private TextureContext buildTextureContext(Cosmetic cosmetic, ItemMetadata metadata, Player player) {
        UUID playerUuid = player.getUniqueId();
        UUID textureUuid = getTextureUuid(metadata, player);
        CosmeticOptions options = buildOptions(textureUuid);
        if (options == null) {
            return null;
        }
        String texturePath = "remote/cosmetic/" + cosmetic.id() + "/" + String.valueOf(textureUuid != null ? textureUuid : playerUuid);
        return new TextureContext(playerUuid, options, texturePath);
    }

    @Nullable
    private CompletableResourceLocation fetchTexture(Cosmetic cosmetic, TextureContext ctx, Function<StreamingHttpResponse, CompletableResourceLocation> onSuccess) {
        Result texture = this.textureService.getTexture(cosmetic, ctx.options, ctx.playerUuid);
        Objects.requireNonNull(onSuccess);
        return (CompletableResourceLocation) texture.fold((v1) -> {
            return r1.apply(v1);
        }, error -> {
            LOGGER.warn("Failed to load texture for cosmetic {}: {}", Integer.valueOf(cosmetic.id()), error.message());
            return null;
        });
    }

    @Nullable
    private CompletableResourceLocation registerAndComposite(StreamingHttpResponse response, String texturePath, UUID skinUuid) {
        try {
            try {
                byte[] imageData = response.body().readAllBytes();
                if (response != null) {
                    response.close();
                }
                try {
                    GameImage baseImage = GameImage.IMAGE_PROVIDER.getImage(new ByteArrayInputStream(imageData));
                    CompletableResourceLocation result = new CompletableResourceLocation(null);
                    String combinedPath = texturePath + "_" + String.valueOf(skinUuid) + "_combined.png";
                    ResourceLocation destination = ResourceLocation.create("labymod", combinedPath);
                    CompletableResourceLocation skinLocation = this.mojangTextureService.getTexture(skinUuid, MojangTextureType.SKIN);
                    Runnable compositeAction = () -> {
                        compositeSkinOnto(baseImage, skinLocation, result, destination);
                    };
                    skinLocation.addCompletableListener(compositeAction);
                    return result;
                } catch (Exception e) {
                    LOGGER.warn("Failed to decode base texture: {}", e.getMessage());
                    return null;
                }
            } finally {
            }
        } catch (Exception e2) {
            LOGGER.warn("Failed to read texture data: {}", e2.getMessage());
            return null;
        }
    }

    private void compositeSkinOnto(GameImage baseImage, CompletableResourceLocation skinLocation, CompletableResourceLocation result, ResourceLocation destination) {
        Runnable action = () -> {
            try {
                ResourceLocation skinResourceLocation = skinLocation.getCompleted();
                GameImage skinImage = resolveSkinImage(skinResourceLocation);
                if (skinImage == null) {
                    return;
                }
                compositeImages(baseImage, skinImage, destination);
                result.executeCompletableListeners(destination);
                baseImage.close();
            } finally {
                baseImage.close();
            }
        };
        if (ThreadSafe.isRenderThread()) {
            action.run();
        } else {
            ThreadSafe.executeOnRenderThread(action);
        }
    }

    @Nullable
    private GameImage resolveSkinImage(ResourceLocation location) {
        GameImage image = getUsableImage(location);
        if (image != null) {
            return image;
        }
        if (Namespaces.HDSKINS.equals(location.getNamespace())) {
            ResourceLocation fallback = ResourceLocation.create(Namespaces.MINECRAFT, location.getPath());
            return getUsableImage(fallback);
        }
        return null;
    }

    @Nullable
    private GameImage getUsableImage(ResourceLocation location) {
        try {
            GameImage image = this.textureRepository.getImageFromTexture(location);
            if (image != null) {
                if (!image.isFreed()) {
                    return image;
                }
            }
            return null;
        } catch (Exception e) {
            LOGGER.warn("Failed to load skin texture: {}", location, e);
            return null;
        }
    }

    private void compositeImages(GameImage baseImage, GameImage skinImage, ResourceLocation destination) {
        GameImage scaledBase = null;
        GameImage scaledSkin = null;
        try {
            int requiredWidth = skinImage.getWidth() * 2;
            int requiredHeight = skinImage.getHeight() * 2;
            if (requiredWidth > baseImage.getWidth() || requiredHeight > baseImage.getHeight()) {
                scaledBase = baseImage.scale(requiredWidth, requiredHeight);
                baseImage = scaledBase;
            }
            int targetWidth = baseImage.getWidth() / 2;
            int targetHeight = baseImage.getHeight() / 2;
            if (skinImage.getWidth() != targetWidth || skinImage.getHeight() != targetHeight) {
                scaledSkin = skinImage.scale(targetWidth, targetHeight);
                skinImage = scaledSkin;
            }
            baseImage.drawImage(skinImage, 0, 0, 0, 0, skinImage.getWidth(), skinImage.getHeight());
            baseImage.uploadTextureAt(destination);
            if (scaledBase != null) {
                scaledBase.close();
            }
            if (scaledSkin != null) {
                scaledSkin.close();
            }
        } catch (Throwable th) {
            if (scaledBase != null) {
                scaledBase.close();
            }
            if (scaledSkin != null) {
                scaledSkin.close();
            }
            throw th;
        }
    }

    @Nullable
    private CompletableResourceLocation registerTexture(StreamingHttpResponse response, ResourceLocation location, CosmeticState state) {
        try {
            try {
                byte[] imageData = response.body().readAllBytes();
                net.labymod.api.client.resources.texture.TextureDetails details = net.labymod.api.client.resources.texture.TextureDetails.builder(location).withImageData(imageData).build();
                if (this.uploadThrottler != null) {
                    CompletableResourceLocation completable = new CompletableResourceLocation(null);
                    this.uploadThrottler.queueUpload(details, this.textureRepository, result -> {
                        result.addCompletableListener(() -> {
                            completable.executeCompletableListeners(result.getCompleted());
                        });
                    });
                    if (response != null) {
                        response.close();
                    }
                    return completable;
                }
                CompletableResourceLocation orRegisterTexture = this.textureRepository.getOrRegisterTexture(details);
                if (response != null) {
                    response.close();
                }
                return orRegisterTexture;
            } finally {
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to read texture data: {}", e.getMessage());
            return null;
        }
    }

    @Nullable
    private CompletableResourceLocation registerAnimatedTexture(StreamingHttpResponse response, String texturePath, FrameAspectRatio ratio, CosmeticState state, int frameDelay) {
        try {
            try {
                byte[] imageData = response.body().readAllBytes();
                AnimatedResourceLocation animatedLocation = this.resources.resourceLocationFactory().createAnimated("labymod", texturePath, new ByteArrayInputStream(imageData), ratio.width(), ratio.height(), frameDelay);
                state.setAnimatedTextureLocation(animatedLocation);
                CompletableResourceLocation completable = new CompletableResourceLocation(null);
                completable.executeCompletableListeners(animatedLocation.getCurrentResourceLocation());
                if (response != null) {
                    response.close();
                }
                return completable;
            } finally {
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to load animated texture: {}", e.getMessage());
            return null;
        }
    }

    @Nullable
    private UUID getTextureUuid(ItemMetadata metadata, Player player) {
        if (metadata.isUserBound()) {
            return player.getUniqueId();
        }
        TextureDetails cosmeticTexture = metadata.getCosmeticTexture();
        if (cosmeticTexture != null) {
            return cosmeticTexture.getUuid();
        }
        return null;
    }

    @Nullable
    private CosmeticOptions buildOptions(@Nullable UUID textureUuid) {
        if (textureUuid == null) {
            return (CosmeticOptions) CosmeticOptions.parse(List.of(), List.of()).fold(options -> {
                return options;
            }, error -> {
                return null;
            });
        }
        return (CosmeticOptions) CosmeticOptions.parse(List.of(ItemMetadata.TEXTURE_KEY), List.of(textureUuid.toString())).fold(options2 -> {
            return options2;
        }, error2 -> {
            return null;
        });
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/texture/CosmeticTextureService$TextureContext.class */
    private static final class TextureContext extends Record {
        private final UUID playerUuid;
        private final CosmeticOptions options;
        private final String texturePath;

        private TextureContext(UUID playerUuid, CosmeticOptions options, String texturePath) {
            this.playerUuid = playerUuid;
            this.options = options;
            this.texturePath = texturePath;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TextureContext.class), TextureContext.class, "playerUuid;options;texturePath", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/CosmeticTextureService$TextureContext;->playerUuid:Ljava/util/UUID;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/CosmeticTextureService$TextureContext;->options:Lnet/laby/lib/cosmetics/CosmeticOptions;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/CosmeticTextureService$TextureContext;->texturePath:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TextureContext.class), TextureContext.class, "playerUuid;options;texturePath", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/CosmeticTextureService$TextureContext;->playerUuid:Ljava/util/UUID;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/CosmeticTextureService$TextureContext;->options:Lnet/laby/lib/cosmetics/CosmeticOptions;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/CosmeticTextureService$TextureContext;->texturePath:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TextureContext.class, Object.class), TextureContext.class, "playerUuid;options;texturePath", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/CosmeticTextureService$TextureContext;->playerUuid:Ljava/util/UUID;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/CosmeticTextureService$TextureContext;->options:Lnet/laby/lib/cosmetics/CosmeticOptions;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/CosmeticTextureService$TextureContext;->texturePath:Ljava/lang/String;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public UUID playerUuid() {
            return this.playerUuid;
        }

        public CosmeticOptions options() {
            return this.options;
        }

        public String texturePath() {
            return this.texturePath;
        }
    }
}
