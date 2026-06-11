package org.lwjgl.opengl;

/* JADX INFO: loaded from: LabyMod-4.jar:org/lwjgl/opengl/DisplayMode.class */
public final class DisplayMode {
    private int width;
    private int height;
    private int bpp;
    private int freq;
    private final boolean fullscreen;

    public DisplayMode(int width, int height) {
        this(width, height, 0, 0, false);
    }

    DisplayMode(int width, int height, int bpp, int freq) {
        this(width, height, bpp, freq, false);
    }

    private DisplayMode(int width, int height, int bpp, int freq, boolean fullscreen) {
        this.width = width;
        this.height = height;
        this.bpp = bpp;
        this.freq = freq;
        this.fullscreen = fullscreen;
    }

    public DisplayMode(DisplayMode other) {
        this.width = other.width;
        this.height = other.height;
        this.bpp = other.bpp;
        this.freq = other.freq;
        this.fullscreen = other.fullscreen;
    }

    public boolean isFullscreenCapable() {
        return this.fullscreen;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBitsPerPixel() {
        return this.bpp;
    }

    public void setBitsPerPixel(int bpp) {
        this.bpp = bpp;
    }

    public int getFrequency() {
        return this.freq;
    }

    public void setFrequency(int freq) {
        this.freq = freq;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof DisplayMode)) {
            return false;
        }
        DisplayMode dm = (DisplayMode) obj;
        return dm.width == this.width && dm.height == this.height && dm.bpp == this.bpp && dm.freq == this.freq;
    }

    public int hashCode() {
        return ((this.width ^ this.height) ^ this.freq) ^ this.bpp;
    }

    public String toString() {
        return this.width + " x " + this.height + " x " + this.bpp + " @" + this.freq + "Hz";
    }
}
