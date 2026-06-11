package net.labymod.api.configuration.labymod.main.laby;

import net.labymod.api.configuration.labymod.main.laby.ingame.MarkerConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.AutoReconnectConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/MultiplayerConfig.class */
public interface MultiplayerConfig extends ConfigAccessor {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/MultiplayerConfig$ServerInfoPosition.class */
    public enum ServerInfoPosition {
        DISABLED,
        ABOVE_BUTTONS,
        BELOW_BUTTONS
    }

    ClassicPvPConfig classicPvP();

    ServerListConfig serverList();

    ConfigProperty<Boolean> confirmDisconnect();

    @NotNull
    AutoReconnectConfig autoReconnect();

    ConfigProperty<ServerInfoPosition> showCurrentServerInfo();

    TabListConfig tabList();

    PingConfig ping();

    ConfigProperty<Boolean> serverBanner();

    ConfigProperty<Boolean> customPlayerList();

    ConfigProperty<Boolean> clearTitles();

    MarkerConfig marker();
}
