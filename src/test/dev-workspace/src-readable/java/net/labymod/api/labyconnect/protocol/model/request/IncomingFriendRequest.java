package net.labymod.api.labyconnect.protocol.model.request;

import net.labymod.api.labyconnect.protocol.model.User;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/protocol/model/request/IncomingFriendRequest.class */
public interface IncomingFriendRequest extends User {
    void accept();

    void decline();
}
