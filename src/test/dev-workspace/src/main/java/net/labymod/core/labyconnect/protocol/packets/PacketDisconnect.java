package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketDisconnect.class */
public class PacketDisconnect extends Packet {
    private String reason;

    public PacketDisconnect() {
    }

    public PacketDisconnect(String reason) {
        this.reason = reason;
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.reason = buf.readString();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        if (getReason() == null) {
            buf.writeString("Client Error");
        } else {
            buf.writeString(getReason());
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public String getReason() {
        return this.reason;
    }
}
