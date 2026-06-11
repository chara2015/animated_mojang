package net.labymod.api.server.event;

import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;
import net.labymod.api.mojang.GameProfile;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/server/event/IntegratedServerPlayerDisconnectEvent.class */
public class IntegratedServerPlayerDisconnectEvent implements Event {
    private final Phase phase;
    private final GameProfile profile;

    public IntegratedServerPlayerDisconnectEvent(@NotNull Phase phase, @NotNull GameProfile profile) {
        this.phase = phase;
        this.profile = profile;
    }

    @NotNull
    public Phase phase() {
        return this.phase;
    }

    @NotNull
    public GameProfile profile() {
        return this.profile;
    }
}
