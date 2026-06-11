package net.labymod.v1_17_1.client.gfx.pipeline.renderer.text;

import java.util.List;
import java.util.function.Function;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderStates;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertex;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertexProvider;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import net.labymod.v1_17_1.client.gfx.pipeline.blaze3d.PositionTextureColorVertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/gfx/pipeline/renderer/text/VanillaRenderedEffect.class */
public class VanillaRenderedEffect implements GlyphRenderable {
    private static final GameMathMapper MAPPER = MathHelper.mapper();
    private final Function<ResourceLocation, DeviceTextureView> textureViews = location -> {
        ShaderTextures.setShaderTexture(0, location);
        return ShaderTextures.getShaderTexture(0);
    };
    private final dyp bakedGlyph;
    private final a effect;
    private final RenderState renderState;
    private final BakedGlyphExtension extension;
    private final ResourceLocation resourceLocation;

    public VanillaRenderedEffect(dyp bakedGlyph, a effect) {
        this.extension = BakedGlyphExtension.cast(bakedGlyph);
        this.resourceLocation = this.extension.labyMod$getTextureLocation();
        this.bakedGlyph = bakedGlyph;
        this.effect = effect;
        this.renderState = FontRenderStates.resolve(TextDrawMode.NORMAL, this.extension.labyMod$isColored());
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
        d mojangPose = (d) MAPPER.toMatrix4f(pose);
        this.bakedGlyph.a(this.effect, mojangPose, vertexConsumer, lightCoords);
        Blaze3DVertexProvider provider = (Blaze3DVertexProvider) CastUtil.requireInstanceOf(vertexConsumer, Blaze3DVertexProvider.class);
        List<Blaze3DVertex> vertices = provider.vertices();
        for (Blaze3DVertex vertex : vertices) {
            consumer.addVertex(vertex.getX(), vertex.getY(), vertex.getZ()).setColor(vertex.getArgb()).setUv(vertex.getU(), vertex.getV()).setPackedLight(lightCoords);
        }
    }
}
