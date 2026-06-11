package net.labymod.core.configuration.labymod.main.laby.multiplayer.tablist;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/multiplayer/tablist/DefaultPingConfig.class */
@SettingRequires("enabled")
public class DefaultPingConfig extends Config implements PingConfig {

    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SpriteSlot(x = 5, y = 6)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> exact = new ConfigProperty<>(false);

    @SpriteSlot(x = 6, y = 6)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> exactColored = new ConfigProperty<>(true);

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig
    public ConfigProperty<Boolean> exact() {
        return this.exact;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig
    public ConfigProperty<Boolean> exactColored() {
        return this.exactColored;
    }
}
