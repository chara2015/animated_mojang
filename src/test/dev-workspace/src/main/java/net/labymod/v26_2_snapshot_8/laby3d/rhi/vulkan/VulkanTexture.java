package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.textures.GpuTexture;
import net.labymod.laby3d.api.rhi.texture.Texture;
import net.labymod.laby3d.api.rhi.texture.TextureView;
import net.labymod.laby3d.api.rhi.texture.TextureViewDescriptor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanTexture.class */
class VulkanTexture implements Texture {
    private final GpuDevice gpuDevice;
    private final GpuTexture mcTexture;
    private boolean closed;

    VulkanTexture(GpuDevice gpuDevice, GpuTexture mcTexture) {
        this.gpuDevice = gpuDevice;
        this.mcTexture = mcTexture;
    }

    GpuTexture mcTexture() {
        return this.mcTexture;
    }

    public TextureView createView(TextureViewDescriptor desc) {
        return new VulkanTextureView(this.gpuDevice.createTextureView(this.mcTexture, desc.baseMipLevel(), desc.mipLevelCount()));
    }

    public TextureView createView() {
        return new VulkanTextureView(this.gpuDevice.createTextureView(this.mcTexture));
    }

    public void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.mcTexture.close();
    }
}
