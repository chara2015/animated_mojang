package net.labymod.core.thirdparty.discord.listener;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.session.Session;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.core.thirdparty.discord.DefaultDiscordApp;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/thirdparty/discord/listener/DiscordSessionUpdateListener.class */
@Singleton
public class DiscordSessionUpdateListener {
    private final DefaultDiscordApp discordRichPresence = (DefaultDiscordApp) Laby.references().discordApp();

    @Inject
    public DiscordSessionUpdateListener() {
    }

    @Subscribe
    public void onSessionUpdate(SessionUpdateEvent event) {
        Session session = event.newSession();
        if (!session.isPremium()) {
            return;
        }
        this.discordRichPresence.updateUserAsset(session.getUsername(), session.getUniqueId());
    }
}
