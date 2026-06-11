package net.labymod.core.configuration.labymod.main.laby.appearance;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.appearance.NavigationConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/appearance/DefaultNavigationConfig.class */
public class DefaultNavigationConfig extends Config implements NavigationConfig {

    @SpriteSlot(x = 7)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showSingleplayer = new ConfigProperty<>(false);

    @SpriteSlot(y = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showOptions = new ConfigProperty<>(true);

    @SpriteSlot(x = 7, y = 4, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> rememberLastTab = new ConfigProperty<>(false);

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.NavigationConfig
    public ConfigProperty<Boolean> showSingleplayer() {
        return this.showSingleplayer;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.NavigationConfig
    public ConfigProperty<Boolean> showOptions() {
        return this.showOptions;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.NavigationConfig
    public ConfigProperty<Boolean> rememberLastTab() {
        return this.rememberLastTab;
    }
}
