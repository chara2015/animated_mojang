package net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist;

import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.playerlist.PlayerListUpdateEvent;
import net.labymod.api.event.client.gui.screen.playerlist.PlayerListUserUpdateEvent;
import net.labymod.api.event.client.gui.screen.playerlist.ServerBannerEvent;
import net.labymod.api.event.client.gui.screen.theme.ThemeChangeEvent;
import net.labymod.api.event.client.gui.screen.theme.ThemeUpdateEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoAddEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoUpdateEvent;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.ServerLoginEvent;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamEntryAddEvent;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamEntryRemoveEvent;
import net.labymod.api.event.client.scoreboard.ScoreboardTeamUpdateEvent;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;
import net.labymod.api.event.labymod.user.UserUpdateDataEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/playerlist/PlayerListListener.class */
public class PlayerListListener {
    private final PlayerListOverlay playerList;

    public PlayerListListener(PlayerListOverlay playerList) {
        this.playerList = playerList;
    }

    @Subscribe
    public void onTeamUpdate(ScoreboardTeamUpdateEvent event) {
        this.playerList.forEachUserFromTeam(event.team(), (v0) -> {
            v0.invalidate();
        });
    }

    @Subscribe
    public void onTeamEntryAdd(ScoreboardTeamEntryAddEvent event) {
        PlayerListUser user = this.playerList.getUser(event.getEntry());
        if (user != null && user.updateTeam(event.team())) {
            this.playerList.needSorting();
        }
    }

    @Subscribe
    public void onTeamEntryRemove(ScoreboardTeamEntryRemoveEvent event) {
        PlayerListUser user = this.playerList.getUser(event.getEntry());
        if (user != null && user.updateTeam(null)) {
            this.playerList.needSorting();
        }
    }

    @Subscribe
    public void onPlayerInfoAdd(PlayerInfoAddEvent event) {
        NetworkPlayerInfo networkPlayerInfo = event.playerInfo();
        if (!networkPlayerInfo.isListed()) {
            return;
        }
        this.playerList.addUser(event.playerInfo());
    }

    @Subscribe
    public void onPlayerInfoUpdate(PlayerInfoUpdateEvent event) {
        PlayerInfoUpdateEvent.UpdateType type = event.type();
        switch (type) {
            case UPDATE_LISTED:
                NetworkPlayerInfo networkPlayerInfo = event.playerInfo();
                if (networkPlayerInfo.isListed()) {
                    this.playerList.addUser(networkPlayerInfo);
                } else {
                    this.playerList.removeUser(networkPlayerInfo);
                }
                break;
            case GAME_MODE:
                PlayerListUser user = this.playerList.getUser(event.playerInfo());
                if (user != null) {
                    if (user.isSpectator() != (event.playerInfo().gameMode() == GameMode.SPECTATOR)) {
                        user.invalidate();
                        this.playerList.needSorting();
                    }
                }
                break;
            case DISPLAY_NAME:
            case UPDATE_LIST_ORDER:
                PlayerListUser user2 = this.playerList.getUser(event.playerInfo());
                if (user2 != null) {
                    user2.invalidate();
                }
                if (type == PlayerInfoUpdateEvent.UpdateType.UPDATE_LIST_ORDER) {
                    this.playerList.needSorting();
                }
                break;
        }
    }

    @Subscribe
    public void onResourceReload(ResourceReloadEvent event) {
        for (PlayerListUser user : this.playerList.getUsers()) {
            user.refresh();
        }
    }

    @Subscribe
    public void onPlayerInfoRemove(PlayerInfoRemoveEvent event) {
        PlayerListUser user;
        NetworkPlayerInfo info = event.playerInfo();
        if (info.isListed() && (user = this.playerList.getUser(info)) != null) {
            this.playerList.removeUser(user);
        }
    }

    @Subscribe
    public void onPlayerListUpdate(PlayerListUpdateEvent event) {
        for (PlayerListUser user : this.playerList.getUsers()) {
            user.invalidate();
        }
    }

    @Subscribe
    public void onPlayerListUserUpdate(PlayerListUserUpdateEvent event) {
        PlayerListUser user = this.playerList.getUser(event.playerInfo());
        if (user != null) {
            user.invalidate();
        }
    }

    @Subscribe
    public void onServerBanner(ServerBannerEvent event) {
        this.playerList.renderer.updateServerBanner(event.getUrl(), event.getHash());
    }

    @Subscribe
    public void onThemeChange(ThemeChangeEvent event) {
        if (event.phase() == Phase.POST) {
            this.playerList.updateTheme();
        }
    }

    @Subscribe
    public void onThemeUpdate(ThemeUpdateEvent event) {
        if (event.reason().isChangingDimensions()) {
            this.playerList.updateTheme();
        }
    }

    @Subscribe
    public void onServerSwitch(SubServerSwitchEvent event) {
        resetTabList();
    }

    @Subscribe
    public void onSettingUpdate(SettingUpdateEvent event) {
        this.playerList.needUpdate();
    }

    @Subscribe
    public void onUserUpdateData(UserUpdateDataEvent event) {
        PlayerListUser user;
        if (event.phase() == Phase.POST && (user = this.playerList.getUser(event.gameUser().getUniqueId())) != null) {
            user.invalidate();
        }
    }

    @Subscribe
    public void onServerDisconnect(ServerDisconnectEvent event) {
        resetTabList();
    }

    @Subscribe
    public void onServerLogin(ServerLoginEvent event) {
        resetTabList();
    }

    private void resetTabList() {
        this.playerList.users.clear();
        this.playerList.renderer.updateServerBanner(null, null);
    }
}
