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

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/configuration/ServerboundSelectKnownPacks.class */
public final class ServerboundSelectKnownPacks extends Record implements Packet<ServerConfigurationPacketListener> {
    private final List<KnownPack> knownPacks;
    public static final StreamCodec<ByteBuf, ServerboundSelectKnownPacks> STREAM_CODEC = StreamCodec.composite(KnownPack.STREAM_CODEC.apply(ByteBufCodecs.list(64)), (v0) -> {
        return v0.knownPacks();
    }, ServerboundSelectKnownPacks::new);

    public ServerboundSelectKnownPacks(List<KnownPack> $$0) {
        this.knownPacks = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ServerboundSelectKnownPacks.class), ServerboundSelectKnownPacks.class, "knownPacks", "FIELD:Lnet/minecraft/network/protocol/configuration/ServerboundSelectKnownPacks;->knownPacks:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ServerboundSelectKnownPacks.class), ServerboundSelectKnownPacks.class, "knownPacks", "FIELD:Lnet/minecraft/network/protocol/configuration/ServerboundSelectKnownPacks;->knownPacks:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ServerboundSelectKnownPacks.class, Object.class), ServerboundSelectKnownPacks.class, "knownPacks", "FIELD:Lnet/minecraft/network/protocol/configuration/ServerboundSelectKnownPacks;->knownPacks:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public List<KnownPack> knownPacks() {
        return this.knownPacks;
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ServerboundSelectKnownPacks> type() {
        return ConfigurationPacketTypes.SERVERBOUND_SELECT_KNOWN_PACKS;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ServerConfigurationPacketListener $$0) {
        $$0.handleSelectKnownPacks(this);
    }
}
