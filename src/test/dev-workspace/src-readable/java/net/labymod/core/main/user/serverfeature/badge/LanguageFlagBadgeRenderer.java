package net.labymod.core.main.user.serverfeature.badge;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.util.CountryCode;
import net.labymod.core.main.user.serverfeature.DefaultServerFeatureService;
import net.labymod.core.main.user.serverfeature.UserServerFeature;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/serverfeature/badge/LanguageFlagBadgeRenderer.class */
public class LanguageFlagBadgeRenderer extends BadgeRenderer {
    private final DefaultServerFeatureService serverFeatureService = (DefaultServerFeatureService) Laby.references().serverFeatureService();

    @Override // net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer
    public void render(ScreenContext context, float x, float y, NetworkPlayerInfo player) {
        CountryCode countryCode;
        UserServerFeature user = this.serverFeatureService.get().getUserFeature(player.profile().getUniqueId());
        if (user == null || (countryCode = user.getCountryCode()) == null) {
            return;
        }
        context.canvas().submitIcon(countryCode.getIcon(), x, y + 1.0f, 10.0f, 6.0f);
    }

    @Override // net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer
    public boolean isVisible(NetworkPlayerInfo player) {
        UserServerFeature user = this.serverFeatureService.get().getUserFeature(player.profile().getUniqueId());
        return (user == null || user.getCountryCode() == null) ? false : true;
    }

    @Override // net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer
    public int getSize(NetworkPlayerInfo player) {
        return 10;
    }

    @Override // net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer
    protected boolean begin(ScreenContext context) {
        return Laby.labyAPI().config().multiplayer().tabList().showCountryFlags().get().booleanValue();
    }
}
