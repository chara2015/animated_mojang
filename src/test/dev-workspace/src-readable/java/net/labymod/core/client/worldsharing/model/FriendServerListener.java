package net.labymod.core.client.worldsharing.model;

import net.labymod.api.labyconnect.protocol.model.friend.Friend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/model/FriendServerListener.class */
public interface FriendServerListener {
    void add(Friend friend);

    void remove(Friend friend);

    void join(Friend friend);
}
