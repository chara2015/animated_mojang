package net.labymod.core.client.worldsharing.network;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.IngameMenuInitializeEvent;
import net.labymod.api.event.client.gui.screen.VanillaWidgetReplacementEvent;
import net.labymod.api.event.client.network.server.IntegratedServerStoppingEvent;
import net.labymod.api.event.client.world.WorldLeaveEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatInitializeEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendStatusEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.notification.Notification;
import net.labymod.api.server.LocalWorld;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.gui.screen.activity.activities.worldsharing.LocalInviteActivity;
import net.labymod.core.client.gui.screen.activity.activities.worldsharing.WorldConnectActivity;
import net.labymod.core.client.worldsharing.WorldStatusBroadcast;
import net.labymod.core.client.worldsharing.Worldsharing;
import net.labymod.core.client.worldsharing.event.FriendsServerListInitializeEvent;
import net.labymod.core.client.worldsharing.model.FriendServerListener;
import net.labymod.core.client.worldsharing.model.InviteProperties;
import net.labymod.core.client.worldsharing.model.LocalPlayer;
import net.labymod.core.client.worldsharing.model.WorldPrivacy;
import net.labymod.core.client.worldsharing.network.events.JoinWorldEvent;
import net.labymod.core.client.worldsharing.network.events.LocalServerInviteEvent;
import net.labymod.core.client.worldsharing.network.events.TunnelRequestEvent;
import net.labymod.core.client.worldsharing.network.events.WorldConfigEvent;
import net.labymod.core.client.worldsharing.network.events.WorldInviteEvent;
import net.labymod.core.client.worldsharing.network.events.WorldReadyEvent;
import net.labymod.core.client.worldsharing.network.events.WorldSharedEvent;
import net.labymod.core.client.worldsharing.network.events.WorldStopEvent;
import net.labymod.core.event.labymod.PacketAddonDevelopmentEvent;
import net.labymod.core.event.labymod.PacketAddonMessageEvent;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonDevelopment;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonMessage;
import net.labymod.core.main.LabyMod;
import net.labymod.core.server.AbstractIntegratedServer;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/NetEventHandler.class */
public class NetEventHandler {
    private static final String I18N_PREFIX = "labymod.activity.worldsharing.";
    private final Worldsharing worldsharing;
    private static final Logging LOGGER = Logging.getLogger();
    private FriendServerListener friendServerList;
    private CompletableFuture<Void> timeoutFuture;
    private List<String> cachedWhitelist;
    private static final int UUID_BYTES = 16;
    private CompletableFuture<JoinWorldEvent.Response> joinWorldCallback;
    private final EventCodec eventCodec = EventCodec.INSTANCE;
    private final List<Runnable> successCallbacks = new ArrayList();
    private WorldPrivacy worldPrivacy = WorldPrivacy.FRIENDS;
    private State state = State.OFFLINE;
    private String host = "";
    private final Cache<UUID, Object> invitedPlayersTimeout = Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(1)).build();
    private final Map<UUID, LocalPlayer> onlineLocalPlayers = new ConcurrentHashMap();
    private final Cache<UUID, LocalPlayer> pendingOnlinePlayers = Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(2)).build();
    private final TunnelInitializer tunnelInitializer = new TunnelInitializer();
    private final LocalInviteActivity localInviteActivity = new LocalInviteActivity(this);
    private final AbstractIntegratedServer integratedServer = Worldsharing.integratedServer();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/NetEventHandler$State.class */
    public enum State {
        PUBLISHED,
        WAITING,
        OFFLINE
    }

    public NetEventHandler(Worldsharing worldsharing) {
        this.worldsharing = worldsharing;
    }

    public void publish(NetworkEvent event) {
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session != null) {
            String subject = this.eventCodec.subjectFor(event);
            byte[] payload = this.eventCodec.encode(event);
            if (subject == null || subject.isBlank()) {
                LOGGER.warn("Can't publish NetworkEvent {}: subject not found!", event.getClass().getSimpleName());
            } else if (payload == null) {
                LOGGER.warn("Can't publish NetworkEvent {}: empty payload!", event.getClass().getSimpleName());
            } else {
                session.sendAddonMessage(subject, payload);
            }
        }
    }

    public void handle(JoinWorldEvent.Response response) {
        if (this.joinWorldCallback != null) {
            if (!this.joinWorldCallback.isDone() && !this.joinWorldCallback.isCancelled()) {
                this.joinWorldCallback.complete(response);
            }
            this.joinWorldCallback = null;
        }
    }

    public void handle(TunnelRequestEvent event) {
        LocalPlayer pendingPlayer;
        try {
            switch (event.type()) {
                case LOCAL_SERVER:
                    if (event.targetId() == null || (pendingPlayer = (LocalPlayer) this.pendingOnlinePlayers.getIfPresent(event.targetId())) == null) {
                        return;
                    }
                    this.pendingOnlinePlayers.invalidate(event.targetId());
                    ServerAddress address = ServerAddress.parse(event.endpoint());
                    SocketBridge socketBridge = new SocketBridge(event.sessionId(), pendingPlayer.server(), address.getAddress(), () -> {
                        this.onlineLocalPlayers.remove(event.targetId());
                    });
                    UUID uuid = pendingPlayer.uuid();
                    String strName = pendingPlayer.name();
                    InetSocketAddress inetSocketAddressServer = pendingPlayer.server();
                    Objects.requireNonNull(socketBridge);
                    LocalPlayer localPlayer = new LocalPlayer(uuid, strName, inetSocketAddressServer, socketBridge::shutdown);
                    this.onlineLocalPlayers.put(event.targetId(), localPlayer);
                    break;
                    break;
                case WORLD:
                    if (!this.state.equals(State.PUBLISHED)) {
                        return;
                    }
                    this.tunnelInitializer.handle(event);
                    break;
                    break;
            }
        } catch (Exception e) {
            LOGGER.error("tunnel initialization failed: {}", e);
        }
    }

    public void handle(LocalServerInviteEvent invite) {
        Friend friend;
        LabyConnectSession session = Laby.references().labyConnect().getSession();
        if (session != null && (friend = session.getFriend(invite.targetId())) != null) {
            Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                friend.chat().addMessage(friend, "lanserver://" + String.valueOf(friend.getUniqueId()) + "?type=" + JoinWorldEvent.Type.LOCAL_SERVER.name());
            });
        }
    }

    public void handle(WorldReadyEvent event) {
        if (!this.state.equals(State.WAITING)) {
            LOGGER.debug("unexpected WorldReadyEvent", new Object[0]);
            return;
        }
        this.state = State.PUBLISHED;
        this.timeoutFuture.complete(null);
        this.timeoutFuture = null;
        this.worldsharing.broadcast().send(WorldStatusBroadcast.Type.ADD);
        this.worldsharing.dashboardActivity().reloadDashboard();
        Laby.labyAPI().minecraft().chatExecutor().displayClientMessage(Component.translatable("labymod.activity.worldsharing.messages.public_domain", NamedTextColor.AQUA).argument(Component.text(event.domain(), NamedTextColor.WHITE).clickEvent(ClickEvent.copyToClipboard(event.domain())).hoverEvent(HoverEvent.showText(Component.translatable("labymod.activity.worldsharing.messages.copy_domain", NamedTextColor.GREEN)))));
        if (!this.successCallbacks.isEmpty()) {
            for (Runnable callback : this.successCallbacks) {
                callback.run();
            }
            this.successCallbacks.clear();
        }
    }

    @Subscribe
    public void onIngameMenuInit(IngameMenuInitializeEvent event) {
        if (isLabyPlus() && isOnLocalServer()) {
            event.addRightButton(Component.translatable("labymod.activity.worldsharing.menu.invite_friends", new Component[0]), Textures.SpriteCommon.SHARE, this::showInviteScreen);
        }
    }

    @Subscribe
    public void onAddonMessage(PacketAddonMessageEvent packetEvent) {
        NetworkEvent event;
        PacketAddonMessage packet = packetEvent.packet();
        if (packet.getKey().startsWith("world:") && (event = this.eventCodec.decode(packet.getKey(), packet.getData())) != null) {
            event.handle(this);
        }
    }

    @Subscribe
    public void onAddonDevelopment(PacketAddonDevelopmentEvent event) {
        LabyConnectSession session;
        Friend friend;
        WorldStatusBroadcast.Type type;
        PacketAddonDevelopment packet = event.packet();
        if (!packet.getKey().equals(Worldsharing.NAMESPACE) || packet.getData().length < 1 || (session = Laby.references().labyConnect().getSession()) == null || this.friendServerList == null || (friend = session.getFriend(packet.getSender())) == null || (type = WorldStatusBroadcast.Type.fromByte(packet.getData()[0])) == null) {
            return;
        }
        switch (type) {
            case ADD:
                if (!friend.isHostingWorld()) {
                    friend.setHostingWorld(true);
                    this.friendServerList.add(friend);
                }
                break;
            case REMOVE:
                if (friend.isHostingWorld()) {
                    friend.setHostingWorld(false);
                    this.friendServerList.remove(friend);
                }
                break;
        }
    }

    @Subscribe
    public void onLabyConnectChatInit(LabyConnectChatInitializeEvent event) {
        if (event.friend().isHostingWorld()) {
            ButtonWidget joinWorldButton = ButtonWidget.i18n("labymod.activity.worldsharing.menu.join_world");
            joinWorldButton.setActionListener(() -> {
                joinServer(event.friend(), JoinWorldEvent.Type.WORLD);
            });
            joinWorldButton.addId("invite-button");
            event.buttonContainer().addEntry(joinWorldButton);
        }
    }

    @Subscribe
    public void onFriendStatusChange(LabyConnectFriendStatusEvent event) {
        if (event.friend().isHostingWorld() && event.wasOnline() && !event.isOnline()) {
            event.friend().setHostingWorld(false);
        }
    }

    @Subscribe
    public void onIntegratedServerStopping(IntegratedServerStoppingEvent event) {
        stop();
    }

    @Subscribe
    public void onWorldLeave(WorldLeaveEvent event) {
        stop();
    }

    @Subscribe
    public void onWidget(VanillaWidgetReplacementEvent event) {
        Supplier<AbstractWidget<?>> replacementButton = () -> {
            return ButtonWidget.i18n("labymod.activity.worldsharing." + (this.integratedServer.isPublished() ? "menu.manage_world" : "menu.invite_friends"), () -> {
                Laby.labyAPI().minecraft().minecraftWindow().displayScreen(this.worldsharing.dashboardActivity());
            });
        };
        List<CharSequence> ids = event.getWidget().getIds();
        if (MinecraftVersions.V1_12_2.orOlder() && ids.contains("ingame-menu-screen") && ids.contains("7-widget")) {
            event.setReplacement(replacementButton);
            return;
        }
        for (CharSequence id : ids) {
            if (id.equals("menu-sharetolan-widget") || (id.equals("menu-playerreporting-widget") && this.integratedServer.isPublished())) {
                event.setReplacement(replacementButton);
                return;
            }
        }
    }

    @Subscribe
    public void onFriendServerListInit(FriendsServerListInitializeEvent event) {
        this.friendServerList = event.friendServerListener();
    }

    private void showInviteScreen() {
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            Laby.labyAPI().minecraft().minecraftWindow().displayScreen(this.localInviteActivity);
        });
    }

    public void updateConfig() {
        if (isPublished()) {
            publish(new WorldConfigEvent((byte) this.integratedServer.getSlots(), this.worldPrivacy.get()));
        }
    }

    public void setWhitelist(String name, boolean add, @Nullable Runnable callback) {
        this.cachedWhitelist = null;
        CompletableFuture.runAsync(() -> {
            UUID target = this.integratedServer.setWhitelist(name, add);
            if (target == null) {
                return;
            }
            if (callback != null) {
                Laby.labyAPI().minecraft().executeOnRenderThread(callback);
            }
            if (isPublished()) {
                publish(new WorldInviteEvent(target, add));
                if (this.worldPrivacy.isWhitelist()) {
                    this.worldsharing.broadcast().send(target, add ? WorldStatusBroadcast.Type.ADD : WorldStatusBroadcast.Type.REMOVE);
                }
            }
        }).exceptionally(e -> {
            Logging logging = LOGGER;
            Object[] objArr = new Object[3];
            objArr[0] = add ? "add" : "remove";
            objArr[1] = name;
            objArr[2] = e;
            logging.warn("failed to {} {} to whitelist", objArr);
            return null;
        });
    }

    public void worldPrivacy(WorldPrivacy privacy) {
        LabyConnectSession session;
        List<String> whitelist;
        WorldPrivacy previous = this.worldPrivacy;
        this.worldPrivacy = privacy;
        if (previous == privacy || (session = Laby.labyAPI().labyConnect().getSession()) == null || !session.isAuthenticated()) {
            return;
        }
        if (privacy.inServerList() && !previous.inServerList()) {
            for (Friend friend : session.getFriends()) {
                if (friend.isOnline()) {
                    this.worldsharing.broadcast().send(friend.getUniqueId(), WorldStatusBroadcast.Type.ADD);
                }
            }
            return;
        }
        if (privacy.inServerList() || !previous.inServerList() || (whitelist = this.worldsharing.broadcast().getWhitelist()) == null) {
            return;
        }
        for (Friend friend2 : session.getFriends()) {
            if (friend2.isOnline() && !whitelist.contains(friend2.getName())) {
                this.worldsharing.broadcast().send(friend2.getUniqueId(), WorldStatusBroadcast.Type.REMOVE);
            }
        }
    }

    public void registerJoinWorldCallback(CompletableFuture<JoinWorldEvent.Response> future) {
        this.joinWorldCallback = future;
    }

    public boolean isPublished() {
        return this.state.equals(State.PUBLISHED);
    }

    private boolean isLabyPlus() {
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        return (session == null || session.self().gameUser().visibleGroup().isDefault()) ? false : true;
    }

    private static void writeLong(byte[] dest, int offset, long value) {
        for (int i = 7; i >= 0; i--) {
            dest[offset + i] = (byte) (value & 255);
            value >>>= 8;
        }
    }

    public String[] getWhitelist() {
        return this.integratedServer.getWhitelist();
    }

    public WorldPrivacy worldPrivacy() {
        return this.worldPrivacy;
    }

    public State state() {
        return this.state;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost() {
        this.host = Worldsharing.integratedServer().getHost();
    }

    public boolean isLocalPlayer(UUID uuid) {
        return this.onlineLocalPlayers.containsKey(uuid) || this.pendingOnlinePlayers.getIfPresent(uuid) != null;
    }

    public boolean isOnLocalServer() {
        InetAddress address;
        ServerData serverData = Laby.labyAPI().serverController().getCurrentServerData();
        return (serverData == null || (address = serverData.actualAddress().getAddress().getAddress()) == null || !address.isLoopbackAddress()) ? false : true;
    }

    public Collection<LocalPlayer> getLocalPlayers() {
        return this.onlineLocalPlayers.values();
    }

    public boolean hasInviteTimeout(UUID uuid) {
        return this.invitedPlayersTimeout.getIfPresent(uuid) != null;
    }

    public boolean isWhitelisted(String name) {
        if (this.cachedWhitelist == null) {
            String[] whitelist = this.integratedServer.getWhitelist();
            if (whitelist == null) {
                return false;
            }
            this.cachedWhitelist = Arrays.asList(whitelist);
        }
        return this.cachedWhitelist.contains(name);
    }

    public synchronized void init() {
        if (!this.state.equals(State.OFFLINE)) {
            return;
        }
        publish(new WorldSharedEvent(this.integratedServer.getWorldName(), Laby.labyAPI().getVersion(), Laby.labyAPI().minecraft().getProtocolVersion(), this.integratedServer.getSlots(), this.worldPrivacy.get()));
        this.state = State.WAITING;
        this.timeoutFuture = new CompletableFuture<>();
        this.timeoutFuture.orTimeout(5L, TimeUnit.SECONDS).exceptionally(ex -> {
            this.state = State.OFFLINE;
            Notification.builder().title(Component.translatable("labymod.activity.worldsharing.messages.failed_to_publish", new Component[0])).text(Component.translatable("labymod.activity.worldsharing.messages.publish_timeout", NamedTextColor.RED)).buildAndPush();
            return null;
        });
    }

    public synchronized void stop() {
        if (!this.onlineLocalPlayers.isEmpty()) {
            List<Runnable> closeCallbacks = new ArrayList<>();
            for (LocalPlayer player : this.onlineLocalPlayers.values()) {
                closeCallbacks.add(player.close());
            }
            for (Runnable callback : closeCallbacks) {
                callback.run();
            }
        }
        if (this.state.equals(State.OFFLINE)) {
            return;
        }
        LabyMod.references().worldsharing().broadcast().send(WorldStatusBroadcast.Type.REMOVE);
        publish(new WorldStopEvent());
        this.state = State.OFFLINE;
        this.worldsharing.dashboardActivity().reloadDashboard();
    }

    public void joinServer(Friend friend, JoinWorldEvent.Type type) {
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            Laby.labyAPI().minecraft().minecraftWindow().displayScreen(new WorldConnectActivity(this, friend, type));
        });
    }

    public void inviteToWorld(Friend friend) {
        if (!Worldsharing.integratedServer().isPublished() || !isPublished()) {
            worldPrivacy(WorldPrivacy.FRIENDS);
            this.worldsharing.dashboardActivity().setUnsavedChanges();
            this.successCallbacks.add(() -> {
                sendWorldInvite(friend);
            });
            Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                Laby.labyAPI().minecraft().minecraftWindow().displayScreen(this.worldsharing.dashboardActivity());
            });
            return;
        }
        sendWorldInvite(friend);
    }

    private void sendWorldInvite(Friend friend) {
        if (hasInviteTimeout(friend.getUniqueId())) {
            return;
        }
        this.invitedPlayersTimeout.put(friend.getUniqueId(), true);
        AbstractIntegratedServer integratedServer = Worldsharing.integratedServer();
        InviteProperties properties = new InviteProperties().setVersion(Laby.labyAPI().minecraft().getVersionId()).setType(JoinWorldEvent.Type.WORLD).setGameMode(integratedServer.getGameMode().getName()).setCheatsEnabled(integratedServer.cheatsEnabled()).setWorldName(integratedServer.getWorldName());
        sendWorldIcon(friend, properties);
        String inviteUrl = "lanserver://" + String.valueOf(Laby.labyAPI().getUniqueId()) + "?" + properties.toQuery();
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            friend.chat().sendMessage(inviteUrl);
            pushInviteNotification(friend);
        });
        NetEventHandler netEventHandler = this.worldsharing.netEventHandler();
        if (netEventHandler.worldPrivacy().isWhitelist()) {
            netEventHandler.setWhitelist(friend.getName(), true, null);
        }
    }

    private void sendWorldIcon(Friend friend, InviteProperties properties) {
        byte[] icon;
        LabyConnectSession session;
        LocalWorld localWorld = Laby.references().integratedServer().getLocalWorld();
        if (localWorld == null || (icon = (byte[]) localWorld.getScreenshotFile().map(path -> {
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                return null;
            }
        }).orElse(null)) == null || (session = Laby.references().labyConnect().getSession()) == null) {
            return;
        }
        UUID iconIdentifier = UUID.randomUUID();
        byte[] payload = new byte[16 + icon.length];
        writeLong(payload, 0, iconIdentifier.getMostSignificantBits());
        writeLong(payload, 8, iconIdentifier.getLeastSignificantBits());
        System.arraycopy(icon, 0, payload, 16, icon.length);
        properties.setIcon(iconIdentifier.toString());
        session.sendAddonDevelopment("labymod:file", new UUID[]{friend.getUniqueId()}, payload);
    }

    public void inviteToLocalServer(Friend friend) {
        ServerData serverData = Laby.labyAPI().serverController().getCurrentServerData();
        if (serverData == null) {
            return;
        }
        InetSocketAddress address = serverData.actualAddress().getAddress();
        if (!address.getAddress().isLoopbackAddress()) {
            return;
        }
        this.pendingOnlinePlayers.put(friend.getUniqueId(), new LocalPlayer(friend.getUniqueId(), friend.getName(), address, null));
        this.worldsharing.netEventHandler().publish(new LocalServerInviteEvent(friend.getUniqueId()));
        pushInviteNotification(friend);
    }

    private void pushInviteNotification(Friend friend) {
        Notification.builder().title(Component.translatable("labymod.activity.worldsharing.menu.invite_friends", new Component[0])).text(Component.translatable("labymod.activity.worldsharing.menu.invited_friend", Component.text(friend.getName()))).buildAndPush();
    }
}
