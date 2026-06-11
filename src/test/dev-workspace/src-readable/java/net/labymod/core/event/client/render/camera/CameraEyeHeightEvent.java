package net.labymod.core.event.client.render.camera;

import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/camera/CameraEyeHeightEvent.class */
public class CameraEyeHeightEvent implements Event {
    private final float partialTicks;
    private float eyeHeight;

    public CameraEyeHeightEvent(float partialTicks, float eyeHeight) {
        this.partialTicks = partialTicks;
        this.eyeHeight = eyeHeight;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public float getEyeHeight() {
        return this.eyeHeight;
    }

    public void setEyeHeight(float eyeHeight) {
        this.eyeHeight = eyeHeight;
    }
}
