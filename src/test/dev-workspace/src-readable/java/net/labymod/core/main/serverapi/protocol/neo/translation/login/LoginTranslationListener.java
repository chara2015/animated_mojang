package net.labymod.core.main.serverapi.protocol.neo.translation.login;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.api.serverapi.KeyedTranslationListener;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.packet.serverbound.login.VersionLoginPacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/translation/login/LoginTranslationListener.class */
public class LoginTranslationListener extends KeyedTranslationListener {
    public LoginTranslationListener() {
        super("INFO");
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected Packet translateIncomingMessage(JsonElement jsonElement) {
        return null;
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected JsonElement translateOutgoingMessage(Packet packet) {
        if (!(packet instanceof VersionLoginPacket)) {
            return null;
        }
        VersionLoginPacket versionLoginPacket = (VersionLoginPacket) packet;
        JsonObject object = new JsonObject();
        object.addProperty("version", versionLoginPacket.getVersion());
        return object;
    }
}
