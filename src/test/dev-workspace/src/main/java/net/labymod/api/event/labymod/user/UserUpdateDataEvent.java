package net.labymod.api.event.labymod.user;

import net.labymod.api.event.Cancellable;
import net.labymod.api.event.Phase;
import net.labymod.api.user.GameUser;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/user/UserUpdateDataEvent.class */
public class UserUpdateDataEvent extends AbstractGameUserEvent implements Cancellable {
    private final Phase phase;
    private boolean cancelled;

    public UserUpdateDataEvent(Phase phase, GameUser user) {
        super(user);
        this.phase = phase;
    }

    public Phase phase() {
        return this.phase;
    }

    @Override // net.labymod.api.event.Cancellable
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override // net.labymod.api.event.Cancellable
    public void setCancelled(boolean cancelled) {
        if (this.phase == Phase.POST) {
            throw new IllegalStateException("Cannot cancel POST phase");
        }
        this.cancelled = cancelled;
    }
}
