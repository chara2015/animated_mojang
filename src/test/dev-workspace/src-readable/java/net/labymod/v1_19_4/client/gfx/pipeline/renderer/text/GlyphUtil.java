package net.labymod.v1_19_4.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontUtil;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphUtil.class */
public final class GlyphUtil {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/gfx/pipeline/renderer/text/GlyphUtil$Emitter.class */
    @FunctionalInterface
    public interface Emitter {
        void emit(boolean z, float f, float f2, float f3, Matrix4f matrix4f, ehi ehiVar, int i, boolean z2, int i2);
    }

    public static void emitGlyph(GlyphInstance instance, Matrix4f pose, ehi consumer, int lightCoords, boolean ignoreDepthOffset, Emitter emitter) {
        Style style = instance.style();
        boolean italic = style.hasDecoration(TextDecoration.ITALIC);
        float x = instance.x();
        float y = instance.y();
        int argb = instance.argb();
        boolean bold = style.hasDecoration(TextDecoration.BOLD);
        int shadowArgb = instance.shadowArgb();
        float zFightOffset = ignoreDepthOffset ? 0.0f : 0.001f;
        float baseDepth = 0.0f;
        boolean hasShadow = shadowArgb != 0;
        boolean emitFake = FontUtil.isEmitFakeGlyphs();
        if (hasShadow) {
            float shadowX = x + instance.shadowOffset();
            float shadowY = y + instance.shadowOffset();
            emitter.emit(italic, shadowX, shadowY, 0.0f, pose, consumer, shadowArgb, bold, lightCoords);
            if (emitFake) {
                emitter.emit(italic, shadowX, shadowY, 0.0f, pose, consumer, 0, bold, lightCoords);
            }
            if (bold) {
                float shadowBoldX = shadowX + instance.boldOffset();
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
                emitter.emit(italic, x + instance.boldOffset(), y, baseDepth + zFightOffset, pose, consumer, argb, true, lightCoords);
            }
            emitter.emit(italic, x + instance.boldOffset(), y, baseDepth + zFightOffset, pose, consumer, argb, true, lightCoords);
        }
    }
}
