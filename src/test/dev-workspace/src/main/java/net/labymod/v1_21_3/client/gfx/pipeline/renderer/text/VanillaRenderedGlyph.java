package net.labymod.v1_21_3.client.gfx.pipeline.renderer.text;

import java.util.List;
import java.util.function.Function;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderStates;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertex;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertexProvider;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import net.labymod.v1_21_3.client.gfx.pipeline.blaze3d.PositionTextureColorVertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/gfx/pipeline/renderer/text/VanillaRenderedGlyph.class */
public class VanillaRenderedGlyph implements StyledGlyphRenderable {
    private final Function<ResourceLocation, DeviceTextureView> textureViews = location -> {
        ShaderTextures.setShaderTexture(0, location);
        return ShaderTextures.getShaderTexture(0);
    };
    private final BakedGlyphExtension extension;
    private final ResourceLocation resourceLocation;
    private final GlyphInstance glyphInstance;
    private final RenderState renderState;

    public VanillaRenderedGlyph(GlyphInstance glyphInstance) {
        this.glyphInstance = glyphInstance;
        this.extension = BakedGlyphExtension.cast(glyphInstance.glyph());
        this.renderState = FontRenderStates.resolve(TextDrawMode.NORMAL, this.extension.labyMod$isColored());
        this.resourceLocation = this.extension.labyMod$getTextureLocation();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable
    public Material material(int flags) {
        return VanillaGlyphUtil.material(this.extension, flags);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable
    public RenderState guiRenderState() {
        return this.renderState;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable
    public DeviceTextureView textureView() {
        return this.textureViews.apply(this.resourceLocation);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable
    public void buildVertices(Matrix4f pose, VertexConsumer consumer, int lightCoords, boolean ignoreDepthOffset, GlyphProperties properties) {
        PositionTextureColorVertexConsumer vertexConsumer = PositionTextureColorVertexConsumer.create();
        this.extension.labyMod$renderGlyph(this.glyphInstance, pose, vertexConsumer, lightCoords, ignoreDepthOffset);
        Blaze3DVertexProvider provider = (Blaze3DVertexProvider) CastUtil.requireInstanceOf(vertexConsumer, Blaze3DVertexProvider.class);
        List<Blaze3DVertex> vertices = provider.vertices();
        for (Blaze3DVertex vertex : vertices) {
            consumer.addVertex(vertex.getX(), vertex.getY(), vertex.getZ()).setColor(vertex.getArgb()).setUv(vertex.getU(), vertex.getV()).setPackedLight(lightCoords);
        }
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable
    public Style style() {
        return (Style) CastUtil.cast(this.glyphInstance.style());
    }
}
