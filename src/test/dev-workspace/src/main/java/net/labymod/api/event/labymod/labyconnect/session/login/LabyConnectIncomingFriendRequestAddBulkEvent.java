package net.labymod.api.event.labymod.labyconnect.session.login;

import java.util.List;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/login/LabyConnectIncomingFriendRequestAddBulkEvent.class */
public class LabyConnectIncomingFriendRequestAddBulkEvent extends LabyConnectEvent {
    private final List<IncomingFriendRequest> requests;

    public LabyConnectIncomingFriendRequestAddBulkEvent(LabyConnect api, List<IncomingFriendRequest> requests) {
        super(api);
        this.requests = requests;
    }

    public List<IncomingFriendRequest> getRequests() {
        return this.requests;
    }
}
