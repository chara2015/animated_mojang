package net.labymod.api.client.network.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/PingProtectionService.class */
public interface PingProtectionService {
    ServerInfo ping(String str, ServerAddress serverAddress, int i) throws IOException;

    boolean supports(ServerAddress serverAddress);

    default boolean isLocalAddress(String host) {
        try {
            InetAddress inetAddress = InetAddress.getByName(host);
            if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && !inetAddress.isSiteLocalAddress()) {
                if (!inetAddress.isAnyLocalAddress()) {
                    return false;
                }
            }
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
