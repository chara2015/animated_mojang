package net.labymod.api.event.client.render.entity;

import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/entity/EntityRenderPassEvent.class */
public class EntityRenderPassEvent implements Event {
    private final Phase phase;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/entity/EntityRenderPassEvent$Phase.class */
    public enum Phase {
        BEFORE,
        AFTER
    }

    public EntityRenderPassEvent(Phase phase) {
        this.phase = phase;
    }

    public Phase getPhase() {
        return this.phase;
    }
}
