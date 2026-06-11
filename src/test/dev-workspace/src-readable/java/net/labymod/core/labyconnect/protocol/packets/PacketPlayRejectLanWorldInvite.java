package net.labymod.core.labyconnect.protocol.packets;

import java.util.UUID;
import net.labymod.api.labyconnect.LanWorldRejectReason;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayRejectLanWorldInvite.class */
public class PacketPlayRejectLanWorldInvite extends Packet {
    private UUID player;
    private LanWorldRejectReason reason;
    private boolean host;

    public PacketPlayRejectLanWorldInvite(UUID player, LanWorldRejectReason reason, boolean host) {
        this.player = player;
        this.reason = reason;
        this.host = host;
    }

    public PacketPlayRejectLanWorldInvite() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.player = buf.readUUID();
        this.reason = LanWorldRejectReason.fromId(buf.readShort());
        this.host = buf.readBoolean();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeUUID(this.player);
        buf.writeShort((short) this.reason.getId());
        buf.writeBoolean(this.host);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public UUID getPlayer() {
        return this.player;
    }

    public LanWorldRejectReason getReason() {
        return this.reason;
    }

    public boolean isHost() {
        return this.host;
    }
}
