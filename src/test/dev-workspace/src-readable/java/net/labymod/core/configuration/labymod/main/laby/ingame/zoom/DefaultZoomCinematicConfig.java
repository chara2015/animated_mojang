package net.labymod.core.configuration.labymod.main.laby.ingame.zoom;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.ingame.zoom.ZoomCinematicConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/ingame/zoom/DefaultZoomCinematicConfig.class */
public class DefaultZoomCinematicConfig extends Config implements ZoomCinematicConfig {

    @SpriteSlot(x = 3, y = 5)
    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @SpriteSlot(x = 4, y = 5)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> disableAfterTransition = new ConfigProperty<>(false);

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.zoom.ZoomCinematicConfig
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.zoom.ZoomCinematicConfig
    public ConfigProperty<Boolean> disableAfterTransition() {
        return this.disableAfterTransition;
    }
}
