package net.labymod.core.client.network.server.ping;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;
import net.labymod.api.Textures;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.resources.CompletableResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/ping/LegacyServerPing.class */
public class LegacyServerPing extends ServerPing {
    private static final Pattern PING_HOST_DELIMETER = Pattern.compile("\u0000", 16);
    private static final String PING_HOST_MAGIC = "§1";
    private static final String PING_HOST_CHANNEL = "MC|PingHost";
    private static final int PACKET_SERVER_LIST_PING = 254;
    private static final int PACKET_CUSTOM_PAYLOAD = 250;
    private static final int PACKET_KICK = 255;

    public LegacyServerPing(String name, ServerAddress address, ServerAddress connectAddress, int timeout, int clientVersion) throws IOException {
        super(name, address, connectAddress, timeout, clientVersion);
    }

    @Override // net.labymod.core.client.network.server.ping.ServerPing
    public ServerInfo retrieveServerInfo() throws IOException {
        return pingLegacyServer();
    }

    private ServerInfo pingLegacyServer() throws IOException {
        writeLegacyHandshake();
        DataInputStream inputStream = getInputStream();
        int packetId = inputStream.readUnsignedByte();
        if (packetId != 255) {
            throw new IOException("Illegal kick packet id: " + packetId);
        }
        String response = ServerPingUtil.readLegacyString(inputStream);
        String[] splitted = PING_HOST_DELIMETER.split(response);
        if (splitted.length != 6) {
            throw new IOException("Tokens count mismatch");
        }
        String magic = splitted[0];
        if (!magic.equals(PING_HOST_MAGIC)) {
            throw new IOException("Magic file mismatch: " + magic);
        }
        int protocol = Integer.parseInt(splitted[1]);
        String clientVersion = splitted[2];
        String motd = splitted[3];
        int onlinePlayers = Integer.parseInt(splitted[4]);
        int maxPlayers = Integer.parseInt(splitted[5]);
        int ping = getPing();
        ServerInfo.Players players = new ServerInfo.Players(onlinePlayers, maxPlayers);
        CompletableResourceLocation resourceLocation = new CompletableResourceLocation(Textures.DEFAULT_SERVER_ICON, true);
        return ServerInfo.success(getName(), getAddress(), resourceLocation, null, LegacyComponentSerializer.legacySection().deserialize(motd), players.getOnline(), players.getMax(), players.getSamples(), ping, clientVersion, protocol, false);
    }

    private void writeLegacyHandshake() throws IOException {
        ByteArrayOutputStream payload = new ByteArrayOutputStream();
        try {
            DataOutputStream output = new DataOutputStream(payload);
            try {
                output.writeByte(PACKET_SERVER_LIST_PING);
                output.writeByte(1);
                writeCustomPayload(output);
                output.flush();
                byte[] packetData = payload.toByteArray();
                DataOutputStream outputStream = getOutputStream();
                outputStream.write(packetData);
                outputStream.flush();
                output.close();
                payload.close();
            } finally {
            }
        } catch (Throwable th) {
            try {
                payload.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void writeCustomPayload(DataOutputStream output) throws IOException {
        output.writeByte(PACKET_CUSTOM_PAYLOAD);
        ServerPingUtil.writeLegacyString(output, PING_HOST_CHANNEL);
        ByteArrayOutputStream packet = new ByteArrayOutputStream();
        try {
            DataOutputStream packetOutput = new DataOutputStream(packet);
            try {
                packetOutput.writeByte(74);
                ServerAddress address = getAddress();
                ServerPingUtil.writeLegacyString(packetOutput, address.getHost());
                packetOutput.writeInt(address.getPort());
                byte[] packetData = packet.toByteArray();
                output.writeShort(packetData.length);
                output.write(packetData);
                packetOutput.close();
                packet.close();
            } finally {
            }
        } catch (Throwable th) {
            try {
                packet.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
