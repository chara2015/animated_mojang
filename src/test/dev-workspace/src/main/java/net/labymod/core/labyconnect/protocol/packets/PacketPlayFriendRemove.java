package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayFriendRemove.class */
public class PacketPlayFriendRemove extends Packet {
    private DefaultFriend toRemove;

    public PacketPlayFriendRemove(DefaultFriend toRemove) {
        this.toRemove = toRemove;
    }

    public PacketPlayFriendRemove() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.toRemove = buf.readChatUser();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeUser(this.toRemove);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public Friend getToRemove() {
        return this.toRemove;
    }
}
