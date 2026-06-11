package net.labymod.core.laby3d.opengl;

import net.labymod.api.client.gfx.GlConst;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/GlColorLogicOpState.class */
public class GlColorLogicOpState extends GlBooleanState {
    private int mode;

    public GlColorLogicOpState() {
        super(GlConst.GL_COLOR_LOGIC_OP);
        this.mode = GlConst.GL_COPY;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
