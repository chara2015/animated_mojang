package net.labymod.core.laby3d.opengl;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/GlFramebufferState.class */
public class GlFramebufferState {
    private int drawFramebuffer;
    private int readFramebuffer;

    public int getDrawFramebuffer() {
        return this.drawFramebuffer;
    }

    public void setDrawFramebuffer(int drawFramebuffer) {
        this.drawFramebuffer = drawFramebuffer;
    }

    public int getReadFramebuffer() {
        return this.readFramebuffer;
    }

    public void setReadFramebuffer(int readFramebuffer) {
        this.readFramebuffer = readFramebuffer;
    }
}
