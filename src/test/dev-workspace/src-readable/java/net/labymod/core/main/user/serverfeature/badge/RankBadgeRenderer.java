package net.labymod.core.main.user.serverfeature.badge;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.GameUserService;
import net.labymod.api.user.group.Group;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.serverfeature.DefaultServerFeatureService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/serverfeature/badge/RankBadgeRenderer.class */
public class RankBadgeRenderer extends BadgeRenderer {
    private final GameUserService gameUserService = Laby.references().gameUserService();
    private final Icon wolfIcon = Textures.SpriteLabyMod.DEFAULT_WOLF_BLURRY;
    private final Icon groupIcon = Textures.SpriteLabyMod.WHITE_WOLF_BLURRY;

    @Override // net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer
    public void render(ScreenContext context, float x, float y, NetworkPlayerInfo player) {
        GameUser gameUser = this.gameUserService.gameUser(player.profile().getUniqueId());
        if (gameUser.isUsingLabyMod()) {
            Group visibleGroup = gameUser.visibleGroup();
            if (visibleGroup.isDefault()) {
                context.canvas().submitIcon(this.wolfIcon, x, y, 8.0f, 8.0f);
            } else {
                context.canvas().submitIcon(this.groupIcon, x, y, 8.0f, 8.0f, false, visibleGroup.getColor().getRGB());
            }
        }
    }

    @Override // net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer
    public boolean isVisible(NetworkPlayerInfo player) {
        DefaultServerFeatureService service = (DefaultServerFeatureService) Laby.references().serverFeatureService();
        return service.get().isUserIndicatorVisible() && this.gameUserService.gameUser(player.profile().getUniqueId()).isUsingLabyMod();
    }

    @Override // net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer
    public int getSize(NetworkPlayerInfo player) {
        return 7;
    }

    @Override // net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer
    protected boolean begin(ScreenContext context) {
        return LabyMod.getInstance().config().multiplayer().tabList().labyModBadge().get().booleanValue();
    }
}
