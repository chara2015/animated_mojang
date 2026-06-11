package net.labymod.core.labyconnect.session.advertisement;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonMessage;
import net.labymod.core.labyconnect.session.message.MessageListener;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/advertisement/AdvertisementController.class */
public class AdvertisementController implements MessageListener {
    private final DefaultLabyConnect labyConnect;
    private List<AdServerData> servers = new ArrayList();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/advertisement/AdvertisementController$Event.class */
    public enum Event {
        SEE,
        JOIN,
        KEEP,
        DELETE
    }

    public AdvertisementController(DefaultLabyConnect labyConnect) {
        this.labyConnect = labyConnect;
    }

    public List<AdServerData> getServers() {
        return this.servers;
    }

    @Override // net.labymod.core.labyconnect.session.message.MessageListener
    public void listen(String message) {
        JsonObject obj = (JsonObject) GSON.fromJson(message, JsonObject.class);
        if (obj.has("servers")) {
            List<AdServerData> servers = new ArrayList<>();
            for (JsonElement element : obj.getAsJsonArray("servers")) {
                JsonObject server = element.getAsJsonObject();
                int id = server.get("id").getAsInt();
                String name = server.get("name").getAsString();
                String address = server.get("address").getAsString();
                servers.add(new AdServerData(this, id, name, address));
            }
            this.servers = servers;
        }
    }

    public void sendEvent(Event event, AdServerData server) {
        JsonObject obj = new JsonObject();
        obj.addProperty("event", event.name());
        obj.addProperty("id", Integer.valueOf(server.getAdId()));
        this.labyConnect.sendPacket(new PacketAddonMessage("advertisement", (JsonElement) obj));
    }
}
