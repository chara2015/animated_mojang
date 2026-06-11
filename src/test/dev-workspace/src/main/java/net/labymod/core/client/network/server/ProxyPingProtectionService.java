package net.labymod.core.client.network.server;

import java.io.EOFException;
import java.io.IOException;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.PingProtectionService;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.core.client.network.server.ping.LegacyServerPing;
import net.labymod.core.client.network.server.ping.ServerPing;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/ProxyPingProtectionService.class */
public class ProxyPingProtectionService implements PingProtectionService {
    private static final boolean DEBUG = false;
    private final ServerAddress proxyAddress = new ServerAddress("service.labymod.net", 25580);

    @Override // net.labymod.api.client.network.server.PingProtectionService
    public ServerInfo ping(String name, ServerAddress address, int timeout) throws IOException {
        int clientVersion = Laby.labyAPI().minecraft().getProtocolVersion();
        try {
            ServerPing ping = new ServerPing(name, address, this.proxyAddress, timeout, clientVersion);
            try {
                ServerInfo serverInfoRetrieveServerInfo = ping.retrieveServerInfo();
                ping.close();
                return serverInfoRetrieveServerInfo;
            } finally {
            }
        } catch (EOFException e) {
            LegacyServerPing legacyPing = new LegacyServerPing(name, address, this.proxyAddress, timeout, clientVersion);
            try {
                ServerInfo serverInfoRetrieveServerInfo2 = legacyPing.retrieveServerInfo();
                legacyPing.close();
                return serverInfoRetrieveServerInfo2;
            } catch (Throwable th) {
                try {
                    legacyPing.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    }

    @Override // net.labymod.api.client.network.server.PingProtectionService
    public boolean supports(ServerAddress address) {
        return !isLocalAddress(address.getHost());
    }
}
