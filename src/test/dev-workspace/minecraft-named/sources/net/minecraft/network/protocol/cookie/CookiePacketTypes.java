package net.minecraft.network.protocol.cookie;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/cookie/CookiePacketTypes.class */
public class CookiePacketTypes {
    public static final PacketType<ClientboundCookieRequestPacket> CLIENTBOUND_COOKIE_REQUEST = createClientbound("cookie_request");
    public static final PacketType<ServerboundCookieResponsePacket> SERVERBOUND_COOKIE_RESPONSE = createServerbound("cookie_response");

    private static <T extends Packet<ClientCookiePacketListener>> PacketType<T> createClientbound(String $$0) {
        return new PacketType<>(PacketFlow.CLIENTBOUND, Identifier.withDefaultNamespace($$0));
    }

    private static <T extends Packet<ServerCookiePacketListener>> PacketType<T> createServerbound(String $$0) {
        return new PacketType<>(PacketFlow.SERVERBOUND, Identifier.withDefaultNamespace($$0));
    }
}
