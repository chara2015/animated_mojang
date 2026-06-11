package net.labymod.v1_19_4.client.gfx.pipeline.renderer.text;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.util.color.format.ColorFormat;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance.class */
public final class GlyphInstance extends Record {
    private final float x;
    private final float y;
    private final int argb;
    private final int shadowArgb;
    private final eqh glyph;
    private final Style style;
    private final float boldOffset;
    private final float shadowOffset;
    private static final float SHADOW_OFFSET = 1.0f;
    private static final float NORMAL_OFFSET = 0.0f;

    public GlyphInstance(float x, float y, int argb, int shadowArgb, eqh glyph, Style style, float boldOffset, float shadowOffset) {
        this.x = x;
        this.y = y;
        this.argb = argb;
        this.shadowArgb = shadowArgb;
        this.glyph = glyph;
        this.style = style;
        this.boldOffset = boldOffset;
        this.shadowOffset = shadowOffset;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GlyphInstance.class), GlyphInstance.class, "x;y;argb;shadowArgb;glyph;style;boldOffset;shadowOffset", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->x:F", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->y:F", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->argb:I", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->shadowArgb:I", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->glyph:Leqh;", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->style:Lnet/labymod/api/client/component/format/Style;", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->boldOffset:F", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->shadowOffset:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GlyphInstance.class), GlyphInstance.class, "x;y;argb;shadowArgb;glyph;style;boldOffset;shadowOffset", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->x:F", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->y:F", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->argb:I", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->shadowArgb:I", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->glyph:Leqh;", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->style:Lnet/labymod/api/client/component/format/Style;", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->boldOffset:F", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->shadowOffset:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GlyphInstance.class, Object.class), GlyphInstance.class, "x;y;argb;shadowArgb;glyph;style;boldOffset;shadowOffset", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->x:F", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->y:F", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->argb:I", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->shadowArgb:I", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->glyph:Leqh;", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->style:Lnet/labymod/api/client/component/format/Style;", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->boldOffset:F", "FIELD:Lnet/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphInstance;->shadowOffset:F").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public int argb() {
        return this.argb;
    }

    public int shadowArgb() {
        return this.shadowArgb;
    }

    public eqh glyph() {
        return this.glyph;
    }

    public Style style() {
        return this.style;
    }

    public float boldOffset() {
        return this.boldOffset;
    }

    public float shadowOffset() {
        return this.shadowOffset;
    }

    public GlyphInstance(float x, float y, int argb, int shadowArgb, eqh glyph, Style style, float boldOffset) {
        this(x, y, argb, shadowArgb, glyph, style, boldOffset, SHADOW_OFFSET);
    }

    void render(ehi consumer, Matrix4f pose) {
        ColorFormat format = ColorFormat.ARGB32;
        float red = format.normalizedRed(argb());
        float green = format.normalizedGreen(argb());
        float blue = format.normalizedBlue(argb());
        float alpha = format.normalizedAlpha(argb());
        boolean italic = this.style.hasDecoration(TextDecoration.ITALIC);
        boolean bold = this.style.hasDecoration(TextDecoration.BOLD);
        if (hasShadow()) {
            float shadowRed = format.normalizedRed(shadowArgb());
            float shadowGreen = format.normalizedGreen(shadowArgb());
            float shadowBlue = format.normalizedBlue(shadowArgb());
            float shadowAlpha = format.normalizedAlpha(shadowArgb());
            renderGlyph(consumer, pose, shadowOffset(), italic, bold, shadowRed, shadowGreen, shadowBlue, shadowAlpha);
        }
        renderGlyph(consumer, pose, 0.0f, italic, bold, red, green, blue, alpha);
    }

    private void renderGlyph(ehi consumer, Matrix4f pose, float offset, boolean italic, boolean bold, float red, float green, float blue, float alpha) {
        this.glyph.a(italic, x() + offset, y() + offset, pose, consumer, red, green, blue, alpha, RenderEnvironmentContext.FULL_BRIGHT);
        if (bold) {
            this.glyph.a(italic, x() + offset + boldOffset(), y() + offset, pose, consumer, red, green, blue, alpha, RenderEnvironmentContext.FULL_BRIGHT);
        }
    }

    boolean hasShadow() {
        return this.shadowArgb != 0;
    }
}
