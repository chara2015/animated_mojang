package net.labymod.api.client.gfx.pipeline.renderer.text;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/FontFlags.class */
public final class FontFlags {
    public static final int NONE = 0;
    public static final int SHADOW = 1;
    public static final int INVERSE_DEPTH = 2;
    public static final int DISPLAY_MODE_NORMAL = 4;
    public static final int DISPLAY_MODE_SEE_THROUGH = 8;
    public static final int DISPLAY_MODE_POLYGON_OFFSET = 16;

    public static boolean hasFlag(int flags, int flag) {
        return (flags & flag) == flag;
    }

    public static boolean isShadow(int flags) {
        return hasFlag(flags, 1);
    }

    public static boolean isInverseDepth(int flags) {
        return hasFlag(flags, 2);
    }

    public static void ensureOneDisplayMode(int flags) {
        boolean normalModeSet = hasFlag(flags, 4);
        boolean seeThroughModeSet = hasFlag(flags, 8);
        boolean polygonOffsetModeSet = hasFlag(flags, 16);
        if ((normalModeSet && seeThroughModeSet) || ((normalModeSet && polygonOffsetModeSet) || (seeThroughModeSet && polygonOffsetModeSet))) {
            throw new IllegalArgumentException("Multiple display modes set for text. Only one is is allowed");
        }
    }
}
