package net.labymod.core.labyconnect.protocol.packets;

import java.util.Objects;
import net.labymod.api.labyconnect.protocol.model.friend.ServerInfo;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultServerInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayServerStatus.class */
public class PacketPlayServerStatus extends Packet {
    private String serverIp;
    private int port;
    private String gamemode;

    public PacketPlayServerStatus(String serverIp, int port) {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
        this.serverIp = serverIp;
        this.port = port;
        this.gamemode = null;
    }

    public PacketPlayServerStatus() {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
    }

    public PacketPlayServerStatus(String serverIp, int port, String gamemode) {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
        this.serverIp = serverIp;
        this.port = port;
        this.gamemode = gamemode;
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.serverIp = buf.readString();
        this.port = buf.readInt();
        if (buf.readBoolean()) {
            this.gamemode = buf.readString();
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeString(this.serverIp);
        buf.writeInt(this.port);
        if (this.gamemode != null && !this.gamemode.isEmpty()) {
            buf.writeBoolean(true);
            buf.writeString(this.gamemode);
        } else {
            buf.writeBoolean(false);
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
    }

    public String getServerIp() {
        return this.serverIp;
    }

    public int getPort() {
        return this.port;
    }

    public String getGamemode() {
        return this.gamemode;
    }

    public ServerInfo build() {
        if (this.gamemode == null) {
            return new DefaultServerInfo(this.serverIp, this.port);
        }
        return new DefaultServerInfo(this.serverIp, this.port, this.gamemode);
    }

    public boolean equals(PacketPlayServerStatus packet) {
        return this.serverIp.equals(packet.getServerIp()) && this.port == packet.getPort() && Objects.equals(this.gamemode, packet.getGamemode());
    }
}
