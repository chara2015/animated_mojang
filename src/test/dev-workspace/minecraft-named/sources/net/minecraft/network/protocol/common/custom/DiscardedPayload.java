package net.minecraft.network.protocol.common.custom;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/custom/DiscardedPayload.class */
public final class DiscardedPayload extends Record implements CustomPacketPayload {
    private final Identifier id;

    public DiscardedPayload(Identifier $$0) {
        this.id = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DiscardedPayload.class), DiscardedPayload.class, "id", "FIELD:Lnet/minecraft/network/protocol/common/custom/DiscardedPayload;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DiscardedPayload.class), DiscardedPayload.class, "id", "FIELD:Lnet/minecraft/network/protocol/common/custom/DiscardedPayload;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DiscardedPayload.class, Object.class), DiscardedPayload.class, "id", "FIELD:Lnet/minecraft/network/protocol/common/custom/DiscardedPayload;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Identifier id() {
        return this.id;
    }

    public static <T extends FriendlyByteBuf> StreamCodec<T, DiscardedPayload> codec(Identifier $$0, int $$1) {
        return CustomPacketPayload.codec(($$02, $$12) -> {
        }, $$2 -> {
            int $$3 = $$2.readableBytes();
            if ($$3 < 0 || $$3 > $$1) {
                throw new IllegalArgumentException("Payload may not be larger than " + $$1 + " bytes");
            }
            $$2.m1595skipBytes($$3);
            return new DiscardedPayload($$0);
        });
    }

    @Override // net.minecraft.network.protocol.common.custom.CustomPacketPayload
    public CustomPacketPayload.Type<DiscardedPayload> type() {
        return new CustomPacketPayload.Type<>(this.id);
    }
}
