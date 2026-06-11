package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketNotAllowed.class */
public class PacketNotAllowed extends Packet {
    private String reason;
    private long until;

    public PacketNotAllowed(String reason, long until) {
        this.reason = reason;
        this.until = until;
    }

    public PacketNotAllowed() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.reason = buf.readString();
        this.until = buf.readLong();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeString(this.reason);
        buf.writeLong(this.until);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public long getUntil() {
        return this.until;
    }

    public String getReason() {
        return this.reason;
    }
}
