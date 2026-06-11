package net.labymod.core.labyconnect.protocol.packets;

import java.util.Objects;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayServerStatusUpdate.class */
public class PacketPlayServerStatusUpdate extends Packet {
    private String serverIp;
    private int port;
    private String gamemode;
    private boolean viaServerlist;

    public PacketPlayServerStatusUpdate(String serverIp, int port) {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
        this.serverIp = serverIp;
        this.port = port;
        this.gamemode = null;
    }

    public PacketPlayServerStatusUpdate() {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
    }

    public PacketPlayServerStatusUpdate(String serverIp, int port, String gamemode, boolean viaServerlist) {
        this.serverIp = "";
        this.port = 25565;
        this.gamemode = null;
        this.serverIp = serverIp;
        this.port = port;
        this.gamemode = gamemode;
        this.viaServerlist = viaServerlist;
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

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.serverIp = buf.readString();
        this.port = buf.readInt();
        this.viaServerlist = buf.readBoolean();
        if (buf.readBoolean()) {
            this.gamemode = buf.readString();
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeString(this.serverIp);
        buf.writeInt(this.port);
        buf.writeBoolean(this.viaServerlist);
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

    public boolean equals(PacketPlayServerStatusUpdate packet) {
        return this.serverIp.equals(packet.serverIp) && this.port == packet.port && Objects.equals(this.gamemode, packet.gamemode);
    }
}
