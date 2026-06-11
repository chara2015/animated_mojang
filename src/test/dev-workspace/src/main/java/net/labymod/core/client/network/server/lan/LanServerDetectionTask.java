package net.labymod.core.client.network.server.lan;

import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.lan.LanServerCallback;
import net.labymod.api.server.LocalWorld;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/lan/LanServerDetectionTask.class */
public class LanServerDetectionTask implements Runnable {
    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(10);
    private static final int MAX_SERVERS = 256;
    private static final int MAX_MOTD_LENGTH = 64;
    private MulticastSocket socket;
    private LanServerCallback callback;
    private final Map<ConnectableServerData, Long> servers = new ConcurrentHashMap();
    private final byte[] buffer = new byte[1024];

    @Override // java.lang.Runnable
    public void run() {
        ClientPlayer clientPlayer;
        DatagramPacket packet = new DatagramPacket(this.buffer, this.buffer.length);
        try {
            this.socket.receive(packet);
            String serverInfoString = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
            String motd = parseMotd(serverInfoString);
            String rawPort = parsePort(serverInfoString);
            if (motd == null || rawPort == null) {
                return;
            }
            String hostAddress = packet.getAddress().getHostAddress();
            try {
                int port = Integer.parseInt(rawPort);
                if (port < 1 || port > 65535) {
                    return;
                }
                LocalWorld localServer = Laby.references().integratedServer().getLocalWorld();
                if (localServer != null && localServer.port() == port && (clientPlayer = Laby.labyAPI().minecraft().getClientPlayer()) != null && motd.contains(clientPlayer.getName())) {
                    return;
                }
                ConnectableServerData serverInfo = ConnectableServerData.builder().name(motd).address(hostAddress + ":" + port).lan(true).build();
                boolean registered = this.servers.containsKey(serverInfo);
                if (!registered && this.servers.size() >= 256) {
                    return;
                }
                this.servers.put(serverInfo, Long.valueOf(TimeUtil.getMillis() + TIMEOUT));
                if (!registered) {
                    this.callback.onServerAdd(serverInfo);
                }
            } catch (NumberFormatException e) {
            }
        } catch (Exception e2) {
        }
    }

    public void handleRemoval() {
        try {
            this.servers.entrySet().removeIf(entry -> {
                if (((Long) entry.getValue()).longValue() <= TimeUtil.getMillis()) {
                    this.callback.onServerRemove((ConnectableServerData) entry.getKey());
                    return true;
                }
                return false;
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private String parseMotd(String s) {
        int motdStart = s.indexOf("[MOTD]");
        int motdEnd = s.indexOf("[/MOTD]");
        if (motdStart < 0 || motdEnd < 0 || motdEnd <= motdStart + "[MOTD]".length()) {
            return null;
        }
        String motd = s.substring(motdStart + "[MOTD]".length(), motdEnd);
        StringBuilder sanitized = new StringBuilder(Math.min(motd.length(), 64));
        for (int i = 0; i < motd.length() && sanitized.length() < 64; i++) {
            char c = motd.charAt(i);
            if (c >= ' ' && c != 127) {
                sanitized.append(c);
            }
        }
        return sanitized.toString();
    }

    private String parsePort(String s) {
        int portStart = s.indexOf("[AD]");
        int portEnd = s.indexOf("[/AD]");
        if (portStart < 0 || portEnd < 0 || portEnd <= portStart + "[AD]".length()) {
            return null;
        }
        return s.substring(portStart + "[AD]".length(), portEnd);
    }

    public void setSocket(MulticastSocket socket) {
        this.socket = socket;
    }

    public void setCallback(@NotNull LanServerCallback callback) {
        this.callback = callback;
    }

    public void reset() {
        this.servers.clear();
    }
}
