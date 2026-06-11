package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.textures.GpuTextureView;
import net.labymod.laby3d.api.rhi.texture.TextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanTextureView.class */
class VulkanTextureView implements TextureView {
    private final GpuTextureView mcView;
    private boolean closed;

    VulkanTextureView(GpuTextureView mcView) {
        this.mcView = mcView;
    }

    GpuTextureView mcView() {
        return this.mcView;
    }

    public void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.mcView.close();
    }
}
