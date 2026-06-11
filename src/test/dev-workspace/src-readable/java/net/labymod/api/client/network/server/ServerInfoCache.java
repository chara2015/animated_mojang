package net.labymod.api.client.network.server;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.resources.CompletableResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ServerInfoCache.class */
public class ServerInfoCache<T extends ServerData> {
    private static final ServerPinger SERVER_PINGER = Laby.references().serverPinger();
    protected final T serverData;
    private ServerAddress lastServerAddress;
    private ServerInfo serverInfo;
    private final Collection<Consumer<ServerInfoCache<T>>> callbacks = new ArrayList();
    private int timeout = 2500;
    private boolean protectIp = true;

    public ServerInfoCache(T serverData, Consumer<ServerInfoCache<T>> callback) {
        this.serverData = serverData;
        refresh(false);
        if (callback != null) {
            this.callbacks.add(callback);
            callback.accept(this);
        }
    }

    public String getName() {
        return this.serverData.getName();
    }

    public ServerAddress serverAddress() {
        return this.serverData.address();
    }

    public ServerInfo serverInfo() {
        return this.serverInfo;
    }

    public void setCallback(Consumer<ServerInfoCache<T>> callback) {
        this.callbacks.clear();
        this.callbacks.add(callback);
        callback.accept(this);
    }

    public void addCallback(Consumer<ServerInfoCache<T>> callback) {
        this.callbacks.add(callback);
    }

    public boolean matches(ServerInfoCache<T> serverInfoCache) {
        return matches(serverInfoCache.serverData);
    }

    public boolean matches(T serverData) {
        return this.serverData.equals(serverData);
    }

    public void refresh(boolean callback) {
        ServerAddress address = this.serverData.address();
        if (address == null) {
            return;
        }
        this.serverInfo = ServerInfo.loading(this.serverData.getName(), address);
        this.lastServerAddress = address;
        if (callback && !this.callbacks.isEmpty()) {
            for (Consumer<ServerInfoCache<T>> consumer : this.callbacks) {
                consumer.accept(this);
            }
        }
    }

    public void setServerInfo(ServerInfo serverInfo) {
        CompletableResourceLocation icon;
        CompletableResourceLocation newIcon = serverInfo.getIcon();
        if (this.serverInfo == null) {
            icon = null;
        } else {
            icon = this.serverInfo.getIcon();
        }
        CompletableResourceLocation oldIcon = icon;
        if (oldIcon != null && oldIcon.isLoading() && oldIcon != newIcon) {
            newIcon.addCompletableListener(() -> {
                oldIcon.executeCompletableListeners(newIcon.getCompleted());
            });
        }
        this.serverInfo = serverInfo;
        if (!this.callbacks.isEmpty()) {
            for (Consumer<ServerInfoCache<T>> consumer : this.callbacks) {
                consumer.accept(this);
            }
        }
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setProtectIp(boolean protectIp) {
        this.protectIp = protectIp;
    }

    public void update() {
        String name = getName();
        ServerAddress address = serverAddress();
        if (!address.equals(this.lastServerAddress)) {
            refresh(false);
        }
        SERVER_PINGER.pingServerAsync(name, address, this.timeout, this.protectIp).exceptionally(throwable -> {
            return ServerInfo.failed(name, address, throwable instanceof UnknownHostException ? ServerInfo.Status.UNKNOWN_HOST : ServerInfo.Status.CANNOT_CONNECT);
        }).thenAccept(serverInfo -> {
            Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                setServerInfo(serverInfo);
            });
        });
    }

    public T serverData() {
        return this.serverData;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServerInfoCache<?> that = (ServerInfoCache) o;
        return this.serverData.equals(that.serverData);
    }

    public int hashCode() {
        if (this.serverData != null) {
            return this.serverData.hashCode();
        }
        return 0;
    }
}
