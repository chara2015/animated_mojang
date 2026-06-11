package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/AbstractTexture.class */
public abstract class AbstractTexture implements AutoCloseable {
    protected GpuTexture texture;
    protected GpuTextureView textureView;
    protected GpuSampler sampler = RenderSystem.getSamplerCache().getSampler(AddressMode.REPEAT, AddressMode.REPEAT, FilterMode.NEAREST, FilterMode.LINEAR, false);

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.texture != null) {
            this.texture.close();
            this.texture = null;
        }
        if (this.textureView != null) {
            this.textureView.close();
            this.textureView = null;
        }
    }

    public GpuTexture getTexture() {
        if (this.texture == null) {
            throw new IllegalStateException("Texture does not exist, can't get it before something initializes it");
        }
        return this.texture;
    }

    public GpuTextureView getTextureView() {
        if (this.textureView == null) {
            throw new IllegalStateException("Texture view does not exist, can't get it before something initializes it");
        }
        return this.textureView;
    }

    public GpuSampler getSampler() {
        return this.sampler;
    }
}
