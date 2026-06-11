package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.buffers.GpuFence;
import net.labymod.laby3d.api.rhi.sync.Fence;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanFence.class */
class VulkanFence implements Fence {
    private final GpuFence mcFence;
    private boolean closed;

    VulkanFence(GpuFence mcFence) {
        this.mcFence = mcFence;
    }

    public boolean await(long timeoutNanos) {
        long timeoutMs = Math.max(0L, timeoutNanos / 1000000);
        return this.mcFence.awaitCompletion(timeoutMs);
    }

    public boolean isSignaled() {
        return this.mcFence.awaitCompletion(0L);
    }

    public void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.mcFence.close();
    }
}
