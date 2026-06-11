package net.labymod.api.event.labymod.labyconnect.session.request;

import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/request/LabyConnectOutgoingRequestEvent.class */
public class LabyConnectOutgoingRequestEvent extends LabyConnectEvent {
    private final OutgoingFriendRequest request;

    protected LabyConnectOutgoingRequestEvent(LabyConnect api, OutgoingFriendRequest request) {
        super(api);
        this.request = request;
    }

    public OutgoingFriendRequest request() {
        return this.request;
    }
}
