package net.labymod.core.main.serverapi.protocol.neo.translation.game.feature;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.UUID;
import net.labymod.api.serverapi.KeyedTranslationListener;
import net.labymod.core.main.LabyMod;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.AddMarkerPacket;
import net.labymod.serverapi.core.packet.clientbound.game.feature.marker.MarkerPacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/translation/game/feature/MarkerTranslationListener.class */
public class MarkerTranslationListener extends KeyedTranslationListener {
    public MarkerTranslationListener() {
        super("marker");
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected Packet translateIncomingMessage(JsonElement messageContent) {
        UUID uuidFromString;
        if (!messageContent.isJsonObject()) {
            return null;
        }
        JsonObject object = messageContent.getAsJsonObject();
        LabyMod.references().markerService();
        if (object.has("send_markers")) {
            MarkerPacket.MarkerSendType type = MarkerPacket.MarkerSendType.LABY_CONNECT;
            if (object.has("send_markers") && object.get("send_markers").getAsBoolean()) {
                type = MarkerPacket.MarkerSendType.SERVER;
            }
            return new MarkerPacket(type);
        }
        if (object.has("add_marker") && object.get("add_marker").isJsonObject()) {
            JsonObject marker = object.getAsJsonObject("add_marker");
            UUID sender = UUID.fromString(marker.get("sender").getAsString());
            int x = marker.get("x").getAsInt();
            int y = marker.get("y").getAsInt();
            int z = marker.get("z").getAsInt();
            boolean large = marker.get("large").getAsBoolean();
            if (marker.has("target")) {
                uuidFromString = UUID.fromString(marker.get("target").getAsString());
            } else {
                uuidFromString = null;
            }
            UUID target = uuidFromString;
            return new AddMarkerPacket(sender, x, y, z, large, target);
        }
        return null;
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected JsonElement translateOutgoingMessage(Packet packet) {
        return null;
    }
}
