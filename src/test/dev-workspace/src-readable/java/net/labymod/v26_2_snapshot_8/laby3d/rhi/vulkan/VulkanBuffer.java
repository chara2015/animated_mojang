package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import java.nio.ByteBuffer;
import net.labymod.laby3d.api.rhi.buffer.Buffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanBuffer.class */
class VulkanBuffer implements Buffer {
    private final GpuBuffer mcBuffer;
    private GpuBufferSlice.MappedView currentView;
    private boolean closed;

    VulkanBuffer(GpuBuffer mcBuffer) {
        this.mcBuffer = mcBuffer;
    }

    GpuBuffer mcBuffer() {
        return this.mcBuffer;
    }

    public long size() {
        return this.mcBuffer.size();
    }

    public ByteBuffer mapWrite(long offset, long length) {
        if (this.currentView != null) {
            throw new IllegalStateException("Buffer already mapped");
        }
        this.currentView = this.mcBuffer.map(offset, length, false, true);
        return this.currentView.data();
    }

    public void unmap() {
        if (this.currentView == null) {
            return;
        }
        this.currentView.close();
        this.currentView = null;
    }

    public void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        unmap();
        this.mcBuffer.close();
    }
}
