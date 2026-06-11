package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.friend.DefaultFriend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayTyping.class */
public class PacketPlayTyping extends Packet {
    private DefaultFriend player;
    private DefaultFriend inChatWith;
    private boolean typing;

    public PacketPlayTyping(DefaultFriend player, DefaultFriend inChatWith, boolean typing) {
        this.player = player;
        this.inChatWith = inChatWith;
        this.typing = typing;
    }

    public PacketPlayTyping() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.player = buf.readChatUser();
        this.inChatWith = buf.readChatUser();
        this.typing = buf.readBoolean();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeUser(this.player);
        buf.writeUser(this.inChatWith);
        buf.writeBoolean(this.typing);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public Friend getInChatWith() {
        return this.inChatWith;
    }

    public Friend getPlayer() {
        return this.player;
    }

    public boolean isTyping() {
        return this.typing;
    }
}
