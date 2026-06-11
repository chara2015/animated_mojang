package net.labymod.core.labyconnect.protocol;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/Packet.class */
public abstract class Packet {
    public abstract void read(PacketBuffer packetBuffer);

    public abstract void write(PacketBuffer packetBuffer);

    public abstract void handle(PacketHandler packetHandler);
}
