package net.labymod.core.main.user.serverfeature.badge;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.user.badge.ServerBadge;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.serverfeature.DefaultServerFeatureService;
import net.labymod.core.main.user.serverfeature.UserServerFeature;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/serverfeature/badge/ServerBadgeRenderer.class */
public class ServerBadgeRenderer extends BadgeRenderer {
    private final DefaultServerFeatureService serverFeatureService = (DefaultServerFeatureService) Laby.references().serverFeatureService();

    @Override // net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer
    public void render(ScreenContext context, float x, float y, NetworkPlayerInfo player) {
        UserServerFeature user = this.serverFeatureService.get().getUserFeature(player.profile().getUniqueId());
        if (user == null) {
            return;
        }
        List<ServerBadge> serverBadges = user.getBadges();
        if (serverBadges.isEmpty()) {
            return;
        }
        float offsetX = x;
        boolean first = true;
        for (ServerBadge serverBadge : serverBadges) {
            if (serverBadge.isVisible()) {
                if (!first) {
                    offsetX += 2.0f;
                }
                context.canvas().submitIcon(serverBadge.icon(), offsetX, y, serverBadge.width(), serverBadge.height(), false, serverBadge.color().getRGB());
                offsetX += serverBadge.width();
                first = false;
            }
        }
    }

    @Override // net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer
    public boolean isVisible(NetworkPlayerInfo player) {
        UserServerFeature user = this.serverFeatureService.get().getUserFeature(player.profile().getUniqueId());
        return (user == null || user.getBadges().isEmpty()) ? false : true;
    }

    @Override // net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer
    public int getSize(NetworkPlayerInfo player) {
        UserServerFeature user = this.serverFeatureService.get().getUserFeature(player.profile().getUniqueId());
        if (user == null) {
            return 0;
        }
        List<ServerBadge> serverBadges = user.getBadges();
        if (serverBadges.isEmpty()) {
            return 0;
        }
        float width = 0.0f;
        boolean first = true;
        for (ServerBadge serverBadge : serverBadges) {
            if (serverBadge.isVisible()) {
                if (!first) {
                    width += 2.0f;
                }
                width += serverBadge.width();
                first = false;
            }
        }
        if (first) {
            return 0;
        }
        return (int) width;
    }

    @Override // net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer
    protected boolean begin(ScreenContext context) {
        return LabyMod.getInstance().config().multiplayer().tabList().labyModBadge().get().booleanValue();
    }
}
