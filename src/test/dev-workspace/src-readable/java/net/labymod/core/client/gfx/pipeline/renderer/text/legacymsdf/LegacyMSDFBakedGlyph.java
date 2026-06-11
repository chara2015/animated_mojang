package net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.EffectGlyph;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.EffectInstance;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderResources;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphInstance;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf.resource.Atlas;
import net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf.resource.Bounds;
import net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf.resource.GlyphInfo;
import net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf.resource.LegacyMSDFMaterials;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/LegacyMSDFBakedGlyph.class */
public class LegacyMSDFBakedGlyph implements BakedGlyph, EffectGlyph, GlyphRenderResources {
    private final GlyphDescription description;
    private final float baselineHeight;
    private final Rectangle bounds;
    private final float minU;
    private final float minV;
    private final float maxU;
    private final float maxV;
    private final LegacyMSDFMaterials materials;
    private final DeviceTextureView textureView;

    public LegacyMSDFBakedGlyph(GlyphDescription description, @Nullable GlyphInfo info, Atlas atlas, float baselineHeight, LegacyMSDFMaterials materials, DeviceTextureView textureView) {
        this.description = description;
        this.baselineHeight = baselineHeight;
        this.bounds = createBounds(info == null ? null : info.planeBounds());
        float left = 0.0f;
        float top = 0.0f;
        float right = 0.0f;
        float bottom = 0.0f;
        Bounds bounds = info == null ? null : info.atlasBounds();
        if (bounds != null) {
            left = bounds.left();
            top = bounds.top();
            right = bounds.right();
            bottom = bounds.bottom();
        }
        float atlasWidth = atlas.width();
        float atlasHeight = atlas.height();
        this.minU = left / atlasWidth;
        this.minV = 1.0f - (top / atlasHeight);
        this.maxU = right / atlasWidth;
        this.maxV = 1.0f - (bottom / atlasHeight);
        this.materials = materials;
        this.textureView = textureView;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph
    public GlyphDescription description() {
        return this.description;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph
    public StyledGlyphRenderable createGlyph(float x, float y, int color, int shadowColor, Style style, float boldOffset, float shadowOffset) {
        return new StyledGlyphInstance(this, x, y, color, shadowColor, style, boldOffset, shadowOffset, this::buildVertices);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.EffectGlyph
    public GlyphRenderable createEffect(float left, float top, float right, float bottom, float depth, int color, int shadowColor, float shadowOffset) {
        return new EffectInstance(this, left, top, right, bottom, depth, color, shadowColor, shadowOffset, this::buildEffectVertices);
    }

    private void buildVertices(Matrix4f pose, VertexConsumer consumer, float x, float y, float depth, int color, int lightCoords, boolean italic, GlyphProperties properties) {
        Rectangle bounds = this.bounds;
        float left = x + bounds.getLeft();
        float baseline = y + this.baselineHeight;
        float y2 = baseline - bounds.getTop();
        float right = x + bounds.getWidth() + bounds.getLeft();
        float bottom = y2 + bounds.getHeight();
        float minU = this.minU;
        float minV = this.minV;
        float maxU = this.maxU;
        float maxV = this.maxV;
        float shearTop = italic ? shearTop() : 0.0f;
        float shearBottom = italic ? shearBottom() : 0.0f;
        float weight = ((Float) properties.getOrDefault(FontProperties.FONT_WEIGHT_KEY, Float.valueOf(0.0f))).floatValue();
        consumer.addVertex(pose, left + shearTop, y2, depth).setUv(minU, minV).setColor(color).setPackedLight(lightCoords).setFloat(weight);
        consumer.addVertex(pose, left + shearBottom, bottom, depth).setUv(minU, maxV).setColor(color).setPackedLight(lightCoords).setFloat(weight);
        consumer.addVertex(pose, right + shearBottom, bottom, depth).setUv(maxU, maxV).setColor(color).setPackedLight(lightCoords).setFloat(weight);
        consumer.addVertex(pose, right + shearTop, y2, depth).setUv(maxU, minV).setColor(color).setPackedLight(lightCoords).setFloat(weight);
    }

    private void buildEffectVertices(EffectInstance<?> effect, Matrix4f pose, VertexConsumer consumer, float offset, float depth, int color, int lightCoords) {
        float left = effect.left() + offset;
        float top = effect.top() + offset;
        float right = effect.right() + offset;
        float bottom = effect.bottom() + offset;
        consumer.addVertex(pose, left, top, depth).setBlankUv().setColor(color).setPackedLight(lightCoords).setFloat(0.0f);
        consumer.addVertex(pose, left, bottom, depth).setBlankUv().setColor(color).setPackedLight(lightCoords).setFloat(0.0f);
        consumer.addVertex(pose, right, bottom, depth).setBlankUv().setColor(color).setPackedLight(lightCoords).setFloat(0.0f);
        consumer.addVertex(pose, right, top, depth).setBlankUv().setColor(color).setPackedLight(lightCoords).setFloat(0.0f);
    }

    private Rectangle createBounds(@Nullable Bounds bounds) {
        if (bounds == null) {
            return Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f);
        }
        return Rectangle.relative(bounds.left() * 8.25f, bounds.top() * 8.25f, (bounds.right() - bounds.left()) * 8.25f, (bounds.top() - bounds.bottom()) * 8.25f);
    }

    private float shearTop() {
        return 1.0f - (0.25f * this.bounds.getTop());
    }

    private float shearBottom() {
        return 1.0f - (0.25f * this.bounds.getBottom());
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderResources
    public Material material(int flags) {
        return this.materials.select(flags);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderResources
    public RenderState guiRenderState() {
        return this.materials.guiRenderState();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderResources
    public DeviceTextureView textureView() {
        return this.textureView;
    }
}
