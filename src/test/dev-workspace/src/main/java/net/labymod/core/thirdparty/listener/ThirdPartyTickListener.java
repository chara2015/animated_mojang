package net.labymod.core.thirdparty.listener;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.thirdparty.ThirdPartyService;
import net.labymod.api.thirdparty.discord.DiscordActivity;
import net.labymod.api.thirdparty.discord.DiscordApp;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.core.thirdparty.discord.DefaultDiscordApp;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/thirdparty/listener/ThirdPartyTickListener.class */
@Singleton
public final class ThirdPartyTickListener {
    private final LabyAPI labyAPI = Laby.labyAPI();
    private final ThirdPartyService thirdPartyService = Laby.references().thirdPartyService();

    @Inject
    public ThirdPartyTickListener() {
    }

    @Subscribe
    public void onPostTick(GameTickEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        DiscordApp discordApp = this.thirdPartyService.discord();
        if (!discordApp.isRunning() || !(discordApp instanceof DefaultDiscordApp)) {
            return;
        }
        DefaultDiscordApp defaultDiscordApp = (DefaultDiscordApp) discordApp;
        handleDiscord(defaultDiscordApp);
        defaultDiscordApp.tick();
    }

    @Subscribe
    public void gameAfterPostStartup(GameInitializeEvent event) {
        if (event.getLifecycle() != GameInitializeEvent.Lifecycle.POST_STARTUP) {
            return;
        }
        DiscordApp discordApp = this.thirdPartyService.discord();
        if (discordApp instanceof DefaultDiscordApp) {
            DefaultDiscordApp defaultDiscordApp = (DefaultDiscordApp) discordApp;
            SessionAccessor sessionAccessor = Laby.labyAPI().minecraft().sessionAccessor();
            Session session = sessionAccessor.getSession();
            if (session != null && session.isPremium()) {
                defaultDiscordApp.updateUserAsset(session.getUsername(), session.getUniqueId());
            }
        }
    }

    private void handleDiscord(DefaultDiscordApp discordApp) {
        if (this.labyAPI.serverController().getCurrentServerData() == null) {
            return;
        }
        boolean connected = this.labyAPI.minecraft().isIngame();
        DiscordActivity displayedActivity = discordApp.getDisplayedActivity();
        if (!connected || displayedActivity == null || displayedActivity.isCustom() || displayedActivity.getState() != null) {
            return;
        }
        DiscordActivity activity = DiscordActivity.builder(this, displayedActivity).state("Ingame").build();
        if (displayedActivity.equals(activity)) {
            return;
        }
        discordApp.displayInternal(activity);
    }
}
