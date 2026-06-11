package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultServerInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayFriendStatus.class */
public class PacketPlayFriendStatus extends Packet {
    private DefaultFriend player;
    private DefaultServerInfo playerInfo;

    public PacketPlayFriendStatus(DefaultFriend player, DefaultServerInfo playerInfo) {
        this.player = player;
        this.playerInfo = playerInfo;
    }

    public PacketPlayFriendStatus() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.player = buf.readChatUser();
        this.playerInfo = buf.readServerInfo();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeUser(this.player);
        buf.writeServerInfo(this.playerInfo);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public Friend getPlayer() {
        return this.player;
    }

    public DefaultServerInfo getPlayerInfo() {
        return this.playerInfo;
    }
}
