package net.labymod.core.client.gui.screen.theme.listener;

import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.util.parity.VanillaParityUtil;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ActivityOpenEvent;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.client.gui.screen.ScreenOpenEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/listener/VanillaParityThemeListener.class */
public class VanillaParityThemeListener {
    @Subscribe
    public void onScreenOpen(ScreenOpenEvent event) {
        updateThemeVariables();
    }

    @Subscribe
    public void onActivityOpen(ActivityOpenEvent event) {
        updateThemeVariables();
    }

    @Subscribe
    public void onScreenDisplay(ScreenDisplayEvent event) {
        updateThemeVariables();
    }

    private void updateThemeVariables() {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        Window window = minecraft.minecraftWindow();
        LabyConfig config = Laby.labyAPI().config();
        window.setVariable("--multiplayer-shadows", VanillaParityUtil.isMultiplayerServerListShadowsEnabled(config.multiplayer().serverList().shadows()));
    }
}
