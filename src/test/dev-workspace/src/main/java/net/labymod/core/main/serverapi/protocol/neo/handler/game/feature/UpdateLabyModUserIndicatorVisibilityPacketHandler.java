package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.core.main.user.serverfeature.DefaultServerFeatureService;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.feature.UpdateLabyModUserIndicatorVisibilityPacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/feature/UpdateLabyModUserIndicatorVisibilityPacketHandler.class */
public class UpdateLabyModUserIndicatorVisibilityPacketHandler implements PacketHandler<UpdateLabyModUserIndicatorVisibilityPacket> {
    private final DefaultServerFeatureService serverFeatureService = (DefaultServerFeatureService) Laby.references().serverFeatureService();

    public void handle(@NotNull UUID uuid, @NotNull UpdateLabyModUserIndicatorVisibilityPacket packet) {
        this.serverFeatureService.get().setUserIndicatorVisible(packet.isVisible());
    }
}
