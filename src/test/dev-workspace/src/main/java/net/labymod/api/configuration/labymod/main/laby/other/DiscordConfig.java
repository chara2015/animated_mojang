package net.labymod.api.configuration.labymod.main.laby.other;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/other/DiscordConfig.class */
public interface DiscordConfig extends ConfigAccessor {
    ConfigProperty<Boolean> enabled();

    ConfigProperty<Boolean> showServerAddress();

    ConfigProperty<Boolean> displayAccount();
}
