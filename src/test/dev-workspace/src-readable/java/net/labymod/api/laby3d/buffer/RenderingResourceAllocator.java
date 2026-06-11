package net.labymod.api.laby3d.buffer;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.mesh.MeshRenderer;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import net.labymod.laby3d.api.mesh.GeometryData;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import net.labymod.laby3d.api.vertex.VertexDescription;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/buffer/RenderingResourceAllocator.class */
@ApiStatus.Internal
public interface RenderingResourceAllocator {
    VertexConsumer getBuffer(RenderMaterial renderMaterial);

    default VertexConsumer getBuffer(RenderState renderState, ResourceLocation textureLocation) {
        return getBuffer(RenderMaterial.LEGACY_FACTORY.apply(renderState, textureLocation));
    }

    default VertexConsumer getBuffer(RenderState renderState, DeviceTextureView textureView) {
        return getBuffer(RenderMaterial.DEFAULT_FACTORY.apply(renderState, textureView));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/buffer/RenderingResourceAllocator$Impl.class */
    public static class Impl implements RenderingResourceAllocator {
        private final ByteBufferBuilder sharedBuffer;
        private final Map<RenderMaterial, BufferBuilder> startedBuffers = new HashMap();
        private RenderMaterial lastMaterial;

        public Impl(ByteBufferBuilder sharedBuffer) {
            this.sharedBuffer = sharedBuffer;
        }

        @Override // net.labymod.api.laby3d.buffer.RenderingResourceAllocator
        public VertexConsumer getBuffer(RenderMaterial renderMaterial) {
            BufferBuilder buffer = this.startedBuffers.get(renderMaterial);
            if (buffer != null) {
                return buffer;
            }
            if (this.lastMaterial != null) {
                endBatch(this.lastMaterial);
            }
            BufferBuilder buffer2 = new BufferBuilder(this.sharedBuffer, renderMaterial.description(), renderMaterial.mode());
            this.lastMaterial = renderMaterial;
            this.startedBuffers.put(renderMaterial, buffer2);
            return buffer2;
        }

        public void endLastBatch() {
            if (this.lastMaterial != null) {
                endBatch(this.lastMaterial);
                this.lastMaterial = null;
            }
        }

        public void endBatch() {
            endLastBatch();
        }

        private void endBatch(RenderMaterial material) {
            BufferBuilder buffer = this.startedBuffers.remove(material);
            if (buffer != null) {
                endBatch(buffer, material);
            }
        }

        private void endBatch(BufferBuilder buffer, RenderMaterial material) {
            GeometryData data = buffer.build();
            if (data != null) {
                ShaderTextures.setShaderTexture(0, material.texture());
                MeshRenderer.drawImmediate(data, material.state());
            }
            if (material.equals(this.lastMaterial)) {
                this.lastMaterial = null;
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/buffer/RenderingResourceAllocator$RenderMaterial.class */
    public static final class RenderMaterial extends Record {
        private final RenderState state;
        private final DeviceTextureView texture;
        private static final BiFunction<RenderState, DeviceTextureView, RenderMaterial> DEFAULT_FACTORY = Laby.references().functionMemoizeStorage().memoize(RenderMaterial::new);
        private static final BiFunction<RenderState, ResourceLocation, RenderMaterial> LEGACY_FACTORY = Laby.references().functionMemoizeStorage().memoize((renderState, location) -> {
            ShaderTextures.setShaderTexture(5, location);
            return new RenderMaterial(renderState, ShaderTextures.getShaderTexture(5));
        });

        public RenderMaterial(RenderState state, DeviceTextureView texture) {
            this.state = state;
            this.texture = texture;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RenderMaterial.class), RenderMaterial.class, "state;texture", "FIELD:Lnet/labymod/api/laby3d/buffer/RenderingResourceAllocator$RenderMaterial;->state:Lnet/labymod/laby3d/api/pipeline/RenderState;", "FIELD:Lnet/labymod/api/laby3d/buffer/RenderingResourceAllocator$RenderMaterial;->texture:Lnet/labymod/laby3d/api/textures/DeviceTextureView;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RenderMaterial.class), RenderMaterial.class, "state;texture", "FIELD:Lnet/labymod/api/laby3d/buffer/RenderingResourceAllocator$RenderMaterial;->state:Lnet/labymod/laby3d/api/pipeline/RenderState;", "FIELD:Lnet/labymod/api/laby3d/buffer/RenderingResourceAllocator$RenderMaterial;->texture:Lnet/labymod/laby3d/api/textures/DeviceTextureView;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RenderMaterial.class, Object.class), RenderMaterial.class, "state;texture", "FIELD:Lnet/labymod/api/laby3d/buffer/RenderingResourceAllocator$RenderMaterial;->state:Lnet/labymod/laby3d/api/pipeline/RenderState;", "FIELD:Lnet/labymod/api/laby3d/buffer/RenderingResourceAllocator$RenderMaterial;->texture:Lnet/labymod/laby3d/api/textures/DeviceTextureView;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public RenderState state() {
            return this.state;
        }

        public DeviceTextureView texture() {
            return this.texture;
        }

        public DrawingMode mode() {
            return this.state.drawingMode();
        }

        public VertexDescription description() {
            return this.state.vertexDescription();
        }
    }
}
