package net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/multiplayer/tablist/AdvancedTabListConfig.class */
public interface AdvancedTabListConfig extends ConfigAccessor {
    ConfigProperty<Boolean> enabled();

    ConfigProperty<Integer> maxPlayers();

    ConfigProperty<Integer> playersPerColumn();
}
