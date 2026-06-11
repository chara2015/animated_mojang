package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.packets.PacketActionBroadcast;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketActionPlay.class */
public class PacketActionPlay extends Packet {
    private short requestId;
    private short actionType;
    private byte[] data;

    public PacketActionPlay(int requestId, PacketActionBroadcast.ActionType actionType, byte[] data) {
        this(requestId, actionType.getId(), data);
    }

    public PacketActionPlay(int requestId, int action, byte[] data) {
        this.requestId = (short) requestId;
        this.actionType = (short) action;
        this.data = data;
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.requestId = buf.readShort();
        this.actionType = buf.readShort();
        int length = PacketBuffer.requireBounded(buf.readVarIntFromBuffer(), 1048576, "PacketActionPlay.data");
        this.data = new byte[length];
        buf.readBytes(this.data);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeShort(this.requestId);
        buf.writeShort(this.actionType);
        if (this.data == null) {
            buf.writeVarIntToBuffer(0);
        } else {
            buf.writeVarIntToBuffer(this.data.length);
            buf.writeBytes(this.data);
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
    }
}
