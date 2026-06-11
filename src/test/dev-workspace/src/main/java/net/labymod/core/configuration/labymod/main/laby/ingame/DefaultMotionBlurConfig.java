package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.laby3d.pipeline.RenderStates;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/ingame/DefaultMotionBlurConfig.class */
public class DefaultMotionBlurConfig extends Config implements MotionBlurConfig {

    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    private final ConfigProperty<Boolean> enabled = ConfigProperty.create(false);

    @DropdownWidget.DropdownSetting
    private final ConfigProperty<MotionBlurConfig.MotionBlurType> motionBlurType = ConfigProperty.createEnum(MotionBlurConfig.MotionBlurType.LABYMOD);

    @SliderWidget.SliderSetting(min = 4.0f, max = 32.0f)
    @SettingRequires(value = "motionBlurType", required = RenderStates.LABYMOD_FLAG)
    private final ConfigProperty<Integer> blurQuality = ConfigProperty.create(12);

    @SliderWidget.SliderSetting(min = 0.0f, max = 100.0f)
    private final ConfigProperty<Float> blurStrength = ConfigProperty.create(Float.valueOf(20.0f));

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig
    public ConfigProperty<MotionBlurConfig.MotionBlurType> motionBlurType() {
        return this.motionBlurType;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig
    public ConfigProperty<Integer> blurQuality() {
        return this.blurQuality;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig
    public ConfigProperty<Float> blurStrength() {
        return this.blurStrength;
    }
}
