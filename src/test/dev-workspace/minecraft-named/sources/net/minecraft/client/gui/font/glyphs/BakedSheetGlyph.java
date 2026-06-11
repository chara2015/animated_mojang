package net.minecraft.client.gui.font.glyphs;

import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.GlyphRenderTypes;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.network.chat.Style;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/glyphs/BakedSheetGlyph.class */
public class BakedSheetGlyph implements BakedGlyph, EffectGlyph {
    public static final float Z_FIGHTER = 0.001f;
    final GlyphInfo info;
    final GlyphRenderTypes renderTypes;
    final GpuTextureView textureView;
    private final float u0;
    private final float u1;
    private final float v0;
    private final float v1;
    private final float left;
    private final float right;
    private final float up;
    private final float down;

    public BakedSheetGlyph(GlyphInfo $$0, GlyphRenderTypes $$1, GpuTextureView $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9, float $$10) {
        this.info = $$0;
        this.renderTypes = $$1;
        this.textureView = $$2;
        this.u0 = $$3;
        this.u1 = $$4;
        this.v0 = $$5;
        this.v1 = $$6;
        this.left = $$7;
        this.right = $$8;
        this.up = $$9;
        this.down = $$10;
    }

    float left(GlyphInstance $$0) {
        return (($$0.x + this.left) + ($$0.style.isItalic() ? Math.min(shearTop(), shearBottom()) : 0.0f)) - extraThickness($$0.style.isBold());
    }

    float top(GlyphInstance $$0) {
        return ($$0.y + this.up) - extraThickness($$0.style.isBold());
    }

    float right(GlyphInstance $$0) {
        return $$0.x + this.right + ($$0.hasShadow() ? $$0.shadowOffset : 0.0f) + ($$0.style.isItalic() ? Math.max(shearTop(), shearBottom()) : 0.0f) + extraThickness($$0.style.isBold());
    }

    float bottom(GlyphInstance $$0) {
        return $$0.y + this.down + ($$0.hasShadow() ? $$0.shadowOffset : 0.0f) + extraThickness($$0.style.isBold());
    }

    void renderChar(GlyphInstance $$0, Matrix4f $$1, VertexConsumer $$2, int $$3, boolean $$4) {
        float $$14;
        Style $$5 = $$0.style();
        boolean $$6 = $$5.isItalic();
        float $$7 = $$0.x();
        float $$8 = $$0.y();
        int $$9 = $$0.color();
        boolean $$10 = $$5.isBold();
        float $$11 = $$4 ? 0.0f : 0.001f;
        if ($$0.hasShadow()) {
            int $$12 = $$0.shadowColor();
            render($$6, $$7 + $$0.shadowOffset(), $$8 + $$0.shadowOffset(), 0.0f, $$1, $$2, $$12, $$10, $$3);
            if ($$10) {
                render($$6, $$7 + $$0.boldOffset() + $$0.shadowOffset(), $$8 + $$0.shadowOffset(), $$11, $$1, $$2, $$12, true, $$3);
            }
            $$14 = $$4 ? 0.0f : 0.03f;
        } else {
            $$14 = 0.0f;
        }
        render($$6, $$7, $$8, $$14, $$1, $$2, $$9, $$10, $$3);
        if ($$10) {
            render($$6, $$7 + $$0.boldOffset(), $$8, $$14 + $$11, $$1, $$2, $$9, true, $$3);
        }
    }

    private void render(boolean $$0, float $$1, float $$2, float $$3, Matrix4f $$4, VertexConsumer $$5, int $$6, boolean $$7, int $$8) {
        float $$9 = $$1 + this.left;
        float $$10 = $$1 + this.right;
        float $$11 = $$2 + this.up;
        float $$12 = $$2 + this.down;
        float $$13 = $$0 ? shearTop() : 0.0f;
        float $$14 = $$0 ? shearBottom() : 0.0f;
        float $$15 = extraThickness($$7);
        $$5.addVertex((Matrix4fc) $$4, ($$9 + $$13) - $$15, $$11 - $$15, $$3).setColor($$6).setUv(this.u0, this.v0).setLight($$8);
        $$5.addVertex((Matrix4fc) $$4, ($$9 + $$14) - $$15, $$12 + $$15, $$3).setColor($$6).setUv(this.u0, this.v1).setLight($$8);
        $$5.addVertex((Matrix4fc) $$4, $$10 + $$14 + $$15, $$12 + $$15, $$3).setColor($$6).setUv(this.u1, this.v1).setLight($$8);
        $$5.addVertex((Matrix4fc) $$4, $$10 + $$13 + $$15, $$11 - $$15, $$3).setColor($$6).setUv(this.u1, this.v0).setLight($$8);
    }

    private static float extraThickness(boolean $$0) {
        return $$0 ? 0.1f : 0.0f;
    }

    private float shearBottom() {
        return 1.0f - (0.25f * this.down);
    }

    private float shearTop() {
        return 1.0f - (0.25f * this.up);
    }

    void renderEffect(EffectInstance $$0, Matrix4f $$1, VertexConsumer $$2, int $$3, boolean $$4) {
        float $$5 = $$4 ? 0.0f : $$0.depth;
        if ($$0.hasShadow()) {
            buildEffect($$0, $$0.shadowOffset(), $$5, $$0.shadowColor(), $$2, $$3, $$1);
            $$5 += $$4 ? 0.0f : 0.03f;
        }
        buildEffect($$0, 0.0f, $$5, $$0.color, $$2, $$3, $$1);
    }

    private void buildEffect(EffectInstance $$0, float $$1, float $$2, int $$3, VertexConsumer $$4, int $$5, Matrix4f $$6) {
        $$4.addVertex((Matrix4fc) $$6, $$0.x0 + $$1, $$0.y1 + $$1, $$2).setColor($$3).setUv(this.u0, this.v0).setLight($$5);
        $$4.addVertex((Matrix4fc) $$6, $$0.x1 + $$1, $$0.y1 + $$1, $$2).setColor($$3).setUv(this.u0, this.v1).setLight($$5);
        $$4.addVertex((Matrix4fc) $$6, $$0.x1 + $$1, $$0.y0 + $$1, $$2).setColor($$3).setUv(this.u1, this.v1).setLight($$5);
        $$4.addVertex((Matrix4fc) $$6, $$0.x0 + $$1, $$0.y0 + $$1, $$2).setColor($$3).setUv(this.u1, this.v0).setLight($$5);
    }

    @Override // net.minecraft.client.gui.font.glyphs.BakedGlyph
    public GlyphInfo info() {
        return this.info;
    }

    @Override // net.minecraft.client.gui.font.glyphs.BakedGlyph
    public TextRenderable.Styled createGlyph(float $$0, float $$1, int $$2, int $$3, Style $$4, float $$5, float $$6) {
        return new GlyphInstance($$0, $$1, $$2, $$3, this, $$4, $$5, $$6);
    }

    @Override // net.minecraft.client.gui.font.glyphs.EffectGlyph
    public TextRenderable createEffect(float $$0, float $$1, float $$2, float $$3, float $$4, int $$5, int $$6, float $$7) {
        return new EffectInstance(this, $$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance.class */
    static final class GlyphInstance extends Record implements TextRenderable.Styled {
        private final float x;
        private final float y;
        private final int color;
        private final int shadowColor;
        private final BakedSheetGlyph glyph;
        private final Style style;
        private final float boldOffset;
        private final float shadowOffset;

        GlyphInstance(float $$0, float $$1, int $$2, int $$3, BakedSheetGlyph $$4, Style $$5, float $$6, float $$7) {
            this.x = $$0;
            this.y = $$1;
            this.color = $$2;
            this.shadowColor = $$3;
            this.glyph = $$4;
            this.style = $$5;
            this.boldOffset = $$6;
            this.shadowOffset = $$7;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GlyphInstance.class), GlyphInstance.class, "x;y;color;shadowColor;glyph;style;boldOffset;shadowOffset", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->x:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->y:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->color:I", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->shadowColor:I", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->glyph:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph;", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->style:Lnet/minecraft/network/chat/Style;", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->boldOffset:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->shadowOffset:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GlyphInstance.class), GlyphInstance.class, "x;y;color;shadowColor;glyph;style;boldOffset;shadowOffset", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->x:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->y:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->color:I", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->shadowColor:I", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->glyph:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph;", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->style:Lnet/minecraft/network/chat/Style;", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->boldOffset:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->shadowOffset:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GlyphInstance.class, Object.class), GlyphInstance.class, "x;y;color;shadowColor;glyph;style;boldOffset;shadowOffset", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->x:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->y:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->color:I", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->shadowColor:I", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->glyph:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph;", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->style:Lnet/minecraft/network/chat/Style;", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->boldOffset:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$GlyphInstance;->shadowOffset:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
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

        public BakedSheetGlyph glyph() {
            return this.glyph;
        }

        @Override // net.minecraft.client.gui.font.ActiveArea
        public Style style() {
            return this.style;
        }

        public float boldOffset() {
            return this.boldOffset;
        }

        public float shadowOffset() {
            return this.shadowOffset;
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public float left() {
            return this.glyph.left(this);
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public float top() {
            return this.glyph.top(this);
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public float right() {
            return this.glyph.right(this);
        }

        @Override // net.minecraft.client.gui.font.TextRenderable.Styled, net.minecraft.client.gui.font.ActiveArea
        public float activeRight() {
            return this.x + this.glyph.info.getAdvance(this.style.isBold());
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public float bottom() {
            return this.glyph.bottom(this);
        }

        boolean hasShadow() {
            return shadowColor() != 0;
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public void render(Matrix4f $$0, VertexConsumer $$1, int $$2, boolean $$3) {
            this.glyph.renderChar(this, $$0, $$1, $$2, $$3);
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public RenderType renderType(Font.DisplayMode $$0) {
            return this.glyph.renderTypes.select($$0);
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public GpuTextureView textureView() {
            return this.glyph.textureView;
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public RenderPipeline guiPipeline() {
            return this.glyph.renderTypes.guiPipeline();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance.class */
    static final class EffectInstance extends Record implements TextRenderable {
        private final BakedSheetGlyph glyph;
        private final float x0;
        private final float y0;
        private final float x1;
        private final float y1;
        private final float depth;
        private final int color;
        private final int shadowColor;
        private final float shadowOffset;

        EffectInstance(BakedSheetGlyph $$0, float $$1, float $$2, float $$3, float $$4, float $$5, int $$6, int $$7, float $$8) {
            this.glyph = $$0;
            this.x0 = $$1;
            this.y0 = $$2;
            this.x1 = $$3;
            this.y1 = $$4;
            this.depth = $$5;
            this.color = $$6;
            this.shadowColor = $$7;
            this.shadowOffset = $$8;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EffectInstance.class), EffectInstance.class, "glyph;x0;y0;x1;y1;depth;color;shadowColor;shadowOffset", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->glyph:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph;", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->x0:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->y0:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->x1:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->y1:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->depth:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->color:I", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->shadowColor:I", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->shadowOffset:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EffectInstance.class), EffectInstance.class, "glyph;x0;y0;x1;y1;depth;color;shadowColor;shadowOffset", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->glyph:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph;", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->x0:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->y0:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->x1:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->y1:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->depth:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->color:I", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->shadowColor:I", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->shadowOffset:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EffectInstance.class, Object.class), EffectInstance.class, "glyph;x0;y0;x1;y1;depth;color;shadowColor;shadowOffset", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->glyph:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph;", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->x0:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->y0:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->x1:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->y1:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->depth:F", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->color:I", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->shadowColor:I", "FIELD:Lnet/minecraft/client/gui/font/glyphs/BakedSheetGlyph$EffectInstance;->shadowOffset:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public BakedSheetGlyph glyph() {
            return this.glyph;
        }

        public float x0() {
            return this.x0;
        }

        public float y0() {
            return this.y0;
        }

        public float x1() {
            return this.x1;
        }

        public float y1() {
            return this.y1;
        }

        public float depth() {
            return this.depth;
        }

        public int color() {
            return this.color;
        }

        public int shadowColor() {
            return this.shadowColor;
        }

        public float shadowOffset() {
            return this.shadowOffset;
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public float left() {
            return this.x0;
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public float top() {
            return this.y0;
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public float right() {
            return this.x1 + (hasShadow() ? this.shadowOffset : 0.0f);
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public float bottom() {
            return this.y1 + (hasShadow() ? this.shadowOffset : 0.0f);
        }

        boolean hasShadow() {
            return shadowColor() != 0;
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public void render(Matrix4f $$0, VertexConsumer $$1, int $$2, boolean $$3) {
            this.glyph.renderEffect(this, $$0, $$1, $$2, false);
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public RenderType renderType(Font.DisplayMode $$0) {
            return this.glyph.renderTypes.select($$0);
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public GpuTextureView textureView() {
            return this.glyph.textureView;
        }

        @Override // net.minecraft.client.gui.font.TextRenderable
        public RenderPipeline guiPipeline() {
            return this.glyph.renderTypes.guiPipeline();
        }
    }
}
