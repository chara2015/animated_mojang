package net.labymod.core.client.gui.screen.activity.activities.multiplayer.child;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerInfoCache;
import net.labymod.api.client.network.server.ServerType;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectConnectEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectDisconnectEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendRemoveEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendServerEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendStatusEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labyconnect.protocol.model.friend.ServerInfo;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.CustomServerEntryWidget;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.friend.FriendPublicServerInfoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.friend.FriendWorldsharingServerInfoWidget;
import net.labymod.core.client.worldsharing.Worldsharing;
import net.labymod.core.client.worldsharing.event.FriendsServerListInitializeEvent;
import net.labymod.core.client.worldsharing.model.FriendServerListener;
import net.labymod.core.client.worldsharing.network.events.JoinWorldEvent;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultServerInfo;
import net.labymod.core.localization.keys.ActivityTranslationKeys;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/multiplayer/child/FriendsServerListActivity.class */
@AutoActivity
@Link("activity/multiplayer/friends-server-list.lss")
public class FriendsServerListActivity extends ServerListActivity<ConnectableServerData, ServerEntryWidget> implements FriendServerListener {
    private static final boolean SHOWCASE_ENABLED = false;
    private ShowcaseMode showcaseMode;
    private final Map<Friend, ServerInfoCache<ConnectableServerData>> sharedWorlds;
    private final Map<ServerAddress, Set<Friend>> friendsByServer;
    private final MultiplayerActivity multiplayerActivity;
    private final ButtonWidget refreshButton;
    private int updateTick;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !FriendsServerListActivity.class.desiredAssertionStatus();
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public FriendsServerListActivity(MultiplayerActivity multiplayerActivity) {
        super("friends", null);
        this.showcaseMode = ShowcaseMode.NONE;
        this.sharedWorlds = new HashMap();
        this.friendsByServer = new HashMap();
        this.multiplayerActivity = multiplayerActivity;
        this.refreshButton = ButtonWidget.i18n("labymod.ui.button.refresh", () -> {
            refresh(true);
        });
        this.serverListWidget.setSelectCallback((Selectable<T>) widget -> {
            setServerButtonsEnabled(true);
        });
        Laby.fireEvent(new FriendsServerListInitializeEvent(this));
    }

    @Subscribe
    public void onLabyConnectStateUpdate(LabyConnectStateUpdateEvent event) {
        if (event.state() == LabyConnectState.PLAY || event.state() == LabyConnectState.OFFLINE) {
            this.sharedWorlds.clear();
            this.friendsByServer.clear();
        }
    }

    @Subscribe
    public void onLabyConnectFriendServer(LabyConnectFriendServerEvent event) {
        Friend friend = event.friend();
        ServerInfo previousServer = event.getPreviousServer();
        ServerInfo newServer = event.serverInfo();
        if (previousServer != null && !previousServer.getAddress().isEmpty()) {
            removeFriendFromServer(friend, previousServer.getAddress());
        }
        if (newServer != null && !newServer.getAddress().isEmpty()) {
            addFriendToServer(friend, newServer.getAddress());
        }
    }

    @Subscribe
    public void onFriendRemove(LabyConnectFriendRemoveEvent event) {
        removeFriendCompletely(event.friend());
    }

    @Subscribe
    public void onFriendStatusChange(LabyConnectFriendStatusEvent event) {
        if (event.wasOnline() && !event.isOnline()) {
            removeFriendCompletely(event.friend());
        }
    }

    @Subscribe
    public void onChatConnect(LabyConnectConnectEvent event) {
        reload();
    }

    @Subscribe
    public void onChatDisconnect(LabyConnectDisconnectEvent event) {
        reload();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
        this.sharedWorlds.clear();
        this.friendsByServer.clear();
    }

    private void removeFriendCompletely(Friend friend) {
        remove(friend);
        ServerInfo server = friend.getServer();
        if (server != null && !server.getAddress().isEmpty()) {
            removeFriendFromServer(friend, server.getAddress());
        }
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void fillServerList(VerticalListWidget<ServerEntryWidget> serverListWidget, String searchQuery) {
        if (!resolveConnected()) {
            ComponentWidget notConnectedToLabyConnect = (ComponentWidget) ComponentWidget.component(ActivityTranslationKeys.getMultiplayerFriendsNotConnectedToLabyConnect()).addId("background");
            serverListWidget.addChild((ServerEntryWidget) new CustomServerEntryWidget(notConnectedToLabyConnect).addId("info-content"));
            return;
        }
        if (this.showcaseMode.showWorldsharing) {
            seedShowcaseWorldsharing();
        }
        populatePublicServersSection(serverListWidget, resolveOnlineFriends());
        serverListWidget.addChild((ServerEntryWidget) new CustomServerEntryWidget(new DivWidget()).addId("spacer"));
        populateWorldsharingSection(serverListWidget);
    }

    private boolean resolveConnected() {
        if (this.showcaseMode == ShowcaseMode.NOT_CONNECTED) {
            return false;
        }
        if (this.showcaseMode != ShowcaseMode.NONE) {
            return true;
        }
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        return session != null && session.isAuthenticated();
    }

    private Collection<Friend> resolveOnlineFriends() {
        if (this.showcaseMode != ShowcaseMode.NONE) {
            return this.showcaseMode.showPublicServers ? createShowcaseFriends() : List.of();
        }
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if ($assertionsDisabled || session != null) {
            return session.getOnlineFriends();
        }
        throw new AssertionError();
    }

    private void addWorldsharingWidget(Friend friend, ServerInfoCache<ConnectableServerData> cache) {
        addWorldsharingWidget(friend, cache, false);
    }

    private void addWorldsharingWidget(Friend friend, ServerInfoCache<ConnectableServerData> cache, boolean initialize) {
        FriendWorldsharingServerInfoWidget serverInfoWidget = new FriendWorldsharingServerInfoWidget(friend, cache, this::join);
        serverInfoWidget.setMovable(ServerInfoWidget.Movable.ADD, m -> {
            serverInfoWidget.connect(null, 0);
        });
        if (initialize) {
            this.serverListWidget.addChildInitialized(serverInfoWidget);
        } else {
            this.serverListWidget.addChild(serverInfoWidget);
        }
        cache.update();
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void fillButtonContainer(FlexibleContentWidget container) {
        container.addFlexibleContent(this.joinButton);
        container.addFlexibleContent(this.refreshButton);
        container.addFlexibleContent(ButtonWidget.i18n("labymod.ui.button.cancel", () -> {
            this.multiplayerActivity.switchTab("private");
            displayScreen((ScreenInstance) null);
        }));
        setServerButtonsEnabled(this.session.hasSelectedEntry());
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.screen.ParentScreen
    public void displayScreen(ScreenInstance screen) {
        this.labyAPI.minecraft().minecraftWindow().displayScreen(screen);
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void refresh(boolean visible) {
        for (ServerInfoCache<ConnectableServerData> cache : this.sharedWorlds.values()) {
            cache.update();
        }
        Iterator it = this.serverInfos.iterator();
        while (it.hasNext()) {
            LabyNetServerInfoCache<ConnectableServerData> cache2 = (LabyNetServerInfoCache) it.next();
            cache2.update();
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        int i = this.updateTick;
        this.updateTick = i + 1;
        if (i == 100) {
            this.updateTick = 0;
            refresh(false);
        }
    }

    @Override // net.labymod.core.client.worldsharing.model.FriendServerListener
    public void add(Friend friend) {
        ConnectableServerData serverData = ConnectableServerData.builder().name(friend.getName()).address(ServerAddress.parse(Constants.Domains.createUserLanDomain(friend.getName()))).type(ServerType.THIRD_PARTY).build();
        ServerInfoCache<ConnectableServerData> cache = new ServerInfoCache<>(serverData, null);
        this.sharedWorlds.put(friend, cache);
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            if (((Document) this.document).isInitialized()) {
                removeEmptyPlaceholder("worldsharing-empty");
                addWorldsharingWidget(friend, cache, true);
            }
        });
    }

    @Override // net.labymod.core.client.worldsharing.model.FriendServerListener
    public void remove(Friend friend) {
        ServerInfoCache<ConnectableServerData> cache = this.sharedWorlds.remove(friend);
        if (cache != null) {
            this.labyAPI.minecraft().executeOnRenderThread(() -> {
                removeServerWidget(cache);
                if (this.sharedWorlds.isEmpty() && ((Document) this.document).isInitialized()) {
                    this.serverListWidget.addChildInitializedAfterId(Worldsharing.NAMESPACE, createWorldsharingEmptyPlaceholder());
                    this.serverListWidget.addChildInitializedAfterId(Worldsharing.NAMESPACE, createWorldsharingHostButton());
                }
            });
        }
    }

    @Override // net.labymod.core.client.worldsharing.model.FriendServerListener
    public void join(Friend friend) {
        LabyMod.references().worldsharing().netEventHandler().joinServer(friend, JoinWorldEvent.Type.WORLD);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    private void removeEmptyPlaceholder(String str) {
        this.serverListWidget.removeChildIf((Predicate<T>) widget -> {
            return widget.hasId(str);
        });
    }

    private static ServerAddress baseDomain(ServerAddress address) {
        String host = address.getHost().toLowerCase(Locale.ROOT);
        String[] parts = host.split("\\.");
        if (parts.length > 2) {
            host = parts[parts.length - 2] + "." + parts[parts.length - 1];
        }
        return new ServerAddress(host, address.getPort());
    }

    private CustomServerEntryWidget createPublicServersEmptyPlaceholder() {
        return (CustomServerEntryWidget) new CustomServerEntryWidget(ComponentWidget.component(ActivityTranslationKeys.getMultiplayerFriendsPublicServersNoPublicServers()).addId("background")).addId("info-content", "public-servers-empty");
    }

    private CustomServerEntryWidget createWorldsharingEmptyPlaceholder() {
        return (CustomServerEntryWidget) new CustomServerEntryWidget(ComponentWidget.component(ActivityTranslationKeys.getMultiplayerFriendsSharedWorldsNoSharedWorlds(ActivityTranslationKeys.getWorldsharingMenuInviteFriends().copy().decorate(TextDecoration.BOLD))).addId("background")).addId("info-content", "worldsharing-empty");
    }

    private CustomServerEntryWidget createWorldsharingHostButton() {
        return (CustomServerEntryWidget) new CustomServerEntryWidget(ButtonWidget.component(ActivityTranslationKeys.getMultiplayerFriendsSharedWorldsBrowseWorlds(), () -> {
            displayScreen(NamedScreen.SINGLEPLAYER.create());
        }).addId("accent-button")).addId("host-world-button");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    private void removeServerWidget(ServerInfoCache<ConnectableServerData> serverInfoCache) {
        this.serverListWidget.removeChildIf((Predicate<T>) widget -> {
            if (widget instanceof ServerInfoWidget) {
                ServerInfoWidget<?> serverInfo = (ServerInfoWidget) widget;
                if (serverInfo.serverData().address().equals(serverInfoCache.serverAddress())) {
                    return true;
                }
            }
            return false;
        });
    }

    private void addSeparator(VerticalListWidget<ServerEntryWidget> serverListWidget, Component title, String type) {
        HorizontalListWidget separator = new HorizontalListWidget();
        separator.addId("separator-content");
        separator.addEntry(new DivWidget());
        separator.addEntry(ComponentWidget.component(title));
        separator.addEntry(new DivWidget());
        serverListWidget.addChild((ServerEntryWidget) new CustomServerEntryWidget(separator).addId("separator", type));
    }

    private void populateWorldsharingSection(VerticalListWidget<ServerEntryWidget> serverListWidget) {
        addSeparator(serverListWidget, ActivityTranslationKeys.getMultiplayerFriendsSharedWorldsTitle(), Worldsharing.NAMESPACE);
        if (this.sharedWorlds.isEmpty()) {
            serverListWidget.addChild(createWorldsharingEmptyPlaceholder());
            serverListWidget.addChild(createWorldsharingHostButton());
        } else {
            this.sharedWorlds.forEach(this::addWorldsharingWidget);
        }
    }

    private void populatePublicServersSection(VerticalListWidget<ServerEntryWidget> serverListWidget, Collection<Friend> onlineFriends) {
        addSeparator(serverListWidget, ActivityTranslationKeys.getMultiplayerFriendsPublicServersTitle(), "public-servers");
        boolean friendsPlayingOnline = false;
        for (Friend onlineFriend : onlineFriends) {
            friendsPlayingOnline |= populateServerListWithFriend(serverListWidget, onlineFriend);
        }
        if (!friendsPlayingOnline) {
            serverListWidget.addChild(createPublicServersEmptyPlaceholder());
        }
    }

    private boolean populateServerListWithFriend(VerticalListWidget<ServerEntryWidget> serverListWidget, Friend friend) {
        ServerInfo server = friend.getServer();
        if (server == null || server.getAddress().isEmpty()) {
            return false;
        }
        LabyNetServerInfoCache<ConnectableServerData> cache = getOrCreateServerCache(server.getAddress());
        Set<Friend> friends = this.friendsByServer.computeIfAbsent(baseDomain(cache.serverAddress()), k -> {
            return new HashSet();
        });
        friends.add(friend);
        if (friends.size() == 1) {
            serverListWidget.addChild(new FriendPublicServerInfoWidget(cache));
            return true;
        }
        return true;
    }

    private void addFriendToServer(Friend friend, String address) {
        LabyNetServerInfoCache<ConnectableServerData> cache = getOrCreateServerCache(address);
        Set<Friend> friends = this.friendsByServer.computeIfAbsent(baseDomain(cache.serverAddress()), k -> {
            return new HashSet();
        });
        if (!friends.add(friend) || friends.size() != 1) {
            return;
        }
        FriendPublicServerInfoWidget widget = new FriendPublicServerInfoWidget(cache);
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            if (((Document) this.document).isInitialized()) {
                removeEmptyPlaceholder("public-servers-empty");
                this.serverListWidget.addChildInitializedAfterId("public-servers", widget);
            }
        });
    }

    private LabyNetServerInfoCache<ConnectableServerData> getOrCreateServerCache(String address) {
        ConnectableServerData serverData = ConnectableServerData.builder().name(address).address(address).build();
        LabyNetServerInfoCache<ConnectableServerData> cache = getCache(serverData);
        if (cache == null) {
            cache = registerCache(serverData, c -> {
            });
        }
        cache.update();
        return cache;
    }

    private LabyNetServerInfoCache<ConnectableServerData> findCacheByAddress(String address) {
        ServerAddress target = ServerAddress.parse(address);
        Iterator it = this.serverInfos.iterator();
        while (it.hasNext()) {
            LabyNetServerInfoCache<ConnectableServerData> cache = (LabyNetServerInfoCache) it.next();
            if (cache.serverAddress().equals(target)) {
                return cache;
            }
        }
        return null;
    }

    private void removeFriendFromServer(Friend friend, String address) {
        ServerAddress serverAddress = baseDomain(ServerAddress.parse(address));
        Set<Friend> friends = this.friendsByServer.get(serverAddress);
        if (friends == null) {
            return;
        }
        friends.remove(friend);
        if (!friends.isEmpty()) {
            return;
        }
        this.friendsByServer.remove(serverAddress);
        LabyNetServerInfoCache<ConnectableServerData> cache = findCacheByAddress(address);
        if (cache != null) {
            unregisterCache(cache.serverData());
            this.labyAPI.minecraft().executeOnRenderThread(() -> {
                removeServerWidget(cache);
                if (this.friendsByServer.isEmpty() && ((Document) this.document).isInitialized()) {
                    this.serverListWidget.addChildInitializedAfterId("public-servers", createPublicServersEmptyPlaceholder());
                }
            });
        }
    }

    private void cycleShowcaseMode() {
        ShowcaseMode[] modes = ShowcaseMode.VALUES;
        this.showcaseMode = modes[(this.showcaseMode.ordinal() + 1) % modes.length];
        this.sharedWorlds.clear();
        this.friendsByServer.clear();
        this.serverInfos.clear();
        reload();
    }

    private void seedShowcaseWorldsharing() {
        seedShowcaseSharedWorld("LabyStudio", "d4be38af-addc-4c63-b981-48b8e7d0e0e7");
        seedShowcaseSharedWorld("SasukeKawaii", "dfa8dbcd-4045-4d55-b53f-bc5b484e8dac");
    }

    private void seedShowcaseSharedWorld(String name, String uuid) {
        DefaultFriend friend = new DefaultFriend(UUID.fromString(uuid), name, UserStatus.ONLINE, "", null, 0, 0L, 0L, null, 0L, 0L, 0, false);
        friend.setHostingWorld(true);
        ConnectableServerData serverData = ConnectableServerData.builder().name(name).address(ServerAddress.parse(Constants.Domains.createUserLanDomain(name))).type(ServerType.THIRD_PARTY).build();
        this.sharedWorlds.put(friend, new ServerInfoCache<>(serverData, null));
    }

    private Collection<Friend> createShowcaseFriends() {
        return List.of(createShowcaseFriend("Notch", "069a79f4-44e9-4726-a5be-fca90e38aaf5", "mc.hypixel.net"), createShowcaseFriend("jeb_", "853c80ef-3c37-49fd-aa49-938b674adae6", "play.cubecraft.net"), createShowcaseFriend("Dinnerbone", "61699b2e-d327-4a01-9f1e-0ea8c3f06bc6", Constants.Domains.LABY_DOMAIN));
    }

    private Friend createShowcaseFriend(String name, String uuid, String serverAddress) {
        return new DefaultFriend(UUID.fromString(uuid), name, UserStatus.ONLINE, "", new DefaultServerInfo(serverAddress, 25565), 0, 0L, 0L, null, 0L, 0L, 0, false);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/multiplayer/child/FriendsServerListActivity$ShowcaseMode.class */
    private enum ShowcaseMode {
        NONE(false, false),
        NOT_CONNECTED(false, false),
        WORLDSHARING_ONLY(true, false),
        PUBLIC_SERVERS_ONLY(false, true),
        MIXED(true, true),
        EMPTY(false, false);

        static final ShowcaseMode[] VALUES = values();
        private final boolean showWorldsharing;
        private final boolean showPublicServers;

        ShowcaseMode(boolean showWorldsharing, boolean showPublicServers) {
            this.showWorldsharing = showWorldsharing;
            this.showPublicServers = showPublicServers;
        }
    }
}
