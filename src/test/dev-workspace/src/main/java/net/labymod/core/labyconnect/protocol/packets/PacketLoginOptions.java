package net.labymod.core.labyconnect.protocol.packets;

import java.util.TimeZone;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.core.labyconnect.protocol.Packet;
import net.labymod.core.labyconnect.protocol.PacketBuffer;
import net.labymod.core.labyconnect.protocol.PacketHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketLoginOptions.class */
public class PacketLoginOptions extends Packet {
    private boolean showServer;
    private UserStatus status;
    private TimeZone timeZone;

    public PacketLoginOptions(boolean showServer, UserStatus status, TimeZone timeZone) {
        this.showServer = showServer;
        this.status = status;
        this.timeZone = timeZone;
    }

    public PacketLoginOptions() {
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void read(PacketBuffer buf) {
        this.showServer = buf.readBoolean();
        this.status = buf.readUserStatus();
        this.timeZone = TimeZone.getTimeZone(buf.readString());
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void write(PacketBuffer buf) {
        buf.writeBoolean(this.showServer);
        buf.writeUserStatus(this.status);
        buf.writeString(this.timeZone.getID());
    }

    @Override // net.labymod.core.labyconnect.protocol.Packet
    public void handle(PacketHandler packetHandler) {
    }

    public Options getOptions() {
        return new Options(this.showServer, this.status, this.timeZone);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/packets/PacketLoginOptions$Options.class */
    public static class Options {
        private final boolean showServer;
        private final UserStatus onlineStatus;
        private final TimeZone timeZone;

        public Options(boolean showServer, UserStatus onlineStatus, TimeZone timeZone) {
            this.showServer = showServer;
            this.timeZone = timeZone;
            this.onlineStatus = onlineStatus;
        }

        public boolean isShowServer() {
            return this.showServer;
        }

        public UserStatus getOnlineStatus() {
            return this.onlineStatus;
        }

        public TimeZone getTimeZone() {
            return this.timeZone;
        }
    }
}
