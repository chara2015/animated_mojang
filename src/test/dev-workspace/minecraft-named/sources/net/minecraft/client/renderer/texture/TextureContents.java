package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/TextureContents.class */
public final class TextureContents extends Record implements Closeable {
    private final NativeImage image;
    private final TextureMetadataSection metadata;

    public TextureContents(NativeImage $$0, TextureMetadataSection $$1) {
        this.image = $$0;
        this.metadata = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TextureContents.class), TextureContents.class, "image;metadata", "FIELD:Lnet/minecraft/client/renderer/texture/TextureContents;->image:Lcom/mojang/blaze3d/platform/NativeImage;", "FIELD:Lnet/minecraft/client/renderer/texture/TextureContents;->metadata:Lnet/minecraft/client/resources/metadata/texture/TextureMetadataSection;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TextureContents.class), TextureContents.class, "image;metadata", "FIELD:Lnet/minecraft/client/renderer/texture/TextureContents;->image:Lcom/mojang/blaze3d/platform/NativeImage;", "FIELD:Lnet/minecraft/client/renderer/texture/TextureContents;->metadata:Lnet/minecraft/client/resources/metadata/texture/TextureMetadataSection;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TextureContents.class, Object.class), TextureContents.class, "image;metadata", "FIELD:Lnet/minecraft/client/renderer/texture/TextureContents;->image:Lcom/mojang/blaze3d/platform/NativeImage;", "FIELD:Lnet/minecraft/client/renderer/texture/TextureContents;->metadata:Lnet/minecraft/client/resources/metadata/texture/TextureMetadataSection;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public NativeImage image() {
        return this.image;
    }

    public TextureMetadataSection metadata() {
        return this.metadata;
    }

    public static TextureContents load(ResourceManager $$0, Identifier $$1) throws IOException {
        Resource $$2 = $$0.getResourceOrThrow($$1);
        InputStream $$3 = $$2.open();
        try {
            NativeImage $$5 = NativeImage.read($$3);
            if ($$3 != null) {
                $$3.close();
            }
            TextureMetadataSection $$6 = (TextureMetadataSection) $$2.metadata().getSection(TextureMetadataSection.TYPE).orElse(null);
            return new TextureContents($$5, $$6);
        } catch (Throwable th) {
            if ($$3 != null) {
                try {
                    $$3.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static TextureContents createMissing() {
        return new TextureContents(MissingTextureAtlasSprite.generateMissingImage(), null);
    }

    public boolean blur() {
        if (this.metadata != null) {
            return this.metadata.blur();
        }
        return false;
    }

    public boolean clamp() {
        if (this.metadata != null) {
            return this.metadata.clamp();
        }
        return false;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.image.close();
    }
}
