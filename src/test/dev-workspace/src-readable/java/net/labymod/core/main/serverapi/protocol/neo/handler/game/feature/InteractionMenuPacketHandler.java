package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.interaction.InteractionMenuRegistry;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import net.labymod.core.client.entity.player.interaction.server.ServerBulletPoint;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.model.feature.InteractionMenuEntry;
import net.labymod.serverapi.core.packet.clientbound.game.feature.InteractionMenuPacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/feature/InteractionMenuPacketHandler.class */
public class InteractionMenuPacketHandler implements PacketHandler<InteractionMenuPacket> {
    private final InteractionMenuRegistry interactionMenuRegistry = Laby.references().interactionMenuRegistry();

    public InteractionMenuPacketHandler() {
        Laby.references().eventBus().registerListener(this);
    }

    public void handle(@NotNull UUID uuid, @NotNull InteractionMenuPacket packet) {
        this.interactionMenuRegistry.unregisterServerBulletPoints();
        for (InteractionMenuEntry entry : packet.getEntries()) {
            this.interactionMenuRegistry.register(new ServerBulletPoint(entry));
        }
    }

    @Subscribe
    public void onNetworkDisconnect(NetworkDisconnectEvent event) {
        this.interactionMenuRegistry.unregisterServerBulletPoints();
    }
}
