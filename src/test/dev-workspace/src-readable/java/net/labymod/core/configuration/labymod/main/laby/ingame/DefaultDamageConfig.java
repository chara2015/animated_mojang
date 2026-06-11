package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.configuration.labymod.main.laby.ingame.DamageConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/ingame/DefaultDamageConfig.class */
@SettingRequires("damageColored")
public class DefaultDamageConfig extends Config implements DamageConfig {

    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    private final ConfigProperty<Boolean> damageColored = new ConfigProperty<>(false);

    @SpriteSlot(x = 2, y = 1, page = 1)
    @ColorPickerWidget.ColorPickerSetting(chroma = true, alpha = true)
    private final ConfigProperty<Color> damageColor = new ConfigProperty<>(Color.ofRGB(255, 0, 0, 76));

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.DamageConfig
    public ConfigProperty<Boolean> damageColored() {
        return this.damageColored;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.DamageConfig
    public ConfigProperty<Color> damageColor() {
        return this.damageColor;
    }
}
