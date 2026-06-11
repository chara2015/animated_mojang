package net.labymod.api.event.client.session;

import net.labymod.api.client.session.Session;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/session/SessionUpdateEvent.class */
@LabyEvent(background = true)
public class SessionUpdateEvent implements Event {
    private final Session previousSession;
    private final Session newSession;

    public SessionUpdateEvent(Session previousSession, Session newSession) {
        this.previousSession = previousSession;
        this.newSession = newSession;
    }

    public Session previousSession() {
        return this.previousSession;
    }

    public Session newSession() {
        return this.newSession;
    }

    public boolean isAnotherAccount() {
        return this.previousSession == null || !(this.newSession == null || this.previousSession.getUniqueId().equals(this.newSession.getUniqueId()));
    }
}
