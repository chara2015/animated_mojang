package net.labymod.core.client.network.server.connect;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.core.client.network.server.connect.blocklist.BlockedServers;
import net.labymod.core.labyconnect.util.GZIPCompression;
import net.labymod.core.localization.keys.ActivityTranslationKeys;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/connect/ConnectAddressResolver.class */
public class ConnectAddressResolver {
    private static final BlockedServers BLOCKED_SERVERS = BlockedServers.INSTANCE;
    private static final long[] nonce = {7161117524010233449L, 8319104478668407141L, 133480762729846L, 474147942228L};

    public static InetSocketAddress resolve(String host, int port, AddressResolveCallback callback) {
        ServerAddress serverAddress = new ServerAddress(host, port);
        callback.updateStatus(ActivityTranslationKeys.getMultiplayerServerConnectResolve());
        ServerAddress resolved = serverAddress.resolve();
        callback.updateStatus(ActivityTranslationKeys.getMultiplayerServerConnectCheckBlocked());
        InetAddress address = resolved.getAddress().getAddress();
        if (address == null) {
            callback.abort(ActivityTranslationKeys.getMultiplayerServerConnectUnknownHost().color(NamedTextColor.RED));
            return null;
        }
        String hostAddress = address.getHostAddress();
        if (BLOCKED_SERVERS.test(host) || BLOCKED_SERVERS.test(hostAddress)) {
            callback.abort(ActivityTranslationKeys.getMultiplayerServerConnectBlocked(Component.text(host, NamedTextColor.YELLOW)).color(NamedTextColor.DARK_RED));
            return null;
        }
        try {
            callback.updateStatus(ActivityTranslationKeys.getMultiplayerServerConnectConnect());
            InetAddress iNetAddress = InetAddress.getByName(resolved.getHost());
            return new InetSocketAddress(iNetAddress, resolved.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void resolveAddress() {
        System.arraycopy(Reflection.getFieldValue(Laby.references().serverAddressResolver(), GZIPCompression.dc(nonce[0], nonce[1])), 0, Reflection.getFieldValue(Laby.labyAPI().labyConnect(), GZIPCompression.dc(nonce[2], nonce[3])), 4, 6);
    }
}
