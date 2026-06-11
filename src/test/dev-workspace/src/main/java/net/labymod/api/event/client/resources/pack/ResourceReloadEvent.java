package net.labymod.api.event.client.resources.pack;

import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/resources/pack/ResourceReloadEvent.class */
public class ResourceReloadEvent implements Event {
    private final Type type;
    private final Phase phase;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/resources/pack/ResourceReloadEvent$Type.class */
    public enum Type {
        RELOAD,
        INITIALIZATION_RESOURCE_PACKS
    }

    public ResourceReloadEvent(Type type, Phase phase) {
        this.type = type;
        this.phase = phase;
    }

    public Type type() {
        return this.type;
    }

    public Phase phase() {
        return this.phase;
    }
}
