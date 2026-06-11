package net.labymod.api.configuration.labymod.main.laby.other;

import net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/other/WindowConfig.class */
public interface WindowConfig extends ConfigAccessor {
    ConfigProperty<Boolean> cleanWindowTitle();

    ConfigProperty<Boolean> borderlessWindow();

    ConfigProperty<Boolean> restoreWindowResolution();

    MicrosoftWindowConfig microsoftWindow();
}
