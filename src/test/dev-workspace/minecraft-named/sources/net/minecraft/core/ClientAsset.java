package net.minecraft.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/ClientAsset.class */
public interface ClientAsset {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/ClientAsset$Texture.class */
    public interface Texture extends ClientAsset {
        Identifier texturePath();
    }

    Identifier id();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/ClientAsset$ResourceTexture.class */
    public static final class ResourceTexture extends Record implements Texture {
        private final Identifier id;
        private final Identifier texturePath;
        public static final Codec<ResourceTexture> CODEC = Identifier.CODEC.xmap(ResourceTexture::new, (v0) -> {
            return v0.id();
        });
        public static final MapCodec<ResourceTexture> DEFAULT_FIELD_CODEC = CODEC.fieldOf("asset_id");
        public static final StreamCodec<ByteBuf, ResourceTexture> STREAM_CODEC = Identifier.STREAM_CODEC.map(ResourceTexture::new, (v0) -> {
            return v0.id();
        });

        public ResourceTexture(Identifier $$0, Identifier $$1) {
            this.id = $$0;
            this.texturePath = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ResourceTexture.class), ResourceTexture.class, "id;texturePath", "FIELD:Lnet/minecraft/core/ClientAsset$ResourceTexture;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/core/ClientAsset$ResourceTexture;->texturePath:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ResourceTexture.class), ResourceTexture.class, "id;texturePath", "FIELD:Lnet/minecraft/core/ClientAsset$ResourceTexture;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/core/ClientAsset$ResourceTexture;->texturePath:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ResourceTexture.class, Object.class), ResourceTexture.class, "id;texturePath", "FIELD:Lnet/minecraft/core/ClientAsset$ResourceTexture;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/core/ClientAsset$ResourceTexture;->texturePath:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.core.ClientAsset
        public Identifier id() {
            return this.id;
        }

        @Override // net.minecraft.core.ClientAsset.Texture
        public Identifier texturePath() {
            return this.texturePath;
        }

        public ResourceTexture(Identifier $$0) {
            this($$0, $$0.withPath($$02 -> {
                return "textures/" + $$02 + ".png";
            }));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/ClientAsset$DownloadedTexture.class */
    public static final class DownloadedTexture extends Record implements Texture {
        private final Identifier texturePath;
        private final String url;

        public DownloadedTexture(Identifier $$0, String $$1) {
            this.texturePath = $$0;
            this.url = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DownloadedTexture.class), DownloadedTexture.class, "texturePath;url", "FIELD:Lnet/minecraft/core/ClientAsset$DownloadedTexture;->texturePath:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/core/ClientAsset$DownloadedTexture;->url:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DownloadedTexture.class), DownloadedTexture.class, "texturePath;url", "FIELD:Lnet/minecraft/core/ClientAsset$DownloadedTexture;->texturePath:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/core/ClientAsset$DownloadedTexture;->url:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DownloadedTexture.class, Object.class), DownloadedTexture.class, "texturePath;url", "FIELD:Lnet/minecraft/core/ClientAsset$DownloadedTexture;->texturePath:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/core/ClientAsset$DownloadedTexture;->url:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.core.ClientAsset.Texture
        public Identifier texturePath() {
            return this.texturePath;
        }

        public String url() {
            return this.url;
        }

        @Override // net.minecraft.core.ClientAsset
        public Identifier id() {
            return this.texturePath;
        }
    }
}
