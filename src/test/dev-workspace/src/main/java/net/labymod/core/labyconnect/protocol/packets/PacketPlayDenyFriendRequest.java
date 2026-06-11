package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;
import net.labymod.core.labyconnect.protocol.model.request.DefaultIncomingFriendRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayDenyFriendRequest.class */
public class PacketPlayDenyFriendRequest extends Packet {
    private DefaultUser denied;

    public PacketPlayDenyFriendRequest(DefaultUser denied) {
        this.denied = denied;
    }

    public PacketPlayDenyFriendRequest() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.denied = (DefaultIncomingFriendRequest) buf.readChatUser();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeUser(this.denied);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
    }

    public <T extends DefaultUser> T getDenied() {
        return (T) this.denied;
    }
}
