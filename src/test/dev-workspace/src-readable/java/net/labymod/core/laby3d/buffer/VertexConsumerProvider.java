package net.labymod.core.laby3d.buffer;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import net.labymod.laby3d.api.mesh.GeometryData;
import net.labymod.laby3d.api.mesh.Mesh;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/buffer/VertexConsumerProvider.class */
public interface VertexConsumerProvider {
    VertexConsumer getBuffer(CommandBuffer commandBuffer, RenderingParameters renderingParameters);

    default VertexConsumer getBuffer(CommandBuffer cmd, RenderState renderState, TextureBindingSet textureBindingSet) {
        return getBuffer(cmd, new RenderingParameters(renderState, textureBindingSet));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/buffer/VertexConsumerProvider$BufferProvider.class */
    public static class BufferProvider implements VertexConsumerProvider {
        private final ByteBufferBuilder sharedBuffer;
        private final Map<RenderingParameters, BufferBuilder> startedBuilders = new HashMap();
        private final Laby3D laby3D = Laby.references().laby3D();

        @Nullable
        private RenderingParameters lastRenderingParameters;

        public BufferProvider(ByteBufferBuilder sharedBuffer) {
            this.sharedBuffer = sharedBuffer;
        }

        @Override // net.labymod.core.laby3d.buffer.VertexConsumerProvider
        public VertexConsumer getBuffer(CommandBuffer cmd, RenderingParameters parameters) {
            BufferBuilder bufferBuilder = this.startedBuilders.get(parameters);
            if (bufferBuilder != null) {
                return bufferBuilder;
            }
            if (this.lastRenderingParameters != null) {
                endBatch(cmd, this.lastRenderingParameters);
            }
            RenderState renderState = parameters.renderState();
            BufferBuilder bufferBuilder2 = new BufferBuilder(this.sharedBuffer, renderState.vertexDescription(), renderState.drawingMode());
            this.lastRenderingParameters = parameters;
            this.startedBuilders.put(parameters, bufferBuilder2);
            return bufferBuilder2;
        }

        public void endLastBatch(CommandBuffer cmd) {
            if (this.lastRenderingParameters == null) {
                return;
            }
            endBatch(cmd, this.lastRenderingParameters);
            this.lastRenderingParameters = null;
        }

        public void endBatch(CommandBuffer cmd) {
            endLastBatch(cmd);
            this.sharedBuffer.clear();
        }

        private void endBatch(CommandBuffer cmd, RenderingParameters parameters) {
            BufferBuilder bufferBuilder = this.startedBuilders.remove(parameters);
            if (bufferBuilder == null) {
                return;
            }
            endBatch(cmd, parameters, bufferBuilder);
        }

        private void endBatch(CommandBuffer cmd, RenderingParameters parameters, BufferBuilder bufferBuilder) {
            GeometryData geometryData = bufferBuilder.build();
            if (geometryData != null) {
                RenderDevice renderDevice = this.laby3D.renderDevice();
                Mesh mesh = Mesh.create(renderDevice, () -> {
                    return "BatchedMesh (" + String.valueOf(parameters.renderState().id()) + ")";
                }, geometryData);
                cmd.bindPipeline(parameters.renderState());
                Objects.requireNonNull(mesh);
                cmd.addCleanupAction(mesh::close);
                this.laby3D.setupOverlayAndLightingTextures(cmd);
                DeviceTextureView[] textures = parameters.textureBindingSet().textures();
                for (int index = 0; index < textures.length; index++) {
                    DeviceTextureView texture = textures[index];
                    if (texture != null) {
                        cmd.bindTexture(index, texture);
                    }
                }
                this.laby3D.setupDefaultUniforms(cmd);
                cmd.draw(mesh);
            }
            if (parameters.equals(this.lastRenderingParameters)) {
                this.lastRenderingParameters = null;
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/buffer/VertexConsumerProvider$RenderingParameters.class */
    public static final class RenderingParameters extends Record {
        private final RenderState renderState;
        private final TextureBindingSet textureBindingSet;

        public RenderingParameters(RenderState renderState, TextureBindingSet textureBindingSet) {
            this.renderState = renderState;
            this.textureBindingSet = textureBindingSet;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RenderingParameters.class), RenderingParameters.class, "renderState;textureBindingSet", "FIELD:Lnet/labymod/core/laby3d/buffer/VertexConsumerProvider$RenderingParameters;->renderState:Lnet/labymod/laby3d/api/pipeline/RenderState;", "FIELD:Lnet/labymod/core/laby3d/buffer/VertexConsumerProvider$RenderingParameters;->textureBindingSet:Lnet/labymod/api/laby3d/textures/TextureBindingSet;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RenderingParameters.class), RenderingParameters.class, "renderState;textureBindingSet", "FIELD:Lnet/labymod/core/laby3d/buffer/VertexConsumerProvider$RenderingParameters;->renderState:Lnet/labymod/laby3d/api/pipeline/RenderState;", "FIELD:Lnet/labymod/core/laby3d/buffer/VertexConsumerProvider$RenderingParameters;->textureBindingSet:Lnet/labymod/api/laby3d/textures/TextureBindingSet;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RenderingParameters.class, Object.class), RenderingParameters.class, "renderState;textureBindingSet", "FIELD:Lnet/labymod/core/laby3d/buffer/VertexConsumerProvider$RenderingParameters;->renderState:Lnet/labymod/laby3d/api/pipeline/RenderState;", "FIELD:Lnet/labymod/core/laby3d/buffer/VertexConsumerProvider$RenderingParameters;->textureBindingSet:Lnet/labymod/api/laby3d/textures/TextureBindingSet;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public RenderState renderState() {
            return this.renderState;
        }

        public TextureBindingSet textureBindingSet() {
            return this.textureBindingSet;
        }
    }
}
