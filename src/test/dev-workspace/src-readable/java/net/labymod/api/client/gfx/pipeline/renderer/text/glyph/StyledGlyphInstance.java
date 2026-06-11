package net.labymod.api.client.gfx.pipeline.renderer.text.glyph;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderResources;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance.class */
public final class StyledGlyphInstance<T extends BakedGlyph & GlyphRenderResources> extends Record implements StyledGlyphRenderable {
    private final T glyph;
    private final float x;
    private final float y;
    private final int color;
    private final int shadowColor;
    private final Style style;
    private final float boldOffset;
    private final float shadowOffset;
    private final GlyphVertexEmitter emitter;
    static final float Z_FIGHTER = 0.001f;
    static final float DEPTH_ADJUSTMENT = 0.03f;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance$GlyphVertexEmitter.class */
    @FunctionalInterface
    public interface GlyphVertexEmitter {
        void emit(Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float f2, float f3, int i, int i2, boolean z, GlyphProperties glyphProperties);
    }

    public StyledGlyphInstance(T glyph, float x, float y, int color, int shadowColor, Style style, float boldOffset, float shadowOffset, GlyphVertexEmitter emitter) {
        this.glyph = glyph;
        this.x = x;
        this.y = y;
        this.color = color;
        this.shadowColor = shadowColor;
        this.style = style;
        this.boldOffset = boldOffset;
        this.shadowOffset = shadowOffset;
        this.emitter = emitter;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, StyledGlyphInstance.class), StyledGlyphInstance.class, "glyph;x;y;color;shadowColor;style;boldOffset;shadowOffset;emitter", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->glyph:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/BakedGlyph;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->x:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->y:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->color:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->shadowColor:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->style:Lnet/labymod/api/client/component/format/Style;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->boldOffset:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->shadowOffset:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->emitter:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance$GlyphVertexEmitter;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StyledGlyphInstance.class), StyledGlyphInstance.class, "glyph;x;y;color;shadowColor;style;boldOffset;shadowOffset;emitter", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->glyph:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/BakedGlyph;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->x:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->y:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->color:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->shadowColor:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->style:Lnet/labymod/api/client/component/format/Style;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->boldOffset:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->shadowOffset:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->emitter:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance$GlyphVertexEmitter;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StyledGlyphInstance.class, Object.class), StyledGlyphInstance.class, "glyph;x;y;color;shadowColor;style;boldOffset;shadowOffset;emitter", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->glyph:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/BakedGlyph;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->x:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->y:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->color:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->shadowColor:I", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->style:Lnet/labymod/api/client/component/format/Style;", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->boldOffset:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->shadowOffset:F", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance;->emitter:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/StyledGlyphInstance$GlyphVertexEmitter;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public T glyph() {
        return this.glyph;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public int color() {
        return this.color;
    }

    public int shadowColor() {
        return this.shadowColor;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable
    public Style style() {
        return this.style;
    }

    public float boldOffset() {
        return this.boldOffset;
    }

    public float shadowOffset() {
        return this.shadowOffset;
    }

    public GlyphVertexEmitter emitter() {
        return this.emitter;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable
    public Material material(int flags) {
        return this.glyph.material(flags);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable
    public RenderState guiRenderState() {
        return this.glyph.guiRenderState();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable
    public DeviceTextureView textureView() {
        return this.glyph.textureView();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable
    public void buildVertices(Matrix4f pose, VertexConsumer consumer, int lightCoords, boolean ignoreDepthOffset, GlyphProperties properties) {
        float depth;
        Style style = style();
        boolean italic = style.hasDecoration(TextDecoration.ITALIC);
        float x = x();
        float y = y();
        int color = color();
        boolean bold = style.hasDecoration(TextDecoration.BOLD);
        float zFighter = ignoreDepthOffset ? 0.0f : Z_FIGHTER;
        if (hasShadow()) {
            int shadowColor = shadowColor();
            float shadowOffset = shadowOffset();
            float shadowX = x + shadowOffset;
            float shadowY = y + shadowOffset;
            this.emitter.emit(pose, consumer, shadowX, shadowY, 0.0f, shadowColor, lightCoords, italic, properties);
            if (bold) {
                this.emitter.emit(pose, consumer, shadowX + boldOffset(), shadowY, zFighter, shadowColor, lightCoords, italic, properties);
            }
            depth = ignoreDepthOffset ? 0.0f : DEPTH_ADJUSTMENT;
        } else {
            depth = 0.0f;
        }
        this.emitter.emit(pose, consumer, x, y, depth, color, lightCoords, italic, properties);
        if (bold) {
            this.emitter.emit(pose, consumer, x + boldOffset(), y, depth + zFighter, color, lightCoords, italic, properties);
        }
    }

    boolean hasShadow() {
        return shadowColor() != 0;
    }
}
