package net.labymod.api.event.labymod.labyconnect.session.friend;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/friend/LabyConnectFriendNoteUpdateEvent.class */
public class LabyConnectFriendNoteUpdateEvent extends LabyConnectFriendEvent {
    public LabyConnectFriendNoteUpdateEvent(LabyConnect api, Friend friend) {
        super(api, friend);
    }
}
