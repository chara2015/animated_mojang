package net.labymod.core.client.network.server;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerAddressResolver;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.network.dns.DNSLogger;
import net.labymod.core.client.network.server.connect.blocklist.BlockedServers;
import net.labymod.core.util.HardwareUtils;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.SRVRecord;
import org.xbill.DNS.logger.LogUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/DefaultAbstractServerAddressResolver.class */
public abstract class DefaultAbstractServerAddressResolver implements ServerAddressResolver {
    private static final String SRV_PREFIX = "_minecraft._tcp.";
    private static final long CACHE_PERIOD = 300000;
    private final Map<ServerAddress, ResolvedAddress> resolvedAddresses = new HashMap();
    private byte[] interfaceAddress;

    static {
        LogUtil.setLoggerFactory(DNSLogger::new);
    }

    protected DefaultAbstractServerAddressResolver() {
        Laby.labyAPI().eventBus().registerListener(this);
        try {
            this.interfaceAddress = HardwareUtils.getMainNetworkInterfaceAddress();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (this.interfaceAddress == null) {
            this.interfaceAddress = new byte[6];
        }
    }

    @Override // net.labymod.api.client.network.server.ServerAddressResolver
    public ServerAddress resolve(ServerAddress address) {
        ResolvedAddress resolvedAddress = this.resolvedAddresses.get(address);
        if (resolvedAddress != null) {
            if (resolvedAddress.prioritized && resolvedAddress.isExpired()) {
                Task.builder(() -> {
                    resolve(address, resolvedAddress, false);
                }).build().execute();
            }
            resolvedAddress.lastResolvedAt = TimeUtil.getMillis();
            return resolvedAddress.address;
        }
        ServerAddress resolve = resolve(address, null, false);
        return resolve == null ? address : resolve;
    }

    @Override // net.labymod.api.client.network.server.ServerAddressResolver
    public void register(ServerAddress address) {
        ResolvedAddress resolvedAddress = this.resolvedAddresses.get(address);
        if (resolvedAddress != null) {
            resolvedAddress.prioritized = true;
        } else {
            resolve(address, null, true);
        }
    }

    @Subscribe
    public void onTick(GameTickEvent event) {
        if (event.phase() == Phase.PRE || this.resolvedAddresses.isEmpty()) {
            return;
        }
        long millis = TimeUtil.getMillis();
        CollectionHelper.removeIf(this.resolvedAddresses.values(), resolvedAddress -> {
            return !resolvedAddress.prioritized && resolvedAddress.isExpired(millis);
        });
    }

    private boolean isIPv4(String input) {
        int length = input.length();
        int octet = 0;
        int octetCount = 0;
        char[] chars = input.toCharArray();
        for (int i = 0; i < length; i++) {
            char c = chars[i];
            if (c == '.') {
                if (octet < 0) {
                    return false;
                }
                octetCount++;
                if (octetCount > 3) {
                    return false;
                }
                octet = 0;
            } else if (c >= '0' && c <= '9') {
                octet = (octet * 10) + (c - '0');
                if (octet > 255) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return octet >= 0 && octetCount == 3;
    }

    public ServerAddress resolveInternal(ServerAddress address) {
        return address.getPort() == 25565 ? getServerAddress(address.getHost()) : address;
    }

    private ServerAddress resolve(ServerAddress address, ResolvedAddress resolved, boolean prioritize) {
        ServerAddress serverAddress;
        if ((resolved == null && isIPv4(address.getHost())) || (serverAddress = resolveInternal(address)) == null) {
            return null;
        }
        if (resolved != null) {
            resolved.address = serverAddress;
            return serverAddress;
        }
        ResolvedAddress value = new ResolvedAddress(serverAddress);
        value.prioritized = prioritize;
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            if (this.resolvedAddresses.containsKey(address)) {
                return;
            }
            this.resolvedAddresses.put(address, value);
        });
        return serverAddress;
    }

    public byte[] getInterfaceAddress() {
        return this.interfaceAddress;
    }

    public static ServerAddress getServerAddress(String address) {
        try {
            if (!BlockedServers.INSTANCE.test("_minecraft._tcp." + address)) {
                ServerAddress resolved = resolveSrv(address);
                if (resolved != null) {
                    return resolved;
                }
            }
        } catch (Exception e) {
        }
        return new ServerAddress(address, 25565);
    }

    public static ServerAddress resolveSrv(String host) {
        try {
            SRVRecord[] sRVRecordArrRun = new Lookup("_minecraft._tcp." + host, 33).run();
            if (sRVRecordArrRun != null && sRVRecordArrRun.length > 0) {
                SRVRecord record = sRVRecordArrRun[0];
                return new ServerAddress(record.getTarget().toString(), record.getPort());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/DefaultAbstractServerAddressResolver$ResolvedAddress.class */
    private static class ResolvedAddress {
        private ServerAddress address;
        private long lastResolvedAt = TimeUtil.getMillis();
        private boolean prioritized;

        private ResolvedAddress(ServerAddress address) {
            this.address = address;
        }

        private boolean isExpired() {
            return isExpired(TimeUtil.getMillis());
        }

        private boolean isExpired(long millis) {
            return millis - this.lastResolvedAt > DefaultAbstractServerAddressResolver.CACHE_PERIOD;
        }
    }
}
