package net.labymod.core.client.gui.screen.theme.fancy.eventlistener;

import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.theme.ThemeEventListener;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.title.TitleScreenLogoInitializeEvent;
import net.labymod.api.event.labymod.config.ConfigurationSaveEvent;
import net.labymod.core.client.gui.screen.theme.fancy.FancyTheme;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/fancy/eventlistener/TitleMenuThemeEventListener.class */
public class TitleMenuThemeEventListener implements ThemeEventListener {
    private final FancyTheme theme;

    public TitleMenuThemeEventListener(FancyTheme theme) {
        this.theme = theme;
    }

    @Subscribe
    public void onConfigurationSave(ConfigurationSaveEvent event) {
        this.theme.updateTransitionProperty();
    }

    @Subscribe(-64)
    public void onTitleScreenRenderLogo(TitleScreenLogoInitializeEvent event) {
        if (event.getType().equals("legacy")) {
            event.setMinecraftWidget(new IconWidget(Icon.sprite(Textures.Title.MINECRAFT_LOGO, 0, 0, 256, 44, 256, 64)));
        }
        event.setEditionWidget(new IconWidget(Icon.sprite(Textures.Title.LABYMOD_EDITION, 0, 0, 128, 14, 128, 16)));
    }
}
