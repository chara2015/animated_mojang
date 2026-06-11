package net.labymod.core.labyconnect.protocol.packets;

import java.util.UUID;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayAcceptLanWorldInvite.class */
public class PacketPlayAcceptLanWorldInvite extends Packet {
    private UUID player;
    private boolean host;

    public PacketPlayAcceptLanWorldInvite(UUID player, boolean host) {
        this.player = player;
        this.host = host;
    }

    public PacketPlayAcceptLanWorldInvite() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.player = buf.readUUID();
        this.host = buf.readBoolean();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeUUID(this.player);
        buf.writeBoolean(this.host);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public UUID getPlayer() {
        return this.player;
    }

    public boolean isHost() {
        return this.host;
    }
}
