package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SimpleButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.WrappedListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labynet.models.GameMode;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.collection.Lists;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/multiplayer/LabyNetServerInfoWidget.class */
@AutoWidget
public class LabyNetServerInfoWidget<T extends ConnectableServerData> extends ServerInfoWidget<T> {
    protected static final Lazy<ServerListConfig> SERVER_LIST_SETTINGS = Lazy.of(() -> {
        return Laby.labyAPI().config().multiplayer().serverList();
    });
    private final LabyNetServerInfoCache<T> cache;
    private final List<GameMode> gameModes;
    protected String serverGroup;
    protected CompletableResourceLocation background;
    protected CompletableResourceLocation icon;
    protected String userStatsUrl;
    private boolean visibleRefresh;
    private boolean quickJoin;
    private boolean friendHeads;

    public LabyNetServerInfoWidget(@NotNull T serverData, @NotNull LabyNetServerInfoCache<T> cache) {
        super(serverData, cache.serverInfo(), cache.getSortingValue());
        this.quickJoin = true;
        this.friendHeads = true;
        Objects.requireNonNull(cache, "Cache cannot be null");
        this.cache = cache;
        this.gameModes = new ArrayList();
        this.visibleRefresh = cache.serverInfo().getStatus() == ServerInfo.Status.LOADING;
        this.cache.setCallback(serverInfoCache -> {
            this.visibleRefresh = false;
            updateServerInfo(serverInfoCache.serverInfo());
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    protected Component serverName() {
        ServerGroup serverGroup;
        if (SERVER_LIST_SETTINGS.get().richServerList().get().booleanValue() && SERVER_LIST_SETTINGS.get().niceNames().get().booleanValue() && (serverGroup = this.cache.getServerGroup()) != null) {
            return Component.text(serverGroup.getNiceName());
        }
        return super.serverName();
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    protected void startInitialize(Parent parent) {
        super.startInitialize(parent);
        if (this.background == null) {
            return;
        }
        IconWidget backgroundWidget = new IconWidget(Icon.texture(this.background.getCompleted()));
        backgroundWidget.addId("server-background");
        addChild(backgroundWidget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    protected void finishInitialize(Parent parent, FlexibleContentWidget content, boolean display) {
        super.finishInitialize(parent, content, display);
        addQuickPlayButtons(content, display);
        if (addFriendHeads(content, display)) {
            addId("with-friend-heads");
        } else {
            removeId("with-friend-heads");
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    protected IconWidget serverIconWidget(boolean loading) {
        if (!loading && this.icon != null) {
            return new IconWidget(Icon.texture(this.icon.getCompleted()));
        }
        return super.serverIconWidget(loading);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget, net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget
    public int getListIndex() {
        return this.cache.getSortingValue();
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    protected boolean display() {
        if (hasQuickJoinButtons()) {
            addId("with-quick-join");
        } else {
            removeId("with-quick-join");
        }
        if (!SERVER_LIST_SETTINGS.get().richServerList().get().booleanValue() || !SERVER_LIST_SETTINGS.get().highQualityTextures().get().booleanValue()) {
            this.icon = null;
            this.background = null;
            return true;
        }
        ServerGroup serverGroup = this.cache.getServerGroup();
        if (serverGroup == null) {
            this.serverGroup = null;
            this.icon = null;
            this.background = null;
            return true;
        }
        if (this.serverGroup == null || !this.serverGroup.equals(serverGroup.getServerName())) {
            this.serverGroup = serverGroup.getServerName();
            this.icon = null;
            this.background = null;
        }
        if (this.userStatsUrl == null && serverGroup.getUserStatsUrl() != null) {
            this.userStatsUrl = serverGroup.getUserStatsUrl();
        }
        if (this.background == null) {
            Optional<ServerGroup.Attachment> optionalBackground = serverGroup.getAttachment("background");
            optionalBackground.ifPresent(attachment -> {
                this.background = attachment.completableResourceLocation();
                if (!this.background.hasResult()) {
                    this.background.addCompletableListener(this::onTextureCompletion);
                }
            });
        }
        if (this.icon == null) {
            Optional<ServerGroup.Attachment> optionalIcon = serverGroup.getAttachment("icon");
            optionalIcon.ifPresent(attachment2 -> {
                this.icon = attachment2.completableResourceLocation();
                if (!this.icon.hasResult()) {
                    this.icon.addCompletableListener(this::onTextureCompletion);
                }
            });
        }
        if (this.icon == null && this.background == null) {
            return true;
        }
        return serverInfo().getStatus() != ServerInfo.Status.LOADING && (this.icon == null || this.icon.hasResult()) && (this.background == null || this.background.hasResult());
    }

    public LabyNetServerInfoCache<T> cache() {
        return this.cache;
    }

    private void onTextureCompletion() {
        this.labyAPI.minecraft().executeNextTick(() -> {
            if (isDestroyed()) {
                return;
            }
            if (this.background == null || this.background.hasResult()) {
                if (this.icon == null || this.icon.hasResult()) {
                    reInitialize();
                }
            }
        });
    }

    private boolean hasQuickJoinButtons() {
        ServerGroup serverGroup;
        this.gameModes.clear();
        if (!this.quickJoin || !SERVER_LIST_SETTINGS.get().richServerList().get().booleanValue() || !SERVER_LIST_SETTINGS.get().quickJoinButtons().get().booleanValue() || (serverGroup = this.cache.getServerGroup()) == null) {
            return false;
        }
        List<GameMode> availableGameModes = serverGroup.getGameModes();
        if (availableGameModes.isEmpty()) {
            return false;
        }
        for (GameMode gameMode : availableGameModes) {
            if (this.gameModes.size() > 9) {
                break;
            }
            String command = gameMode.getCommand();
            if (command != null && !command.isEmpty()) {
                this.gameModes.add(gameMode);
            }
        }
        return !this.gameModes.isEmpty();
    }

    public void refresh(boolean visible) {
        this.visibleRefresh = visible;
        this.cache.update();
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    protected boolean loadingIcon() {
        return this.visibleRefresh;
    }

    private void addQuickPlayButtons(FlexibleContentWidget content, boolean display) {
        if (this.gameModes.isEmpty()) {
            return;
        }
        WrappedListWidget<SimpleButtonWidget> quickJoinButtonList = new WrappedListWidget<>();
        quickJoinButtonList.addId("quick-join-button-list");
        if (display) {
            for (GameMode gameMode : this.gameModes) {
                SimpleButtonWidget button = new SimpleButtonWidget(gameMode.getName());
                button.addId("quick-join-button");
                button.setPressable(() -> {
                    connect(gameMode.getCommand());
                });
                quickJoinButtonList.addChild(button);
            }
        }
        content.addContent(quickJoinButtonList);
    }

    protected boolean addFriendHeads(FlexibleContentWidget content, boolean display) {
        LabyConnectSession session;
        if (!this.friendHeads || !SERVER_LIST_SETTINGS.get().richServerList().get().booleanValue() || !SERVER_LIST_SETTINGS.get().friendsInServerList().get().booleanValue() || (session = this.labyAPI.labyConnect().getSession()) == null || session.getFriends().isEmpty()) {
            return false;
        }
        List<Friend> friends = getFriends(session.getFriends());
        if (friends.isEmpty()) {
            return false;
        }
        HorizontalListWidget friendHeadList = new HorizontalListWidget();
        friendHeadList.addId("friend-head-list");
        if (display) {
            Iterator<Friend> it = friends.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Friend friend = it.next();
                if (friendHeadList.getChildren().size() > 7) {
                    friendHeadList.addEntry(new IconWidget(Textures.SpriteCommon.LARGE_DOTS));
                    break;
                }
                IconWidget friendIcon = new IconWidget(Icon.head(friend.getUniqueId()));
                friendIcon.setHoverComponent(Component.text(friend.getName()));
                friendIcon.addId("friend-head");
                friendHeadList.addEntry(friendIcon);
            }
        }
        content.addContent(friendHeadList);
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    public void connect(String command) {
        ServerGroup serverGroup = this.cache.getServerGroup();
        if (serverGroup == null) {
            super.connect(command);
        } else {
            super.connect(command, serverGroup.getCommandDelay());
        }
    }

    public void setFriendHeadsEnabled(boolean friendHeads) {
        this.friendHeads = friendHeads;
    }

    public void setQuickJoinEnabled(boolean quickJoin) {
        this.quickJoin = quickJoin;
    }

    protected List<Friend> getFriends(List<Friend> onlineFriends) {
        net.labymod.api.labyconnect.protocol.model.friend.ServerInfo server;
        List<Friend> friends = Lists.newArrayList();
        for (Friend onlineFriend : onlineFriends) {
            if (onlineFriend.isOnline() && (server = onlineFriend.getServer()) != null && !server.isLocalHost()) {
                if (!serverData().address().matches(server.getAddress(), server.getPort(), true)) {
                    if (this.cache.getServerGroup() != null) {
                        Optional<ServerGroup> serverByIp = this.labyAPI.labyNetController().getServerByIp(server.getAddress());
                        if (serverByIp.isPresent() && serverByIp.get().equals(this.cache.getServerGroup())) {
                            friends.add(onlineFriend);
                        }
                    }
                } else {
                    friends.add(onlineFriend);
                }
            }
        }
        return friends;
    }
}
