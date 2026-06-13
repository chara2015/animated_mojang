package net.labymod.api.laby3d.buffer;

import java.util.function.Supplier;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.BufferDescription;
import net.labymod.laby3d.api.buffers.BufferType;
import net.labymod.laby3d.api.buffers.BufferUsage;
import net.labymod.laby3d.api.buffers.DeviceBuffer;
import net.labymod.laby3d.api.buffers.MemoryAccess;
import net.labymod.laby3d.api.sync.DeviceFence;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/buffer/SyncedBufferPool.class */
public class SyncedBufferPool implements AutoCloseable {
    private final String name;
    private final RenderDevice renderDevice;
    private final DeviceBuffer[] buffers;
    private final DeviceFence[] fences;
    private final int bufferCount;
    private final int size;
    private int current;

    public SyncedBufferPool(RenderDevice renderDevice, Supplier<String> name, BufferType bufferType, int bufferCount, int size) {
        this.name = name.get();
        this.renderDevice = renderDevice;
        this.buffers = new DeviceBuffer[bufferCount];
        this.fences = new DeviceFence[bufferCount];
        for (int index = 0; index < bufferCount; index++) {
            int id = index;
            this.buffers[index] = renderDevice.createBuffer(this.name + "#" + id, BufferDescription.builder().setType(bufferType).setSize(size).setUsage(BufferUsage.DYNAMIC).setAccess(MemoryAccess.CPU_TO_GPU).build());
            this.fences[index] = null;
        }
        this.bufferCount = bufferCount;
        this.size = size;
    }

    public int size() {
        return this.size;
    }

    public DeviceBuffer acquireBuffer() {
        DeviceFence fence = this.fences[this.current];
        if (fence != null) {
            fence.awaitCompletion(Long.MAX_VALUE);
            fence.close();
            this.fences[this.current] = null;
        }
        return this.buffers[this.current];
    }

    public void rotate() {
        if (this.fences[this.current] != null) {
            this.fences[this.current].close();
        }
        this.fences[this.current] = this.renderDevice.createFence(this.name + "Fence#" + this.current);
        this.current = (this.current + 1) % this.bufferCount;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        for (int index = 0; index < this.bufferCount; index++) {
            this.buffers[index].close();
            if (this.fences[index] != null) {
                this.fences[index].close();
            }
        }
    }
}
