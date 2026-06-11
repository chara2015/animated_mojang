package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/util/GlColorAlphaModifier.class */
public final class GlColorAlphaModifier {
    private static boolean modifiedAlpha;
    private static float alpha;

    private GlColorAlphaModifier() {
    }

    public static boolean isModifiedAlpha() {
        return modifiedAlpha;
    }

    public static float getAlpha() {
        return alpha;
    }

    public static void setAlpha(float alpha2) {
        modifiedAlpha = alpha2 < 1.0f;
        alpha = alpha2;
    }
}
