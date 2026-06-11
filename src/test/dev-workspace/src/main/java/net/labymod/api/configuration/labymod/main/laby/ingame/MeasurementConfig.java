package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/ingame/MeasurementConfig.class */
public interface MeasurementConfig {
    ConfigProperty<Key> toggleKey();

    ConfigProperty<Key> clearKey();
}
