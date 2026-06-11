package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/OutlineBufferSource.class */
public class OutlineBufferSource implements MultiBufferSource {
    private final MultiBufferSource.BufferSource outlineBufferSource = MultiBufferSource.immediate(new ByteBufferBuilder(RenderType.TRANSIENT_BUFFER_SIZE));
    private int outlineColor = -1;

    @Override // net.minecraft.client.renderer.MultiBufferSource
    public VertexConsumer getBuffer(RenderType $$0) {
        if ($$0.isOutline()) {
            VertexConsumer $$1 = this.outlineBufferSource.getBuffer($$0);
            return new EntityOutlineGenerator($$1, this.outlineColor);
        }
        Optional<RenderType> $$2 = $$0.outline();
        if ($$2.isPresent()) {
            VertexConsumer $$3 = this.outlineBufferSource.getBuffer($$2.get());
            return new EntityOutlineGenerator($$3, this.outlineColor);
        }
        throw new IllegalStateException("Can't render an outline for this rendertype!");
    }

    public void setColor(int $$0) {
        this.outlineColor = $$0;
    }

    public void endOutlineBatch() {
        this.outlineBufferSource.endBatch();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/OutlineBufferSource$EntityOutlineGenerator.class */
    static final class EntityOutlineGenerator extends Record implements VertexConsumer {
        private final VertexConsumer delegate;
        private final int color;

        EntityOutlineGenerator(VertexConsumer $$0, int $$1) {
            this.delegate = $$0;
            this.color = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EntityOutlineGenerator.class), EntityOutlineGenerator.class, "delegate;color", "FIELD:Lnet/minecraft/client/renderer/OutlineBufferSource$EntityOutlineGenerator;->delegate:Lcom/mojang/blaze3d/vertex/VertexConsumer;", "FIELD:Lnet/minecraft/client/renderer/OutlineBufferSource$EntityOutlineGenerator;->color:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EntityOutlineGenerator.class), EntityOutlineGenerator.class, "delegate;color", "FIELD:Lnet/minecraft/client/renderer/OutlineBufferSource$EntityOutlineGenerator;->delegate:Lcom/mojang/blaze3d/vertex/VertexConsumer;", "FIELD:Lnet/minecraft/client/renderer/OutlineBufferSource$EntityOutlineGenerator;->color:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EntityOutlineGenerator.class, Object.class), EntityOutlineGenerator.class, "delegate;color", "FIELD:Lnet/minecraft/client/renderer/OutlineBufferSource$EntityOutlineGenerator;->delegate:Lcom/mojang/blaze3d/vertex/VertexConsumer;", "FIELD:Lnet/minecraft/client/renderer/OutlineBufferSource$EntityOutlineGenerator;->color:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public VertexConsumer delegate() {
            return this.delegate;
        }

        public int color() {
            return this.color;
        }

        @Override // com.mojang.blaze3d.vertex.VertexConsumer
        public VertexConsumer addVertex(float $$0, float $$1, float $$2) {
            this.delegate.addVertex($$0, $$1, $$2).setColor(this.color);
            return this;
        }

        @Override // com.mojang.blaze3d.vertex.VertexConsumer
        public VertexConsumer setColor(int $$0, int $$1, int $$2, int $$3) {
            return this;
        }

        @Override // com.mojang.blaze3d.vertex.VertexConsumer
        public VertexConsumer setColor(int $$0) {
            return this;
        }

        @Override // com.mojang.blaze3d.vertex.VertexConsumer
        public VertexConsumer setUv(float $$0, float $$1) {
            this.delegate.setUv($$0, $$1);
            return this;
        }

        @Override // com.mojang.blaze3d.vertex.VertexConsumer
        public VertexConsumer setUv1(int $$0, int $$1) {
            return this;
        }

        @Override // com.mojang.blaze3d.vertex.VertexConsumer
        public VertexConsumer setUv2(int $$0, int $$1) {
            return this;
        }

        @Override // com.mojang.blaze3d.vertex.VertexConsumer
        public VertexConsumer setNormal(float $$0, float $$1, float $$2) {
            return this;
        }

        @Override // com.mojang.blaze3d.vertex.VertexConsumer
        public VertexConsumer setLineWidth(float $$0) {
            return this;
        }
    }
}
