package net.labymod.serverapi.protocol.packet;

import net.labymod.serverapi.protocol.packet.Packet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/packet/PacketHandler.class */
@FunctionalInterface
@Deprecated(forRemoval = true, since = "4.2.24")
public interface PacketHandler<T extends Packet> {
    void handle(T t);
}
