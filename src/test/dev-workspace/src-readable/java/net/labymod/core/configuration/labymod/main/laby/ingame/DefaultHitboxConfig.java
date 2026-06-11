package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/ingame/DefaultHitboxConfig.class */
@SettingRequires("enabled")
public class DefaultHitboxConfig extends Config implements HitboxConfig {

    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(false);

    @SliderWidget.SliderSetting(min = 1.0f, max = 10.0f, steps = 0.1f)
    private final ConfigProperty<Float> lineSize = new ConfigProperty<>(Float.valueOf(2.5f));

    @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
    private final ConfigProperty<Color> eyeLineColor = new ConfigProperty<>(Color.of("#0000AA"));

    @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
    private final ConfigProperty<Color> eyeBoxColor = new ConfigProperty<>(Color.of("#AA0000"));

    @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
    private final ConfigProperty<Color> boxColor = new ConfigProperty<>(Color.WHITE);

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig
    public ConfigProperty<Float> lineSize() {
        return this.lineSize;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig
    public ConfigProperty<Color> eyeLineColor() {
        return this.eyeLineColor;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig
    public ConfigProperty<Color> eyeBoxColor() {
        return this.eyeBoxColor;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig
    public ConfigProperty<Color> boxColor() {
        return this.boxColor;
    }
}
