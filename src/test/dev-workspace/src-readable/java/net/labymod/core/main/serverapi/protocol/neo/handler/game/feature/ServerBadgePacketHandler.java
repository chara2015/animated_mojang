package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.uri.URIParser;
import net.labymod.core.main.user.serverfeature.DefaultServerFeature;
import net.labymod.core.main.user.serverfeature.DefaultServerFeatureService;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.model.display.ServerBadge;
import net.labymod.serverapi.core.packet.clientbound.game.feature.ServerBadgePacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/feature/ServerBadgePacketHandler.class */
public class ServerBadgePacketHandler implements PacketHandler<ServerBadgePacket> {
    private final DefaultServerFeatureService serverFeatureService = (DefaultServerFeatureService) Laby.references().serverFeatureService();

    public void handle(@NotNull UUID uuid, @NotNull ServerBadgePacket packet) {
        DefaultServerFeature serverFeature = this.serverFeatureService.get();
        for (ServerBadge badge : packet.getBadges()) {
            if (URIParser.isHttpUrl(badge.getIconUrl())) {
                serverFeature.getBadges().put(Integer.valueOf(badge.getBadgeId()), new net.labymod.api.user.badge.ServerBadge(Icon.url(badge.getIconUrl()), badge.getColor()));
            }
        }
    }
}
