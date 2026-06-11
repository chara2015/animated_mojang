package net.labymod.core.thirdparty.discord.listener;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import net.labymod.api.event.client.network.server.NetworkLoginEvent;
import net.labymod.core.thirdparty.discord.DefaultDiscordApp;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/thirdparty/discord/listener/DiscordNetworkListener.class */
@Singleton
public final class DiscordNetworkListener {
    private final DefaultDiscordApp discordApp = (DefaultDiscordApp) Laby.references().discordApp();

    @Inject
    public DiscordNetworkListener() {
    }

    @Subscribe
    public void onNetworkLogin(NetworkLoginEvent event) {
        this.discordApp.updateServerInfo(event.serverData(), true);
    }

    @Subscribe
    public void onNetworkDisconnect(NetworkDisconnectEvent event) {
        this.discordApp.displayDefaultActivity();
    }
}
