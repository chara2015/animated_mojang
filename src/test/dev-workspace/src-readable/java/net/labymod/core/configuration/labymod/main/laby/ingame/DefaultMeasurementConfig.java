package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.input.KeybindWidget;
import net.labymod.api.configuration.labymod.main.laby.ingame.MeasurementConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/ingame/DefaultMeasurementConfig.class */
public class DefaultMeasurementConfig extends Config implements MeasurementConfig {

    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    private final ConfigProperty<Key> toggleKey = new ConfigProperty<>(Key.NONE);

    @KeybindWidget.KeyBindSetting(acceptMouseButtons = true)
    private final ConfigProperty<Key> clearKey = new ConfigProperty<>(Key.NONE);

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MeasurementConfig
    public ConfigProperty<Key> toggleKey() {
        return this.toggleKey;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.MeasurementConfig
    public ConfigProperty<Key> clearKey() {
        return this.clearKey;
    }
}
