package net.labymod.core.configuration.labymod.main.laby.appearance;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/appearance/DefaultDynamicBackgroundConfig.class */
@SettingRequires("enabled")
public class DefaultDynamicBackgroundConfig extends Config implements DynamicBackgroundConfig {

    @SpriteSlot(x = 5, y = 2, page = 1)
    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SpriteSlot(x = 5, y = 3, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> shader = new ConfigProperty<>(true);

    @SpriteSlot(x = 6, y = 3, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> coloredLight = new ConfigProperty<>(true);

    @SpriteSlot(x = 7, y = 3, page = 1)
    @SliderWidget.SliderSetting(min = 0.1f, max = 10.0f, steps = 0.1f)
    private final ConfigProperty<Float> brightness = new ConfigProperty<>(Float.valueOf(2.0f));

    @SpriteSlot(x = 1, y = 4, page = 1)
    @SliderWidget.SliderSetting(min = 1.0f, max = 10.0f)
    private final ConfigProperty<Float> resolution = new ConfigProperty<>(Float.valueOf(5.0f));

    @SpriteSlot(x = 2, y = 4, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> limitFpsWhenUnfocused = new ConfigProperty<>(true);

    @SpriteSlot(x = 3, y = 4, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> animatedTextures = new ConfigProperty<>(true);

    @SpriteSlot(x = 5, y = 5, page = 1)
    @SwitchWidget.SwitchSetting
    @Exclude
    private final ConfigProperty<Boolean> snowflakes = new ConfigProperty<>(true);

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig
    public ConfigProperty<Boolean> shader() {
        return this.shader;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig
    public ConfigProperty<Boolean> coloredLight() {
        return this.coloredLight;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig
    public ConfigProperty<Float> brightness() {
        return this.brightness;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig
    public ConfigProperty<Float> resolution() {
        return this.resolution;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig
    public ConfigProperty<Boolean> limitFpsWhenUnfocused() {
        return this.limitFpsWhenUnfocused;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig
    public ConfigProperty<Boolean> animatedTextures() {
        return this.animatedTextures;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig
    public ConfigProperty<Boolean> snowflakes() {
        return this.snowflakes;
    }
}
