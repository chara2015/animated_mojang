package net.labymod.core.configuration.labymod.main.laby.appearance;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.appearance.TitleScreenConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/appearance/DefaultTitleScreenConfig.class */
@SettingRequires("enabled")
public class DefaultTitleScreenConfig extends Config implements TitleScreenConfig {

    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SpriteSlot(x = 2, y = 3, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> socialMediaLinks = new ConfigProperty<>(true);

    @SpriteSlot(x = 7, y = 6)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> quickPlay = new ConfigProperty<>(false);

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.TitleScreenConfig
    public ConfigProperty<Boolean> custom() {
        return this.enabled;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.TitleScreenConfig
    public ConfigProperty<Boolean> socialMediaLinks() {
        return this.socialMediaLinks;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.appearance.TitleScreenConfig
    public ConfigProperty<Boolean> quickPlay() {
        return this.quickPlay;
    }
}
