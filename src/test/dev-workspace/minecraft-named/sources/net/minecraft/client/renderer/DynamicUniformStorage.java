package net.minecraft.client.renderer;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.DynamicUniformStorage.DynamicUniform;
import net.minecraft.util.Mth;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/DynamicUniformStorage.class */
public class DynamicUniformStorage<T extends DynamicUniform> implements AutoCloseable {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final List<MappableRingBuffer> oldBuffers = new ArrayList();
    private final int blockSize;
    private MappableRingBuffer ringBuffer;
    private int nextBlock;
    private int capacity;
    private T lastUniform;
    private final String label;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/DynamicUniformStorage$DynamicUniform.class */
    public interface DynamicUniform {
        void write(ByteBuffer byteBuffer);
    }

    public DynamicUniformStorage(String $$0, int $$1, int $$2) {
        GpuDevice $$3 = RenderSystem.getDevice();
        this.blockSize = Mth.roundToward($$1, $$3.getUniformOffsetAlignment());
        this.capacity = Mth.smallestEncompassingPowerOfTwo($$2);
        this.nextBlock = 0;
        this.ringBuffer = new MappableRingBuffer(() -> {
            return $$0 + " x" + this.blockSize;
        }, 130, this.blockSize * this.capacity);
        this.label = $$0;
    }

    public void endFrame() {
        this.nextBlock = 0;
        this.lastUniform = null;
        this.ringBuffer.rotate();
        if (!this.oldBuffers.isEmpty()) {
            for (MappableRingBuffer $$0 : this.oldBuffers) {
                $$0.close();
            }
            this.oldBuffers.clear();
        }
    }

    private void resizeBuffers(int $$0) {
        this.capacity = $$0;
        this.nextBlock = 0;
        this.lastUniform = null;
        this.oldBuffers.add(this.ringBuffer);
        this.ringBuffer = new MappableRingBuffer(() -> {
            return this.label + " x" + this.blockSize;
        }, 130, this.blockSize * this.capacity);
    }

    public GpuBufferSlice writeUniform(T $$0) {
        if (this.lastUniform != null && this.lastUniform.equals($$0)) {
            return this.ringBuffer.currentBuffer().slice((this.nextBlock - 1) * this.blockSize, this.blockSize);
        }
        if (this.nextBlock >= this.capacity) {
            int $$1 = this.capacity * 2;
            LOGGER.info("Resizing {}, capacity limit of {} reached during a single frame. New capacity will be {}.", new Object[]{this.label, Integer.valueOf(this.capacity), Integer.valueOf($$1)});
            resizeBuffers($$1);
        }
        int $$2 = this.nextBlock * this.blockSize;
        GpuBuffer.MappedView $$3 = RenderSystem.getDevice().createCommandEncoder().mapBuffer(this.ringBuffer.currentBuffer().slice($$2, this.blockSize), false, true);
        try {
            $$0.write($$3.data());
            if ($$3 != null) {
                $$3.close();
            }
            this.nextBlock++;
            this.lastUniform = $$0;
            return this.ringBuffer.currentBuffer().slice($$2, this.blockSize);
        } catch (Throwable th) {
            if ($$3 != null) {
                try {
                    $$3.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public GpuBufferSlice[] writeUniforms(T[] $$0) {
        if ($$0.length == 0) {
            return new GpuBufferSlice[0];
        }
        if (this.nextBlock + $$0.length > this.capacity) {
            int $$1 = Mth.smallestEncompassingPowerOfTwo(Math.max(this.capacity + 1, $$0.length));
            LOGGER.info("Resizing {}, capacity limit of {} reached during a single frame. New capacity will be {}.", new Object[]{this.label, Integer.valueOf(this.capacity), Integer.valueOf($$1)});
            resizeBuffers($$1);
        }
        int $$2 = this.nextBlock * this.blockSize;
        GpuBufferSlice[] $$3 = new GpuBufferSlice[$$0.length];
        GpuBuffer.MappedView $$4 = RenderSystem.getDevice().createCommandEncoder().mapBuffer(this.ringBuffer.currentBuffer().slice($$2, $$0.length * this.blockSize), false, true);
        try {
            ByteBuffer $$5 = $$4.data();
            for (int $$6 = 0; $$6 < $$0.length; $$6++) {
                T $$7 = $$0[$$6];
                $$3[$$6] = this.ringBuffer.currentBuffer().slice($$2 + ($$6 * this.blockSize), this.blockSize);
                $$5.position($$6 * this.blockSize);
                $$7.write($$5);
            }
            if ($$4 != null) {
                $$4.close();
            }
            this.nextBlock += $$0.length;
            this.lastUniform = $$0[$$0.length - 1];
            return $$3;
        } catch (Throwable th) {
            if ($$4 != null) {
                try {
                    $$4.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        for (MappableRingBuffer $$0 : this.oldBuffers) {
            $$0.close();
        }
        this.ringBuffer.close();
    }
}
