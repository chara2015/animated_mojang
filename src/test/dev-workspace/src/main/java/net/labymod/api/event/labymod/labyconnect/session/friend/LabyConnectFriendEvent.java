package net.labymod.api.event.labymod.labyconnect.session.friend;

import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/friend/LabyConnectFriendEvent.class */
public class LabyConnectFriendEvent extends LabyConnectEvent {
    private final Friend friend;

    protected LabyConnectFriendEvent(LabyConnect api, Friend friend) {
        super(api);
        this.friend = friend;
    }

    public Friend friend() {
        return this.friend;
    }
}
