package net.minecraft.util.profiling.jfr.event;

import java.net.SocketAddress;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.Name;
import net.minecraft.obfuscate.DontObfuscate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/event/PacketSentEvent.class */
@DontObfuscate
@Name(PacketSentEvent.NAME)
@Label("Network Packet Sent")
public class PacketSentEvent extends PacketEvent {
    public static final String NAME = "minecraft.PacketSent";
    public static final EventType TYPE = EventType.getEventType(PacketSentEvent.class);

    public PacketSentEvent(String $$0, String $$1, String $$2, SocketAddress $$3, int $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }
}
