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

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/CachedOrthoProjectionMatrixBuffer.class */
public class CachedOrthoProjectionMatrixBuffer implements AutoCloseable {
    private final GpuBuffer buffer;
    private final GpuBufferSlice bufferSlice;
    private final float zNear;
    private final float zFar;
    private final boolean invertY;
    private float width;
    private float height;

    public CachedOrthoProjectionMatrixBuffer(String $$0, float $$1, float $$2, boolean $$3) {
        this.zNear = $$1;
        this.zFar = $$2;
        this.invertY = $$3;
        GpuDevice $$4 = RenderSystem.getDevice();
        this.buffer = $$4.createBuffer(() -> {
            return "Projection matrix UBO " + $$0;
        }, 136, RenderSystem.PROJECTION_MATRIX_UBO_SIZE);
        this.bufferSlice = this.buffer.slice(0L, RenderSystem.PROJECTION_MATRIX_UBO_SIZE);
    }

    public GpuBufferSlice getBuffer(float $$0, float $$1) {
        if (this.width != $$0 || this.height != $$1) {
            Matrix4fc matrix4fcCreateProjectionMatrix = createProjectionMatrix($$0, $$1);
            MemoryStack $$3 = MemoryStack.stackPush();
            try {
                ByteBuffer $$4 = Std140Builder.onStack($$3, RenderSystem.PROJECTION_MATRIX_UBO_SIZE).putMat4f(matrix4fcCreateProjectionMatrix).get();
                RenderSystem.getDevice().createCommandEncoder().writeToBuffer(this.buffer.slice(), $$4);
                if ($$3 != null) {
                    $$3.close();
                }
                this.width = $$0;
                this.height = $$1;
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
        return this.bufferSlice;
    }

    private Matrix4f createProjectionMatrix(float $$0, float $$1) {
        return new Matrix4f().setOrtho(0.0f, $$0, this.invertY ? $$1 : 0.0f, this.invertY ? 0.0f : $$1, this.zNear, this.zFar);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.buffer.close();
    }
}
