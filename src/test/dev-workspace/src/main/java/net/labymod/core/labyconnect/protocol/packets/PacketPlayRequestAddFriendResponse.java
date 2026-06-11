package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayRequestAddFriendResponse.class */
public class PacketPlayRequestAddFriendResponse extends Packet {
    private String searched;
    private boolean requestSent;
    private String reason;

    public PacketPlayRequestAddFriendResponse(String searched, boolean sent) {
        this.searched = searched;
        this.requestSent = sent;
    }

    public PacketPlayRequestAddFriendResponse(String searched, boolean sent, String reason) {
        this.searched = searched;
        this.requestSent = sent;
        this.reason = reason;
    }

    public PacketPlayRequestAddFriendResponse() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.searched = buf.readString();
        this.requestSent = buf.readBoolean();
        if (!this.requestSent) {
            this.reason = buf.readString();
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeString(this.searched);
        buf.writeBoolean(this.requestSent);
        if (!isRequestSent()) {
            buf.writeString(this.reason);
        }
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public boolean isRequestSent() {
        return this.requestSent;
    }

    public String getReason() {
        return this.reason;
    }

    public String getSearched() {
        return this.searched;
    }
}
