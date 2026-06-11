package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import net.labymod.laby3d.api.rhi.pipeline.PipelineLayout;
import net.labymod.laby3d.api.rhi.pipeline.PipelineLayoutDescriptor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanPipelineLayout.class */
class VulkanPipelineLayout implements PipelineLayout {
    private final PipelineLayoutDescriptor descriptor;

    VulkanPipelineLayout(PipelineLayoutDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    PipelineLayoutDescriptor descriptor() {
        return this.descriptor;
    }

    public void close() {
    }
}
