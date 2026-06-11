package net.labymod.core.client.network.server.ping;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.serializer.gson.GsonComponentSerializer;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/ping/ServerPing.class */
public class ServerPing implements AutoCloseable {
    protected static final Logging LOGGER = Logging.create((Class<?>) ServerPing.class);
    private static final String SAMPLE_MEMBER_NAME = "sample";
    private static final String MAX_MEMBER_NAME = "max";
    private static final String ONLINE_MEMBER_NAME = "online";
    private static final String PLAYERS_MEMBER_NAME = "players";
    private static final String NAME_MEMBER_NAME = "name";
    private static final String ID_MEMBER_NAME = "id";
    private static final byte PACKET_HANDSHAKE = 0;
    private static final byte PACKET_STATUSREQUEST = 0;
    private static final byte PACKET_PING = 1;
    private static final int STATUS_HANDSHAKE = 1;
    private static final int MAX_STATUS_JSON_LENGTH = 1048576;
    private final String name;
    private final ServerAddress address;
    private final ServerAddress resolvedAddress;
    private final int clientVersion;
    private final Socket socket = new Socket();
    private final DataOutputStream outputStream;
    private final DataInputStream inputStream;

    public ServerPing(String name, ServerAddress address, ServerAddress connectAddress, int timeout, int clientVersion) throws IOException {
        this.name = name;
        this.address = address;
        this.resolvedAddress = address.resolve();
        this.clientVersion = clientVersion;
        ServerAddress target = connectAddress != null ? connectAddress : this.resolvedAddress;
        this.socket.connect(target.getAddress(), timeout);
        this.inputStream = new DataInputStream(this.socket.getInputStream());
        this.outputStream = new DataOutputStream(this.socket.getOutputStream());
    }

    public ServerInfo retrieveServerInfo() throws IOException {
        writeHandshake();
        try {
            JsonObject json = (JsonObject) GsonUtil.DEFAULT_GSON.fromJson(requestStatusJson(), JsonObject.class);
            int ping = getPing();
            return toServerInfo(json, ping);
        } catch (JsonSyntaxException exception) {
            LOGGER.warn("Server {} sent an invalid server status json", this.address, exception);
            return ServerInfo.failed(this.name, this.address, ServerInfo.Status.CANNOT_CONNECT);
        }
    }

    protected int getPing() {
        try {
            int ping = requestPing();
            if (ping < 0) {
                ping = 0;
            }
            return ping;
        } catch (Exception e) {
            return -1;
        }
    }

    private void writeHandshake() throws IOException {
        ByteArrayOutputStream payload = new ByteArrayOutputStream();
        try {
            ByteArrayOutputStream packet = new ByteArrayOutputStream();
            try {
                DataOutputStream handshake = new DataOutputStream(payload);
                try {
                    handshake.writeByte(0);
                    ServerPingUtil.writeVarInt(handshake, this.clientVersion);
                    String host = this.resolvedAddress.getHost();
                    ServerPingUtil.writeVarInt(handshake, host.length());
                    handshake.writeBytes(host);
                    handshake.writeShort(this.resolvedAddress.getPort());
                    ServerPingUtil.writeVarInt(handshake, 1);
                    byte[] payloadArray = payload.toByteArray();
                    ServerPingUtil.writeVarInt(packet, payloadArray.length);
                    packet.write(payloadArray);
                    this.outputStream.write(packet.toByteArray());
                    handshake.close();
                    packet.close();
                    payload.close();
                } catch (Throwable th) {
                    try {
                        handshake.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } finally {
            }
        } catch (Throwable th3) {
            try {
                payload.close();
            } catch (Throwable th4) {
                th3.addSuppressed(th4);
            }
            throw th3;
        }
    }

    private String requestStatusJson() throws IOException {
        ServerPingUtil.writeVarInt(this.outputStream, 1);
        this.outputStream.writeByte(0);
        ServerPingUtil.readVarInt(this.inputStream);
        int id = ServerPingUtil.readVarInt(this.inputStream);
        validateId(id, 0);
        int length = ServerPingUtil.readVarInt(this.inputStream);
        ServerPingUtil.io(length == -1, "Server prematurely ended stream.");
        ServerPingUtil.io(length == 0, "Server returned unexpected value.");
        ServerPingUtil.io(length < 0 || length > 1048576, "Server status payload exceeds allowed size (" + length + ").");
        byte[] data = new byte[length];
        this.inputStream.readFully(data);
        return new String(data, StandardCharsets.UTF_8);
    }

    private int requestPing() throws IOException {
        long startTime = TimeUtil.getMillis();
        ByteArrayOutputStream payload = new ByteArrayOutputStream();
        try {
            ByteArrayOutputStream packet = new ByteArrayOutputStream();
            try {
                DataOutputStream ping = new DataOutputStream(payload);
                try {
                    ping.writeByte(1);
                    ping.writeLong(Laby.labyAPI().minecraft().getRunningMillis());
                    byte[] payloadArray = payload.toByteArray();
                    ServerPingUtil.writeVarInt(packet, payloadArray.length);
                    packet.write(payloadArray);
                    this.outputStream.write(packet.toByteArray());
                    ping.close();
                    packet.close();
                    payload.close();
                    ServerPingUtil.readVarInt(this.inputStream);
                    int id = ServerPingUtil.readVarInt(this.inputStream);
                    validateId(id, 1);
                    return (int) (TimeUtil.getMillis() - startTime);
                } catch (Throwable th) {
                    try {
                        ping.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } finally {
            }
        } catch (Throwable th3) {
            try {
                payload.close();
            } catch (Throwable th4) {
                th3.addSuppressed(th4);
            }
            throw th3;
        }
    }

    private ServerInfo toServerInfo(JsonObject object, int ping) {
        return parseServerStatus(this.name, this.address, this.clientVersion, object, ping);
    }

    public static ServerInfo parseServerStatus(String name, ServerAddress address, int clientVersion, JsonObject object, int ping) {
        try {
            ServerInfo.Players players = parsePlayers(object);
            JsonObject version = object.getAsJsonObject("version");
            int serverVersion = version.get("protocol").getAsInt();
            boolean versionValid = serverVersion == clientVersion;
            String favicon = object.has("favicon") ? object.get("favicon").getAsString() : null;
            return ServerInfo.success(name, address, ServerInfo.resourceLocationFromBase64(favicon), (favicon == null || !favicon.startsWith(ServerInfo.ICON_PREFIX)) ? null : favicon, GsonComponentSerializer.gson().deserializeFromTree(object.get("description")), players.getOnline(), players.getMax(), players.getSamples(), ping, version.get(NAME_MEMBER_NAME).getAsString(), serverVersion, versionValid);
        } catch (NullPointerException exception) {
            LOGGER.error("Failed to parse server status for {}", address, exception);
            return ServerInfo.failed(name, address, ServerInfo.Status.CANNOT_CONNECT);
        }
    }

    private static ServerInfo.Players parsePlayers(JsonObject object) {
        if (!object.has(PLAYERS_MEMBER_NAME)) {
            return ServerInfo.Players.EMPTY;
        }
        JsonElement playersElement = object.get(PLAYERS_MEMBER_NAME);
        if (!playersElement.isJsonObject()) {
            return ServerInfo.Players.EMPTY;
        }
        JsonObject players = playersElement.getAsJsonObject();
        int online = getInt(players, ONLINE_MEMBER_NAME, 0);
        int max = getInt(players, MAX_MEMBER_NAME, 0);
        if (!players.has(SAMPLE_MEMBER_NAME)) {
            return new ServerInfo.Players(online, max);
        }
        JsonElement sampleElement = players.get(SAMPLE_MEMBER_NAME);
        if (!sampleElement.isJsonArray()) {
            return new ServerInfo.Players(online, max);
        }
        JsonArray sampleArray = sampleElement.getAsJsonArray();
        int size = sampleArray.size();
        List<ServerInfo.Player> samples = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            JsonObject playerObject = sampleArray.get(i).getAsJsonObject();
            String id = getString(playerObject, ID_MEMBER_NAME);
            if (id == null) {
                LOGGER.error("An {} of a player sample is null (Index: {})", ID_MEMBER_NAME, Integer.valueOf(i));
            } else {
                String name = getString(playerObject, NAME_MEMBER_NAME);
                if (name == null) {
                    LOGGER.error("A {} of a player sample is null (Index: {})", NAME_MEMBER_NAME, Integer.valueOf(i));
                } else {
                    samples.add(new ServerInfo.Player(UUID.fromString(id), name));
                }
            }
        }
        return new ServerInfo.Players(online, max, (ServerInfo.Player[]) samples.toArray(new ServerInfo.Player[0]));
    }

    private void validateId(int id, int expected) throws IOException {
        ServerPingUtil.io(id == -1, "Server prematurely ended stream");
        ServerPingUtil.io(id != expected, "Server returned invalid packet");
    }

    @Override // java.lang.AutoCloseable
    public void close() throws IOException {
        this.outputStream.close();
        this.inputStream.close();
        this.socket.close();
    }

    private static int getInt(JsonObject object, String memberName, int defaultValue) {
        if (object.has(memberName) && object.get(memberName).isJsonPrimitive()) {
            return object.get(memberName).getAsInt();
        }
        return defaultValue;
    }

    @Nullable
    private static String getString(JsonObject object, String memberName) {
        if (object.has(memberName) && object.get(memberName).isJsonPrimitive()) {
            return object.get(memberName).getAsString();
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public DataInputStream getInputStream() {
        return this.inputStream;
    }

    public DataOutputStream getOutputStream() {
        return this.outputStream;
    }

    public ServerAddress getAddress() {
        return this.address;
    }
}
