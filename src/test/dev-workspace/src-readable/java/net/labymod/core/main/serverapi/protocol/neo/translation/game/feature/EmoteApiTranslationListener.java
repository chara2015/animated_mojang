package net.labymod.core.main.serverapi.protocol.neo.translation.game.feature;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.labymod.api.serverapi.KeyedTranslationListener;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.model.feature.Emote;
import net.labymod.serverapi.core.packet.clientbound.game.feature.EmotePacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/translation/game/feature/EmoteApiTranslationListener.class */
public class EmoteApiTranslationListener extends KeyedTranslationListener {
    public EmoteApiTranslationListener() {
        super("emote_api");
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected Packet translateIncomingMessage(JsonElement messageContent) {
        if (!messageContent.isJsonArray()) {
            return null;
        }
        JsonArray emotes = messageContent.getAsJsonArray();
        List<Emote> entries = new ArrayList<>();
        for (int i = 0; i < emotes.size(); i++) {
            JsonElement emoteElement = emotes.get(i);
            if (emoteElement.isJsonObject()) {
                JsonObject emoteObject = emoteElement.getAsJsonObject();
                UUID uniqueId = UUID.fromString(emoteObject.get("uuid").getAsString());
                int emoteId = emoteObject.get("emote_id").getAsInt();
                entries.add(Emote.play(uniqueId, emoteId));
            }
        }
        return new EmotePacket(entries);
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected JsonElement translateOutgoingMessage(Packet packet) {
        return null;
    }
}
