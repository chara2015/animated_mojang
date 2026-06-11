package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import net.labymod.laby3d.api.rhi.Queue;
import net.labymod.laby3d.api.rhi.SurfaceTexture;
import net.labymod.laby3d.api.rhi.command.CommandBuffer;
import net.labymod.laby3d.api.rhi.sync.Fence;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanQueue.class */
class VulkanQueue implements Queue {
    private final VulkanRenderDevice device;

    VulkanQueue(VulkanRenderDevice device) {
        this.device = device;
    }

    public void submit(CommandBuffer... buffers) {
        for (CommandBuffer buffer : buffers) {
            ((VulkanCommandBuffer) buffer).mcEncoder().submit();
        }
    }

    public void present(SurfaceTexture frame) {
        throw new UnsupportedOperationException("Mojang's 26.2 swapchain is blit-based; call VulkanSurface.mcSurface().blitFromTexture(...) + present() directly instead of routing through Queue.present");
    }

    public Fence insertFence() {
        return new VulkanFence(this.device.gpuDevice().createCommandEncoder().createFence());
    }
}
