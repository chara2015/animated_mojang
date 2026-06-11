package net.labymod.core.laby3d.opengl;

import net.labymod.api.client.gfx.GlConst;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/GlAlphaTestState.class */
public class GlAlphaTestState extends GlBooleanState {
    private int func;
    private float ref;

    public GlAlphaTestState() {
        super(GlConst.GL_ALPHA_TEST);
        this.func = GlConst.GL_ALWAYS;
        this.ref = 0.0f;
    }

    public int getFunc() {
        return this.func;
    }

    public void setFunc(int func) {
        this.func = func;
    }

    public float getRef() {
        return this.ref;
    }

    public void setRef(float ref) {
        this.ref = ref;
    }
}
