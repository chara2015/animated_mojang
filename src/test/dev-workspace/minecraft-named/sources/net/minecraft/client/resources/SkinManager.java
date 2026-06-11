package net.minecraft.client.resources;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.SignatureState;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTextures;
import com.mojang.authlib.properties.Property;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import net.minecraft.SharedConstants;
import net.minecraft.client.renderer.texture.SkinTextureDownloader;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Services;
import net.minecraft.util.Util;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.entity.player.PlayerSkin;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/SkinManager.class */
public class SkinManager {
    static final Logger LOGGER = LogUtils.getLogger();
    private final Services services;
    final SkinTextureDownloader skinTextureDownloader;
    private final LoadingCache<CacheKey, CompletableFuture<Optional<PlayerSkin>>> skinCache;
    private final TextureCache skinTextures;
    private final TextureCache capeTextures;
    private final TextureCache elytraTextures;

    public SkinManager(Path $$0, final Services $$1, SkinTextureDownloader $$2, final Executor $$3) {
        this.services = $$1;
        this.skinTextureDownloader = $$2;
        this.skinTextures = new TextureCache($$0, MinecraftProfileTexture.Type.SKIN);
        this.capeTextures = new TextureCache($$0, MinecraftProfileTexture.Type.CAPE);
        this.elytraTextures = new TextureCache($$0, MinecraftProfileTexture.Type.ELYTRA);
        this.skinCache = CacheBuilder.newBuilder().expireAfterAccess(Duration.ofSeconds(15L)).build(new CacheLoader<CacheKey, CompletableFuture<Optional<PlayerSkin>>>() { // from class: net.minecraft.client.resources.SkinManager.1
            public CompletableFuture<Optional<PlayerSkin>> load(CacheKey $$02) {
                Services services = $$1;
                return CompletableFuture.supplyAsync(() -> {
                    Property $$22 = $$02.packedTextures();
                    if ($$22 == null) {
                        return MinecraftProfileTextures.EMPTY;
                    }
                    MinecraftProfileTextures $$32 = services.sessionService().unpackTextures($$22);
                    if ($$32.signatureState() == SignatureState.INVALID) {
                        SkinManager.LOGGER.warn("Profile contained invalid signature for textures property (profile id: {})", $$02.profileId());
                    }
                    return $$32;
                }, Util.backgroundExecutor().forName("unpackSkinTextures")).thenComposeAsync($$12 -> {
                    return SkinManager.this.registerTextures($$02.profileId(), $$12);
                }, $$3).handle(($$13, $$22) -> {
                    if ($$22 != null) {
                        SkinManager.LOGGER.warn("Failed to load texture for profile {}", $$02.profileId, $$22);
                    }
                    return Optional.ofNullable($$13);
                });
            }
        });
    }

    public Supplier<PlayerSkin> createLookup(GameProfile $$0, boolean $$1) {
        CompletableFuture<Optional<PlayerSkin>> $$2 = get($$0);
        PlayerSkin $$3 = DefaultPlayerSkin.get($$0);
        if (SharedConstants.DEBUG_DEFAULT_SKIN_OVERRIDE) {
            return () -> {
                return $$3;
            };
        }
        Optional<PlayerSkin> $$4 = $$2.getNow(null);
        if ($$4 != null) {
            PlayerSkin $$5 = $$4.filter($$12 -> {
                return !$$1 || $$12.secure();
            }).orElse($$3);
            return () -> {
                return $$5;
            };
        }
        return () -> {
            return (PlayerSkin) ((Optional) $$2.getNow(Optional.empty())).filter($$13 -> {
                return !$$1 || $$13.secure();
            }).orElse($$3);
        };
    }

    public CompletableFuture<Optional<PlayerSkin>> get(GameProfile $$0) {
        if (SharedConstants.DEBUG_DEFAULT_SKIN_OVERRIDE) {
            PlayerSkin $$1 = DefaultPlayerSkin.get($$0);
            return CompletableFuture.completedFuture(Optional.of($$1));
        }
        Property $$2 = this.services.sessionService().getPackedTextures($$0);
        return (CompletableFuture) this.skinCache.getUnchecked(new CacheKey($$0.id(), $$2));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    CompletableFuture<PlayerSkin> registerTextures(UUID $$0, MinecraftProfileTextures $$1) throws MatchException {
        CompletableFuture<ClientAsset.Texture> $$6;
        PlayerModelType $$7;
        MinecraftProfileTexture $$2 = $$1.skin();
        if ($$2 != null) {
            $$6 = this.skinTextures.getOrLoad($$2);
            $$7 = PlayerModelType.byLegacyServicesName($$2.getMetadata("model"));
        } else {
            PlayerSkin $$5 = DefaultPlayerSkin.get($$0);
            $$6 = CompletableFuture.completedFuture($$5.body());
            $$7 = $$5.model();
        }
        MinecraftProfileTexture $$8 = $$1.cape();
        CompletableFuture<ClientAsset.Texture> $$9 = $$8 != null ? this.capeTextures.getOrLoad($$8) : CompletableFuture.completedFuture(null);
        MinecraftProfileTexture $$10 = $$1.elytra();
        CompletableFuture<ClientAsset.Texture> $$11 = $$10 != null ? this.elytraTextures.getOrLoad($$10) : CompletableFuture.completedFuture(null);
        CompletableFuture<ClientAsset.Texture> completableFuture = $$6;
        PlayerModelType playerModelType = $$7;
        return CompletableFuture.allOf($$6, $$9, $$11).thenApply($$52 -> {
            return new PlayerSkin((ClientAsset.Texture) completableFuture.join(), (ClientAsset.Texture) $$9.join(), (ClientAsset.Texture) $$11.join(), playerModelType, $$1.signatureState() == SignatureState.SIGNED);
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/SkinManager$TextureCache.class */
    class TextureCache {
        private final Path root;
        private final MinecraftProfileTexture.Type type;
        private final Map<String, CompletableFuture<ClientAsset.Texture>> textures = new Object2ObjectOpenHashMap();

        TextureCache(Path $$0, MinecraftProfileTexture.Type $$1) {
            this.root = $$0;
            this.type = $$1;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        public CompletableFuture<ClientAsset.Texture> getOrLoad(MinecraftProfileTexture $$0) throws MatchException {
            String $$1 = $$0.getHash();
            CompletableFuture<ClientAsset.Texture> $$2 = this.textures.get($$1);
            if ($$2 == null) {
                $$2 = registerTexture($$0);
                this.textures.put($$1, $$2);
            }
            return $$2;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        private CompletableFuture<ClientAsset.Texture> registerTexture(MinecraftProfileTexture $$0) throws MatchException {
            String $$1 = Hashing.sha1().hashUnencodedChars($$0.getHash()).toString();
            Identifier $$2 = getTextureLocation($$1);
            Path $$3 = this.root.resolve($$1.length() > 2 ? $$1.substring(0, 2) : "xx").resolve($$1);
            return SkinManager.this.skinTextureDownloader.downloadAndRegisterSkin($$2, $$3, $$0.getUrl(), this.type == MinecraftProfileTexture.Type.SKIN);
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        private Identifier getTextureLocation(String $$0) throws MatchException {
            String str;
            switch (AnonymousClass2.$SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type[this.type.ordinal()]) {
                case 1:
                    str = "skins";
                    break;
                case 2:
                    str = "capes";
                    break;
                case 3:
                    str = "elytra";
                    break;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
            String $$1 = str;
            return Identifier.withDefaultNamespace($$1 + "/" + $$0);
        }
    }

    /* JADX INFO: renamed from: net.minecraft.client.resources.SkinManager$2, reason: invalid class name */
    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/SkinManager$2.class */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type = new int[MinecraftProfileTexture.Type.values().length];

        static {
            try {
                $SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type[MinecraftProfileTexture.Type.SKIN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type[MinecraftProfileTexture.Type.CAPE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type[MinecraftProfileTexture.Type.ELYTRA.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/SkinManager$CacheKey.class */
    static final class CacheKey extends Record {
        private final UUID profileId;
        private final Property packedTextures;

        CacheKey(UUID $$0, Property $$1) {
            this.profileId = $$0;
            this.packedTextures = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CacheKey.class), CacheKey.class, "profileId;packedTextures", "FIELD:Lnet/minecraft/client/resources/SkinManager$CacheKey;->profileId:Ljava/util/UUID;", "FIELD:Lnet/minecraft/client/resources/SkinManager$CacheKey;->packedTextures:Lcom/mojang/authlib/properties/Property;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CacheKey.class), CacheKey.class, "profileId;packedTextures", "FIELD:Lnet/minecraft/client/resources/SkinManager$CacheKey;->profileId:Ljava/util/UUID;", "FIELD:Lnet/minecraft/client/resources/SkinManager$CacheKey;->packedTextures:Lcom/mojang/authlib/properties/Property;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CacheKey.class, Object.class), CacheKey.class, "profileId;packedTextures", "FIELD:Lnet/minecraft/client/resources/SkinManager$CacheKey;->profileId:Ljava/util/UUID;", "FIELD:Lnet/minecraft/client/resources/SkinManager$CacheKey;->packedTextures:Lcom/mojang/authlib/properties/Property;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public UUID profileId() {
            return this.profileId;
        }

        public Property packedTextures() {
            return this.packedTextures;
        }
    }
}
