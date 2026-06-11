package net.labymod.core.laby3d.opengl;

import net.labymod.api.client.gfx.GlConst;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/GlScissorTestState.class */
public class GlScissorTestState extends GlBooleanState {
    private int x;
    private int y;
    private int width;
    private int height;

    public GlScissorTestState() {
        super(GlConst.GL_SCISSOR_TEST);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
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
}
