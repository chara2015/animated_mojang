package net.labymod.api.event.labymod.labyconnect.session.request;

import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/request/LabyConnectIncomingRequestEvent.class */
public class LabyConnectIncomingRequestEvent extends LabyConnectEvent {
    private final IncomingFriendRequest request;

    protected LabyConnectIncomingRequestEvent(LabyConnect api, IncomingFriendRequest request) {
        super(api);
        this.request = request;
    }

    public IncomingFriendRequest request() {
        return this.request;
    }
}
