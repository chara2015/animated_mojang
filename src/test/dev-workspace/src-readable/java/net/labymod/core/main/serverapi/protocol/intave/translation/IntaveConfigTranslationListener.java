package net.labymod.core.main.serverapi.protocol.intave.translation;

import com.google.gson.JsonElement;
import net.labymod.api.serverapi.KeyedTranslationListener;
import net.labymod.core.main.serverapi.protocol.intave.packet.IntaveClientConfigReceivedPacket;
import net.labymod.serverapi.api.packet.Packet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/intave/translation/IntaveConfigTranslationListener.class */
public class IntaveConfigTranslationListener extends KeyedTranslationListener {
    public IntaveConfigTranslationListener() {
        super("clientconfig");
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected Packet translateIncomingMessage(JsonElement element) {
        boolean received = false;
        if (element.isJsonPrimitive()) {
            received = element.getAsString().equalsIgnoreCase("received");
        }
        return new IntaveClientConfigReceivedPacket(received);
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected JsonElement translateOutgoingMessage(Packet packet) {
        return this.gson.toJsonTree(packet);
    }
}
