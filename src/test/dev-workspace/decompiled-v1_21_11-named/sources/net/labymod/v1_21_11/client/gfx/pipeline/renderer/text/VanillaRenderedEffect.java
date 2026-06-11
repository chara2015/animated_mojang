package net.labymod.v1_21_11.client.gfx.pipeline.renderer.text;

import java.util.List;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderStates;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertex;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertexProvider;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import net.labymod.v1_21_11.client.gfx.pipeline.blaze3d.PositionTextureColorVertexConsumer;
import net.minecraft.client.gui.font.TextRenderable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/gfx/pipeline/renderer/text/VanillaRenderedEffect.class */
public class VanillaRenderedEffect implements GlyphRenderable {
    private final TextRenderable renderable;
    private final RenderState renderState;

    public VanillaRenderedEffect(TextRenderable renderable) {
        this.renderable = renderable;
        this.renderState = FontRenderStates.resolve(TextDrawMode.NORMAL, VanillaGlyphUtil.isColored(renderable.guiPipeline()));
    }

    public Material material(int flags) {
        return VanillaGlyphUtil.material(this.renderable, flags);
    }

    public RenderState guiRenderState() {
        return this.renderState;
    }

    public DeviceTextureView textureView() {
        return (DeviceTextureView) CastUtil.requireInstanceOf(this.renderable.textureView(), DeviceTextureView.class);
    }

    public void buildVertices(Matrix4f pose, VertexConsumer consumer, int lightCoords, boolean ignoreDepthOffset, GlyphProperties properties) {
        PositionTextureColorVertexConsumer vertexConsumer = PositionTextureColorVertexConsumer.create();
        this.renderable.render(pose, vertexConsumer, lightCoords, ignoreDepthOffset);
        Blaze3DVertexProvider provider = (Blaze3DVertexProvider) CastUtil.requireInstanceOf(vertexConsumer, Blaze3DVertexProvider.class);
        List<Blaze3DVertex> vertices = provider.vertices();
        for (Blaze3DVertex vertex : vertices) {
            consumer.addVertex(vertex.getX(), vertex.getY(), vertex.getZ()).setColor(vertex.getArgb()).setUv(vertex.getU(), vertex.getV()).setPackedLight(lightCoords);
        }
    }
}
