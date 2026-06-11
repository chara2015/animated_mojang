package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.systems.CommandEncoder;
import net.labymod.laby3d.api.rhi.command.CommandBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanCommandBuffer.class */
class VulkanCommandBuffer implements CommandBuffer {
    private final CommandEncoder mcEncoder;

    VulkanCommandBuffer(CommandEncoder mcEncoder) {
        this.mcEncoder = mcEncoder;
    }

    CommandEncoder mcEncoder() {
        return this.mcEncoder;
    }

    public void close() {
    }
}
