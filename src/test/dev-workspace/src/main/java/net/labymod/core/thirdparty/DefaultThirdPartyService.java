package net.labymod.core.thirdparty;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.configuration.labymod.main.laby.other.DiscordConfig;
import net.labymod.api.event.EventBus;
import net.labymod.api.models.Implements;
import net.labymod.api.thirdparty.ThirdPartyService;
import net.labymod.api.thirdparty.discord.DiscordApp;
import net.labymod.core.thirdparty.discord.DefaultDiscordApp;
import net.labymod.core.thirdparty.discord.listener.DiscordNetworkListener;
import net.labymod.core.thirdparty.discord.listener.DiscordSessionUpdateListener;
import net.labymod.core.thirdparty.discord.listener.DiscordSingleplayerListener;
import net.labymod.core.thirdparty.listener.ThirdPartyTickListener;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/thirdparty/DefaultThirdPartyService.class */
@Singleton
@Implements(ThirdPartyService.class)
public class DefaultThirdPartyService implements ThirdPartyService {
    private final LabyAPI labyAPI;
    private final EventBus eventBus;
    private final DiscordApp discordRichPresence;

    @Inject
    public DefaultThirdPartyService(LabyAPI labyAPI, EventBus eventBus, DiscordApp discordRichPresence) {
        this.labyAPI = labyAPI;
        this.eventBus = eventBus;
        this.discordRichPresence = discordRichPresence;
        DiscordConfig config = this.labyAPI.config().other().discord();
        config.enabled().addChangeListener((type, oldValue, newValue) -> {
            ThirdPartyService thirdPartyService = Laby.labyAPI().thirdPartyService();
            if (thirdPartyService == null) {
                return;
            }
            DiscordApp discordApp = thirdPartyService.discord();
            if (!(discordApp instanceof DefaultDiscordApp)) {
                return;
            }
            DefaultDiscordApp defaultDiscordApp = (DefaultDiscordApp) discordApp;
            if (newValue.booleanValue()) {
                defaultDiscordApp.initialize();
                defaultDiscordApp.updateServerInfo(true);
            } else {
                defaultDiscordApp.dispose();
            }
        });
        config.showServerAddress().addChangeListener((type2, oldValue2, newValue2) -> {
            ThirdPartyService thirdPartyService = Laby.labyAPI().thirdPartyService();
            if (thirdPartyService == null) {
                return;
            }
            DiscordApp discordApp = thirdPartyService.discord();
            if (!(discordApp instanceof DefaultDiscordApp)) {
                return;
            }
            ((DefaultDiscordApp) discordApp).updateServerInfo(false);
        });
    }

    @Override // net.labymod.api.thirdparty.ThirdPartyService
    public void initialize() {
        this.eventBus.registerListener(new ThirdPartyTickListener());
        initializeDiscordApp();
    }

    @Override // net.labymod.api.thirdparty.ThirdPartyService
    public DiscordApp discord() {
        return this.discordRichPresence;
    }

    private void initializeDiscordApp() {
        this.eventBus.registerListener(new DiscordNetworkListener());
        this.eventBus.registerListener(new DiscordSingleplayerListener());
        this.eventBus.registerListener(new DiscordSessionUpdateListener());
        if (this.labyAPI.config().other().discord().enabled().get().booleanValue()) {
            ((DefaultDiscordApp) this.discordRichPresence).initialize();
        }
    }
}
