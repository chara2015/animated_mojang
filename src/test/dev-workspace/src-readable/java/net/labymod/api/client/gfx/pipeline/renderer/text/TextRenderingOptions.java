package net.labymod.api.client.gfx.pipeline.renderer.text;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/TextRenderingOptions.class */
public final class TextRenderingOptions {
    public static final int NONE = 0;
    public static final int SHADOW = 1;
    public static final int CENTERED = 2;
    public static final int USE_FLOATING_POINT_VALUES = 4;

    public static boolean hasFlag(int flags, int flag) {
        return (flags & flag) == flag;
    }

    public static boolean isCentered(int flags) {
        return hasFlag(flags, 2);
    }

    public static boolean isFloatingPointValues(int flags) {
        return hasFlag(flags, 4);
    }

    public static boolean hasShadow(int flags) {
        return hasFlag(flags, 1);
    }
}
