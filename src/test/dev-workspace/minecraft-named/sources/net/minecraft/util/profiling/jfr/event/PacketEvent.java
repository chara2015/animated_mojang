package net.minecraft.util.profiling.jfr.event;

import java.net.SocketAddress;
import jdk.jfr.Category;
import jdk.jfr.DataAmount;
import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;
import net.minecraft.util.profiling.jfr.JfrProfiler;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/event/PacketEvent.class */
@Enabled(false)
@Category({JfrProfiler.ROOT_CATEGORY, JfrProfiler.NETWORK_CATEGORY})
@StackTrace(false)
public abstract class PacketEvent extends Event {

    @Name(Fields.PROTOCOL_ID)
    @Label("Protocol Id")
    public final String protocolId;

    @Name(Fields.PACKET_DIRECTION)
    @Label("Packet Direction")
    public final String packetDirection;

    @Name(Fields.PACKET_ID)
    @Label("Packet Id")
    public final String packetId;

    @Name("remoteAddress")
    @Label("Remote Address")
    public final String remoteAddress;

    @DataAmount
    @Name("bytes")
    @Label("Bytes")
    public final int bytes;

    public PacketEvent(String $$0, String $$1, String $$2, SocketAddress $$3, int $$4) {
        this.protocolId = $$0;
        this.packetDirection = $$1;
        this.packetId = $$2;
        this.remoteAddress = $$3.toString();
        this.bytes = $$4;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/event/PacketEvent$Fields.class */
    public static final class Fields {
        public static final String REMOTE_ADDRESS = "remoteAddress";
        public static final String PROTOCOL_ID = "protocolId";
        public static final String PACKET_DIRECTION = "packetDirection";
        public static final String PACKET_ID = "packetId";
        public static final String BYTES = "bytes";

        private Fields() {
        }
    }
}
