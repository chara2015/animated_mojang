package net.labymod.api.configuration.labymod.main.laby.appearance;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/appearance/NavigationConfig.class */
public interface NavigationConfig extends ConfigAccessor {
    ConfigProperty<Boolean> showSingleplayer();

    ConfigProperty<Boolean> showOptions();

    ConfigProperty<Boolean> rememberLastTab();
}
