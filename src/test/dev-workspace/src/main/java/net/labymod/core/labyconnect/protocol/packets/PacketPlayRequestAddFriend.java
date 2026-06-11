package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayRequestAddFriend.class */
public class PacketPlayRequestAddFriend extends Packet {
    private String name;

    public PacketPlayRequestAddFriend(String name) {
        this.name = name;
    }

    public PacketPlayRequestAddFriend() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        byte[] a = new byte[buf.readInt()];
        for (int i = 0; i < a.length; i++) {
            a[i] = buf.readByte();
        }
        this.name = new String(a);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeInt(this.name.getBytes().length);
        buf.writeBytes(this.name.getBytes());
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
    }

    public String getName() {
        return this.name;
    }
}
