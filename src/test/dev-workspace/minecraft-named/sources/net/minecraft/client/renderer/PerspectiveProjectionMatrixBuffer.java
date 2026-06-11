package net.minecraft.client.renderer;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/PerspectiveProjectionMatrixBuffer.class */
public class PerspectiveProjectionMatrixBuffer implements AutoCloseable {
    private final GpuBuffer buffer;
    private final GpuBufferSlice bufferSlice;

    public PerspectiveProjectionMatrixBuffer(String $$0) {
        GpuDevice $$1 = RenderSystem.getDevice();
        this.buffer = $$1.createBuffer(() -> {
            return "Projection matrix UBO " + $$0;
        }, 136, RenderSystem.PROJECTION_MATRIX_UBO_SIZE);
        this.bufferSlice = this.buffer.slice(0L, RenderSystem.PROJECTION_MATRIX_UBO_SIZE);
    }

    public GpuBufferSlice getBuffer(Matrix4f $$0) {
        MemoryStack $$1 = MemoryStack.stackPush();
        try {
            ByteBuffer $$2 = Std140Builder.onStack($$1, RenderSystem.PROJECTION_MATRIX_UBO_SIZE).putMat4f($$0).get();
            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(this.buffer.slice(), $$2);
            if ($$1 != null) {
                $$1.close();
            }
            return this.bufferSlice;
        } catch (Throwable th) {
            if ($$1 != null) {
                try {
                    $$1.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.buffer.close();
    }
}
