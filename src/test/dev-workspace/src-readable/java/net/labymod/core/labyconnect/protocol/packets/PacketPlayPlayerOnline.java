package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayPlayerOnline.class */
public class PacketPlayPlayerOnline extends Packet {
    private DefaultFriend newOnlinePlayer;

    public PacketPlayPlayerOnline(DefaultFriend newOnlinePlayer) {
        this.newOnlinePlayer = newOnlinePlayer;
    }

    public PacketPlayPlayerOnline() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.newOnlinePlayer = buf.readChatUser();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeUser(this.newOnlinePlayer);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public DefaultFriend getPlayer() {
        return this.newOnlinePlayer;
    }
}
