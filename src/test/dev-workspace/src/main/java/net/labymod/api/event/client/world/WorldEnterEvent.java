package net.labymod.api.event.client.world;

import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/WorldEnterEvent.class */
public class WorldEnterEvent implements Event {
    private final Type type;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/world/WorldEnterEvent$Type.class */
    public enum Type {
        SINGLEPLAYER,
        MULTIPLAYER
    }

    public WorldEnterEvent(@NotNull Type type) {
        this.type = type;
    }

    @NotNull
    public Type type() {
        return this.type;
    }
}
