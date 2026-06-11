package net.labymod.api.client.render;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/GraphicsColor.class */
public class GraphicsColor {
    public static final GraphicsColor DEFAULT_COLOR = new GraphicsColor(-1);
    private int color;
    private int shadowColor;

    public GraphicsColor(int color) {
        updateColor(color);
    }

    public GraphicsColor update(int color) {
        updateColor(color);
        return this;
    }

    public float red() {
        return red(false);
    }

    public float shadowRed() {
        return red(true);
    }

    public float red(boolean shadow) {
        return ((color(shadow) >> 16) & 255) / 255.0f;
    }

    public float green() {
        return green(false);
    }

    public float shadowGreen() {
        return green(true);
    }

    public float green(boolean shadow) {
        return ((color(shadow) >> 8) & 255) / 255.0f;
    }

    public float blue() {
        return blue(false);
    }

    public float shadowBlue() {
        return blue(true);
    }

    public float blue(boolean shadow) {
        return (color(shadow) & 255) / 255.0f;
    }

    public float alpha() {
        return alpha(false);
    }

    public float shadowAlpha() {
        return alpha(true);
    }

    public float alpha(boolean shadow) {
        return ((color(shadow) >> 24) & 255) / 255.0f;
    }

    public int color() {
        return color(false);
    }

    public int shadowColor() {
        return color(true);
    }

    public int color(boolean shadow) {
        return shadow ? this.shadowColor : this.color;
    }

    private void updateColor(int color) {
        this.color = color;
        this.shadowColor = ((color & 16579836) >> 2) | (color & (-16777216));
    }
}
