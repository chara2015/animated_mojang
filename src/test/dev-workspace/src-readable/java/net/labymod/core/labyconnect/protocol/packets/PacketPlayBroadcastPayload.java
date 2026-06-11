package net.labymod.core.labyconnect.protocol.packets;

import java.io.IOException;
import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.packets.PacketUserTracker;
import net.labymod.core.labyconnect.util.GZIPCompression;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayBroadcastPayload.class */
public class PacketPlayBroadcastPayload extends Packet {
    private PacketUserTracker.EnumTrackingChannel channel;
    private UUID sender;
    private String key;
    private byte[] compressedJson;

    public PacketPlayBroadcastPayload(PacketUserTracker.EnumTrackingChannel channel, String key, String json) {
        this.channel = channel;
        this.sender = new UUID(0L, 0L);
        this.key = key;
        setJson(json);
    }

    public PacketPlayBroadcastPayload() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.channel = (PacketUserTracker.EnumTrackingChannel) buf.readEnum(PacketUserTracker.EnumTrackingChannel.values());
        this.sender = new UUID(buf.readLong(), buf.readLong());
        this.key = buf.readString();
        int length = PacketBuffer.requireBounded(buf.readInt(), 1048576, "PacketPlayBroadcastPayload.data");
        byte[] data = new byte[length];
        buf.readBytes(data);
        this.compressedJson = data;
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeByte(this.channel.ordinal());
        buf.writeLong(this.sender.getMostSignificantBits());
        buf.writeLong(this.sender.getLeastSignificantBits());
        buf.writeString(this.key);
        buf.writeInt(this.compressedJson.length);
        buf.writeBytes(this.compressedJson);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public PacketUserTracker.EnumTrackingChannel getChannel() {
        return this.channel;
    }

    public UUID getSender() {
        return this.sender;
    }

    public String getKey() {
        return this.key;
    }

    public String getJson() {
        try {
            return GZIPCompression.decompress(this.compressedJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getCompressedJson() {
        return this.compressedJson;
    }

    public void setJson(String json) {
        try {
            this.compressedJson = GZIPCompression.compress(json);
            if (this.compressedJson.length > 256) {
                throw new IllegalArgumentException("Payload is too big! " + this.compressedJson.length + " > 256");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
