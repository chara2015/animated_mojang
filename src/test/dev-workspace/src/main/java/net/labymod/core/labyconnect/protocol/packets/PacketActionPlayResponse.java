package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketActionPlayResponse.class */
public class PacketActionPlayResponse extends Packet {
    private short requestId;
    private boolean allowed;
    private String reason;

    public PacketActionPlayResponse() {
    }

    public PacketActionPlayResponse(boolean allowed) {
        this.allowed = allowed;
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.requestId = buf.readShort();
        this.allowed = buf.readBoolean();
        if (!this.allowed) {
            this.reason = buf.readString();
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeShort(this.requestId);
        buf.writeBoolean(this.allowed);
        if (!this.allowed) {
            buf.writeString(this.reason);
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public boolean isAllowed() {
        return this.allowed;
    }
}
