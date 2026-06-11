package net.labymod.api.client.gfx.pipeline.renderer.text.glyph;

import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphRenderable.class */
public interface GlyphRenderable {
    Material material(int i);

    RenderState guiRenderState();

    DeviceTextureView textureView();

    void buildVertices(Matrix4f matrix4f, VertexConsumer vertexConsumer, int i, boolean z, GlyphProperties glyphProperties);
}
