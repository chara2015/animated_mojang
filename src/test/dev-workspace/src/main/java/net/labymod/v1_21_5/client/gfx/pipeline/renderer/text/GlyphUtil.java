package net.labymod.v1_21_5.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.gfx.pipeline.renderer.text.FontUtil;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/gfx/pipeline/renderer/text/GlyphUtil.class */
public final class GlyphUtil {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/gfx/pipeline/renderer/text/GlyphUtil$Emitter.class */
    @FunctionalInterface
    public interface Emitter {
        void emit(boolean z, float f, float f2, float f3, Matrix4f matrix4f, flg flgVar, int i, boolean z2, int i2);
    }

    public static void emitGlyph(b instance, Matrix4f pose, flg consumer, int lightCoords, boolean ignoreDepthOffset, Emitter emitter) {
        yd style = instance.f();
        boolean italic = style.d();
        float x = instance.a();
        float y = instance.b();
        int argb = instance.c();
        boolean bold = style.c();
        int shadowArgb = instance.d();
        float zFightOffset = ignoreDepthOffset ? 0.0f : 0.001f;
        float baseDepth = 0.0f;
        boolean hasShadow = shadowArgb != 0;
        boolean emitFake = FontUtil.isEmitFakeGlyphs();
        if (hasShadow) {
            float shadowX = x + instance.h();
            float shadowY = y + instance.h();
            emitter.emit(italic, shadowX, shadowY, 0.0f, pose, consumer, shadowArgb, bold, lightCoords);
            if (emitFake) {
                emitter.emit(italic, shadowX, shadowY, 0.0f, pose, consumer, 0, bold, lightCoords);
            }
            if (bold) {
                float shadowBoldX = shadowX + instance.g();
                emitter.emit(italic, shadowBoldX, shadowY, zFightOffset, pose, consumer, shadowArgb, true, lightCoords);
                if (emitFake) {
                    emitter.emit(italic, shadowBoldX, shadowY, zFightOffset, pose, consumer, 0, true, lightCoords);
                }
            }
            baseDepth = ignoreDepthOffset ? 0.0f : 0.03f;
        }
        if (emitFake) {
            emitter.emit(italic, x, y, baseDepth, pose, consumer, argb, bold, lightCoords);
        }
        emitter.emit(italic, x, y, baseDepth, pose, consumer, argb, bold, lightCoords);
        if (bold) {
            if (emitFake) {
                emitter.emit(italic, x + instance.g(), y, baseDepth + zFightOffset, pose, consumer, argb, true, lightCoords);
            }
            emitter.emit(italic, x + instance.g(), y, baseDepth + zFightOffset, pose, consumer, argb, true, lightCoords);
        }
    }
}
