package net.labymod.api.configuration.labymod.main.laby.multiplayer.classicpvp;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/multiplayer/classicpvp/OldEquipConfig.class */
public interface OldEquipConfig extends ConfigAccessor {
    ConfigProperty<Boolean> enabled();

    ConfigProperty<Boolean> hideIndicator();
}
