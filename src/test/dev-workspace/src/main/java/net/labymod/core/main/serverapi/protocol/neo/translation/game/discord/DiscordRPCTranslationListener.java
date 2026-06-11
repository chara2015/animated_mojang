package net.labymod.core.main.serverapi.protocol.neo.translation.game.discord;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.api.serverapi.KeyedTranslationListener;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.model.feature.DiscordRPC;
import net.labymod.serverapi.core.packet.clientbound.game.feature.DiscordRPCPacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/translation/game/discord/DiscordRPCTranslationListener.class */
public class DiscordRPCTranslationListener extends KeyedTranslationListener {
    public DiscordRPCTranslationListener() {
        super("discord_rpc");
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected Packet translateIncomingMessage(JsonElement messageContent) {
        if (!messageContent.isJsonObject()) {
            return null;
        }
        JsonObject object = messageContent.getAsJsonObject();
        boolean hasGame = false;
        String gameMode = null;
        long startTime = 0;
        long endTime = 0;
        if (object.has("hasGame")) {
            hasGame = object.get("hasGame").getAsBoolean();
        }
        if (object.has("game_mode")) {
            gameMode = object.get("game_mode").getAsString();
        }
        if (object.has("game_startTime")) {
            startTime = object.get("game_startTime").getAsLong();
        }
        if (object.has("game_endTime")) {
            endTime = object.get("game_endTime").getAsLong();
        }
        if (!hasGame || gameMode == null) {
            return new DiscordRPCPacket(DiscordRPC.createReset());
        }
        if (startTime != 0) {
            return new DiscordRPCPacket(DiscordRPC.createWithStart(gameMode, startTime));
        }
        if (endTime != 0) {
            return new DiscordRPCPacket(DiscordRPC.createWithEnd(gameMode, endTime));
        }
        return new DiscordRPCPacket(DiscordRPC.create(gameMode));
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected JsonElement translateOutgoingMessage(Packet packet) {
        return null;
    }
}
