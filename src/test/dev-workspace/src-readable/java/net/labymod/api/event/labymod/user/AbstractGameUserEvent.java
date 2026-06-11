package net.labymod.api.event.labymod.user;

import net.labymod.api.event.Event;
import net.labymod.api.user.GameUser;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/user/AbstractGameUserEvent.class */
public abstract class AbstractGameUserEvent implements Event {
    private final GameUser gameUser;

    public AbstractGameUserEvent(GameUser gameUser) {
        this.gameUser = gameUser;
    }

    public GameUser gameUser() {
        return this.gameUser;
    }
}
