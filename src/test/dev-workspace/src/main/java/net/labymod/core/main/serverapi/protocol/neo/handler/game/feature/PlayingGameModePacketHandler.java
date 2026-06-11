package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.client.network.server.ServerLobbyEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.feature.PlayingGameModePacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/feature/PlayingGameModePacketHandler.class */
public class PlayingGameModePacketHandler implements PacketHandler<PlayingGameModePacket> {
    public void handle(@NotNull UUID uuid, @NotNull PlayingGameModePacket playingGameModePacket) {
        LabyConnectSession session;
        ServerData serverData = Laby.labyAPI().serverController().getCurrentServerData();
        if (serverData == null || (session = Laby.references().labyConnect().getSession()) == null) {
            return;
        }
        session.sendCurrentServer(serverData, playingGameModePacket.getGameMode(), false);
        Laby.fireEvent(new ServerLobbyEvent(ConnectableServerData.from(serverData), playingGameModePacket.getGameMode()));
    }
}
