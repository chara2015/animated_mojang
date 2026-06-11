package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.textures.GpuSampler;
import net.labymod.laby3d.api.rhi.texture.Sampler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanSampler.class */
class VulkanSampler implements Sampler {
    private final GpuSampler mcSampler;
    private boolean closed;

    VulkanSampler(GpuSampler mcSampler) {
        this.mcSampler = mcSampler;
    }

    GpuSampler mcSampler() {
        return this.mcSampler;
    }

    public void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.mcSampler.close();
    }
}
