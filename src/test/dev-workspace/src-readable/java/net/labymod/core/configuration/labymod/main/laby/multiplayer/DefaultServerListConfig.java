package net.labymod.core.configuration.labymod.main.laby.multiplayer;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.util.parity.VanillaOverride;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingSection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/multiplayer/DefaultServerListConfig.class */
public class DefaultServerListConfig extends Config implements ServerListConfig {

    @SwitchWidget.SwitchSetting
    @IntroducedIn("4.2.0")
    private final ConfigProperty<Boolean> draggableEntries = new ConfigProperty<>(true);

    @DropdownWidget.DropdownSetting
    private final ConfigProperty<VanillaOverride> shadows = ConfigProperty.createEnum(VanillaOverride.VANILLA);

    @SpriteSlot(y = 7)
    @SwitchWidget.SwitchSetting
    @SettingSection("liveServerList")
    private final ConfigProperty<Boolean> liveServerList = new ConfigProperty<>(true);

    @SpriteSlot(x = 1, y = 7)
    @SliderWidget.SliderSetting(min = 15.0f, max = 30.0f)
    @SettingRequires("liveServerList")
    private final ConfigProperty<Integer> cooldown = new ConfigProperty<>(30);

    @SwitchWidget.SwitchSetting
    @SettingSection("richServerList")
    private final ConfigProperty<Boolean> richServerList = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    @SettingRequires("richServerList")
    private final ConfigProperty<Boolean> niceNames = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    @SettingRequires("richServerList")
    private final ConfigProperty<Boolean> highQualityTextures = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    @SettingRequires("richServerList")
    private final ConfigProperty<Boolean> quickJoinButtons = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    @SettingRequires("richServerList")
    private final ConfigProperty<Boolean> friendsInServerList = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    @IntroducedIn("4.4.0")
    @SettingSection("tabs")
    private final ConfigProperty<Boolean> publicServerList = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    @IntroducedIn("4.4.0")
    private final ConfigProperty<Boolean> friendsServerList = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    @IntroducedIn("4.4.0")
    @SettingSection("privacy")
    private final ConfigProperty<Boolean> proxyServerPing = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    @IntroducedIn("4.4.0")
    @SettingRequires("proxyServerPing")
    private final ConfigProperty<Boolean> protectPrivateServerList = new ConfigProperty<>(false);

    @SwitchWidget.SwitchSetting
    @IntroducedIn("4.4.0")
    @SettingRequires("proxyServerPing")
    private final ConfigProperty<Boolean> strictProxyMode = new ConfigProperty<>(true);

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<Boolean> draggableEntries() {
        return this.draggableEntries;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<Boolean> liveServerList() {
        return this.liveServerList;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<Integer> cooldown() {
        return this.cooldown;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<Boolean> richServerList() {
        return this.richServerList;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<Boolean> niceNames() {
        return this.niceNames;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<Boolean> highQualityTextures() {
        return this.highQualityTextures;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<Boolean> quickJoinButtons() {
        return this.quickJoinButtons;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<VanillaOverride> shadows() {
        return this.shadows;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<Boolean> friendsInServerList() {
        return this.friendsInServerList;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<Boolean> publicServerList() {
        return this.publicServerList;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<Boolean> friendsServerList() {
        return this.friendsServerList;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<Boolean> proxyServerPing() {
        return this.proxyServerPing;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<Boolean> protectPrivateServerList() {
        return this.protectPrivateServerList;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig
    public ConfigProperty<Boolean> strictProxyMode() {
        return this.strictProxyMode;
    }
}
