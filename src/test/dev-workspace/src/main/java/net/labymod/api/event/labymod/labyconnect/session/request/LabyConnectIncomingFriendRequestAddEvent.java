package net.labymod.api.event.labymod.labyconnect.session.request;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/request/LabyConnectIncomingFriendRequestAddEvent.class */
public class LabyConnectIncomingFriendRequestAddEvent extends LabyConnectIncomingRequestEvent {
    public LabyConnectIncomingFriendRequestAddEvent(LabyConnect api, IncomingFriendRequest request) {
        super(api, request);
    }
}
