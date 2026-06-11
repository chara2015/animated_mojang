package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import net.labymod.laby3d.api.rhi.bind.BindGroup;
import net.labymod.laby3d.api.rhi.bind.BindGroupDescriptor;
import net.labymod.laby3d.api.rhi.bind.BindGroupEntry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanBindGroup.class */
class VulkanBindGroup implements BindGroup {
    private final VulkanBindGroupLayout layout;
    private final BindGroupEntry[] entries;

    VulkanBindGroup(BindGroupDescriptor descriptor) {
        this.layout = (VulkanBindGroupLayout) descriptor.layout();
        this.entries = descriptor.entries();
    }

    VulkanBindGroupLayout layout() {
        return this.layout;
    }

    BindGroupEntry[] entries() {
        return this.entries;
    }

    public void close() {
    }
}
