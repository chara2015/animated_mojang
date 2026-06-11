package net.labymod.core.laby3d.opengl;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/GlVertexAttrib.class */
public class GlVertexAttrib {
    private final int index;
    private boolean enabled;

    public GlVertexAttrib(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled() {
        setEnabled(true);
    }

    public void setDisabled() {
        setEnabled(false);
    }

    private void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
