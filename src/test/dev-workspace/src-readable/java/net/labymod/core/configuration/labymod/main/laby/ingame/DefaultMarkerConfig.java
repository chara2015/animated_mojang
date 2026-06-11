package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.ingame.MarkerConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/ingame/DefaultMarkerConfig.class */
public class DefaultMarkerConfig extends Config implements MarkerConfig {

    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SliderWidget.SliderSetting(min = 2.0f, max = 10.0f)
    private final ConfigProperty<Integer> duration = new ConfigProperty<>(10);

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MarkerConfig
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MarkerConfig
    public ConfigProperty<Integer> duration() {
        return this.duration;
    }
}
