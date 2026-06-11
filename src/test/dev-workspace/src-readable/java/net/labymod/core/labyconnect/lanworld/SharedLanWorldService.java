package net.labymod.core.labyconnect.lanworld;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.session.Session;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.ServerKickEvent;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.event.client.world.WorldLeaveEvent;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.notification.Notification;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.server.LocalWorld;
import net.labymod.api.server.event.IntegratedServerPlayerDisconnectEvent;
import net.labymod.api.util.I18n;
import net.labymod.api.util.TextFormat;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldInvite;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayInviteLanWorld;
import net.labymod.core.main.LabyMod;
import net.labymod.labypeer.client.ClientConfig;
import net.labymod.labypeer.client.PeerClient;
import net.labymod.labypeer.client.tunnel.PeerTunnel;
import net.labymod.labypeer.client.tunnel.TunnelOpenException;
import net.labymod.labypeer.client.tunnel.config.ClientTunnelConfig;
import net.labymod.labypeer.client.tunnel.config.ServerTunnelConfig;
import net.labymod.labypeer.logger.LabyPeerLogger;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/lanworld/SharedLanWorldService.class */
@Singleton
@Referenceable
public class SharedLanWorldService {
    private static final Logging LOGGER = Logging.create((Class<?>) SharedLanWorldService.class);
    public static final String TRANSLATION_PREFIX = "labymod.activity.labyconnect.chat.lanworld.";
    private final PeerClient peerClient;
    private final Map<UUID, PeerTunnel> openTunnels = new ConcurrentHashMap();
    private final List<SharedLanWorldInvite> invites = new ArrayList();
    private final LabyConnectIceCredentialsTransmitter credentialsTransmitter = new LabyConnectIceCredentialsTransmitter();
    private final CandidateHarvesterService harvesterService;
    private boolean host;

    @Inject
    public SharedLanWorldService() {
        LabyPeerLogger labyPeerLoggerErrorOnly;
        if (Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment()) {
            labyPeerLoggerErrorOnly = new DebugLabyPeerLogger();
        } else {
            Logging logging = LOGGER;
            Objects.requireNonNull(logging);
            labyPeerLoggerErrorOnly = LabyPeerLogger.errorOnly((v1, v2) -> {
                r3.error(v1, v2);
            });
        }
        this.peerClient = new PeerClient(labyPeerLoggerErrorOnly, this.credentialsTransmitter);
        this.harvesterService = LabyMod.references().candidateHarvesterService();
        updateConfig();
    }

    public SharedLanWorldInvite createInvite(UUID sender, UUID receiver) {
        LocalWorld world = Laby.references().integratedServer().getLocalWorld();
        if (world == null || !world.isOpen()) {
            throw new IllegalStateException("Currently no LAN world is opened");
        }
        PacketPlayInviteLanWorld.LanWorldOptions options = new PacketPlayInviteLanWorld.LanWorldOptions(world.worldName(), (byte) world.gameMode().getId(), world.allowCheats(), (String) world.getScreenshotFile().map(path -> {
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                return null;
            }
        }).map(bytes -> {
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
        }).orElse(null));
        return pushInvite(sender, receiver, options, TimeUtil.getMillis());
    }

    public SharedLanWorldInvite pushInvite(UUID sender, UUID receiver, PacketPlayInviteLanWorld.LanWorldOptions options, long timestamp) {
        Friend friend;
        SharedLanWorldInvite invite = new SharedLanWorldInvite(sender, receiver, options, timestamp);
        dropInviteOfSender(sender, SharedLanWorldInvite.InviteStatus.EXPIRED);
        this.invites.add(invite);
        LabyConnectSession session = Laby.references().labyConnect().getSession();
        if (session != null && (friend = session.getFriend(sender)) != null) {
            friend.chat().addMessage(friend, invite.toString(), timestamp);
        }
        return invite;
    }

    public SharedLanWorldInvite dropInviteOfSender(UUID sender, SharedLanWorldInvite.InviteStatus reason) {
        Iterator<SharedLanWorldInvite> iterator = this.invites.iterator();
        while (iterator.hasNext()) {
            SharedLanWorldInvite invite = iterator.next();
            if (invite.getSender().equals(sender)) {
                invite.setStatus(reason);
                iterator.remove();
                return invite;
            }
        }
        return null;
    }

    public SharedLanWorldInvite dropInviteOfReceiver(UUID receiver, SharedLanWorldInvite.InviteStatus reason) {
        Iterator<SharedLanWorldInvite> iterator = this.invites.iterator();
        while (iterator.hasNext()) {
            SharedLanWorldInvite invite = iterator.next();
            if (invite.getReceiver().equals(receiver)) {
                invite.setStatus(reason);
                iterator.remove();
                return invite;
            }
        }
        return null;
    }

    public void establishTunnelFor(boolean host, UUID uniqueId, String username) {
        SharedLanWorldInvite invite;
        ServerTunnelConfig clientTunnelConfig;
        this.host = host;
        if (host) {
            invite = dropInviteOfReceiver(uniqueId, SharedLanWorldInvite.InviteStatus.ACCEPTED);
        } else {
            invite = dropInviteOfSender(uniqueId, SharedLanWorldInvite.InviteStatus.ACCEPTED);
        }
        if (this.host) {
            LocalWorld world = Laby.references().integratedServer().getLocalWorld();
            if (world == null || !world.isOpen()) {
                return;
            } else {
                clientTunnelConfig = new ServerTunnelConfig(new InetSocketAddress(InetAddress.getLoopbackAddress(), world.port()));
            }
        } else {
            try {
                int port = IOUtil.getFreePort();
                clientTunnelConfig = new ClientTunnelConfig(new InetSocketAddress(InetAddress.getLoopbackAddress(), port));
            } catch (IOException e) {
                LOGGER.error("Failed to allocate a free local port for LAN-world tunnel", e);
                Laby.labyAPI().notificationController().push(Notification.builder().title(Component.translatable("labymod.activity.labyconnect.chat.lanworld.connection.failed.title", new Component[0])).text(Component.text("Failed to allocate a free local port.")).build());
                return;
            }
        }
        ServerTunnelConfig serverTunnelConfig = clientTunnelConfig;
        SharedLanWorldInvite sharedLanWorldInvite = invite;
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            PopupWidget popup;
            if (!this.host) {
                Laby.labyAPI().serverController().leaveServer();
                popup = PopupWidget.builder().title(Component.text("Connecting...")).notClosable().build();
                popup.displayInOverlay();
            } else {
                popup = null;
            }
            try {
                PopupWidget popupWidget = popup;
                this.peerClient.openTunnel(uniqueId, serverTunnelConfig).handle((tunnel, throwable) -> {
                    String errorMessage;
                    String strTranslate;
                    if (popupWidget != null) {
                        Minecraft minecraft = Laby.labyAPI().minecraft();
                        Objects.requireNonNull(popupWidget);
                        minecraft.executeOnRenderThread(popupWidget::close);
                    }
                    if (tunnel != null) {
                        PeerTunnel oldTunnel = this.openTunnels.remove(uniqueId);
                        if (oldTunnel != null) {
                            try {
                                oldTunnel.close();
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        }
                        this.openTunnels.put(uniqueId, tunnel);
                        tunnel.setTunnelHandler(new SharedLanWorldHandler(this, tunnel, username));
                        if (!uniqueId.equals(this.peerClient.config().uniqueId()) && (serverTunnelConfig instanceof ClientTunnelConfig)) {
                            Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                                Laby.labyAPI().serverController().joinServer("localhost", ((ClientTunnelConfig) serverTunnelConfig).hostAddress().getPort());
                            });
                            return null;
                        }
                        queueJoinTimeoutTask(uniqueId, tunnel);
                        return null;
                    }
                    if (throwable != null) {
                        if (throwable instanceof TunnelOpenException) {
                            TunnelOpenException e2 = (TunnelOpenException) throwable;
                            if (e2.extendedError() != null) {
                                strTranslate = e2.extendedError();
                            } else {
                                strTranslate = I18n.translate("labymod.activity.labyconnect.chat.lanworld.connection.failed.error." + TextFormat.SNAKE_CASE.toLowerCamelCase(e2.error().name()), new Object[0]);
                            }
                            errorMessage = strTranslate;
                        } else {
                            errorMessage = throwable.getClass().getSimpleName();
                        }
                        LOGGER.error("Failed to open tunnel to {}", uniqueId, throwable);
                    } else {
                        errorMessage = "unknown error";
                        LOGGER.error("Failed to open tunnel to {}", uniqueId);
                    }
                    String str = errorMessage;
                    Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                        TranslatableComponent description = Component.translatable("labymod.activity.labyconnect.chat.lanworld.connection.failed.text", Component.text(str));
                        Laby.labyAPI().notificationController().push(Notification.builder().title(Component.translatable("labymod.activity.labyconnect.chat.lanworld.connection.failed.title", new Component[0])).text(description).build());
                        if (sharedLanWorldInvite != null) {
                            sharedLanWorldInvite.setConnectError(description);
                        }
                    });
                    return null;
                });
            } catch (Throwable throwable2) {
                throwable2.printStackTrace();
            }
        });
    }

    private void queueJoinTimeoutTask(UUID uniqueId, PeerTunnel tunnel) {
        Task.builder(() -> {
            if (!tunnel.isOpen()) {
                return;
            }
            ClientPacketListener clientPacketListener = Laby.labyAPI().minecraft().getClientPacketListener();
            if (clientPacketListener == null || clientPacketListener.getNetworkPlayerInfo(uniqueId) == null) {
                this.openTunnels.remove(uniqueId);
                try {
                    tunnel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).delay(10L, TimeUnit.SECONDS).build().executeOnRenderThread();
    }

    @Nullable
    public SharedLanWorldInvite getInviteOfSender(UUID sender) {
        for (SharedLanWorldInvite invite : this.invites) {
            if (invite.getSender().equals(sender)) {
                return invite;
            }
        }
        return null;
    }

    @Subscribe
    public void onSessionUpdate(SessionUpdateEvent event) {
        updateConfig();
    }

    @Subscribe
    public void onLabyConnectStateUpdate(LabyConnectStateUpdateEvent event) {
        if (event.state() == LabyConnectState.OFFLINE) {
            this.invites.clear();
        } else {
            updateConfig();
        }
    }

    @Subscribe
    public void onWorldLeave(WorldLeaveEvent event) {
        disconnected();
    }

    @Subscribe
    public void onDisconnect(ServerDisconnectEvent event) {
        disconnected();
    }

    @Subscribe
    public void onKick(ServerKickEvent event) {
        disconnected();
    }

    private void disconnected() {
        if (!this.openTunnels.isEmpty()) {
            closeAllConnections();
        }
        UUID self = Laby.labyAPI().minecraft().sessionAccessor().getSession().getUniqueId();
        Iterator<SharedLanWorldInvite> iterator = this.invites.iterator();
        while (iterator.hasNext()) {
            SharedLanWorldInvite invite = iterator.next();
            if (invite.getSender().equals(self)) {
                invite.setStatus(SharedLanWorldInvite.InviteStatus.EXPIRED);
                iterator.remove();
            }
        }
    }

    @Subscribe
    public void onPlayerDisconnect(IntegratedServerPlayerDisconnectEvent event) {
        if (event.phase() != Phase.POST || this.openTunnels.isEmpty()) {
            return;
        }
        UUID uniqueId = event.profile().getUniqueId();
        if (uniqueId.equals(Laby.labyAPI().getUniqueId())) {
            return;
        }
        Task.builder(() -> {
            closeConnection(uniqueId);
        }).delay(1L, TimeUnit.SECONDS).build().execute();
    }

    private void updateConfig() {
        Session session = Laby.labyAPI().minecraft().sessionAccessor().getSession();
        if (session != null) {
            PeerClient peerClient = this.peerClient;
            UUID uniqueId = session.getUniqueId();
            CandidateHarvesterService candidateHarvesterService = this.harvesterService;
            Objects.requireNonNull(candidateHarvesterService);
            peerClient.setConfig(new ClientConfig(uniqueId, candidateHarvesterService::buildHarvesters));
        }
    }

    public void closeConnection(UUID uniqueId) {
        PeerTunnel tunnel = this.openTunnels.remove(uniqueId);
        if (tunnel != null) {
            try {
                tunnel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (this.openTunnels.isEmpty()) {
            this.host = false;
        }
    }

    public Map<UUID, PeerTunnel> getOpenTunnels() {
        return this.openTunnels;
    }

    public void closeAllConnections() {
        for (PeerTunnel tunnel : this.openTunnels.values()) {
            try {
                tunnel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.openTunnels.clear();
        this.host = false;
    }

    public boolean canSendInvite(UUID uniqueId) {
        return !this.openTunnels.containsKey(uniqueId);
    }

    public boolean isHost() {
        return this.host;
    }

    public UUID getCurrentHost() {
        if (this.openTunnels.isEmpty()) {
            return null;
        }
        if (this.host) {
            return Laby.labyAPI().minecraft().sessionAccessor().getSession().getUniqueId();
        }
        return this.openTunnels.keySet().stream().findFirst().get();
    }

    public boolean hasOpenTunnel() {
        return !this.openTunnels.isEmpty();
    }

    public LabyConnectIceCredentialsTransmitter credentialsTransmitter() {
        return this.credentialsTransmitter;
    }
}
