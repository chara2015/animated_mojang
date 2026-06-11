package net.labymod.api.event.labymod.labyconnect.session.request;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/request/LabyConnectOutgoingFriendRequestAddEvent.class */
public class LabyConnectOutgoingFriendRequestAddEvent extends LabyConnectOutgoingRequestEvent {
    public LabyConnectOutgoingFriendRequestAddEvent(LabyConnect api, OutgoingFriendRequest request) {
        super(api, request);
    }
}
