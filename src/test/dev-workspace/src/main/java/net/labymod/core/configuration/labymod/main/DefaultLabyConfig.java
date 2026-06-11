package net.labymod.core.configuration.labymod.main;

import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import net.labymod.api.configuration.labymod.main.laby.HotkeysConfig;
import net.labymod.api.configuration.labymod.main.laby.IngameConfig;
import net.labymod.api.configuration.labymod.main.laby.MultiplayerConfig;
import net.labymod.api.configuration.labymod.main.laby.NotificationsConfig;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.core.configuration.labymod.main.laby.DefaultAppearanceConfig;
import net.labymod.core.configuration.labymod.main.laby.DefaultHotkeysConfig;
import net.labymod.core.configuration.labymod.main.laby.DefaultIngameConfig;
import net.labymod.core.configuration.labymod.main.laby.DefaultMultiplayerConfig;
import net.labymod.core.configuration.labymod.main.laby.DefaultNotificationsConfig;
import net.labymod.core.configuration.labymod.main.laby.DefaultOtherConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/DefaultLabyConfig.class */
@ConfigName("settings")
@SpriteTexture("settings/main/laby")
public class DefaultLabyConfig extends Config implements LabyConfig {
    private DefaultIngameConfig ingame = new DefaultIngameConfig();
    private DefaultAppearanceConfig appearance = new DefaultAppearanceConfig();
    private DefaultMultiplayerConfig multiplayer = new DefaultMultiplayerConfig();
    private DefaultNotificationsConfig notifications = new DefaultNotificationsConfig();
    private DefaultOtherConfig other = new DefaultOtherConfig();
    private DefaultHotkeysConfig hotkeys = new DefaultHotkeysConfig();

    @Override // net.labymod.api.configuration.labymod.main.LabyConfig
    public IngameConfig ingame() {
        return this.ingame;
    }

    @Override // net.labymod.api.configuration.labymod.main.LabyConfig
    public AppearanceConfig appearance() {
        return this.appearance;
    }

    @Override // net.labymod.api.configuration.labymod.main.LabyConfig
    public MultiplayerConfig multiplayer() {
        return this.multiplayer;
    }

    @Override // net.labymod.api.configuration.labymod.main.LabyConfig
    public NotificationsConfig notifications() {
        return this.notifications;
    }

    @Override // net.labymod.api.configuration.labymod.main.LabyConfig
    public OtherConfig other() {
        return this.other;
    }

    @Override // net.labymod.api.configuration.labymod.main.LabyConfig
    public HotkeysConfig hotkeys() {
        return this.hotkeys;
    }

    @Override // net.labymod.api.configuration.loader.Config
    public int getConfigVersion() {
        return 4;
    }
}
