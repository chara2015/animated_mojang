package net.labymod.api.event.client.entity.player;

import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/entity/player/FieldOfViewTickEvent.class */
public class FieldOfViewTickEvent implements Event {
    private final float originalFov;
    private final float originalOldFov;
    private final int tick;
    private float fov;
    private float oldFov;
    private float modifier;
    private boolean overwriteVanilla = false;

    public FieldOfViewTickEvent(float fov, float oldFov, float modifier, int tick) {
        this.tick = tick;
        this.fov = fov;
        this.oldFov = oldFov;
        this.modifier = modifier;
        this.originalFov = fov;
        this.originalOldFov = oldFov;
    }

    public float getFov() {
        return this.fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getOldFov() {
        return this.oldFov;
    }

    public void setOldFov(float oldFov) {
        this.oldFov = oldFov;
    }

    public float getModifier() {
        return this.modifier;
    }

    public void setModifier(float modifier) {
        this.modifier = modifier;
    }

    public boolean isOverwriteVanilla() {
        return this.overwriteVanilla;
    }

    public void setOverwriteVanilla(boolean overwriteVanilla) {
        this.overwriteVanilla = overwriteVanilla;
    }

    public float getOriginalFov() {
        return this.originalFov;
    }

    public float getOriginalOldFov() {
        return this.originalOldFov;
    }

    public int getTick() {
        return this.tick;
    }
}
