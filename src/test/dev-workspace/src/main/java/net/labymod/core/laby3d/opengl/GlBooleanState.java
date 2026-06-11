package net.labymod.core.laby3d.opengl;

import org.lwjgl.opengl.GL11;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/opengl/GlBooleanState.class */
public class GlBooleanState {
    private final int target;
    private final boolean updateState;
    private boolean enabled;

    public GlBooleanState(int target) {
        this(target, false);
    }

    public GlBooleanState(int target, boolean updateState) {
        this.target = target;
        this.updateState = updateState;
    }

    public void enable() {
        setEnabled(true);
    }

    public void disable() {
        setEnabled(false);
    }

    public int getTarget() {
        return this.target;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    boolean isUpdateState() {
        return this.updateState;
    }

    private void setEnabled(boolean enabled) {
        if (enabled == this.enabled) {
            return;
        }
        this.enabled = enabled;
        if (this.updateState) {
            if (enabled) {
                GL11.glEnable(this.target);
            } else {
                GL11.glDisable(this.target);
            }
        }
    }
}
