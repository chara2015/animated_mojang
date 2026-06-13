package net.labymod.core.client.render.schematic.renderer;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.shaders.block.SchematicUniformBlock;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.util.Disposable;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.BlockRenderer;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import net.labymod.laby3d.api.mesh.GeometryData;
import net.labymod.laby3d.api.mesh.Mesh;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/renderer/RenderedSchematicLayer.class */
public class RenderedSchematicLayer implements Disposable {
    private final RenderLayer renderLayer;
    private final DynamicBackgroundController controller;
    private BufferBuilder bufferBuilder;
    private TextureAtlas atlas;
    private final List<GeometryData> pendingGeometryData = new ArrayList();
    private final List<Mesh> meshes = new ArrayList();
    private boolean dirty = true;
    private LegacyLightEngine lightEngine = null;
    private final ByteBufferBuilder buffer = new ByteBufferBuilder(256);
    private final Laby3D laby3D = Laby.references().laby3D();

    public RenderedSchematicLayer(RenderLayer renderLayer, DynamicBackgroundController controller) {
        this.renderLayer = renderLayer;
        this.controller = controller;
    }

    public void setAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public boolean renderMesh(CommandBuffer cmd) {
        if (this.meshes.isEmpty()) {
            return false;
        }
        ShaderTextures.setShaderTexture(0, this.atlas.resource());
        for (Mesh mesh : this.meshes) {
            cmd.bindPipeline(RenderStates.SCHEMATIC);
            cmd.bindTexture(0, ShaderTextures.getShaderTexture(0));
            cmd.bindUniformBlock(SchematicUniformBlock.NAME, this.laby3D.schematic());
            cmd.draw(mesh);
        }
        return true;
    }

    public void render(BlockRenderer renderer, Block block, int x, int y, int z) {
        beginBuffer();
        renderer.renderCube(this.atlas, this.bufferBuilder, block, x, y, z);
    }

    public void upload(CommandBuffer cmd) {
        uploadChunk();
        if (isDirty()) {
            dispose();
            for (GeometryData geometryData : this.pendingGeometryData) {
                this.meshes.add(Mesh.create(this.laby3D.renderDevice(), () -> {
                    return String.valueOf(this.renderLayer) + " mesh";
                }, geometryData));
            }
            this.pendingGeometryData.clear();
            this.dirty = false;
            if (this.atlas != null) {
                ShaderTextures.setShaderTexture(0, this.atlas.resource());
            }
            renderMesh(cmd);
        }
    }

    private void beginBuffer() {
        if (this.bufferBuilder == null) {
            this.bufferBuilder = new BufferBuilder(this.buffer, VertexDescriptions.SCHEMATIC, DrawingMode.QUADS);
        }
    }

    @Override // net.labymod.api.util.Disposable
    public void dispose() {
        for (Mesh mesh : this.meshes) {
            mesh.close();
        }
        this.meshes.clear();
        this.dirty = true;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public boolean isAnyMeshIndexBufferClosed() {
        for (Mesh mesh : this.meshes) {
            if (mesh.bufferResource().indexBuffer().isClosed()) {
                return true;
            }
        }
        return false;
    }

    public void setDirty() {
        this.dirty = true;
    }

    public void setLightEngine(LegacyLightEngine lightEngine) {
        this.lightEngine = lightEngine;
    }

    public LegacyLightEngine getLightEngine() {
        return this.lightEngine;
    }

    public void close() {
        if (this.buffer != null) {
            this.buffer.close();
        }
    }

    public void uploadChunk() {
        if (this.bufferBuilder == null) {
            return;
        }
        GeometryData geometryData = this.bufferBuilder.build();
        this.bufferBuilder = null;
        if (geometryData == null) {
            return;
        }
        this.pendingGeometryData.add(geometryData);
    }
}
