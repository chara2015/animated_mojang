package com.mojang.blaze3d.systems;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/systems/ScissorState.class */
public class ScissorState {
    private boolean enabled;
    private int x;
    private int y;
    private int width;
    private int height;

    public void enable(int $$0, int $$1, int $$2, int $$3) {
        this.enabled = true;
        this.x = $$0;
        this.y = $$1;
        this.width = $$2;
        this.height = $$3;
    }

    public void disable() {
        this.enabled = false;
    }

    public boolean enabled() {
        return this.enabled;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }
}
