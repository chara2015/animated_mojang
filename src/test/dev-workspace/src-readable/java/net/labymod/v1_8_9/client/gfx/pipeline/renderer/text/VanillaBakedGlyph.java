package net.labymod.v1_8_9.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.EffectGlyph;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.EffectInstance;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderResources;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphTextureResolver;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphInstance;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/gfx/pipeline/renderer/text/VanillaBakedGlyph.class */
public class VanillaBakedGlyph implements BakedGlyph, EffectGlyph, GlyphRenderResources {
    private final boolean space;
    private final ResourceLocation location;
    private final Rectangle bounds;
    private final float minU;
    private final float minV;
    private final float maxU;
    private final float maxV;
    private final GlyphDescription description;
    private final Lazy<DeviceTextureView> textureView;

    public VanillaBakedGlyph(ResourceLocation location, Rectangle bounds, float minU, float minV, float maxU, float maxV, GlyphDescription description) {
        this(false, location, bounds, minU, minV, maxU, maxV, description);
    }

    public VanillaBakedGlyph(boolean space, ResourceLocation location, Rectangle bounds, float minU, float minV, float maxU, float maxV, GlyphDescription description) {
        this.space = space;
        this.location = location;
        this.bounds = bounds;
        this.minU = minU;
        this.minV = minV;
        this.maxU = maxU;
        this.maxV = maxV;
        this.description = description;
        this.textureView = Lazy.of(() -> {
            return GlyphTextureResolver.resolveTexture(this.location);
        });
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph
    public GlyphDescription description() {
        return this.description;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph
    public StyledGlyphRenderable createGlyph(float x, float y, int color, int shadowColor, Style style, float boldOffset, float shadowOffset) {
        if (this.space) {
            return null;
        }
        return new StyledGlyphInstance(this, x, y, color, shadowColor, style, boldOffset, shadowOffset, this::buildVertices);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.EffectGlyph
    public GlyphRenderable createEffect(float left, float top, float right, float bottom, float depth, int color, int shadowColor, float shadowOffset) {
        return new EffectInstance(this, left, top, right, bottom, depth, color, shadowColor, shadowOffset, this::buildEffectVertices);
    }

    private void buildVertices(Matrix4f pose, VertexConsumer consumer, float x, float y, float depth, int color, int lightCoords, boolean italic, GlyphProperties properties) {
        Rectangle bounds = this.bounds;
        float left = x + bounds.getLeft();
        float top = y + bounds.getTop();
        float right = x + bounds.getRight();
        float bottom = y + bounds.getBottom();
        float minU = this.minU;
        float minV = this.minV;
        float maxU = this.maxU;
        float maxV = this.maxV;
        float shearTop = italic ? shearTop() : 0.0f;
        float shearBottom = italic ? shearBottom() : 0.0f;
        consumer.addVertex(pose, left + shearTop, top, depth).setUv(minU, minV).setColor(color);
        consumer.addVertex(pose, left + shearBottom, bottom, depth).setUv(minU, maxV).setColor(color);
        consumer.addVertex(pose, right + shearBottom, bottom, depth).setUv(maxU, maxV).setColor(color);
        consumer.addVertex(pose, right + shearTop, top, depth).setUv(maxU, minV).setColor(color);
    }

    private void buildEffectVertices(EffectInstance<?> effect, Matrix4f pose, VertexConsumer consumer, float offset, float depth, int color, int lightCoords) {
        float left = effect.left() + offset;
        float top = effect.top() + offset;
        float right = effect.right() + offset;
        float bottom = effect.bottom() + offset;
        consumer.addVertex(pose, left, top, depth).setBlankUv().setColor(color);
        consumer.addVertex(pose, left, bottom, depth).setBlankUv().setColor(color);
        consumer.addVertex(pose, right, bottom, depth).setBlankUv().setColor(color);
        consumer.addVertex(pose, right, top, depth).setBlankUv().setColor(color);
    }

    private float shearTop() {
        return 1.0f - (0.25f * this.bounds.getTop());
    }

    private float shearBottom() {
        return 1.0f - (0.25f * this.bounds.getBottom());
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderResources
    public Material material(int flags) {
        return LevelMaterial.builder(RenderStates.GUI_TEXTURED).setTexture(0, this.location).useLightmap().build();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderResources
    public RenderState guiRenderState() {
        return RenderStates.GUI_TEXTURED;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderResources
    public DeviceTextureView textureView() {
        return this.textureView.get();
    }
}
