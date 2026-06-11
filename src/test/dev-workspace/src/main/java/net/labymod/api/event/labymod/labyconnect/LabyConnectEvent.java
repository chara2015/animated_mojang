package net.labymod.api.event.labymod.labyconnect;

import net.labymod.api.event.Event;
import net.labymod.api.labyconnect.LabyConnect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/LabyConnectEvent.class */
public class LabyConnectEvent implements Event {
    private final LabyConnect api;

    protected LabyConnectEvent(LabyConnect api) {
        this.api = api;
    }

    public LabyConnect labyConnect() {
        return this.api;
    }
}
