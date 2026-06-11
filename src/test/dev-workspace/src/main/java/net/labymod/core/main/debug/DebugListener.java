package net.labymod.core.main.debug;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.lifecycle.GameFpsLimitEvent;
import net.labymod.core.client.gui.screen.theme.DefaultThemeService;
import net.labymod.core.client.gui.screen.theme.fancy.FancyTheme;
import net.labymod.core.client.gui.screen.theme.vanilla.VanillaTheme;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/DebugListener.class */
@Singleton
public final class DebugListener {
    private final LabyAPI labyAPI = Laby.labyAPI();
    private final DefaultThemeService defaultThemeService = (DefaultThemeService) this.labyAPI.themeService();
    private boolean framerateLimit;

    @Inject
    public DebugListener() {
    }

    @Subscribe
    public void onKey(KeyEvent event) {
        if (event.state() != KeyEvent.State.PRESS) {
            return;
        }
        Key key = event.key();
        boolean controlPressed = KeyHandler.isLeftControlDown();
        boolean developmentEnvironment = this.labyAPI.labyModLoader().isLabyModDevelopmentEnvironment();
        AdvancedConfig advanced = this.labyAPI.config().other().advanced();
        if (controlPressed && key == Key.L && advanced.refreshHotkey()) {
            this.labyAPI.refresh();
            return;
        }
        if (this.labyAPI.minecraft().isMouseLocked()) {
            return;
        }
        if (controlPressed) {
            DebugRegistry.toggleStates(key);
        }
        if (!developmentEnvironment) {
            return;
        }
        if (controlPressed && key == Key.T) {
            switchTheme();
            return;
        }
        if (controlPressed && key == Key.R) {
            this.defaultThemeService.reload(true);
        } else if (controlPressed && key == Key.L) {
            this.framerateLimit = !this.framerateLimit;
        }
    }

    @Subscribe
    public void handleFramerateLimit(GameFpsLimitEvent event) {
        if (this.framerateLimit) {
            event.setFramerateLimit(1);
        }
    }

    private void switchTheme() {
        String str;
        Theme currentTheme = this.defaultThemeService.currentTheme();
        if (currentTheme.getId().equals(VanillaTheme.ID)) {
            str = FancyTheme.ID;
        } else {
            str = VanillaTheme.ID;
        }
        String name = str;
        this.defaultThemeService.reload(name, true);
    }
}
