package net.labymod.api.event.labymod.labyconnect.session;

import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;
import net.labymod.api.labyconnect.LabyConnect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/LabyConnectDisconnectEvent.class */
public class LabyConnectDisconnectEvent extends LabyConnectEvent {
    private final String reason;
    private final Initiator initiator;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/LabyConnectDisconnectEvent$Initiator.class */
    public enum Initiator {
        USER,
        CLIENT,
        SERVER
    }

    public LabyConnectDisconnectEvent(LabyConnect labyConnect, Initiator initiator, String reason) {
        super(labyConnect);
        this.initiator = initiator;
        this.reason = reason;
    }

    public Initiator getInitiator() {
        return this.initiator;
    }

    public String getReason() {
        return this.reason;
    }
}
