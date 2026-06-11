package net.labymod.core.event.client.render.entity;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/entity/EntityEyeHeightEvent.class */
public class EntityEyeHeightEvent implements Event {
    private final Entity entity;
    private float eyeHeight;

    public EntityEyeHeightEvent(Entity entity, float eyeHeight) {
        this.entity = entity;
        this.eyeHeight = eyeHeight;
    }

    public Entity entity() {
        return this.entity;
    }

    public float getEyeHeight() {
        return this.eyeHeight;
    }

    public void setEyeHeight(float eyeHeight) {
        this.eyeHeight = eyeHeight;
    }
}
