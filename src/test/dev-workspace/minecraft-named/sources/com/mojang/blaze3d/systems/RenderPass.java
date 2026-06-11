package com.mojang.blaze3d.systems;

import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/systems/RenderPass.class */
@DontObfuscate
public interface RenderPass extends AutoCloseable {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/systems/RenderPass$UniformUploader.class */
    public interface UniformUploader {
        void upload(String str, GpuBufferSlice gpuBufferSlice);
    }

    void pushDebugGroup(Supplier<String> supplier);

    void popDebugGroup();

    void setPipeline(RenderPipeline renderPipeline);

    void bindTexture(String str, GpuTextureView gpuTextureView, GpuSampler gpuSampler);

    void setUniform(String str, GpuBuffer gpuBuffer);

    void setUniform(String str, GpuBufferSlice gpuBufferSlice);

    void enableScissor(int i, int i2, int i3, int i4);

    void disableScissor();

    void setVertexBuffer(int i, GpuBuffer gpuBuffer);

    void setIndexBuffer(GpuBuffer gpuBuffer, VertexFormat.IndexType indexType);

    void drawIndexed(int i, int i2, int i3, int i4);

    <T> void drawMultipleIndexed(Collection<Draw<T>> collection, GpuBuffer gpuBuffer, VertexFormat.IndexType indexType, Collection<String> collection2, T t);

    void draw(int i, int i2);

    @Override // java.lang.AutoCloseable
    void close();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/systems/RenderPass$Draw.class */
    public static final class Draw<T> extends Record {
        private final int slot;
        private final GpuBuffer vertexBuffer;
        private final GpuBuffer indexBuffer;
        private final VertexFormat.IndexType indexType;
        private final int firstIndex;
        private final int indexCount;
        private final BiConsumer<T, UniformUploader> uniformUploaderConsumer;

        public Draw(int $$0, GpuBuffer $$1, GpuBuffer $$2, VertexFormat.IndexType $$3, int $$4, int $$5, BiConsumer<T, UniformUploader> $$6) {
            this.slot = $$0;
            this.vertexBuffer = $$1;
            this.indexBuffer = $$2;
            this.indexType = $$3;
            this.firstIndex = $$4;
            this.indexCount = $$5;
            this.uniformUploaderConsumer = $$6;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Draw.class), Draw.class, "slot;vertexBuffer;indexBuffer;indexType;firstIndex;indexCount;uniformUploaderConsumer", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->slot:I", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->vertexBuffer:Lcom/mojang/blaze3d/buffers/GpuBuffer;", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->indexBuffer:Lcom/mojang/blaze3d/buffers/GpuBuffer;", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->indexType:Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->firstIndex:I", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->indexCount:I", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->uniformUploaderConsumer:Ljava/util/function/BiConsumer;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Draw.class), Draw.class, "slot;vertexBuffer;indexBuffer;indexType;firstIndex;indexCount;uniformUploaderConsumer", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->slot:I", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->vertexBuffer:Lcom/mojang/blaze3d/buffers/GpuBuffer;", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->indexBuffer:Lcom/mojang/blaze3d/buffers/GpuBuffer;", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->indexType:Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->firstIndex:I", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->indexCount:I", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->uniformUploaderConsumer:Ljava/util/function/BiConsumer;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Draw.class, Object.class), Draw.class, "slot;vertexBuffer;indexBuffer;indexType;firstIndex;indexCount;uniformUploaderConsumer", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->slot:I", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->vertexBuffer:Lcom/mojang/blaze3d/buffers/GpuBuffer;", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->indexBuffer:Lcom/mojang/blaze3d/buffers/GpuBuffer;", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->indexType:Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->firstIndex:I", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->indexCount:I", "FIELD:Lcom/mojang/blaze3d/systems/RenderPass$Draw;->uniformUploaderConsumer:Ljava/util/function/BiConsumer;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int slot() {
            return this.slot;
        }

        public GpuBuffer vertexBuffer() {
            return this.vertexBuffer;
        }

        public GpuBuffer indexBuffer() {
            return this.indexBuffer;
        }

        public VertexFormat.IndexType indexType() {
            return this.indexType;
        }

        public int firstIndex() {
            return this.firstIndex;
        }

        public int indexCount() {
            return this.indexCount;
        }

        public BiConsumer<T, UniformUploader> uniformUploaderConsumer() {
            return this.uniformUploaderConsumer;
        }

        public Draw(int $$0, GpuBuffer $$1, GpuBuffer $$2, VertexFormat.IndexType $$3, int $$4, int $$5) {
            this($$0, $$1, $$2, $$3, $$4, $$5, null);
        }
    }
}
