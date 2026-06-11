package net.labymod.core.laby3d.opengl;

import net.labymod.api.client.gfx.GlConst;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/GlBlendState.class */
public class GlBlendState extends GlBooleanState {
    private int equationRgb;
    private int equationAlpha;
    private int srcRgb;
    private int dstRgb;
    private int srcAlpha;
    private int dstAlpha;

    public GlBlendState() {
        super(GlConst.GL_BLEND);
        this.equationRgb = GlConst.GL_FUNC_ADD;
        this.equationAlpha = GlConst.GL_FUNC_ADD;
        this.srcRgb = 1;
        this.dstRgb = 0;
        this.srcAlpha = 1;
        this.dstAlpha = 0;
    }

    public int getEquationRgb() {
        return this.equationRgb;
    }

    public void setEquationRgb(int equationRgb) {
        this.equationRgb = equationRgb;
    }

    public int getEquationAlpha() {
        return this.equationAlpha;
    }

    public void setEquationAlpha(int equationAlpha) {
        this.equationAlpha = equationAlpha;
    }

    public int getSrcRgb() {
        return this.srcRgb;
    }

    public void setSrcRgb(int srcRgb) {
        this.srcRgb = srcRgb;
    }

    public int getDstRgb() {
        return this.dstRgb;
    }

    public void setDstRgb(int dstRgb) {
        this.dstRgb = dstRgb;
    }

    public int getSrcAlpha() {
        return this.srcAlpha;
    }

    public void setSrcAlpha(int srcAlpha) {
        this.srcAlpha = srcAlpha;
    }

    public int getDstAlpha() {
        return this.dstAlpha;
    }

    public void setDstAlpha(int dstAlpha) {
        this.dstAlpha = dstAlpha;
    }
}
