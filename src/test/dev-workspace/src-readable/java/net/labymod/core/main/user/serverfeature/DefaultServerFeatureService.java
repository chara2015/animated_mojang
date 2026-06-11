package net.labymod.core.main.user.serverfeature;

import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.badge.BadgeRegistry;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.user.serverfeature.ServerFeature;
import net.labymod.api.user.serverfeature.ServerFeatureService;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.serverfeature.badge.LanguageFlagBadgeRenderer;
import net.labymod.core.main.user.serverfeature.badge.ServerBadgeRenderer;
import net.labymod.core.main.user.serverfeature.subtitle.SubtitleTag;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/serverfeature/DefaultServerFeatureService.class */
@Singleton
@Implements(ServerFeatureService.class)
public class DefaultServerFeatureService implements ServerFeatureService {

    @NotNull
    private ServerFeature serverFeature = new DefaultServerFeature();

    public void register() {
        LabyAPI labyAPI = Laby.labyAPI();
        TagRegistry tagRegistry = labyAPI.tagRegistry();
        BadgeRegistry badgeRegistry = LabyMod.references().badgeRegistry();
        tagRegistry.register("subtitle", PositionType.BELOW_NAME, new SubtitleTag());
        badgeRegistry.register("server_badge", net.labymod.api.client.entity.player.badge.PositionType.LEFT_TO_NAME, new ServerBadgeRenderer());
        badgeRegistry.register("language_flag", net.labymod.api.client.entity.player.badge.PositionType.OVERWRITE_PING, new LanguageFlagBadgeRenderer());
        labyAPI.eventBus().registerListener(this);
    }

    @NotNull
    public DefaultServerFeature get() {
        return (DefaultServerFeature) this.serverFeature;
    }

    @Subscribe
    public void onServerDisconnect(ServerDisconnectEvent event) {
        this.serverFeature = new DefaultServerFeature();
    }

    @Subscribe
    public void onSubServerSwitch(SubServerSwitchEvent event) {
        this.serverFeature = new DefaultServerFeature();
    }

    @Override // net.labymod.api.user.serverfeature.ServerFeatureService
    @NotNull
    public ServerFeature serverFeature() {
        return this.serverFeature;
    }
}
