package net.labymod.core.labyconnect.session.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectOutgoingFriendRequestAddBulkEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestAddEvent;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.core.labyconnect.protocol.model.request.DefaultOutgoingFriendRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/message/OutgoingFriendRequestsMessageListener.class */
public class OutgoingFriendRequestsMessageListener implements MessageListener {
    private final DefaultLabyConnect labyConnect;
    private final List<OutgoingFriendRequest> requests;

    public OutgoingFriendRequestsMessageListener(DefaultLabyConnect labyConnect, List<OutgoingFriendRequest> requests) {
        this.labyConnect = labyConnect;
        this.requests = requests;
    }

    @Override // net.labymod.core.labyconnect.session.message.MessageListener
    public void listen(String message) {
        JsonArray array = (JsonArray) GSON.fromJson(message, JsonArray.class);
        List<OutgoingFriendRequest> requests = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject request = array.get(i).getAsJsonObject();
            String name = request.get("name").getAsString();
            UUID uuid = UUID.fromString(request.get("uuid").getAsString());
            requests.add(new DefaultOutgoingFriendRequest(uuid, name));
        }
        this.requests.addAll(requests);
        if (requests.size() == 1) {
            this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestAddEvent(this.labyConnect, requests.get(0)));
        } else {
            this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestAddBulkEvent(this.labyConnect, new ArrayList(requests)));
        }
    }
}
