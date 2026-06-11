package net.minecraft.client.renderer.chunk;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/chunk/SectionBuffers.class */
public final class SectionBuffers implements AutoCloseable {
    private GpuBuffer vertexBuffer;
    private GpuBuffer indexBuffer;
    private int indexCount;
    private VertexFormat.IndexType indexType;

    public SectionBuffers(GpuBuffer $$0, GpuBuffer $$1, int $$2, VertexFormat.IndexType $$3) {
        this.vertexBuffer = $$0;
        this.indexBuffer = $$1;
        this.indexCount = $$2;
        this.indexType = $$3;
    }

    public GpuBuffer getVertexBuffer() {
        return this.vertexBuffer;
    }

    public GpuBuffer getIndexBuffer() {
        return this.indexBuffer;
    }

    public void setIndexBuffer(GpuBuffer $$0) {
        this.indexBuffer = $$0;
    }

    public int getIndexCount() {
        return this.indexCount;
    }

    public VertexFormat.IndexType getIndexType() {
        return this.indexType;
    }

    public void setIndexType(VertexFormat.IndexType $$0) {
        this.indexType = $$0;
    }

    public void setIndexCount(int $$0) {
        this.indexCount = $$0;
    }

    public void setVertexBuffer(GpuBuffer $$0) {
        this.vertexBuffer = $$0;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.vertexBuffer.close();
        if (this.indexBuffer != null) {
            this.indexBuffer.close();
        }
    }
}
