package net.labymod.core.labyconnect.protocol.packets;

import java.util.UUID;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldInvite;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayInviteLanWorld.class */
public class PacketPlayInviteLanWorld extends Packet {
    private boolean allowAll;
    private UUID player;
    private LanWorldOptions options;
    private long timeoutTimestamp;

    public PacketPlayInviteLanWorld(SharedLanWorldInvite invite) {
        this(invite.getReceiver(), invite.options());
    }

    public PacketPlayInviteLanWorld(LanWorldOptions options) {
        this.allowAll = true;
        this.options = options;
    }

    public PacketPlayInviteLanWorld(UUID player, LanWorldOptions options) {
        this.allowAll = false;
        this.player = player;
        this.options = options;
    }

    public PacketPlayInviteLanWorld() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.allowAll = buf.readBoolean();
        this.player = !this.allowAll ? buf.readUUID() : null;
        this.options = new LanWorldOptions(buf.readString(), buf.readByte(), buf.readBoolean(), buf.readBoolean() ? buf.readString() : null);
        this.timeoutTimestamp = buf.readLong();
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeBoolean(this.allowAll);
        if (!this.allowAll) {
            buf.writeUUID(this.player);
        }
        buf.writeString(this.options.worldName);
        buf.writeByte(this.options.gameMode);
        buf.writeBoolean(this.options.allowCheats);
        buf.writeBoolean(this.options.icon != null);
        if (this.options.icon != null) {
            buf.writeString(this.options.icon);
        }
        buf.writeLong(this.timeoutTimestamp);
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    public boolean isAllowAll() {
        return this.allowAll;
    }

    public UUID getPlayer() {
        return this.player;
    }

    public LanWorldOptions getOptions() {
        return this.options;
    }

    public long getTimeoutTimestamp() {
        return this.timeoutTimestamp;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketPlayInviteLanWorld$LanWorldOptions.class */
    public static class LanWorldOptions {
        private final String worldName;
        private final byte gameMode;
        private final boolean allowCheats;
        private final String icon;

        public LanWorldOptions(String worldName, byte gameMode, boolean allowCheats, String icon) {
            this.worldName = worldName;
            this.gameMode = gameMode;
            this.allowCheats = allowCheats;
            this.icon = (icon == null || !icon.startsWith(ServerInfo.ICON_PREFIX)) ? null : icon;
        }

        public String getWorldName() {
            return this.worldName;
        }

        public GameMode gameMode() {
            return GameMode.fromId(this.gameMode);
        }

        public boolean isAllowCheats() {
            return this.allowCheats;
        }

        public String getIcon() {
            return this.icon;
        }
    }
}
