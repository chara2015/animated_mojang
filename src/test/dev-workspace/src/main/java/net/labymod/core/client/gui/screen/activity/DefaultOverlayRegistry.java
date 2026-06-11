package net.labymod.core.client.gui.screen.activity;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.overlay.OverlayRegistry;
import net.labymod.api.client.gui.screen.activity.overlay.RegisteredReplacement;
import net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.service.DefaultRegistry;
import net.labymod.api.util.KeyValue;
import net.labymod.core.client.gui.screen.accessor.PauseScreenAccessor;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.core.client.gui.screen.activity.activities.labymod.LabyModActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.SkinActivity;
import net.labymod.core.client.gui.screen.activity.activities.menu.IngameMenuOverlay;
import net.labymod.core.client.gui.screen.activity.activities.menu.MainMenuActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.connect.ConnectOverlay;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.connect.DisconnectedOverlay;
import net.labymod.core.client.gui.screen.activity.activities.options.OptionsOverlay;
import net.labymod.core.client.gui.screen.activity.activities.options.SkinCustomizationOverlay;
import net.labymod.core.client.gui.screen.activity.activities.singleplayer.SingleplayerOverlay;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/DefaultOverlayRegistry.class */
@Singleton
@Implements(OverlayRegistry.class)
public class DefaultOverlayRegistry extends DefaultRegistry<RegisteredReplacement> implements OverlayRegistry {
    private final LabyAPI api;

    @Inject
    public DefaultOverlayRegistry(LabyAPI api) {
        this.api = api;
        this.api.eventBus().registerListener(this);
        register(NamedScreen.MAIN_MENU, MainMenuActivity.class, MainMenuActivity::new);
        register(NamedScreen.OPTIONS, OptionsOverlay.class, OptionsOverlay::new);
        register(NamedScreen.CONNECTING, ConnectOverlay.class, ConnectOverlay::new);
        register(NamedScreen.DISCONNECTED, DisconnectedOverlay.class, DisconnectedOverlay::new);
        register(NamedScreen.INGAME_MENU, IngameMenuOverlay.class, screenInstance -> {
            if (screenInstance instanceof ScreenWrapper) {
                ScreenWrapper wrapper = (ScreenWrapper) screenInstance;
                Object versionedScreen = wrapper.getVersionedScreen();
                if (versionedScreen instanceof PauseScreenAccessor) {
                    PauseScreenAccessor accessor = (PauseScreenAccessor) versionedScreen;
                    if (!accessor.showPauseMenu()) {
                        return null;
                    }
                }
            }
            return new IngameMenuOverlay(screenInstance);
        });
        register(NamedScreen.CHAT_INPUT, ChatInputOverlay.class, ChatInputOverlay::new);
        register(NamedScreen.CHAT_INPUT_IN_BED, ChatInputOverlay.class, ChatInputOverlay::new);
        register(NamedScreen.SINGLEPLAYER, SingleplayerOverlay.class, SingleplayerOverlay::new);
        register(NamedScreen.MULTIPLAYER, MultiplayerActivity.class, () -> {
            return MultiplayerActivity.INSTANCE;
        });
        register(NamedScreen.SKIN_CUSTOMIZATION, SkinCustomizationOverlay.class, parentScreen -> {
            LabyModActivity labyModActivity;
            PlayerActivity playerActivity;
            LabyAPI labyAPI = Laby.labyAPI();
            if (labyAPI.config().appearance().replaceSkinCustomization().get().booleanValue() && (labyModActivity = LabyModActivity.getFromNavigationRegistry()) != null && (playerActivity = (PlayerActivity) labyModActivity.switchTab(PlayerActivity.class)) != null) {
                playerActivity.displayChild(SkinActivity.class);
                return labyModActivity;
            }
            return new SkinCustomizationOverlay(parentScreen);
        });
        updateSettings();
    }

    private void updateSettings() {
        AppearanceConfig appearance = Laby.labyAPI().config().appearance();
        get(NamedScreen.MAIN_MENU).setEnabled(appearance.titleScreen().custom().get().booleanValue());
    }

    @Subscribe
    public void onSettingUpdate(SettingUpdateEvent event) {
        if (event.phase() == Phase.POST && event.setting().getPath().equals("settings.appearance.customTitleScreen.enabled")) {
            updateSettings();
        }
    }

    @Subscribe
    public void onScreenOpen(ScreenDisplayEvent event) {
        ScreenInstance wrapper = event.getScreen();
        if (wrapper == null) {
            return;
        }
        ScreenInstance screen = wrapper.mostInnerScreenInstance();
        Activity activity = toOverlay(screen);
        if (activity == null) {
            return;
        }
        event.setScreen(activity);
    }

    @Override // net.labymod.api.client.gui.screen.activity.overlay.OverlayRegistry
    public Activity toOverlay(ScreenInstance screen) {
        RegisteredReplacement registered;
        if (screen instanceof AbstractLayerActivity) {
            return (Activity) screen;
        }
        Object raw = screen.mostInnerScreen();
        String screenName = this.api.screenService().getScreenNameByClass(raw.getClass());
        if (screenName == null || (registered = getById(screenName)) == null || !registered.isEnabled()) {
            return null;
        }
        return registered.getInitializer().create(screen);
    }

    @Override // net.labymod.api.client.gui.screen.activity.overlay.OverlayRegistry
    public Object toRawScreen(Activity overlay) {
        for (KeyValue<RegisteredReplacement> element : getElements()) {
            if (element.getValue().getClazz().equals(overlay.getClass())) {
                return this.api.screenService().createScreen(element.getKey());
            }
        }
        return null;
    }
}
