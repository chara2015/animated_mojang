package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.user.badge.ServerBadge;
import net.labymod.core.main.user.serverfeature.DefaultServerFeature;
import net.labymod.core.main.user.serverfeature.DefaultServerFeatureService;
import net.labymod.core.main.user.serverfeature.UserServerFeature;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.model.display.ServerUserBadge;
import net.labymod.serverapi.core.packet.clientbound.game.feature.ServerUserBadgePacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/feature/ServerUserBadgePacketHandler.class */
public class ServerUserBadgePacketHandler implements PacketHandler<ServerUserBadgePacket> {
    private final DefaultServerFeatureService serverFeatureService = (DefaultServerFeatureService) Laby.references().serverFeatureService();

    public void handle(@NotNull UUID uuid, @NotNull ServerUserBadgePacket packet) {
        DefaultServerFeature serverFeature = this.serverFeatureService.get();
        for (ServerUserBadge badge : packet.getBadges()) {
            UUID uniqueId = badge.getUniqueId();
            UserServerFeature user = serverFeature.getOrCreateUserFeature(uniqueId);
            List<ServerBadge> serverBadges = new ArrayList<>();
            for (int badgeId : badge.getBadgeIds()) {
                ServerBadge serverBadge = serverFeature.getBadges().get(Integer.valueOf(badgeId));
                if (serverBadge != null) {
                    serverBadges.add(serverBadge);
                }
            }
            user.setBadges(serverBadges);
        }
    }
}
