package net.labymod.v1_21_3.client.gfx.pipeline.renderer.text;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance.class */
public final class GlyphInstance extends Record {
    private final float x;
    private final float y;
    private final int argb;
    private final int shadowArgb;
    private final frc glyph;
    private final ys style;
    private final float boldOffset;
    private final float shadowOffset;

    public GlyphInstance(float x, float y, int argb, int shadowArgb, frc glyph, ys style, float boldOffset, float shadowOffset) {
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
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GlyphInstance.class), GlyphInstance.class, "x;y;argb;shadowArgb;glyph;style;boldOffset;shadowOffset", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->x:F", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->y:F", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->argb:I", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->shadowArgb:I", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->glyph:Lfrc;", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->style:Lys;", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->boldOffset:F", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->shadowOffset:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GlyphInstance.class), GlyphInstance.class, "x;y;argb;shadowArgb;glyph;style;boldOffset;shadowOffset", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->x:F", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->y:F", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->argb:I", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->shadowArgb:I", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->glyph:Lfrc;", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->style:Lys;", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->boldOffset:F", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->shadowOffset:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GlyphInstance.class, Object.class), GlyphInstance.class, "x;y;argb;shadowArgb;glyph;style;boldOffset;shadowOffset", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->x:F", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->y:F", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->argb:I", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->shadowArgb:I", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->glyph:Lfrc;", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->style:Lys;", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->boldOffset:F", "FIELD:Lnet/labymod/v1_21_3/client/gfx/pipeline/renderer/text/GlyphInstance;->shadowOffset:F").dynamicInvoker().invoke(this, o) /* invoke-custom */;
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

    public frc glyph() {
        return this.glyph;
    }

    public ys style() {
        return this.style;
    }

    public float boldOffset() {
        return this.boldOffset;
    }

    public float shadowOffset() {
        return this.shadowOffset;
    }
}
