package net.labymod.core.labyconnect.protocol.packets;

import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketActionRequest.class */
public class PacketActionRequest extends Packet {
    private UUID uuid;

    public PacketActionRequest() {
    }

    public PacketActionRequest(UUID uuid) {
        this.uuid = uuid;
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.uuid = UUID.fromString(buf.readString());
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeString(this.uuid.toString());
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
