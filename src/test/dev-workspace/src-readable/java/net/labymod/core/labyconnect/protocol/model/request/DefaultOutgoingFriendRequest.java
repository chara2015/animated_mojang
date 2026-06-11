package net.labymod.core.labyconnect.protocol.model.request;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/model/request/DefaultOutgoingFriendRequest.class */
public class DefaultOutgoingFriendRequest extends DefaultFriend implements OutgoingFriendRequest {
    public DefaultOutgoingFriendRequest(UUID uuid, String username) {
        super(uuid, username, UserStatus.OFFLINE, "", null, 0, TimeUtil.getCurrentTimeMillis(), 0L, "", 0L, 0L, 0, false);
    }

    @Override // net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest
    public void withdraw() {
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session != null) {
            session.declineFriendRequest(this.uniqueId);
        }
    }
}
