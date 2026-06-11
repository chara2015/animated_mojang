package net.labymod.api.event.client.world;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/EntityDestructEvent.class */
public class EntityDestructEvent implements Event {
    private final Entity entity;

    public EntityDestructEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity entity() {
        return this.entity;
    }
}
