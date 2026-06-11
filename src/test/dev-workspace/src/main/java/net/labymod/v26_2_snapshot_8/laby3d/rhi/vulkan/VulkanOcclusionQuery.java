package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import net.labymod.laby3d.api.rhi.sync.OcclusionQuery;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanOcclusionQuery.class */
class VulkanOcclusionQuery implements OcclusionQuery {
    VulkanOcclusionQuery() {
    }

    public boolean isResultAvailable() {
        throw new UnsupportedOperationException("Occlusion queries not exposed by Mojang's 26.2 GpuDevice (only timestamp queries)");
    }

    public long result() {
        throw new UnsupportedOperationException("Occlusion queries not exposed by Mojang's 26.2 GpuDevice (only timestamp queries)");
    }

    public void close() {
    }
}
