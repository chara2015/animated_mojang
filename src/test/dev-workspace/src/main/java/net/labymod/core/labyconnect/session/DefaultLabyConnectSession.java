package net.labymod.core.labyconnect.session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.channel.socket.nio.NioSocketChannel;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import javax.crypto.SecretKey;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.session.MinecraftAuthenticator;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.session.model.MojangTextureChangedResponse;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectBroadcastEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectDisconnectEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectPlayEmoteEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectRejectAuthenticationEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectUpdateSettingEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendAddEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendRemoveEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendServerEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendStatusEvent;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectFriendAddBulkEvent;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectIncomingFriendRequestAddBulkEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestAddEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestRemoveEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestAddResponseEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestRemoveEvent;
import net.labymod.api.event.labymod.user.UserFamiliarEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.LanWorldRejectReason;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.GameUserService;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.function.Consumers;
import net.labymod.api.util.gson.UUIDTypeAdapter;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.LabyConnectDirectChatActivity;
import net.labymod.core.event.labymod.PacketAddonDevelopmentEvent;
import net.labymod.core.event.labymod.PacketAddonMessageEvent;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldInvite;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldService;
import net.labymod.core.labyconnect.pipeline.PacketEncryptingDecoder;
import net.labymod.core.labyconnect.pipeline.PacketEncryptingEncoder;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;
import net.labymod.core.labyconnect.protocol.model.chat.DefaultChat;
import net.labymod.core.labyconnect.protocol.model.chat.DefaultTextChatMessage;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultServerInfo;
import net.labymod.core.labyconnect.protocol.model.request.DefaultIncomingFriendRequest;
import net.labymod.core.labyconnect.protocol.model.request.DefaultOutgoingFriendRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketActionBroadcast;
import net.labymod.core.labyconnect.protocol.packets.PacketActionPlay;
import net.labymod.core.labyconnect.protocol.packets.PacketActionPlayResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketActionRequestResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonDevelopment;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketDisconnect;
import net.labymod.core.labyconnect.protocol.packets.PacketEncryptionRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketEncryptionResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketHelloPong;
import net.labymod.core.labyconnect.protocol.packets.PacketIceCredentials;
import net.labymod.core.labyconnect.protocol.packets.PacketKick;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginComplete;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginData;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginFriend;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginOptions;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginTime;
import net.labymod.core.labyconnect.protocol.packets.PacketLoginVersion;
import net.labymod.core.labyconnect.protocol.packets.PacketMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketMojangStatus;
import net.labymod.core.labyconnect.protocol.packets.PacketNotAllowed;
import net.labymod.core.labyconnect.protocol.packets.PacketPing;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayAcceptLanWorldInvite;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayBroadcastPayload;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayChangeOptions;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayDenyFriendRequest;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendPlayingOn;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendRemove;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayFriendStatus;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayInviteLanWorld;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayPlayerOnline;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRejectLanWorldInvite;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestAddFriend;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestAddFriendResponse;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayRequestRemove;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayServerStatusUpdate;
import net.labymod.core.labyconnect.protocol.packets.PacketPlayTyping;
import net.labymod.core.labyconnect.protocol.packets.PacketPong;
import net.labymod.core.labyconnect.protocol.packets.PacketServerMessage;
import net.labymod.core.labyconnect.protocol.packets.PacketUpdateCosmetics;
import net.labymod.core.labyconnect.protocol.packets.PacketUserBadge;
import net.labymod.core.labyconnect.protocol.packets.PacketUserTracker;
import net.labymod.core.labyconnect.session.advertisement.AdvertisementController;
import net.labymod.core.labyconnect.session.message.DashboardPinMessageListener;
import net.labymod.core.labyconnect.session.message.FriendRequestResponseMessageListener;
import net.labymod.core.labyconnect.session.message.IncentiveMessageListener;
import net.labymod.core.labyconnect.session.message.LanguageFlagsMessageListener;
import net.labymod.core.labyconnect.session.message.MessageListener;
import net.labymod.core.labyconnect.session.message.OutgoingFriendRequestsMessageListener;
import net.labymod.core.labyconnect.session.message.RolesMessageListener;
import net.labymod.core.labyconnect.session.message.SkinApplyMessageListener;
import net.labymod.core.labyconnect.session.message.TexturesUpdatedMessageListener;
import net.labymod.core.labyconnect.session.message.UpdateTokenMessageListener;
import net.labymod.core.labyconnect.session.message.WebNotificationMessageListener;
import net.labymod.core.labyconnect.session.spray.SprayProtocol;
import net.labymod.core.labyconnect.session.spray.V1SprayProtocol;
import net.labymod.core.labyconnect.session.spray.V2SprayProtocol;
import net.labymod.core.labyconnect.util.CryptManager;
import net.labymod.core.labyconnect.util.Snooper;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.labypeer.ice.IceCredentials;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/DefaultLabyConnectSession.class */
public class DefaultLabyConnectSession extends PacketHandler implements LabyConnectSession {
    private static final int SPRAY_V1_PACKET_SIZE = 26;
    private static final int SPRAY_V2_PACKET_SIZE = 38;
    private final DefaultLabyConnect labyConnect;
    private final Session session;
    private final User self;
    private Consumer<String> dashboardPinCallback;
    private boolean premium;
    private AdvertisementController advertisementController;
    private IncentiveMessageListener incentiveListener;
    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
    private static final Logging LOGGER = Logging.getLogger();
    private final IntArrayList roles = new IntArrayList();
    private final Int2ObjectMap<SprayProtocol> sprayProtocols = new Int2ObjectOpenHashMap();
    private final List<Friend> friends = new CopyOnWriteArrayList();
    private final Map<UUID, Friend> uuid2Friend = new ConcurrentHashMap();
    private final List<Friend> unmodifiableFriends = Collections.unmodifiableList(this.friends);
    private final List<IncomingFriendRequest> incomingRequests = new CopyOnWriteArrayList();
    private final List<IncomingFriendRequest> unmodifiableIncomingRequests = Collections.unmodifiableList(this.incomingRequests);
    private final List<OutgoingFriendRequest> outgoingFriendRequests = new CopyOnWriteArrayList();
    private final List<OutgoingFriendRequest> unmodifiableOutgoingFriendRequests = Collections.unmodifiableList(this.outgoingFriendRequests);
    private final Map<UUID, byte[]> fileStorage = new HashMap();
    private final Map<String, MessageListener> messageListeners = new HashMap();
    private final LabyAPI labyApi = Laby.labyAPI();
    private boolean connectionEstablished = false;
    private boolean authenticated = false;
    private final GameUserService gameUserService = Laby.references().gameUserService();
    private final ApplyTextureController applyTextureController = LabyMod.references().applyTextureController();
    private final DefaultTokenStorage tokenStorage = (DefaultTokenStorage) Laby.references().tokenStorage();

    public DefaultLabyConnectSession(DefaultLabyConnect labyConnect, Session session) {
        this.labyConnect = labyConnect;
        this.session = session;
        this.premium = session.isPremium();
        this.self = new DefaultUser(session.getUniqueId(), session.getUsername());
        this.sprayProtocols.put(1, new V1SprayProtocol());
        this.sprayProtocols.put(2, new V2SprayProtocol());
        registerMessageListeners();
    }

    private void registerMessageListeners() {
        this.messageListeners.put("dashboard_pin", new DashboardPinMessageListener(pin -> {
            Consumers.accept(this.dashboardPinCallback, pin);
        }));
        Map<String, MessageListener> map = this.messageListeners;
        IncentiveMessageListener incentiveMessageListener = new IncentiveMessageListener(this.labyConnect, this.self);
        this.incentiveListener = incentiveMessageListener;
        map.put("incentive", incentiveMessageListener);
        this.messageListeners.put("language_flags", new LanguageFlagsMessageListener());
        this.messageListeners.put("roles", new RolesMessageListener(this.roles, this.self));
        this.messageListeners.put("skin_apply", new SkinApplyMessageListener(this.applyTextureController));
        this.messageListeners.put("textures_updated", new TexturesUpdatedMessageListener(this.applyTextureController));
        this.messageListeners.put("unauthenticated", message -> {
            resetAuthentication();
        });
        this.messageListeners.put("update_token", new UpdateTokenMessageListener(this.labyConnect, this.self, this.tokenStorage));
        this.messageListeners.put("web_notification", new WebNotificationMessageListener());
        this.messageListeners.put("outgoing_friend_requests", new OutgoingFriendRequestsMessageListener(this.labyConnect, this.outgoingFriendRequests));
        this.messageListeners.put("friend_request_response", new FriendRequestResponseMessageListener(this.labyConnect, this.outgoingFriendRequests));
        Map<String, MessageListener> map2 = this.messageListeners;
        AdvertisementController advertisementController = new AdvertisementController(this.labyConnect);
        this.advertisementController = advertisementController;
        map2.put("advertisement", advertisementController);
        this.messageListeners.put("rplace", LabyMod.references().rPlaceRegistry());
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void playEmote(short id) {
        byte[] data = ByteBuffer.wrap(new byte[2]).order(ByteOrder.LITTLE_ENDIAN).putShort(id).array();
        this.labyConnect.sendPacket(new PacketActionPlay(-1, PacketActionBroadcast.ActionType.EMOTE, data));
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void spray(short id, int protocolVersion, double x, double y, double z, Direction direction, float rotation) {
        ByteBuffer buffer = ByteBuffer.allocate(38).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(id);
        buffer.putInt(protocolVersion);
        buffer.putDouble(x);
        buffer.putDouble(y);
        buffer.putDouble(z);
        buffer.putInt(direction.getIndex());
        buffer.putFloat(rotation);
        buffer.flip();
        byte[] data = new byte[38];
        buffer.get(data);
        this.labyConnect.sendPacket(new PacketActionPlay(-1, PacketActionBroadcast.ActionType.SPRAY, data));
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void removeFriend(UUID uniqueId) {
        Friend friend = getFriend(uniqueId);
        if (friend instanceof DefaultFriend) {
            DefaultFriend defaultFriend = (DefaultFriend) friend;
            this.labyConnect.sendPacket(new PacketPlayFriendRemove(defaultFriend));
        }
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void sendFriendRequest(String username) {
        this.labyConnect.sendPacket(new PacketPlayRequestAddFriend(username));
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void acceptFriendRequest(UUID uniqueId) {
        IncomingFriendRequest request = getIncomingRequest(uniqueId);
        if (request == null) {
            return;
        }
        this.labyConnect.sendPacket(new PacketPlayRequestAddFriend(request.getName()));
        this.incomingRequests.remove(request);
        this.labyConnect.fireEventSync(new LabyConnectIncomingFriendRequestRemoveEvent(this.labyConnect, request));
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void declineFriendRequest(UUID uniqueId) {
        IncomingFriendRequest incomingRequest = getIncomingRequest(uniqueId);
        if (incomingRequest != null) {
            this.labyConnect.sendPacket(new PacketPlayDenyFriendRequest((DefaultIncomingFriendRequest) incomingRequest));
            this.incomingRequests.remove(incomingRequest);
            this.labyConnect.fireEventSync(new LabyConnectIncomingFriendRequestRemoveEvent(this.labyConnect, incomingRequest));
        }
        OutgoingFriendRequest outgoingRequest = getOutgoingRequest(uniqueId);
        if (outgoingRequest != null) {
            this.labyConnect.sendPacket(new PacketPlayDenyFriendRequest((DefaultOutgoingFriendRequest) outgoingRequest));
            this.outgoingFriendRequests.remove(outgoingRequest);
            this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestRemoveEvent(this.labyConnect, outgoingRequest));
        }
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void sendAddonMessage(String key, byte[] data) {
        this.labyConnect.sendPacket(new PacketAddonMessage(key, data));
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void sendAddonDevelopment(String key, UUID[] receivers, byte[] data) {
        this.labyConnect.sendPacket(new PacketAddonDevelopment(this.session.getUniqueId(), receivers, key, data));
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void sendChatMessage(UUID chatIdentifier, TextChatMessage message) {
        Friend friend = getFriend(chatIdentifier);
        if (!(friend instanceof DefaultFriend)) {
            return;
        }
        this.labyConnect.sendPacket(new PacketMessage((DefaultUser) this.self, (DefaultFriend) friend, message.getRawMessage(), 0L, 0.0d, TimeUtil.getCurrentTimeMillis()));
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public UUID sendChatFile(UUID chatIdentifier, String type, byte[] data) {
        Friend friend = getFriend(chatIdentifier);
        if (!(friend instanceof DefaultFriend)) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        UUID identifier = UUID.randomUUID();
        out.write(ByteBuffer.allocate(16).putLong(identifier.getMostSignificantBits()).putLong(identifier.getLeastSignificantBits()).array(), 0, 16);
        out.write(data, 0, data.length);
        if (!storeFile(identifier, data)) {
            return null;
        }
        sendAddonDevelopment("labymod:file", new UUID[]{chatIdentifier}, out.toByteArray());
        friend.chat().sendMessage("file://" + String.valueOf(identifier) + "?type=" + type);
        return identifier;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void sendCurrentServer(ServerData serverData, String gameMode, boolean viaServerList) {
        PacketPlayServerStatusUpdate packet = new PacketPlayServerStatusUpdate(serverData.address().getHost(), serverData.address().getPort(), gameMode, viaServerList);
        this.labyConnect.sendPacket(packet);
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void sendLeaveCurrentServer() {
        this.labyConnect.sendPacket(new PacketPlayServerStatusUpdate("", 0, "", false));
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void sendSettings() {
        LabyConnectConfigAccessor config = (LabyConnectConfigAccessor) this.labyConnect.configProvider().get();
        boolean showServer = config.showConnectedServer().get().booleanValue();
        UserStatus status = config.onlineStatus().get();
        this.self.updateStatus(config.onlineStatus().get());
        this.labyConnect.sendPacket(new PacketPlayChangeOptions(showServer, status, Calendar.getInstance().getTimeZone()));
        this.labyConnect.fireEventSync(new LabyConnectUpdateSettingEvent(this.labyConnect, config));
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    protected void handlePacket(Packet packet) {
        this.connectionEstablished = true;
        super.handlePacket(packet);
        this.labyConnect.keepAlive();
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketHelloPong packet) {
        this.labyConnect.sendPacket(new PacketLoginVersion(29, this.labyApi.minecraft().getVersionId() + "_" + this.labyApi.getVersion(), this.labyApi.minecraft().getProtocolVersion()));
        if (this.session.isPremium()) {
            this.labyConnect.updateState(LabyConnectState.LOGIN);
            LabyConnectConfigAccessor config = (LabyConnectConfigAccessor) this.labyConnect.configProvider().get();
            boolean showServer = config.showConnectedServer().get().booleanValue();
            UserStatus status = config.onlineStatus().get();
            this.labyConnect.sendPacket(new PacketLoginData(this.session.getUniqueId(), this.session.getUsername(), ""));
            this.self.updateStatus(config.onlineStatus().get());
            this.labyConnect.sendPacket(new PacketLoginOptions(showServer, status, Calendar.getInstance().getTimeZone()));
        } else {
            this.labyConnect.fireEventSync(new LabyConnectRejectAuthenticationEvent(this.labyConnect));
        }
        this.labyConnect.keepAlive();
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketEncryptionRequest encryptionRequest) {
        try {
            PublicKey publicKey = CryptManager.decodePublicKey(encryptionRequest.getPublicKey());
            SecretKey secretKey = CryptManager.createNewSharedKey();
            String serverId = encryptionRequest.getServerId();
            MinecraftAuthenticator authenticator = this.labyApi.minecraft().authenticator();
            byte[] bytes = CryptManager.getServerIdHash(serverId, publicKey, secretKey);
            if (bytes == null) {
                this.labyConnect.disconnect(LabyConnectDisconnectEvent.Initiator.CLIENT, "Failed to hash server id");
                return;
            }
            String hash = new BigInteger(bytes).toString(16);
            NioSocketChannel nio = this.labyConnect.getChannel();
            authenticator.joinServer(this.session, hash).exceptionally(throwable -> {
                return false;
            }).thenAccept(result -> {
                if (this.labyConnect.getChannel() != nio) {
                    return;
                }
                byte[] verifyTokenBuffer = this.labyConnect.getVerifyToken();
                System.arraycopy(encryptionRequest.getVerifyToken(), 0, verifyTokenBuffer, 0, 4);
                this.labyConnect.sendPacket(new PacketEncryptionResponse(secretKey, publicKey, verifyTokenBuffer), channel -> {
                    channel.pipeline().addBefore("splitter", "decrypt", new PacketEncryptingDecoder(CryptManager.createNetCipherInstance(2, secretKey)));
                    channel.pipeline().addBefore("prepender", "encrypt", new PacketEncryptingEncoder(CryptManager.createNetCipherInstance(1, secretKey)));
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.labyConnect.disconnect(LabyConnectDisconnectEvent.Initiator.CLIENT, e.getMessage());
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketKick packet) {
        this.labyConnect.disconnect(LabyConnectDisconnectEvent.Initiator.SERVER, packet.getReason());
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketPing packet) {
        this.labyConnect.sendPacket(new PacketPong());
        this.labyConnect.keepAlive();
        if (isAuthenticated()) {
            for (TokenStorage.Purpose purpose : TokenStorage.Purpose.values()) {
                if (!this.tokenStorage.hasValidToken(purpose, this.self.getUniqueId())) {
                    JsonObject payload = new JsonObject();
                    payload.addProperty("purpose", purpose.name());
                    this.labyConnect.sendPacket(new PacketAddonMessage("request_token", (JsonElement) payload));
                }
            }
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketLoginComplete packet) {
        this.labyConnect.updateState(LabyConnectState.PLAY);
        this.authenticated = true;
        if (packet.getDashboardPin() != null && !packet.getDashboardPin().isEmpty()) {
            try {
                JsonObject jsonObject = (JsonObject) GSON.fromJson(packet.getDashboardPin(), JsonObject.class);
                if (jsonObject.has("pin")) {
                    String pin = jsonObject.get("pin").getAsString();
                    long expiresAt = jsonObject.get("expires_at").getAsLong();
                    this.tokenStorage.updateToken(TokenStorage.Purpose.CLIENT, this.self.getUniqueId(), new TokenStorage.Token(pin, expiresAt));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.labyConnect.keepAlive();
        ServerData serverData = this.labyApi.serverController().getCurrentServerData();
        if (serverData != null) {
            sendCurrentServer(serverData, "", false);
        }
        Snooper snooper = this.labyConnect.getSnooper();
        if (snooper != null) {
            snooper.collect().thenAccept(obj -> {
                this.labyConnect.sendPacket(new PacketAddonMessage("anonymous_statistics", (JsonElement) obj));
            });
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketLoginFriend packet) {
        List<DefaultFriend> friends = packet.getFriends();
        if (friends.isEmpty()) {
            return;
        }
        this.friends.addAll(friends);
        for (DefaultFriend friend : friends) {
            this.uuid2Friend.put(friend.getUniqueId(), friend);
        }
        if (friends.size() == 1) {
            this.labyConnect.fireEventSync(new LabyConnectFriendAddEvent(this.labyConnect, friends.get(0)));
        } else {
            this.labyConnect.fireEventSync(new LabyConnectFriendAddBulkEvent(this.labyConnect, new ArrayList(friends)));
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketLoginRequest packet) {
        List<DefaultIncomingFriendRequest> requests = packet.getRequests();
        if (requests.isEmpty()) {
            return;
        }
        this.incomingRequests.addAll(requests);
        if (requests.size() == 1) {
            this.labyConnect.fireEventSync(new LabyConnectIncomingFriendRequestAddEvent(this.labyConnect, requests.get(0)));
        } else {
            this.labyConnect.fireEventSync(new LabyConnectIncomingFriendRequestAddBulkEvent(this.labyConnect, new ArrayList(requests)));
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketDisconnect packet) {
        this.labyConnect.disconnect(LabyConnectDisconnectEvent.Initiator.SERVER, packet.getReason());
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketPlayPlayerOnline packet) {
        Friend friend = getFriend(packet.getPlayer().getUniqueId());
        if (friend instanceof DefaultFriend) {
            DefaultFriend defaultFriend = (DefaultFriend) friend;
            UserStatus previousState = friend.userStatus();
            defaultFriend.updateStatus(packet.getPlayer().userStatus());
            defaultFriend.setStatusMessage(packet.getPlayer().getStatusMessage());
            if (packet.getPlayer().getServer() != null) {
                defaultFriend.setServer((DefaultServerInfo) packet.getPlayer().getServer());
            } else {
                defaultFriend.setServer(null);
            }
            this.labyConnect.fireEventSync(new LabyConnectFriendStatusEvent(this.labyConnect, friend, previousState, friend.userStatus()));
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketUserBadge packet) {
        byte[] ranks = packet.getRanks();
        UUID[] uuids = packet.getUuids();
        for (int i = 0; i < uuids.length; i++) {
            DefaultGameUser gameUser = (DefaultGameUser) this.gameUserService.gameUser(uuids[i]);
            gameUser.setTag(GameUser.FAMILIAR);
            gameUser.setVisibleGroup(ranks[i]);
            this.labyConnect.fireEventSync(new UserFamiliarEvent(gameUser));
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketActionBroadcast packet) {
        switch (packet.getType()) {
            case EMOTE:
                short emoteId = ByteBuffer.wrap(packet.getData()).order(ByteOrder.LITTLE_ENDIAN).getShort();
                this.labyConnect.fireEventSync(new LabyConnectPlayEmoteEvent(this.labyConnect, packet.getUniqueId(), emoteId));
                break;
            case COSMETIC_CHANGE:
                DefaultGameUserService service = (DefaultGameUserService) this.gameUserService;
                DefaultGameUser gameUser = (DefaultGameUser) service.gameUser(packet.getUniqueId());
                try {
                    String json = new String(packet.getData(), StandardCharsets.UTF_8);
                    JsonElement element = (JsonElement) service.getUserGson().fromJson(json, JsonElement.class);
                    if (element != null && element.isJsonObject()) {
                        gameUser.updateUserData(element);
                    }
                } catch (Exception e) {
                    LOGGER.warn("Ignoring malformed COSMETIC_CHANGE payload for {}: {}", packet.getUniqueId(), e.getMessage());
                    return;
                }
                break;
            case SPRAY:
                ByteBuffer buffer = ByteBuffer.wrap(packet.getData()).order(ByteOrder.LITTLE_ENDIAN);
                short sprayId = buffer.getShort();
                if (buffer.hasRemaining()) {
                    int protocolVersion = buffer.getInt();
                    SprayProtocol protocol = (SprayProtocol) this.sprayProtocols.get(protocolVersion);
                    if (protocol != null) {
                        protocol.handle(this.labyConnect, sprayId, protocolVersion, packet.getUniqueId(), buffer);
                    }
                }
                break;
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketPlayBroadcastPayload packet) {
        try {
            if (packet.getSender().equals(this.self.getUniqueId())) {
                return;
            }
            JsonElement jsonElement = (JsonElement) GSON.fromJson(packet.getJson(), JsonElement.class);
            Laby.fireEvent(new LabyConnectBroadcastEvent(this.labyConnect, LabyConnectBroadcastEvent.Action.RECEIVE, packet.getSender(), packet.getKey(), jsonElement));
        } catch (Exception e) {
            DefaultLabyConnect.LOGGER.info("Failed to parse broadcast payload from " + String.valueOf(packet.getSender()), new Object[0]);
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketPlayRequestRemove packet) {
        IncomingFriendRequest incomingTarget = (IncomingFriendRequest) CollectionHelper.removeFirstIf(this.incomingRequests, request -> {
            return request.getName().equals(packet.getPlayerName());
        });
        if (incomingTarget != null) {
            this.labyConnect.fireEventSync(new LabyConnectIncomingFriendRequestRemoveEvent(this.labyConnect, incomingTarget));
        }
        OutgoingFriendRequest outgoingTarget = (OutgoingFriendRequest) CollectionHelper.removeFirstIf(this.outgoingFriendRequests, request2 -> {
            return request2.getName().equals(packet.getPlayerName());
        });
        if (outgoingTarget != null) {
            this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestRemoveEvent(this.labyConnect, outgoingTarget));
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketPlayFriendRemove packet) {
        Friend target = (Friend) CollectionHelper.removeFirstIf(this.friends, friend -> {
            return friend.getUniqueId().equals(packet.getToRemove().getUniqueId());
        });
        this.uuid2Friend.remove(packet.getToRemove().getUniqueId());
        if (target != null) {
            this.labyConnect.fireEventSync(new LabyConnectFriendRemoveEvent(this.labyConnect, target));
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketPlayFriendStatus packet) {
        Friend friend = getFriend(packet.getPlayer().getUniqueId());
        if (friend == null) {
            return;
        }
        DefaultServerInfo newServerInfo = packet.getPlayerInfo();
        DefaultFriend defaultFriend = (DefaultFriend) friend;
        UserStatus prevStatus = defaultFriend.userStatus();
        UserStatus newStatus = packet.getPlayer().userStatus();
        if (prevStatus != newStatus) {
            defaultFriend.updateStatus(newStatus);
            this.labyConnect.fireEventSync(new LabyConnectFriendStatusEvent(this.labyConnect, friend, prevStatus, newStatus));
        }
        DefaultServerInfo serverInfo = (DefaultServerInfo) defaultFriend.getServer();
        if (Objects.equals(serverInfo, newServerInfo)) {
            return;
        }
        if (newServerInfo.getAddress().isEmpty()) {
            defaultFriend.setServer(null);
        } else {
            defaultFriend.setServer(newServerInfo);
        }
        this.labyConnect.fireEventSync(new LabyConnectFriendServerEvent(this.labyConnect, friend, defaultFriend.getServer(), serverInfo));
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketMessage packet) {
        Friend friend = getFriend(packet.getSender().getUniqueId());
        if (friend == null) {
            return;
        }
        DefaultChat chat = (DefaultChat) friend.chat();
        DefaultTextChatMessage message = new DefaultTextChatMessage(chat, friend, packet.getSentTime(), packet.getMessage());
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            chat.addMessage(message);
        });
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketPlayTyping packet) {
        Friend friend = getFriend(packet.getPlayer().getUniqueId());
        if (friend == null) {
            return;
        }
        ((DefaultChat) friend.chat()).updateTyping(packet.isTyping());
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketPlayRequestAddFriendResponse packet) {
        if (!packet.isRequestSent()) {
            this.labyConnect.fireEventSync(new LabyConnectOutgoingFriendRequestAddResponseEvent(this.labyConnect, packet.getSearched(), packet.getReason()));
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketPlayFriendPlayingOn packet) {
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketPlayInviteLanWorld packet) {
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            LabyMod.references().sharedLanWorldService().pushInvite(packet.getPlayer(), this.self.getUniqueId(), packet.getOptions(), TimeUtil.getCurrentTimeMillis());
        });
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketPlayAcceptLanWorldInvite packet) {
        Friend friend = getFriend(packet.getPlayer());
        if (friend == null) {
            return;
        }
        LabyMod.references().sharedLanWorldService().establishTunnelFor(packet.isHost(), packet.getPlayer(), friend.getName());
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void inviteToLanWorld(Friend friend) {
        SharedLanWorldInvite invite = LabyMod.references().sharedLanWorldService().createInvite(this.self.getUniqueId(), friend.getUniqueId());
        this.labyConnect.sendPacket(new PacketPlayInviteLanWorld(invite));
        friend.chat().addMessage(this.self, invite.toString());
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void acceptLanWorldInvite(Friend friend) {
        this.labyConnect.sendPacket(new PacketPlayAcceptLanWorldInvite(friend.getUniqueId(), false));
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void rejectLanWorldInvite(Friend friend) {
        this.labyConnect.sendPacket(new PacketPlayRejectLanWorldInvite(friend.getUniqueId(), LanWorldRejectReason.REQUEST_REJECTED, false));
        LabyMod.references().sharedLanWorldService().dropInviteOfSender(friend.getUniqueId(), SharedLanWorldInvite.InviteStatus.REJECTED);
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void retractLanWorldInvite(Friend friend) {
        this.labyConnect.sendPacket(new PacketPlayRejectLanWorldInvite(friend.getUniqueId(), LanWorldRejectReason.REQUEST_RETRACTED, true));
        LabyMod.references().sharedLanWorldService().dropInviteOfReceiver(friend.getUniqueId(), SharedLanWorldInvite.InviteStatus.RETRACTED);
    }

    public void sendIceCredentials(UUID targetUser, IceCredentials credentials) {
        this.labyConnect.sendPacket(new PacketIceCredentials(targetUser, credentials));
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketPlayRejectLanWorldInvite packet) {
        Laby.labyAPI().minecraft().executeNextTick(() -> {
            SharedLanWorldInvite.InviteStatus status;
            SharedLanWorldService service = LabyMod.references().sharedLanWorldService();
            switch (packet.getReason()) {
                case USER_DISCONNECTED:
                    status = SharedLanWorldInvite.InviteStatus.DISCONNECTED;
                    break;
                case INCOMPATIBLE_MINECRAFT_VERSION:
                    status = SharedLanWorldInvite.InviteStatus.INCOMPATIBLE_MINECRAFT_VERSION;
                    break;
                default:
                    status = packet.isHost() ? SharedLanWorldInvite.InviteStatus.REJECTED : SharedLanWorldInvite.InviteStatus.EXPIRED;
                    break;
            }
            if (packet.isHost()) {
                service.dropInviteOfReceiver(packet.getPlayer(), status);
            } else {
                service.dropInviteOfSender(packet.getPlayer(), status);
            }
        });
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketIceCredentials packet) {
        if (!packet.getTargetUser().equals(this.self.getUniqueId())) {
            return;
        }
        SharedLanWorldService service = LabyMod.references().sharedLanWorldService();
        service.credentialsTransmitter().credentialsReceived(packet.getCredentials());
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketNotAllowed packet) {
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketServerMessage packet) {
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketLoginTime packet) {
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketMojangStatus packet) {
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketUpdateCosmetics packet) {
        String json = packet.getJson();
        if (json == null) {
            return;
        }
        GameUser clientGameUser = this.gameUserService.clientGameUser();
        if (!(clientGameUser instanceof DefaultGameUser)) {
            return;
        }
        ((DefaultGameUser) clientGameUser).updateUserData((JsonElement) GSON.fromJson(json, JsonElement.class));
        LabyMod.references().labyModNetService().reload();
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketAddonMessage packet) {
        Laby.fireEvent(new PacketAddonMessageEvent(packet));
        String key = packet.getKey();
        MessageListener messageListener = this.messageListeners.get(key);
        if (messageListener == null) {
            LOGGER.debug("Unknown addon message {}", key);
        } else {
            messageListener.listen(packet.getJson());
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketActionPlayResponse packet) {
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketActionRequestResponse packet) {
    }

    @Override // net.labymod.core.labyconnect.protocol.PacketHandler
    public void handle(PacketAddonDevelopment packet) {
        Laby.fireEvent(new PacketAddonDevelopmentEvent(packet));
        if (packet.getKey().equals("labymod:file")) {
            byte[] data = packet.getData();
            if (data.length < 16) {
                return;
            }
            ByteBuffer buffer = ByteBuffer.wrap(data);
            UUID identifier = new UUID(buffer.getLong(), buffer.getLong());
            byte[] fileData = new byte[data.length - 16];
            buffer.get(fileData);
            storeFile(identifier, fileData);
        }
    }

    private boolean storeFile(UUID identifier, byte[] fileData) {
        if (this.fileStorage.size() > 20) {
            this.fileStorage.remove(this.fileStorage.keySet().iterator().next());
        }
        if (fileData.length > 5242880) {
            return false;
        }
        this.fileStorage.put(identifier, fileData);
        return true;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    @Nullable
    public Friend getFriend(UUID uniqueId) {
        return this.uuid2Friend.get(uniqueId);
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    @Nullable
    public Chat getChat(UUID uniqueId) {
        Friend friend = getFriend(uniqueId);
        if (friend != null) {
            return friend.chat();
        }
        return null;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    @Nullable
    public IncomingFriendRequest getIncomingRequest(UUID uniqueId) {
        for (IncomingFriendRequest request : this.incomingRequests) {
            if (request.getUniqueId().equals(uniqueId)) {
                return request;
            }
        }
        return null;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    @Nullable
    public OutgoingFriendRequest getOutgoingRequest(UUID uniqueId) {
        for (OutgoingFriendRequest request : this.outgoingFriendRequests) {
            if (request.getUniqueId().equals(uniqueId)) {
                return request;
            }
        }
        return null;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public List<Friend> getFriends() {
        return this.unmodifiableFriends;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public Collection<Friend> getOnlineFriends() {
        List<Friend> onlineFriends = new ArrayList<>();
        for (Friend friend : getFriends()) {
            if (friend.isOnline()) {
                onlineFriends.add(friend);
            }
        }
        return onlineFriends;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public List<Chat> getChats() {
        List<Chat> chats = new ArrayList<>();
        for (Friend friend : this.friends) {
            chats.add(friend.chat());
        }
        return chats;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public List<IncomingFriendRequest> getIncomingRequests() {
        return this.unmodifiableIncomingRequests;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public List<OutgoingFriendRequest> getOutgoingRequests() {
        return this.unmodifiableOutgoingFriendRequests;
    }

    @Nullable
    public AdvertisementController getAdvertisementController() {
        return this.advertisementController;
    }

    @Nullable
    public IncentiveMessageListener getIncentiveListener() {
        return this.incentiveListener;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public User self() {
        return this.self;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public TokenStorage tokenStorage() {
        return this.tokenStorage;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public boolean isPremium() {
        return this.premium;
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public boolean isConnectionEstablished() {
        return this.connectionEstablished;
    }

    public void requestDashboardPin(Consumer<String> callback) {
        this.dashboardPinCallback = callback;
        this.labyConnect.sendPacket(new PacketAddonMessage("dashboard_pin", (JsonElement) new JsonObject()));
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    @Deprecated
    public void refreshOpenChat(UUID friendId) {
        ThreadSafe.ensureRenderThread();
        Laby.references().activityController().getActivityTree(activity -> {
            if ((activity instanceof LabyConnectDirectChatActivity) && ((LabyConnectDirectChatActivity) activity).friend().getUniqueId().equals(friendId)) {
                activity.reload();
            }
        });
    }

    @Deprecated
    public String getJwt() {
        TokenStorage.Token token = this.tokenStorage.getToken(TokenStorage.Purpose.JWT, this.self.getUniqueId());
        if (token == null) {
            return null;
        }
        return token.getToken();
    }

    @Deprecated
    public String getPin() {
        TokenStorage.Token token = this.tokenStorage.getToken(TokenStorage.Purpose.CLIENT, this.self.getUniqueId());
        if (token == null) {
            return null;
        }
        return token.getToken();
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void sendBroadcastPayload(@NotNull String key, @NotNull JsonElement payload) {
        sendBroadcastPayload(PacketUserTracker.EnumTrackingChannel.LIST, key, payload);
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void sendSurroundingBroadcastPayload(@NotNull String key, @NotNull JsonElement payload) {
        sendBroadcastPayload(PacketUserTracker.EnumTrackingChannel.ENTITIES, key, payload);
    }

    private void sendBroadcastPayload(PacketUserTracker.EnumTrackingChannel channel, String key, JsonElement payload) {
        Objects.requireNonNull(key, "Key cannot be null");
        Objects.requireNonNull(payload, "Payload cannot be null");
        PacketPlayBroadcastPayload packet = new PacketPlayBroadcastPayload(channel, key, GSON.toJson(payload));
        this.labyConnect.sendPacket(packet);
        Laby.fireEvent(new LabyConnectBroadcastEvent(this.labyConnect, LabyConnectBroadcastEvent.Action.SEND, this.self.getUniqueId(), key, payload));
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public void sendTextureUpdated(MojangTextureChangedResponse response) {
        response.filterActiveTextures();
        String json = GSON.toJson(response, MojangTextureChangedResponse.class);
        this.labyConnect.sendPacket(new PacketAddonMessage("textures_updated", json));
    }

    @Override // net.labymod.api.labyconnect.LabyConnectSession
    public byte[] getFile(UUID identifier) {
        return this.fileStorage.get(identifier);
    }

    public void dispose() {
        this.connectionEstablished = false;
    }

    private void resetAuthentication() {
        this.authenticated = false;
        this.premium = false;
        this.labyConnect.updateState(LabyConnectState.HELLO);
        this.labyConnect.fireEventSync(new LabyConnectRejectAuthenticationEvent(this.labyConnect));
    }
}
