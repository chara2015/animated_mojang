package net.labymod.core.laby3d.opengl;

import net.labymod.api.client.gfx.GlConst;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/GlDepthTestState.class */
public class GlDepthTestState extends GlBooleanState {
    private int func;
    private boolean writeEnabled;

    public GlDepthTestState() {
        super(GlConst.GL_DEPTH_TEST);
        this.func = GlConst.GL_LESS;
        this.writeEnabled = true;
    }

    public int getFunc() {
        return this.func;
    }

    public void setFunc(int func) {
        this.func = func;
    }

    public boolean isWriteEnabled() {
        return this.writeEnabled;
    }

    public void setWriteEnabled(boolean writeEnabled) {
        this.writeEnabled = writeEnabled;
    }
}
