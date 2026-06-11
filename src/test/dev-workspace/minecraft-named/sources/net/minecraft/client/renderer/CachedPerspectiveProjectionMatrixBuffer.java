package net.minecraft.client.renderer;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.lwjgl.system.MemoryStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/CachedPerspectiveProjectionMatrixBuffer.class */
public class CachedPerspectiveProjectionMatrixBuffer implements AutoCloseable {
    private final GpuBuffer buffer;
    private final GpuBufferSlice bufferSlice;
    private final float zNear;
    private final float zFar;
    private int width;
    private int height;
    private float fov;

    public CachedPerspectiveProjectionMatrixBuffer(String $$0, float $$1, float $$2) {
        this.zNear = $$1;
        this.zFar = $$2;
        GpuDevice $$3 = RenderSystem.getDevice();
        this.buffer = $$3.createBuffer(() -> {
            return "Projection matrix UBO " + $$0;
        }, 136, RenderSystem.PROJECTION_MATRIX_UBO_SIZE);
        this.bufferSlice = this.buffer.slice(0L, RenderSystem.PROJECTION_MATRIX_UBO_SIZE);
    }

    public GpuBufferSlice getBuffer(int $$0, int $$1, float $$2) {
        if (this.width != $$0 || this.height != $$1 || this.fov != $$2) {
            Matrix4fc matrix4fcCreateProjectionMatrix = createProjectionMatrix($$0, $$1, $$2);
            MemoryStack $$4 = MemoryStack.stackPush();
            try {
                ByteBuffer $$5 = Std140Builder.onStack($$4, RenderSystem.PROJECTION_MATRIX_UBO_SIZE).putMat4f(matrix4fcCreateProjectionMatrix).get();
                RenderSystem.getDevice().createCommandEncoder().writeToBuffer(this.buffer.slice(), $$5);
                if ($$4 != null) {
                    $$4.close();
                }
                this.width = $$0;
                this.height = $$1;
                this.fov = $$2;
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
        return this.bufferSlice;
    }

    private Matrix4f createProjectionMatrix(int $$0, int $$1, float $$2) {
        return new Matrix4f().perspective($$2 * 0.017453292f, $$0 / $$1, this.zNear, this.zFar);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.buffer.close();
    }
}
