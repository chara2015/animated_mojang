package net.labymod.api.event.labymod.labyconnect.session.friend;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/friend/LabyConnectFriendStatusEvent.class */
public class LabyConnectFriendStatusEvent extends LabyConnectFriendEvent {
    private final UserStatus previousStatus;
    private final UserStatus status;

    public LabyConnectFriendStatusEvent(LabyConnect api, Friend friend, UserStatus previousStatus, UserStatus status) {
        super(api, friend);
        this.previousStatus = previousStatus;
        this.status = status;
    }

    public UserStatus getPreviousStatus() {
        return this.previousStatus;
    }

    public UserStatus getStatus() {
        return this.status;
    }

    public boolean isOnline() {
        return this.status != UserStatus.OFFLINE;
    }

    public boolean wasOnline() {
        return this.previousStatus != UserStatus.OFFLINE;
    }
}
