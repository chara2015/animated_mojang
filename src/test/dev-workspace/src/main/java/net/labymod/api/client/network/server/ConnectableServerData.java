package net.labymod.api.client.network.server;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.network.server.JoinServerRequest;
import net.labymod.api.client.network.server.storage.ServerResourcePackStatus;
import net.labymod.api.util.concurrent.task.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ConnectableServerData.class */
public class ConnectableServerData extends ServerData {
    protected ServerResourcePackStatus resourcePackStatus;

    protected ConnectableServerData(@Nullable String name, @NotNull ServerAddress serverAddress, ServerType type, @Nullable ServerResourcePackStatus resourcePackStatus) {
        super(name, serverAddress, type);
        this.resourcePackStatus = resourcePackStatus;
        if (this.resourcePackStatus == null) {
            this.resourcePackStatus = ServerResourcePackStatus.PROMPT;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ConnectableServerData from(@NotNull ServerData serverData) {
        if (serverData instanceof ConnectableServerData) {
            return (ConnectableServerData) serverData;
        }
        return new ConnectableServerData(serverData.name, serverData.address(), serverData.type(), null);
    }

    public ServerResourcePackStatus resourcePackStatus() {
        return this.resourcePackStatus;
    }

    public void connect() {
        connect(null);
    }

    public void connect(String command) {
        connect(command, 0);
    }

    public void connect(String command, int delay) {
        JoinServerRequest.Builder requestBuilder = JoinServerRequest.builder().serverData(this);
        if (command != null) {
            requestBuilder.loginExecutor(() -> {
                if (delay == 0) {
                    Laby.labyAPI().minecraft().chatExecutor().chat(command);
                } else {
                    Task.builder(() -> {
                        LabyAPI labyAPI = Laby.labyAPI();
                        ServerController serverController = labyAPI.serverController();
                        ServerData currentServerData = serverController.getCurrentServerData();
                        if (currentServerData != this) {
                            return;
                        }
                        labyAPI.minecraft().chatExecutor().chat(command);
                    }).delay(delay, TimeUnit.MILLISECONDS).build().execute();
                }
            });
        }
        Laby.labyAPI().serverController().joinServer(requestBuilder.build());
    }

    public ConnectableServerData copy() {
        return new ConnectableServerData(this.name, this.serverAddress, type(), this.resourcePackStatus);
    }

    @Override // net.labymod.api.client.network.server.ServerData
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectableServerData)) {
            return false;
        }
        ConnectableServerData that = (ConnectableServerData) o;
        return super.equals(o) && this.resourcePackStatus == that.resourcePackStatus;
    }

    @Override // net.labymod.api.client.network.server.ServerData
    public int hashCode() {
        int result = super.hashCode();
        return (31 * result) + (this.resourcePackStatus != null ? this.resourcePackStatus.hashCode() : 0);
    }

    public String toString() {
        return this.name;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/ConnectableServerData$Builder.class */
    public static class Builder {
        private String name;
        private ServerAddress serverAddress;
        private ServerResourcePackStatus resourcePackStatus = ServerResourcePackStatus.PROMPT;
        private ServerType type = ServerType.THIRD_PARTY;

        protected Builder() {
        }

        public Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        public Builder address(@NotNull ServerAddress serverAddress) {
            Objects.requireNonNull(serverAddress, "Server address cannot be null!");
            this.serverAddress = serverAddress;
            return this;
        }

        public Builder address(@NotNull String serverAddress) {
            Objects.requireNonNull(serverAddress, "Server address cannot be null!");
            return address(ServerAddress.parse(serverAddress));
        }

        public Builder resourcePackStatus(@Nullable ServerResourcePackStatus resourcePackStatus) {
            if (resourcePackStatus == null) {
                resourcePackStatus = ServerResourcePackStatus.PROMPT;
            }
            this.resourcePackStatus = resourcePackStatus;
            return this;
        }

        public Builder lan(boolean lan) {
            this.type = lan ? ServerType.LAN : ServerType.THIRD_PARTY;
            return this;
        }

        public Builder type(ServerType type) {
            this.type = type;
            return this;
        }

        public ConnectableServerData build() {
            Objects.requireNonNull(this.serverAddress, "Server address cannot be null!");
            return new ConnectableServerData(this.name, this.serverAddress, this.type, this.resourcePackStatus);
        }
    }
}
