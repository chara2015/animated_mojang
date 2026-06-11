package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketHelloPing.class */
public class PacketHelloPing extends Packet {
    private long timestamp;

    public PacketHelloPing() {
    }

    public PacketHelloPing(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.timestamp = buf.readLong();
        buf.readInt();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeLong(this.timestamp);
        buf.writeInt(29);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
