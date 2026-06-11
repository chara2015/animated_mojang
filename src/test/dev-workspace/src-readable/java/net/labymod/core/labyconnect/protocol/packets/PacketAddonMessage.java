package net.labymod.core.labyconnect.protocol.packets;

import com.google.gson.JsonElement;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.util.GZIPCompression;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketAddonMessage.class */
public class PacketAddonMessage extends Packet {
    private String key;
    private byte[] data;

    public PacketAddonMessage(String key, byte[] data) {
        this.key = key;
        this.data = data;
    }

    public PacketAddonMessage(String key, String json) {
        this.key = key;
        this.data = toBytes(json);
    }

    public PacketAddonMessage(String key, JsonElement element) {
        this(key, element.toString());
    }

    public PacketAddonMessage() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.key = buf.readString();
        int length = PacketBuffer.requireBounded(buf.readInt(), 1048576, "PacketAddonMessage.data");
        byte[] data = new byte[length];
        buf.readBytes(data);
        this.data = data;
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeString(this.key);
        buf.writeInt(this.data.length);
        buf.writeBytes(this.data);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public String getKey() {
        return this.key;
    }

    public byte[] getData() {
        return this.data;
    }

    public String getJson() {
        try {
            return GZIPCompression.decompress(this.data);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private byte[] toBytes(String in) {
        byte[] str = in.getBytes(StandardCharsets.UTF_8);
        try {
            if (str.length == 0) {
                return new byte[0];
            }
            ByteArrayOutputStream obj = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(obj);
            gzip.write(str);
            gzip.flush();
            gzip.close();
            return obj.toByteArray();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
