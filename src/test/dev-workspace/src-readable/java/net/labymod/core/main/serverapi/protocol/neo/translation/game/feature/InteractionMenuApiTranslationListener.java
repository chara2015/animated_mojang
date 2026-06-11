package net.labymod.core.main.serverapi.protocol.neo.translation.game.feature;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.labymod.api.serverapi.KeyedTranslationListener;
import net.labymod.core.client.render.schematic.block.ParameterType;
import net.labymod.serverapi.api.model.component.ServerAPIComponent;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.model.feature.InteractionMenuEntry;
import net.labymod.serverapi.core.packet.clientbound.game.feature.InteractionMenuPacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/translation/game/feature/InteractionMenuApiTranslationListener.class */
public class InteractionMenuApiTranslationListener extends KeyedTranslationListener {
    public InteractionMenuApiTranslationListener() {
        super("user_menu_actions");
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected Packet translateIncomingMessage(JsonElement messageContent) {
        if (!messageContent.isJsonArray()) {
            return null;
        }
        List<InteractionMenuEntry> entries = new ArrayList<>();
        for (JsonElement entry : messageContent.getAsJsonArray()) {
            if (entry.isJsonObject()) {
                JsonObject entryObject = entry.getAsJsonObject();
                try {
                    InteractionMenuEntry.InteractionMenuType type = InteractionMenuEntry.InteractionMenuType.fromString(entryObject.get(ParameterType.TYPE).getAsString().toUpperCase(Locale.ENGLISH));
                    entries.add(InteractionMenuEntry.create(ServerAPIComponent.text(entryObject.get("displayName").getAsString()), type, entryObject.get("value").getAsString()));
                } catch (Exception e) {
                }
            }
        }
        return new InteractionMenuPacket(entries);
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected JsonElement translateOutgoingMessage(Packet packet) {
        return null;
    }
}
