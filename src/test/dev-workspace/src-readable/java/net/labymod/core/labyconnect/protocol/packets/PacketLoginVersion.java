package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketLoginVersion.class */
public class PacketLoginVersion extends Packet {
    private int versionId;
    private String versionName;
    private String betaToken;
    private int minecraftVersion;

    public PacketLoginVersion(int internalVersion, String externalVersion, int minecraftVersion) {
        this.versionId = internalVersion;
        this.versionName = externalVersion;
        this.betaToken = "";
        this.minecraftVersion = minecraftVersion;
    }

    public PacketLoginVersion() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.versionId = buf.readInt();
        this.versionName = buf.readString();
        this.betaToken = buf.readString();
        if (this.versionId >= 28) {
            this.minecraftVersion = buf.readInt();
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeInt(this.versionId);
        buf.writeString(this.versionName);
        buf.writeString(this.betaToken);
        if (this.versionId >= 28) {
            buf.writeInt(this.minecraftVersion);
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
    }

    public String getVersionName() {
        return this.versionName;
    }

    public int getVersionID() {
        return this.versionId;
    }

    public String getBetaToken() {
        return this.betaToken;
    }

    public int getMinecraftVersion() {
        return this.minecraftVersion;
    }
}
