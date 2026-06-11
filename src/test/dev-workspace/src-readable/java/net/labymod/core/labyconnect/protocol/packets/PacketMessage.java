package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;
import net.labymod.core.labyconnect.protocol.model.DefaultUser;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketMessage.class */
public class PacketMessage extends Packet {
    private DefaultUser sender;
    private DefaultUser to;
    private String message;
    private long sentTime;
    private long fileSize;
    private double audioTime;

    public PacketMessage(DefaultUser sender, DefaultUser to, String message, long fileSize, double time, long sentTime) {
        this.sender = sender;
        this.to = to;
        this.message = message;
        this.fileSize = fileSize;
        this.audioTime = time;
        this.sentTime = sentTime;
    }

    public PacketMessage() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.sender = buf.readChatUser();
        this.to = buf.readChatUser();
        this.message = buf.readString();
        this.fileSize = buf.readLong();
        this.audioTime = buf.readDouble();
        this.sentTime = buf.readLong();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeUser(this.sender);
        buf.writeUser(this.to);
        buf.writeString(this.message);
        buf.writeLong(this.fileSize);
        buf.writeDouble(this.audioTime);
        buf.writeLong(this.sentTime);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public double getAudioTime() {
        return this.audioTime;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public String getMessage() {
        return this.message;
    }

    public User getSender() {
        return this.sender;
    }

    public User getTo() {
        return this.to;
    }

    public long getSentTime() {
        return this.sentTime;
    }
}
