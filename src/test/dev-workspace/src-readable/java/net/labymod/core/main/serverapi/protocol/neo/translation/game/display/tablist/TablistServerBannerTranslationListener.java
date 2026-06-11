package net.labymod.core.main.serverapi.protocol.neo.translation.game.display.tablist;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.api.serverapi.KeyedTranslationListener;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.packet.clientbound.game.display.TabListBannerPacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/translation/game/display/tablist/TablistServerBannerTranslationListener.class */
public class TablistServerBannerTranslationListener extends KeyedTranslationListener {
    public TablistServerBannerTranslationListener() {
        super("server_banner");
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected Packet translateIncomingMessage(JsonElement messageContent) {
        if (!messageContent.isJsonObject()) {
            return null;
        }
        JsonObject serverBannerObject = messageContent.getAsJsonObject();
        String imageUrl = null;
        if (serverBannerObject.has("url")) {
            imageUrl = serverBannerObject.get("url").getAsString();
        }
        return new TabListBannerPacket(imageUrl);
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected JsonElement translateOutgoingMessage(Packet packet) {
        return null;
    }
}
