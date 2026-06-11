package net.labymod.api.client.network.server;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ServerData.class */
public abstract class ServerData {
    private final ServerType type;
    protected String name;
    protected ServerAddress serverAddress;
    protected ServerAddress actualAddress;

    protected ServerData(@Nullable String name, @NotNull ServerAddress serverAddress, ServerType type) {
        Objects.requireNonNull(serverAddress, "Server address cannot be null!");
        this.name = name;
        this.serverAddress = serverAddress;
        this.type = type;
    }

    public String getName() {
        return this.name == null ? this.serverAddress.getHost() : this.name;
    }

    public ServerAddress address() {
        return this.serverAddress;
    }

    public ServerAddress actualAddress() {
        if (this.actualAddress == null) {
            this.actualAddress = this.serverAddress.resolve();
        }
        return this.actualAddress;
    }

    public boolean isLan() {
        return this.type == ServerType.LAN;
    }

    public boolean isRealm() {
        return this.type == ServerType.REALM;
    }

    public ServerType type() {
        return this.type;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServerData that = (ServerData) o;
        return this.type == that.type && Objects.equals(this.name, that.name) && this.serverAddress.equals(that.serverAddress);
    }

    public int hashCode() {
        int result = this.type != null ? this.type.hashCode() : 0;
        return (31 * ((31 * result) + (this.name != null ? this.name.hashCode() : 0))) + (this.serverAddress != null ? this.serverAddress.hashCode() : 0);
    }
}
