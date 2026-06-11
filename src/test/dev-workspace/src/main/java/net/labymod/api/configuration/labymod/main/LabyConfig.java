package net.labymod.api.configuration.labymod.main;

import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import net.labymod.api.configuration.labymod.main.laby.HotkeysConfig;
import net.labymod.api.configuration.labymod.main.laby.IngameConfig;
import net.labymod.api.configuration.labymod.main.laby.MultiplayerConfig;
import net.labymod.api.configuration.labymod.main.laby.NotificationsConfig;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.api.configuration.loader.ConfigAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/LabyConfig.class */
public interface LabyConfig extends ConfigAccessor {
    IngameConfig ingame();

    AppearanceConfig appearance();

    MultiplayerConfig multiplayer();

    NotificationsConfig notifications();

    OtherConfig other();

    HotkeysConfig hotkeys();
}
