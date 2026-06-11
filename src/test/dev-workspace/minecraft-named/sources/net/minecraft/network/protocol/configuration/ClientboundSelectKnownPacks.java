package net.minecraft.network.protocol.configuration;

import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.server.packs.repository.KnownPack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/configuration/ClientboundSelectKnownPacks.class */
public final class ClientboundSelectKnownPacks extends Record implements Packet<ClientConfigurationPacketListener> {
    private final List<KnownPack> knownPacks;
    public static final StreamCodec<ByteBuf, ClientboundSelectKnownPacks> STREAM_CODEC = StreamCodec.composite(KnownPack.STREAM_CODEC.apply(ByteBufCodecs.list()), (v0) -> {
        return v0.knownPacks();
    }, ClientboundSelectKnownPacks::new);

    public ClientboundSelectKnownPacks(List<KnownPack> $$0) {
        this.knownPacks = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientboundSelectKnownPacks.class), ClientboundSelectKnownPacks.class, "knownPacks", "FIELD:Lnet/minecraft/network/protocol/configuration/ClientboundSelectKnownPacks;->knownPacks:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientboundSelectKnownPacks.class), ClientboundSelectKnownPacks.class, "knownPacks", "FIELD:Lnet/minecraft/network/protocol/configuration/ClientboundSelectKnownPacks;->knownPacks:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientboundSelectKnownPacks.class, Object.class), ClientboundSelectKnownPacks.class, "knownPacks", "FIELD:Lnet/minecraft/network/protocol/configuration/ClientboundSelectKnownPacks;->knownPacks:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public List<KnownPack> knownPacks() {
        return this.knownPacks;
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundSelectKnownPacks> type() {
        return ConfigurationPacketTypes.CLIENTBOUND_SELECT_KNOWN_PACKS;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientConfigurationPacketListener $$0) {
        $$0.handleSelectKnownPacks(this);
    }
}
