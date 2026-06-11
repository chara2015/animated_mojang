package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/ingame/DefaultMenuBlurConfig.class */
public class DefaultMenuBlurConfig extends Config implements MenuBlurConfig {

    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    private final ConfigProperty<Boolean> enabled = ConfigProperty.create(false);

    @SliderWidget.SliderSetting(min = 1.0f, max = 10.0f)
    private final ConfigProperty<Float> strength = ConfigProperty.create(Float.valueOf(4.0f));

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> enhancedContrast = ConfigProperty.create(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> titleScreen = ConfigProperty.create(false);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> pauseMenu = ConfigProperty.create(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> inventories = ConfigProperty.create(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> emoteWheel = ConfigProperty.create(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> sprayWheel = ConfigProperty.create(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> interactionWheel = ConfigProperty.create(true);

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig
    public ConfigProperty<Float> strength() {
        return this.strength;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig
    public ConfigProperty<Boolean> enhancedContrast() {
        return this.enhancedContrast;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig
    public ConfigProperty<Boolean> titleScreen() {
        return this.titleScreen;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig
    public ConfigProperty<Boolean> pauseMenu() {
        return this.pauseMenu;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig
    public ConfigProperty<Boolean> inventories() {
        return this.inventories;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig
    public ConfigProperty<Boolean> emoteWheel() {
        return this.emoteWheel;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig
    public ConfigProperty<Boolean> sprayWheel() {
        return this.sprayWheel;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig
    public ConfigProperty<Boolean> interactionWheel() {
        return this.interactionWheel;
    }
}
