package net.labymod.api.laby3d.render.buffer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.state.VanillaMeshUtil;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.pipeline.material.TextureMaterial;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import net.labymod.laby3d.api.mesh.GeometryData;
import net.labymod.laby3d.api.mesh.Mesh;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/buffer/Laby3DRenderBufferSource.class */
public class Laby3DRenderBufferSource implements RenderBufferSource {
    private final CommandBuffer commandBuffer;
    private final ByteBufferBuilder buffer;

    @Nullable
    private Material lastRenderingParameters;
    private final Laby3D laby3D = Laby.references().laby3D();
    private final Map<Material, BufferBuilder> startedBuffers = new HashMap();

    public Laby3DRenderBufferSource(CommandBuffer commandBuffer, ByteBufferBuilder buffer) {
        this.commandBuffer = commandBuffer;
        this.buffer = buffer;
    }

    @Override // net.labymod.api.laby3d.render.buffer.RenderBufferSource
    public VertexConsumer getBuffer(Material material) {
        BufferBuilder bufferBuilder = this.startedBuffers.get(material);
        if (bufferBuilder != null) {
            return bufferBuilder;
        }
        if (this.lastRenderingParameters != null) {
            endBatch(this.lastRenderingParameters);
        }
        RenderState renderState = material.renderState();
        BufferBuilder bufferBuilder2 = new BufferBuilder(this.buffer, renderState.vertexDescription(), renderState.drawingMode());
        this.lastRenderingParameters = material;
        this.startedBuffers.put(material, bufferBuilder2);
        return bufferBuilder2;
    }

    public void endLastBatch() {
        if (this.lastRenderingParameters == null) {
            return;
        }
        endBatch(this.lastRenderingParameters);
        this.lastRenderingParameters = null;
    }

    @Override // net.labymod.api.laby3d.render.buffer.RenderBufferSource
    public void endBatch() {
        endLastBatch();
        this.buffer.clear();
    }

    private void endBatch(Material material) {
        BufferBuilder bufferBuilder = this.startedBuffers.remove(material);
        if (bufferBuilder == null) {
            return;
        }
        endBatch(material, bufferBuilder);
    }

    private void endBatch(Material material, BufferBuilder bufferBuilder) {
        GeometryData geometryData = bufferBuilder.build();
        if (geometryData != null) {
            RenderDevice renderDevice = this.laby3D.renderDevice();
            RenderState renderState = material.renderState();
            Mesh mesh = Mesh.create(renderDevice, () -> {
                return "BatchedMesh (" + String.valueOf(renderState.id()) + ")";
            }, geometryData);
            this.commandBuffer.bindPipeline(renderState);
            CommandBuffer commandBuffer = this.commandBuffer;
            Objects.requireNonNull(mesh);
            commandBuffer.addCleanupAction(mesh::close);
            this.laby3D.setupOverlayAndLightingTextures(this.commandBuffer);
            TextureMaterial[] textureMaterials = material.textureMaterials();
            for (int index = 0; index < textureMaterials.length; index++) {
                TextureMaterial textureMaterial = textureMaterials[index];
                if (textureMaterial != null) {
                    this.commandBuffer.bindTexture(index, textureMaterial.textureView());
                }
            }
            this.laby3D.setupDefaultUniforms(this.commandBuffer);
            VanillaMeshUtil.applyVanillaWorkaround(this.laby3D, renderState, this.commandBuffer, this.laby3D.getProjectionMatrix(), this.laby3D.getModelViewMatrix(), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));
            this.commandBuffer.draw(mesh);
        }
        if (material.equals(this.lastRenderingParameters)) {
            this.lastRenderingParameters = null;
        }
    }
}
