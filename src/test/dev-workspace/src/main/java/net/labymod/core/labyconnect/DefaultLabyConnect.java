package net.labymod.core.labyconnect;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.CommandService;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.concurrent.ThreadFactoryBuilder;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.event.Event;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import net.labymod.api.event.client.network.server.NetworkLoginEvent;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectConnectEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectDisconnectEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.models.Implements;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.service.Service;
import net.labymod.api.util.I18n;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.network.server.connect.ConnectAddressResolver;
import net.labymod.core.client.screenshot.command.ScreenshotViewerCommand;
import net.labymod.core.client.session.DefaultSession;
import net.labymod.core.client.world.rplace.RPlaceOverlayCommand;
import net.labymod.core.labyconnect.commands.CapeReportCommand;
import net.labymod.core.labyconnect.configuration.DefaultLabyConnectConfig;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.Protocol;
import net.labymod.core.labyconnect.protocol.packets.PacketDisconnect;
import net.labymod.core.labyconnect.protocol.packets.PacketHelloPing;
import net.labymod.core.labyconnect.session.DefaultLabyConnectSession;
import net.labymod.core.labyconnect.session.LabyConnectAwayTracker;
import net.labymod.core.labyconnect.session.LabyConnectUserTracker;
import net.labymod.core.labyconnect.util.Snooper;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/DefaultLabyConnect.class */
@Singleton
@Implements(LabyConnect.class)
public class DefaultLabyConnect extends Service implements LabyConnect {
    public static final Logging LOGGER = DefaultLoggingFactory.createLogger((Class<?>) LabyConnect.class);
    public static final String ADDRESS = "chat.labymod.net";
    public static final String ADDRESS_BACKUP = "chat2.labymod.net";
    public static final int PORT = 30336;
    public static final int PORT_TEST_SERVER = 30337;
    public static final int PROTOCOL_VERSION = 29;
    private final SessionAccessor sessionAccessor;
    private final EventBus eventBus;
    private final NotificationController notifications;

    @Nullable
    private Snooper snooper;
    private Bootstrap bootstrap;
    private long timeLastKeepAlive;
    private boolean doNotConnect;
    private String lastDisconnectReason;
    private final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(0, new ThreadFactoryBuilder().withNameFormat("LabyConnectNio#%d").build());
    private final ExecutorService executor = LabyExecutors.newFixedThreadPool(2, "LabyConnectExecutor#%d");
    private final ScheduledExecutorService timeoutExecutor = LabyExecutors.newScheduledThreadPool(1, "LabyConnectTimeoutExecutor#%d");
    private final Protocol protocol = new Protocol();
    private final byte[] verifyToken = new byte[10];
    private DefaultLabyConnectSession session = null;
    private LabyConnectChannelHandler channelHandler = null;
    private volatile LabyConnectState state = LabyConnectState.OFFLINE;
    private long timeNextConnect = TimeUtil.getMillis();
    private long lastConnectTriesReset = 0;
    private final ConfigProvider<LabyConnectConfigAccessor> labyConnectConfigProvider = DefaultLabyConnectConfig.LabyConnectConfigProvider.INSTANCE;

    @Inject
    public DefaultLabyConnect(SessionAccessor sessionAccessor, EventBus eventBus, NotificationController notifications, CommandService commandService) {
        this.labyConnectConfigProvider.loadJson();
        this.sessionAccessor = sessionAccessor;
        this.eventBus = eventBus;
        this.notifications = notifications;
        try {
            this.snooper = new Snooper();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        commandService.register(new CapeReportCommand());
        commandService.register(new ScreenshotViewerCommand());
        commandService.register(new RPlaceOverlayCommand());
        eventBus.registerListener(new LabyConnectNotifications(this));
        eventBus.registerListener(new LabyConnectUserTracker(this));
        eventBus.registerListener(new LabyConnectAwayTracker(this));
        eventBus.registerListener(this);
    }

    @Override // net.labymod.api.service.Service
    protected void prepare() {
        super.prepare();
        this.timeoutExecutor.scheduleWithFixedDelay(() -> {
            try {
                long durationKeepAlive = TimeUtil.getMillis() - this.timeLastKeepAlive;
                long durationConnect = this.timeNextConnect - TimeUtil.getMillis();
                if (this.state != LabyConnectState.OFFLINE && durationKeepAlive > 25000) {
                    disconnect(LabyConnectDisconnectEvent.Initiator.CLIENT, I18n.translate("labymod.activity.labyconnect.protocol.disconnect.timeout", new Object[0]));
                }
                if (this.state == LabyConnectState.OFFLINE && !this.doNotConnect && durationConnect < 0) {
                    connect();
                }
                if (this.lastConnectTriesReset + 300000 < TimeUtil.getMillis()) {
                    this.lastConnectTriesReset = TimeUtil.getMillis();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }, 0L, 5L, TimeUnit.SECONDS);
    }

    @Override // net.labymod.api.labyconnect.LabyConnect
    public void connect() {
        connect(ADDRESS, PORT);
    }

    @Override // net.labymod.api.labyconnect.LabyConnect
    public void connect(String address, int port) {
        this.doNotConnect = false;
        this.executor.execute(() -> {
            synchronized (this) {
                if (this.state != LabyConnectState.OFFLINE) {
                    return;
                }
                keepAlive();
                updateState(LabyConnectState.HELLO);
                Session session = this.sessionAccessor.getSession();
                if (session == null) {
                    session = new DefaultSession("Player", UUID.randomUUID(), null, Session.Type.LEGACY);
                }
                this.session = new DefaultLabyConnectSession(this, session);
                this.channelHandler = new LabyConnectChannelHandler(this, this.session);
                this.lastDisconnectReason = null;
                this.bootstrap = new Bootstrap();
                this.bootstrap.group(this.nioEventLoopGroup);
                this.bootstrap.option(ChannelOption.TCP_NODELAY, true);
                this.bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
                this.bootstrap.channel(NioSocketChannel.class);
                this.bootstrap.handler(this.channelHandler);
                ConnectAddressResolver.resolveAddress();
                try {
                    this.bootstrap.connect(address, port).syncUninterruptibly();
                    sendPacket(new PacketHelloPing(TimeUtil.getMillis()));
                } catch (Exception e) {
                    e.printStackTrace();
                    updateState(LabyConnectState.OFFLINE);
                }
            }
        });
    }

    @Subscribe
    public void onSessionUpdate(SessionUpdateEvent event) {
        if (!event.isAnotherAccount()) {
            return;
        }
        disconnect(LabyConnectDisconnectEvent.Initiator.USER, I18n.translate("labymod.activity.labyconnect.protocol.disconnect.sessionSwitch", new Object[0]));
        if (event.newSession().isPremium()) {
            connect();
        }
    }

    @Subscribe
    public void onNetworkLogin(NetworkLoginEvent event) {
        if (isAuthenticated()) {
            this.session.sendCurrentServer(event.serverData(), null, false);
        } else {
            this.timeNextConnect = TimeUtil.getMillis() + 10000;
        }
    }

    @Subscribe
    public void onNetworkDisconnect(NetworkDisconnectEvent event) {
        if (isAuthenticated()) {
            this.session.sendLeaveCurrentServer();
        }
    }

    @Override // net.labymod.api.labyconnect.LabyConnect
    public void disconnect(LabyConnectDisconnectEvent.Initiator initiator, String reason) {
        long delay = (long) (1000.0d * Math.random() * 60.0d);
        this.timeNextConnect = TimeUtil.getMillis() + 10000 + delay;
        if (this.doNotConnect && this.lastDisconnectReason != null) {
            this.lastDisconnectReason = reason;
        }
        if (this.state != LabyConnectState.OFFLINE) {
            if (this.session != null) {
                this.session.dispose();
            }
            fireEventSync(new LabyConnectDisconnectEvent(this, initiator, I18n.translate(reason, new Object[0])));
            updateState(LabyConnectState.OFFLINE);
            sendPacket(new PacketDisconnect("Logout"), channel -> {
                if (channel.isOpen()) {
                    channel.close();
                }
            });
            this.session = null;
        }
    }

    @Override // net.labymod.api.labyconnect.LabyConnect
    public void forceDisconnect(LabyConnectDisconnectEvent.Initiator initiator, String reason) {
        disconnect(initiator, reason);
        this.doNotConnect = true;
    }

    public void sendPacket(Packet packet) {
        sendPacket(packet, null);
    }

    public void sendPacket(Packet packet, Consumer<NioSocketChannel> callback) {
        NioSocketChannel channel = getChannel();
        if (channel == null || !channel.isActive()) {
            return;
        }
        EventLoop loop = channel.eventLoop();
        if (loop.inEventLoop()) {
            channel.writeAndFlush(packet).addListeners(new GenericFutureListener[]{ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE});
            if (callback != null) {
                callback.accept(channel);
                return;
            }
            return;
        }
        loop.execute(() -> {
            channel.writeAndFlush(packet).addListeners(new GenericFutureListener[]{ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE});
            if (callback != null) {
                callback.accept(channel);
            }
        });
    }

    public void updateState(LabyConnectState state) {
        synchronized (this) {
            this.state = state;
        }
        fireEventSync(new LabyConnectStateUpdateEvent(this, this.state));
        if (state == LabyConnectState.PLAY) {
            fireEventSync(new LabyConnectConnectEvent(this));
        }
    }

    public void keepAlive() {
        this.timeLastKeepAlive = TimeUtil.getMillis();
    }

    public void fireEventSync(Event event) {
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            this.eventBus.fire(event);
        });
    }

    @Override // net.labymod.api.labyconnect.LabyConnect
    public boolean isAuthenticated() {
        return this.state == LabyConnectState.PLAY;
    }

    @Override // net.labymod.api.labyconnect.LabyConnect
    public boolean isConnectionEstablished() {
        return (this.state == LabyConnectState.OFFLINE || this.session == null || !this.session.isConnectionEstablished()) ? false : true;
    }

    @Override // net.labymod.api.labyconnect.LabyConnect
    public LabyConnectState state() {
        return this.state;
    }

    @Override // net.labymod.api.labyconnect.LabyConnect
    @Nullable
    public LabyConnectSession getSession() {
        return this.session;
    }

    public Protocol getPacketRegistry() {
        return this.protocol;
    }

    public NioSocketChannel getChannel() {
        if (this.channelHandler == null) {
            return null;
        }
        return this.channelHandler.getChannel();
    }

    @Nullable
    public Snooper getSnooper() {
        return this.snooper;
    }

    public byte[] getVerifyToken() {
        return this.verifyToken;
    }

    @Override // net.labymod.api.labyconnect.LabyConnect
    public String getLastDisconnectReason() {
        return this.lastDisconnectReason;
    }

    @Override // net.labymod.api.labyconnect.LabyConnect
    public ConfigProvider<LabyConnectConfigAccessor> configProvider() {
        return this.labyConnectConfigProvider;
    }
}
