package net.labymod.core.labyconnect.protocol.packets;

import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketLoginComplete.class */
public class PacketLoginComplete extends Packet {
    private String showDashboardButton;

    public PacketLoginComplete(String string) {
        this.showDashboardButton = string;
    }

    public PacketLoginComplete() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.showDashboardButton = buf.readString();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeString(this.showDashboardButton);
    }

    public int getId() {
        return 2;
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public String getDashboardPin() {
        return this.showDashboardButton;
    }
}
