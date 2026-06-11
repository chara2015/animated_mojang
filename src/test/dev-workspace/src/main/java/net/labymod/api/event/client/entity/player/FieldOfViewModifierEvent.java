package net.labymod.api.event.client.entity.player;

import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/entity/player/FieldOfViewModifierEvent.class */
public class FieldOfViewModifierEvent implements Event {
    private float fieldOfView;

    public FieldOfViewModifierEvent(float fieldOfView) {
        this.fieldOfView = fieldOfView;
    }

    public float getFieldOfView() {
        return this.fieldOfView;
    }

    public void setFieldOfView(float fieldOfView) {
        this.fieldOfView = fieldOfView;
    }
}
