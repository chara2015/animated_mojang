package net.labymod.api.event.labymod.labyconnect.session.login;

import java.util.List;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/login/LabyConnectFriendAddBulkEvent.class */
public class LabyConnectFriendAddBulkEvent extends LabyConnectEvent {
    private final List<Friend> friends;

    public LabyConnectFriendAddBulkEvent(LabyConnect api, List<Friend> friends) {
        super(api);
        this.friends = friends;
    }

    public List<Friend> getFriends() {
        return this.friends;
    }
}
