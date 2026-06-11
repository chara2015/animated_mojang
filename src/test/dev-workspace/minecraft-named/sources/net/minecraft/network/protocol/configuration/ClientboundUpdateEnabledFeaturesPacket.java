package net.minecraft.network.protocol.configuration;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/configuration/ClientboundUpdateEnabledFeaturesPacket.class */
public final class ClientboundUpdateEnabledFeaturesPacket extends Record implements Packet<ClientConfigurationPacketListener> {
    private final Set<Identifier> features;
    public static final StreamCodec<FriendlyByteBuf, ClientboundUpdateEnabledFeaturesPacket> STREAM_CODEC = Packet.codec((v0, v1) -> {
        v0.write(v1);
    }, ClientboundUpdateEnabledFeaturesPacket::new);

    public ClientboundUpdateEnabledFeaturesPacket(Set<Identifier> $$0) {
        this.features = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientboundUpdateEnabledFeaturesPacket.class), ClientboundUpdateEnabledFeaturesPacket.class, "features", "FIELD:Lnet/minecraft/network/protocol/configuration/ClientboundUpdateEnabledFeaturesPacket;->features:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientboundUpdateEnabledFeaturesPacket.class), ClientboundUpdateEnabledFeaturesPacket.class, "features", "FIELD:Lnet/minecraft/network/protocol/configuration/ClientboundUpdateEnabledFeaturesPacket;->features:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientboundUpdateEnabledFeaturesPacket.class, Object.class), ClientboundUpdateEnabledFeaturesPacket.class, "features", "FIELD:Lnet/minecraft/network/protocol/configuration/ClientboundUpdateEnabledFeaturesPacket;->features:Ljava/util/Set;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Set<Identifier> features() {
        return this.features;
    }

    private ClientboundUpdateEnabledFeaturesPacket(FriendlyByteBuf $$0) {
        this((Set<Identifier>) $$0.readCollection(HashSet::new, (v0) -> {
            return v0.readIdentifier();
        }));
    }

    private void write(FriendlyByteBuf $$0) {
        $$0.writeCollection(this.features, (v0, v1) -> {
            v0.writeIdentifier(v1);
        });
    }

    @Override // net.minecraft.network.protocol.Packet
    public PacketType<ClientboundUpdateEnabledFeaturesPacket> type() {
        return ConfigurationPacketTypes.CLIENTBOUND_UPDATE_ENABLED_FEATURES;
    }

    @Override // net.minecraft.network.protocol.Packet
    public void handle(ClientConfigurationPacketListener $$0) {
        $$0.handleEnabledFeatures(this);
    }
}
