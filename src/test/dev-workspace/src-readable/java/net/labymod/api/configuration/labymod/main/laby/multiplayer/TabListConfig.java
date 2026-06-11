package net.labymod.api.configuration.labymod.main.laby.multiplayer;

import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.AdvancedTabListConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/multiplayer/TabListConfig.class */
public interface TabListConfig extends ConfigAccessor {
    ConfigProperty<Boolean> enabled();

    ConfigProperty<Color> backgroundColor();

    ConfigProperty<Color> foregroundColor();

    ConfigProperty<Boolean> serverBanner();

    ConfigProperty<Boolean> labyModPercentage();

    ConfigProperty<Boolean> labyModBadge();

    ConfigProperty<Boolean> showHeads();

    ConfigProperty<Boolean> showCountryFlags();

    PingConfig ping();

    AdvancedTabListConfig advancedTabList();
}
