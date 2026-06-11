package net.labymod.core.configuration.labymod.main.laby.multiplayer.classicpvp;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.classicpvp.OldEquipConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/multiplayer/classicpvp/DefaultOldEquipConfig.class */
public class DefaultOldEquipConfig extends Config implements OldEquipConfig {

    @SpriteSlot(x = 6, y = 2)
    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(false);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> hideIndicator = new ConfigProperty<>(true);

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.classicpvp.OldEquipConfig
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.classicpvp.OldEquipConfig
    public ConfigProperty<Boolean> hideIndicator() {
        return this.hideIndicator;
    }
}
