package net.labymod.core.configuration.labymod.main.laby.multiplayer;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.AutoReconnectConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/multiplayer/DefaultAutoReconnectConfig.class */
public class DefaultAutoReconnectConfig extends Config implements AutoReconnectConfig {

    @SwitchWidget.SwitchSetting
    @IntroducedIn("4.4.0")
    @ShowSettingInParent
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(false);

    @SwitchWidget.SwitchSetting
    @IntroducedIn("4.4.0")
    private final ConfigProperty<Boolean> reconnectAutomatically = new ConfigProperty<>(true);

    @SliderWidget.SliderSetting(min = 5.0f, max = 120.0f, steps = 5.0f)
    @IntroducedIn("4.4.0")
    @SettingRequires("reconnectAutomatically")
    private final ConfigProperty<Integer> delay = new ConfigProperty<>(30);

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.AutoReconnectConfig
    @NotNull
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.AutoReconnectConfig
    @NotNull
    public ConfigProperty<Boolean> reconnectAutomatically() {
        return this.reconnectAutomatically;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.AutoReconnectConfig
    @NotNull
    public ConfigProperty<Integer> delay() {
        return this.delay;
    }
}
