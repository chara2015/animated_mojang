package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/DynamicTexture.class */
public class DynamicTexture extends AbstractTexture implements Dumpable {
    private static final Logger LOGGER = LogUtils.getLogger();
    private NativeImage pixels;

    public DynamicTexture(Supplier<String> $$0, NativeImage $$1) {
        this.pixels = $$1;
        createTexture($$0);
        upload();
    }

    public DynamicTexture(String $$0, int $$1, int $$2, boolean $$3) {
        this.pixels = new NativeImage($$1, $$2, $$3);
        createTexture($$0);
    }

    public DynamicTexture(Supplier<String> $$0, int $$1, int $$2, boolean $$3) {
        this.pixels = new NativeImage($$1, $$2, $$3);
        createTexture($$0);
    }

    private void createTexture(Supplier<String> $$0) {
        GpuDevice $$1 = RenderSystem.getDevice();
        this.texture = $$1.createTexture($$0, 5, TextureFormat.RGBA8, this.pixels.getWidth(), this.pixels.getHeight(), 1, 1);
        this.sampler = RenderSystem.getSamplerCache().getRepeat(FilterMode.NEAREST);
        this.textureView = $$1.createTextureView(this.texture);
    }

    private void createTexture(String $$0) {
        GpuDevice $$1 = RenderSystem.getDevice();
        this.texture = $$1.createTexture($$0, 5, TextureFormat.RGBA8, this.pixels.getWidth(), this.pixels.getHeight(), 1, 1);
        this.sampler = RenderSystem.getSamplerCache().getRepeat(FilterMode.NEAREST);
        this.textureView = $$1.createTextureView(this.texture);
    }

    public void upload() {
        if (this.pixels != null && this.texture != null) {
            RenderSystem.getDevice().createCommandEncoder().writeToTexture(this.texture, this.pixels);
        } else {
            LOGGER.warn("Trying to upload disposed texture {}", getTexture().getLabel());
        }
    }

    public NativeImage getPixels() {
        return this.pixels;
    }

    public void setPixels(NativeImage $$0) {
        if (this.pixels != null) {
            this.pixels.close();
        }
        this.pixels = $$0;
    }

    @Override // net.minecraft.client.renderer.texture.AbstractTexture, java.lang.AutoCloseable
    public void close() {
        if (this.pixels != null) {
            this.pixels.close();
            this.pixels = null;
        }
        super.close();
    }

    @Override // net.minecraft.client.renderer.texture.Dumpable
    public void dumpContents(Identifier $$0, Path $$1) throws IOException {
        if (this.pixels != null) {
            String $$2 = $$0.toDebugFileName() + ".png";
            Path $$3 = $$1.resolve($$2);
            this.pixels.writeToFile($$3);
        }
    }
}
