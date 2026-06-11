package net.labymod.api.event.labymod.labyconnect.session.login;

import java.util.List;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/login/LabyConnectOutgoingFriendRequestAddBulkEvent.class */
public class LabyConnectOutgoingFriendRequestAddBulkEvent extends LabyConnectEvent {
    private final List<OutgoingFriendRequest> requests;

    public LabyConnectOutgoingFriendRequestAddBulkEvent(LabyConnect api, List<OutgoingFriendRequest> requests) {
        super(api);
        this.requests = requests;
    }

    public List<OutgoingFriendRequest> getRequests() {
        return this.requests;
    }
}
