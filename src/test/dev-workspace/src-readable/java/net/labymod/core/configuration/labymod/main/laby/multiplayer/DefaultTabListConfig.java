package net.labymod.core.configuration.labymod.main.laby.multiplayer;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.AdvancedTabListConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.util.Color;
import net.labymod.core.configuration.labymod.main.laby.multiplayer.tablist.DefaultAdvancedTabListConfig;
import net.labymod.core.configuration.labymod.main.laby.multiplayer.tablist.DefaultPingConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/multiplayer/DefaultTabListConfig.class */
@SettingRequires("enabled")
public class DefaultTabListConfig extends Config implements TabListConfig {

    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
    private final ConfigProperty<Color> backgroundColor = new ConfigProperty<>(Color.of(Integer.MIN_VALUE));

    @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
    private final ConfigProperty<Color> foregroundColor = new ConfigProperty<>(Color.of(553648127));

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> serverBanner = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> labyModPercentage = new ConfigProperty<>(false);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> labyModBadge = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showHeads = new ConfigProperty<>(true);

    @SpriteSlot(x = 7, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showCountryFlags = new ConfigProperty<>(true);
    private DefaultPingConfig ping = new DefaultPingConfig();
    private DefaultAdvancedTabListConfig advancedTabList = new DefaultAdvancedTabListConfig();

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig
    public ConfigProperty<Color> backgroundColor() {
        return this.backgroundColor;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig
    public ConfigProperty<Color> foregroundColor() {
        return this.foregroundColor;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig
    public ConfigProperty<Boolean> serverBanner() {
        return this.serverBanner;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig
    public ConfigProperty<Boolean> labyModPercentage() {
        return this.labyModPercentage;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig
    public ConfigProperty<Boolean> labyModBadge() {
        return this.labyModBadge;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig
    public ConfigProperty<Boolean> showHeads() {
        return this.showHeads;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig
    public ConfigProperty<Boolean> showCountryFlags() {
        return this.showCountryFlags;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig
    public PingConfig ping() {
        return this.ping;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig
    public AdvancedTabListConfig advancedTabList() {
        return this.advancedTabList;
    }
}
