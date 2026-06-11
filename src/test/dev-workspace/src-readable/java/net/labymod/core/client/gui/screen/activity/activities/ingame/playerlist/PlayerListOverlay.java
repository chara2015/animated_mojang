package net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.client.scoreboard.TabList;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.network.DefaultNetworkPlayerInfo;
import net.labymod.core.configuration.labymod.LabyConfigProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/playerlist/PlayerListOverlay.class */
@Link("activity/player-list.lss")
@AutoActivity
public class PlayerListOverlay extends IngameOverlayActivity {
    private boolean needsSorting;
    private boolean needsUpdate;
    private long timeLastNotVisible;
    private boolean isDoubleTapped;
    private boolean wasVisibleLastFrame;
    public final CachedComponent header = new CachedComponent();
    public final CachedComponent footer = new CachedComponent();
    public final List<PlayerListUser> users = new ArrayList();
    public final PlayerListListener listener = new PlayerListListener(this);
    public final PlayerListRenderer renderer = new PlayerListRenderer(this);

    public PlayerListOverlay() {
        onOpenScreen();
        updateTheme();
        this.labyAPI.eventBus().registerListener(this.listener);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        sort();
        TabList tabList = this.labyAPI.minecraft().getTabList();
        if (tabList == null) {
            this.header.invalidate();
            this.footer.invalidate();
        } else {
            int width = ((int) bounds().getWidth(BoundsType.INNER)) - 50;
            this.header.update(tabList.header(), width);
            this.footer.update(tabList.footer(), width);
        }
        this.renderer.render(context, this.labyAPI, bounds(), this.needsUpdate);
        this.needsUpdate = false;
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity
    public boolean isVisible() {
        TabList tabList = this.labyAPI.minecraft().getTabList();
        boolean visible = tabList != null && tabList.isVisible() && LabyConfigProvider.INSTANCE.get().multiplayer().customPlayerList().get().booleanValue();
        updateDoubleTab(visible);
        return visible;
    }

    private void updateDoubleTab(boolean visible) {
        if (visible && !this.wasVisibleLastFrame) {
            long timePassed = TimeUtil.getMillis() - this.timeLastNotVisible;
            this.isDoubleTapped = timePassed < 100;
        }
        if (!visible && this.wasVisibleLastFrame) {
            this.timeLastNotVisible = TimeUtil.getMillis();
        }
        if (this.wasVisibleLastFrame && !visible) {
            this.isDoubleTapped = false;
        }
        this.wasVisibleLastFrame = visible;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void resize(int width, int height) {
        super.resize(width, height);
        this.renderer.refreshColumns();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        if (!isVisible()) {
            return;
        }
        for (PlayerListUser user : this.users) {
            user.tick();
        }
        this.renderer.tick();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity
    public int getPriority() {
        return 100;
    }

    public void forEachUserFromTeam(ScoreboardTeam team, Consumer<PlayerListUser> user) {
        for (PlayerListUser playerListUser : this.users) {
            if (playerListUser.getTeam() == team) {
                user.accept(playerListUser);
            }
        }
    }

    public PlayerListUser getUser(String userName) {
        for (PlayerListUser user : this.users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public PlayerListUser getUser(NetworkPlayerInfo info) {
        return getUser(info.profile().getUniqueId());
    }

    public PlayerListUser getUser(UUID uniqueId) {
        for (PlayerListUser user : this.users) {
            if (user.uniqueId.equals(uniqueId)) {
                return user;
            }
        }
        return null;
    }

    public boolean isDoubleTapped() {
        return this.isDoubleTapped;
    }

    public List<PlayerListUser> getUsers() {
        return this.users;
    }

    public PlayerListUser addUser(NetworkPlayerInfo info) {
        PlayerListUser user = getUser(info);
        if (user != null) {
            removeUser(user);
        }
        PlayerListUser user2 = new PlayerListUser(info);
        this.users.add(user2);
        needSorting();
        return user2;
    }

    public void removeUser(NetworkPlayerInfo playerInfo) {
        removeUser(getUser(playerInfo));
    }

    public void removeUser(PlayerListUser user) {
        this.users.remove(user);
        needUpdate();
    }

    public void updateTheme() {
        for (PlayerListUser user : this.users) {
            user.refresh();
        }
        this.renderer.updateTheme();
        this.header.refresh();
        this.footer.refresh();
    }

    public void needSorting() {
        this.needsSorting = true;
        this.needsUpdate = true;
    }

    public void needUpdate() {
        this.needsUpdate = true;
    }

    private void sort() {
        if (!this.needsSorting) {
            return;
        }
        this.needsSorting = false;
        this.users.sort(DefaultNetworkPlayerInfo.PLAYER_LIST_COMPARATOR);
    }
}
