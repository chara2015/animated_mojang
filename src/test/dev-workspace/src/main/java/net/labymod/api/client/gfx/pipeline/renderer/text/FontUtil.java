package net.labymod.api.client.gfx.pipeline.renderer.text;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/FontUtil.class */
public final class FontUtil {
    private static boolean emitFakeGlyphs;

    private FontUtil() {
    }

    public static boolean isEmitFakeGlyphs() {
        return emitFakeGlyphs;
    }

    public static void setEmitFakeGlyphs(boolean emitFakeGlyphs2) {
        emitFakeGlyphs = emitFakeGlyphs2;
    }
}
