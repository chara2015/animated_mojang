package net.labymod.api.client.network.server;

import java.net.InetSocketAddress;
import java.util.Locale;
import net.labymod.api.Laby;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ServerAddress.class */
public class ServerAddress {
    private static final ServerAddressResolver RESOLVER = Laby.references().serverAddressResolver();
    private final String host;
    private final int port;
    private final boolean resolved;
    private InetSocketAddress address;

    public ServerAddress(String host, int port, boolean resolved) {
        this.host = host.endsWith(".") ? host.substring(0, host.length() - 1) : host;
        this.port = port;
        this.resolved = resolved;
    }

    public ServerAddress(String host, int port) {
        this(host, port, false);
    }

    public static ServerAddress parse(String raw) {
        if (raw == null) {
            return null;
        }
        String[] parts = raw.split(":");
        if (parts.length == 0) {
            return new ServerAddress(raw, 25565);
        }
        if (parts.length == 1) {
            return new ServerAddress(parts[0], 25565);
        }
        String host = null;
        String rawPort = null;
        if (parts.length > 2) {
            int start = raw.indexOf("[");
            if (start == 0) {
                int end = raw.indexOf("]");
                if (end > 0) {
                    host = raw.substring(0, end + 1);
                    if (raw.length() > end + 2) {
                        rawPort = raw.substring(end + 2);
                    }
                }
            } else {
                host = raw;
            }
        } else {
            host = parts[0];
            rawPort = parts[1];
        }
        int port = 25565;
        if (rawPort != null) {
            try {
                port = Math.min(65565, Math.max(1, Integer.parseInt(rawPort)));
            } catch (Exception e) {
            }
        }
        if (host == null) {
            host = raw;
        }
        return new ServerAddress(host, port);
    }

    public static ServerAddress resolve(String raw) {
        return RESOLVER.resolve(parse(raw));
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public ServerAddress resolve() {
        if (this.resolved) {
            return this;
        }
        return RESOLVER.resolve(this);
    }

    public InetSocketAddress getAddress() {
        if (this.address == null) {
            this.address = new InetSocketAddress(this.host, this.port);
        }
        return this.address;
    }

    public boolean matches(ServerAddress serverAddress) {
        return matches(serverAddress, false);
    }

    public boolean matches(ServerAddress serverAddress, boolean ignoreSubDomains) {
        if (serverAddress == null) {
            return false;
        }
        return matches(serverAddress.getHost(), serverAddress.getPort(), ignoreSubDomains);
    }

    public boolean matches(String address, int port, boolean ignoreSubDomains) {
        if (address == null) {
            return false;
        }
        String thisAddress = this.host.toLowerCase(Locale.ROOT);
        String thatAddress = address.toLowerCase(Locale.ROOT);
        if (ignoreSubDomains) {
            String[] thisSplit = thisAddress.split("\\.");
            if (thisSplit.length > 2) {
                thisAddress = thisSplit[thisSplit.length - 2] + "." + thisSplit[thisSplit.length - 1];
            }
            String[] thatSplit = thatAddress.split("\\.");
            if (thatSplit.length > 2) {
                thatAddress = thatSplit[thatSplit.length - 2] + "." + thatSplit[thatSplit.length - 1];
            }
        }
        return (thisAddress + ":" + this.port).equals(thatAddress + ":" + port);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServerAddress address = (ServerAddress) o;
        return this.port == address.port && this.host.equalsIgnoreCase(address.host);
    }

    public int hashCode() {
        int result = this.host != null ? this.host.hashCode() : 0;
        return (31 * result) + this.port;
    }

    public String toString() {
        if (this.port == 25565) {
            return this.host;
        }
        return this.host + ":" + this.port;
    }
}
