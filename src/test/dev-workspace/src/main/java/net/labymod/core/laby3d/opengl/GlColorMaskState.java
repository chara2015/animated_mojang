package net.labymod.core.laby3d.opengl;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/GlColorMaskState.class */
public class GlColorMaskState {
    private boolean red = true;
    private boolean green = true;
    private boolean blue = true;
    private boolean alpha = true;

    public boolean isRed() {
        return this.red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public boolean isGreen() {
        return this.green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }

    public boolean isBlue() {
        return this.blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public boolean isAlpha() {
        return this.alpha;
    }

    public void setAlpha(boolean alpha) {
        this.alpha = alpha;
    }
}
