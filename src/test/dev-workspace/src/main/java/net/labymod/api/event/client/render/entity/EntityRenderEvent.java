package net.labymod.api.event.client.render.entity;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/entity/EntityRenderEvent.class */
public class EntityRenderEvent implements Event {
    private final Entity entity;
    private final Phase phase;

    public EntityRenderEvent(Entity entity, Phase phase) {
        this.entity = entity;
        this.phase = phase;
    }

    @NotNull
    public Entity entity() {
        return this.entity;
    }

    public Phase phase() {
        return this.phase;
    }
}
