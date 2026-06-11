package net.labymod.api.client.network.server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.util.HexUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ServerInfo.class */
public class ServerInfo extends ConnectableServerData {
    public static final String ICON_PREFIX = "data:image/png;base64,";
    private static final ServerPinger SERVER_PINGER = Laby.references().serverPinger();
    private static final TextureRepository TEXTURE_REPOSITORY = Laby.references().textureRepository();
    private final CompletableResourceLocation icon;
    private final String rawIcon;
    private final Component description;
    private final int playerCount;
    private final int maxPlayers;
    private final Player[] onlinePlayers;
    private final int ping;
    private final String protocolInfo;
    private final int protocolVersion;
    private final Status status;
    protected Status tempStatus;
    private boolean proxied;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ServerInfo$Status.class */
    public enum Status {
        SUCCESS,
        LOADING,
        CANNOT_CONNECT,
        UNKNOWN_HOST,
        WRONG_VERSION,
        PROXY_UNAVAILABLE
    }

    private ServerInfo(String name, ServerAddress serverAddress, CompletableResourceLocation icon, String rawIcon, Component description, int playerCount, int maxPlayers, Player[] onlinePlayers, int ping, String protocolInfo, int protocolVersion, Status status, ServerType type) {
        super(name, serverAddress, type, null);
        this.icon = icon;
        this.rawIcon = rawIcon;
        this.description = description;
        this.playerCount = playerCount;
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
        this.ping = ping;
        this.protocolInfo = protocolInfo;
        this.protocolVersion = protocolVersion;
        this.status = status;
    }

    public static Builder infoBuilder() {
        return new Builder();
    }

    public static ServerInfo success(String name, ServerAddress address, CompletableResourceLocation icon, String rawIcon, Component description, int playerCount, int maxPlayers, Player[] onlinePlayers, int ping, String protocolInfo, int protocolVersion, boolean versionValid) {
        return new ServerInfo(name, address, icon, rawIcon, description, playerCount, maxPlayers, onlinePlayers, ping, protocolInfo, protocolVersion, versionValid ? Status.SUCCESS : Status.WRONG_VERSION, ServerType.THIRD_PARTY);
    }

    public static ServerInfo loading(String name, ServerAddress address) {
        return failed(name, address, Status.LOADING);
    }

    public static ServerInfo failed(String name, ServerAddress address, Status status) {
        return new ServerInfo(name, address, new CompletableResourceLocation(SERVER_PINGER.getDefaultServerIcon()), null, null, -1, -1, null, -1, null, -1, status, ServerType.THIRD_PARTY);
    }

    public CompletableResourceLocation getIcon() {
        return this.icon;
    }

    public String getRawIcon() {
        return this.rawIcon;
    }

    public Component getDescription() {
        return this.description;
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public Player[] getOnlinePlayers() {
        return this.onlinePlayers;
    }

    public int getPing() {
        return this.ping;
    }

    public String getProtocolInfo() {
        return this.protocolInfo;
    }

    public int getProtocolVersion() {
        return this.protocolVersion;
    }

    public Status getStatus() {
        return this.tempStatus != null ? this.tempStatus : this.status;
    }

    public boolean isProxied() {
        return this.proxied;
    }

    public void setProxied(boolean proxied) {
        this.proxied = proxied;
    }

    public static CompletableResourceLocation resourceLocationFromBase64(String base64Icon) {
        if (base64Icon == null || !base64Icon.startsWith(ICON_PREFIX)) {
            return new CompletableResourceLocation(Textures.DEFAULT_SERVER_ICON);
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(base64Icon.getBytes(StandardCharsets.UTF_8));
            return TEXTURE_REPOSITORY.loadCacheResourceAsync("labymod", new String(HexUtil.encodeHex(md5Bytes)), base64Icon, Textures.DEFAULT_SERVER_ICON);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new CompletableResourceLocation(Textures.DEFAULT_SERVER_ICON);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ServerInfo$Players.class */
    public static class Players {
        public static final Players EMPTY = new Players(0, 0, new Player[0]);
        private final int online;
        private final int max;
        private final Player[] samples;

        public Players(int online, int max) {
            this(online, max, new Player[0]);
        }

        public Players(int online, int max, Player[] samples) {
            this.online = online;
            this.max = max;
            this.samples = samples;
        }

        public int getOnline() {
            return this.online;
        }

        public int getMax() {
            return this.max;
        }

        public Player[] getSamples() {
            return this.samples;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ServerInfo$Player.class */
    public static class Player {
        private final UUID uniqueId;
        private final String name;

        public Player(UUID uniqueId, String name) {
            this.uniqueId = uniqueId;
            this.name = name;
        }

        public UUID getUniqueId() {
            return this.uniqueId;
        }

        public String getName() {
            return this.name;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ServerInfo$Builder.class */
    public static class Builder {
        private String name;
        private ServerAddress serverAddress;
        private String icon;
        private Component description;
        private int playerCount;
        private int maxPlayers;
        private Player[] onlinePlayers;
        private int ping;
        private String protocolInfo;
        private int protocolVersion;
        private Status status;
        private ServerType type = ServerType.THIRD_PARTY;

        protected Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder serverAddress(ServerAddress serverAddress) {
            this.serverAddress = serverAddress;
            return this;
        }

        public Builder serverAddress(String serverAddress) {
            return serverAddress(ServerAddress.parse(serverAddress));
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder lan(boolean lan) {
            this.type = lan ? ServerType.LAN : ServerType.THIRD_PARTY;
            return this;
        }

        public Builder type(ServerType type) {
            this.type = type;
            return this;
        }

        public Builder description(Component description) {
            this.description = description;
            return this;
        }

        public Builder playerCount(int playerCount) {
            this.playerCount = playerCount;
            return this;
        }

        public Builder maxPlayers(int maxPlayers) {
            this.maxPlayers = maxPlayers;
            return this;
        }

        public Builder onlinePlayers(Player[] onlinePlayers) {
            this.onlinePlayers = onlinePlayers;
            return this;
        }

        public Builder ping(int ping) {
            this.ping = ping;
            return this;
        }

        public Builder protocolInfo(String protocolInfo) {
            this.protocolInfo = protocolInfo;
            return this;
        }

        public Builder protocolVersion(int protocolVersion) {
            this.protocolVersion = protocolVersion;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public ServerInfo build() {
            return new ServerInfo(this.name, this.serverAddress, ServerInfo.resourceLocationFromBase64(this.icon), this.icon, this.description, this.playerCount, this.maxPlayers, this.onlinePlayers, this.ping, this.protocolInfo, this.protocolVersion, this.status, this.type);
        }
    }
}
