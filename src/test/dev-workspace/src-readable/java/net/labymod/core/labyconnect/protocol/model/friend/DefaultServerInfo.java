package net.labymod.core.labyconnect.protocol.model.friend;

import java.util.Objects;
import net.labymod.api.labyconnect.protocol.model.friend.ServerInfo;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/model/friend/DefaultServerInfo.class */
public class DefaultServerInfo implements ServerInfo {
    private String serverIp;
    private int serverPort;
    private String gameModeName;

    public DefaultServerInfo(String serverIp, int serverPort, String gameModeName) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.gameModeName = gameModeName;
    }

    public DefaultServerInfo(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.gameModeName = null;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.ServerInfo
    public int getPort() {
        return this.serverPort;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.ServerInfo
    public String getAddress() {
        return this.serverIp;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.ServerInfo
    @Nullable
    public String getGameModeName() {
        return this.gameModeName;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.friend.ServerInfo
    public boolean isSameServer(ServerInfo serverInfo) {
        return serverInfo != null && this.serverPort == serverInfo.getPort() && Objects.equals(this.serverIp, serverInfo.getAddress());
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setGameModeName(String specifiedServerName) {
        this.gameModeName = specifiedServerName;
    }

    public boolean isServerAvailable() {
        return (this.serverIp == null || this.serverIp.replaceAll(" ", "").isEmpty()) ? false : true;
    }

    public String getDisplayAddress() {
        String formattedIp = this.serverIp;
        if (formattedIp.endsWith(".")) {
            formattedIp = formattedIp.substring(0, formattedIp.length() - 1);
        }
        if (this.serverPort != 25565) {
            formattedIp = formattedIp + ":" + this.serverPort;
        }
        return formattedIp;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultServerInfo that = (DefaultServerInfo) o;
        if (this.serverPort != that.serverPort || !Objects.equals(this.serverIp, that.serverIp)) {
            return false;
        }
        return Objects.equals(this.gameModeName, that.gameModeName);
    }

    public int hashCode() {
        int result = this.serverIp != null ? this.serverIp.hashCode() : 0;
        return (31 * ((31 * result) + this.serverPort)) + (this.gameModeName != null ? this.gameModeName.hashCode() : 0);
    }
}
