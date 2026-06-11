package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayFriendPlayingOn.class */
public class PacketPlayFriendPlayingOn extends Packet {
    private DefaultFriend player;
    private String gameModeName;

    public PacketPlayFriendPlayingOn(DefaultFriend player, String gameModeName) {
        this.player = player;
        this.gameModeName = gameModeName;
    }

    public PacketPlayFriendPlayingOn() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.player = buf.readChatUser();
        this.gameModeName = buf.readString();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeUser(this.player);
        buf.writeString(this.gameModeName);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public String getGameModeName() {
        return this.gameModeName;
    }

    public Friend getPlayer() {
        return this.player;
    }
}
