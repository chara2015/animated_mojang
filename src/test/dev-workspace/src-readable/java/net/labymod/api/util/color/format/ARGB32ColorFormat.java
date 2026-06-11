package net.labymod.api.util.color.format;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/color/format/ARGB32ColorFormat.class */
public class ARGB32ColorFormat implements ColorFormat {
    private static final int ALPHA_COMPONENT_OFFSET = 24;
    private static final int RED_COMPONENT_OFFSET = 16;
    private static final int GREEN_COMPONENT_OFFSET = 8;
    private static final int BLUE_COMPONENT_OFFSET = 0;

    @Override // net.labymod.api.util.color.format.ColorFormat
    public int red(int value) {
        return (value >> 16) & 255;
    }

    @Override // net.labymod.api.util.color.format.ColorFormat
    public int green(int value) {
        return (value >> 8) & 255;
    }

    @Override // net.labymod.api.util.color.format.ColorFormat
    public int blue(int value) {
        return (value >> 0) & 255;
    }

    @Override // net.labymod.api.util.color.format.ColorFormat
    public int alpha(int value) {
        return (value >> 24) & 255;
    }

    @Override // net.labymod.api.util.color.format.ColorFormat
    public int pack(int red, int green, int blue, int alpha) {
        return ((alpha & 255) << 24) | ((red & 255) << 16) | ((green & 255) << 8) | ((blue & 255) << 0);
    }

    @Override // net.labymod.api.util.color.format.ColorFormat
    public int withAlpha(int value, int alpha) {
        return (value & 16777215) | ((alpha & 255) << 24);
    }
}
