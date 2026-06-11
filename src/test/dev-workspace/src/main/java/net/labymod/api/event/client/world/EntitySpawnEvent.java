package net.labymod.api.event.client.world;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/EntitySpawnEvent.class */
public class EntitySpawnEvent implements Event {
    private final int entityId;
    private final Entity entity;

    public EntitySpawnEvent(int entityId, Entity entity) {
        this.entityId = entityId;
        this.entity = entity;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public Entity entity() {
        return this.entity;
    }
}
