package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingSection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/ingame/DefaultFieldOfViewConfig.class */
public class DefaultFieldOfViewConfig extends Config implements FieldOfViewConfig {

    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    private final ConfigProperty<Boolean> enabled = ConfigProperty.create(false);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> dynamicSprint = ConfigProperty.create(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> dynamicStatusEffect = ConfigProperty.create(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> dynamicBow = ConfigProperty.create(true);

    @SliderWidget.SliderSetting(min = 30.0f, max = 110.0f)
    private final ConfigProperty<Float> vanilla = ConfigProperty.create(Float.valueOf(70.0f));

    @SwitchWidget.SwitchSetting
    @SettingSection("water")
    private final ConfigProperty<Boolean> overwriteWaterFov = ConfigProperty.create(false);

    @SliderWidget.SliderSetting(min = 30.0f, max = 110.0f)
    @SettingRequires("overwriteWaterFov")
    private final ConfigProperty<Float> waterFov = ConfigProperty.create(Float.valueOf(70.0f));

    @SwitchWidget.SwitchSetting
    @SettingSection("lava")
    private final ConfigProperty<Boolean> overwriteLavaFov = ConfigProperty.create(false);

    @SliderWidget.SliderSetting(min = 30.0f, max = 110.0f)
    @SettingRequires("overwriteLavaFov")
    private final ConfigProperty<Float> lavaFov = ConfigProperty.create(Float.valueOf(70.0f));

    @SwitchWidget.SwitchSetting
    @SettingSection("snowPowder")
    private final ConfigProperty<Boolean> overwriteSnowPowderFov = ConfigProperty.create(false);

    @SliderWidget.SliderSetting(min = 30.0f, max = 110.0f)
    @SettingRequires("overwriteSnowPowderFov")
    private final ConfigProperty<Float> snowPowderFov = ConfigProperty.create(Float.valueOf(70.0f));

    @SwitchWidget.SwitchSetting
    @SettingSection("bowPower")
    private final ConfigProperty<Boolean> bowPowerOverwrite = ConfigProperty.create(false);

    @SliderWidget.SliderSetting(min = 0.1f, max = 1.5f, steps = 0.1f)
    @SettingRequires("bowPowerOverwrite")
    private final ConfigProperty<Float> bowPowerMultiplier = ConfigProperty.create(Float.valueOf(0.15f));

    @SwitchWidget.SwitchSetting
    @SettingSection("flying")
    private final ConfigProperty<Boolean> flyingOverwrite = ConfigProperty.create(false);

    @SliderWidget.SliderSetting(min = 0.1f, max = 1.5f, steps = 0.1f)
    @SettingRequires("flyingOverwrite")
    private final ConfigProperty<Float> flyingMultiplier = ConfigProperty.create(Float.valueOf(1.1f));

    @SwitchWidget.SwitchSetting
    @SettingSection("sprinting")
    private final ConfigProperty<Boolean> sprintingOverwrite = ConfigProperty.create(false);

    @SliderWidget.SliderSetting(min = 0.1f, max = 1.5f, steps = 0.1f)
    @SettingRequires("sprintingOverwrite")
    private final ConfigProperty<Float> sprintingMultiplier = ConfigProperty.create(Float.valueOf(0.3f));

    @SwitchWidget.SwitchSetting
    @SettingSection("speed")
    private final ConfigProperty<Boolean> speedOverwrite = ConfigProperty.create(false);

    @SliderWidget.SliderSetting(min = 0.1f, max = 1.5f, steps = 0.1f)
    @SettingRequires("speedOverwrite")
    private final ConfigProperty<Float> speedMultiplier = ConfigProperty.create(Float.valueOf(0.2f));

    @SwitchWidget.SwitchSetting
    @SettingSection("slowness")
    private final ConfigProperty<Boolean> slownessOverwrite = ConfigProperty.create(false);

    @SliderWidget.SliderSetting(min = 0.1f, max = 1.5f, steps = 0.1f)
    @SettingRequires("slownessOverwrite")
    private final ConfigProperty<Float> slownessMultiplier = ConfigProperty.create(Float.valueOf(0.15f));

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Boolean> dynamicSprint() {
        return this.dynamicSprint;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Boolean> dynamicStatusEffect() {
        return this.dynamicStatusEffect;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Boolean> dynamicBow() {
        return this.dynamicBow;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Float> vanilla() {
        return this.vanilla;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Boolean> overwriteWaterFov() {
        return this.overwriteWaterFov;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Float> waterFov() {
        return this.waterFov;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Boolean> overwriteLavaFov() {
        return this.overwriteLavaFov;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Float> lavaFov() {
        return this.lavaFov;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Boolean> overwriteSnowPowderFov() {
        return this.overwriteSnowPowderFov;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Float> snowPowderFov() {
        return this.snowPowderFov;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Boolean> bowPowerOverwrite() {
        return this.bowPowerOverwrite;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Float> bowPowerMultiplier() {
        return this.bowPowerMultiplier;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Boolean> flyingOverwrite() {
        return this.flyingOverwrite;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Float> flyingMultiplier() {
        return this.flyingMultiplier;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Boolean> sprintingOverwrite() {
        return this.sprintingOverwrite;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Float> sprintingMultiplier() {
        return this.sprintingMultiplier;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Boolean> speedOverwrite() {
        return this.speedOverwrite;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Float> speedMultiplier() {
        return this.speedMultiplier;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Boolean> slownessOverwrite() {
        return this.slownessOverwrite;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig
    public ConfigProperty<Float> slownessMultiplier() {
        return this.slownessMultiplier;
    }
}
