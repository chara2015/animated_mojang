package net.labymod.core.labyconnect.session.message;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.UUID;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestAddEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestAddResponseEvent;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.core.labyconnect.protocol.model.request.DefaultOutgoingFriendRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/message/FriendRequestResponseMessageListener.class */
public class FriendRequestResponseMessageListener implements MessageListener {
    private final DefaultLabyConnect labyConnect;
    private final List<OutgoingFriendRequest> requests;

    public FriendRequestResponseMessageListener(DefaultLabyConnect labyConnect, List<OutgoingFriendRequest> requests) {
        this.labyConnect = labyConnect;
        this.requests = requests;
    }

    @Override // net.labymod.core.labyconnect.session.message.MessageListener
    public void listen(String message) {
        JsonObject obj = (JsonObject) GSON.fromJson(message, JsonObject.class);
        String name = obj.get("name").getAsString();
        UUID uuid = UUID.fromString(obj.get("uuid").getAsString());
        OutgoingFriendRequest request = new DefaultOutgoingFriendRequest(uuid, name);
        this.requests.add(request);
        this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestAddResponseEvent(this.labyConnect, request));
        this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestAddEvent(this.labyConnect, request));
    }
}
