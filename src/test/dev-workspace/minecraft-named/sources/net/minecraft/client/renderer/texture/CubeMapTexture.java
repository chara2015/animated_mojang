package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.TextureFormat;
import java.io.IOException;
import java.util.Objects;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/CubeMapTexture.class */
public class CubeMapTexture extends ReloadableTexture {
    private static final String[] SUFFIXES = {"_1.png", "_3.png", "_5.png", "_4.png", "_0.png", "_2.png"};

    public CubeMapTexture(Identifier $$0) {
        super($$0);
    }

    @Override // net.minecraft.client.renderer.texture.ReloadableTexture
    public TextureContents loadContents(ResourceManager $$0) throws IOException {
        Identifier $$1 = resourceId();
        TextureContents $$2 = TextureContents.load($$0, $$1.withSuffix(SUFFIXES[0]));
        try {
            int $$3 = $$2.image().getWidth();
            int $$4 = $$2.image().getHeight();
            NativeImage $$5 = new NativeImage($$3, $$4 * 6, false);
            $$2.image().copyRect($$5, 0, 0, 0, 0, $$3, $$4, false, true);
            for (int $$6 = 1; $$6 < 6; $$6++) {
                TextureContents $$7 = TextureContents.load($$0, $$1.withSuffix(SUFFIXES[$$6]));
                try {
                    if ($$7.image().getWidth() != $$3 || $$7.image().getHeight() != $$4) {
                        throw new IOException("Image dimensions of cubemap '" + String.valueOf($$1) + "' sides do not match: part 0 is " + $$3 + "x" + $$4 + ", but part " + $$6 + " is " + $$7.image().getWidth() + "x" + $$7.image().getHeight());
                    }
                    $$7.image().copyRect($$5, 0, 0, 0, $$6 * $$4, $$3, $$4, false, true);
                    if ($$7 != null) {
                        $$7.close();
                    }
                } catch (Throwable th) {
                    if ($$7 != null) {
                        try {
                            $$7.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            TextureContents textureContents = new TextureContents($$5, new TextureMetadataSection(true, false, MipmapStrategy.MEAN, 0.0f));
            if ($$2 != null) {
                $$2.close();
            }
            return textureContents;
        } catch (Throwable th3) {
            if ($$2 != null) {
                try {
                    $$2.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
            }
            throw th3;
        }
    }

    @Override // net.minecraft.client.renderer.texture.ReloadableTexture
    protected void doLoad(NativeImage $$0) {
        GpuDevice $$1 = RenderSystem.getDevice();
        int $$2 = $$0.getWidth();
        int $$3 = $$0.getHeight() / 6;
        close();
        Identifier identifierResourceId = resourceId();
        Objects.requireNonNull(identifierResourceId);
        this.texture = $$1.createTexture(identifierResourceId::toString, 21, TextureFormat.RGBA8, $$2, $$3, 6, 1);
        this.textureView = $$1.createTextureView(this.texture);
        for (int $$4 = 0; $$4 < 6; $$4++) {
            $$1.createCommandEncoder().writeToTexture(this.texture, $$0, 0, $$4, 0, 0, $$2, $$3, 0, $$3 * $$4);
        }
    }
}
