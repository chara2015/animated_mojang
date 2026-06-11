package net.labymod.api.event.labymod.serverapi;

import net.labymod.api.event.Event;
import net.labymod.api.user.permission.ClientPermission;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/serverapi/PermissionStateChangeEvent.class */
public class PermissionStateChangeEvent implements Event {
    private final ClientPermission permission;
    private final State state;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/serverapi/PermissionStateChangeEvent$State.class */
    public enum State {
        ALLOWED,
        DISALLOWED
    }

    public PermissionStateChangeEvent(@NotNull ClientPermission permission, @NotNull State state) {
        this.permission = permission;
        this.state = state;
    }

    @NotNull
    public ClientPermission permission() {
        return this.permission;
    }

    @NotNull
    public State state() {
        return this.state;
    }
}
