package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketLoginTime.class */
public class PacketLoginTime extends Packet {
    private DefaultFriend player;
    private long dateJoined;
    private long lastOnline;

    public PacketLoginTime(DefaultFriend player, long dateJoined, long lastOnline) {
        this.player = player;
        this.dateJoined = dateJoined;
        this.lastOnline = lastOnline;
    }

    public PacketLoginTime() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.player = buf.readChatUser();
        this.dateJoined = buf.readLong();
        this.lastOnline = buf.readLong();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeUser(this.player);
        buf.writeLong(this.dateJoined);
        buf.writeLong(this.lastOnline);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public long getDateJoined() {
        return this.dateJoined;
    }

    public long getLastOnline() {
        return this.lastOnline;
    }

    public Friend getPlayer() {
        return this.player;
    }
}
