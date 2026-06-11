package net.labymod.api.event.labymod.labyconnect.session.request;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/request/LabyConnectIncomingFriendRequestRemoveEvent.class */
public class LabyConnectIncomingFriendRequestRemoveEvent extends LabyConnectIncomingRequestEvent {
    public LabyConnectIncomingFriendRequestRemoveEvent(LabyConnect api, IncomingFriendRequest request) {
        super(api, request);
    }
}
