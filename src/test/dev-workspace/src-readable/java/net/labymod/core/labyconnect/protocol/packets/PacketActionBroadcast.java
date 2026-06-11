package net.labymod.core.labyconnect.protocol.packets;

import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketActionBroadcast.class */
public class PacketActionBroadcast extends Packet {
    private UUID uniqueId;
    private ActionType type;
    private byte[] data;

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.uniqueId = new UUID(buf.readLong(), buf.readLong());
        this.type = (ActionType) buf.readEnumShort(ActionType.values(), 1);
        int length = PacketBuffer.requireBounded(buf.readVarIntFromBuffer(), 1048576, "PacketActionBroadcast.data");
        this.data = new byte[length];
        buf.readBytes(this.data);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeLong(this.uniqueId.getMostSignificantBits());
        buf.writeLong(this.uniqueId.getLeastSignificantBits());
        buf.writeShort((short) this.type.getId());
        buf.writeVarIntToBuffer(this.data.length);
        buf.writeBytes(this.data);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public ActionType getType() {
        return this.type;
    }

    public byte[] getData() {
        return this.data;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketActionBroadcast$ActionType.class */
    public enum ActionType {
        EMOTE(1),
        COSMETIC_CHANGE(2),
        STICKER(3),
        SPRAY(4);

        private final int id;

        ActionType(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }
}
