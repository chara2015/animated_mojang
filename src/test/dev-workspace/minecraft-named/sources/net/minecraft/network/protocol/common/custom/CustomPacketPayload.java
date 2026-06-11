package net.minecraft.network.protocol.common.custom;

import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamMemberEncoder;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/custom/CustomPacketPayload.class */
public interface CustomPacketPayload {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/custom/CustomPacketPayload$FallbackProvider.class */
    public interface FallbackProvider<B extends FriendlyByteBuf> {
        StreamCodec<B, ? extends CustomPacketPayload> create(Identifier identifier);
    }

    Type<? extends CustomPacketPayload> type();

    static <B extends ByteBuf, T extends CustomPacketPayload> StreamCodec<B, T> codec(StreamMemberEncoder<B, T> $$0, StreamDecoder<B, T> $$1) {
        return StreamCodec.ofMember($$0, $$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/custom/CustomPacketPayload$Type.class */
    public static final class Type<T extends CustomPacketPayload> extends Record {
        private final Identifier id;

        public Type(Identifier $$0) {
            this.id = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Type.class), Type.class, "id", "FIELD:Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Type.class), Type.class, "id", "FIELD:Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Type.class, Object.class), Type.class, "id", "FIELD:Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Identifier id() {
            return this.id;
        }
    }

    static <T extends CustomPacketPayload> Type<T> createType(String $$0) {
        return new Type<>(Identifier.withDefaultNamespace($$0));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/common/custom/CustomPacketPayload$TypeAndCodec.class */
    public static final class TypeAndCodec<B extends FriendlyByteBuf, T extends CustomPacketPayload> extends Record {
        private final Type<T> type;
        private final StreamCodec<B, T> codec;

        public TypeAndCodec(Type<T> $$0, StreamCodec<B, T> $$1) {
            this.type = $$0;
            this.codec = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TypeAndCodec.class), TypeAndCodec.class, "type;codec", "FIELD:Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$TypeAndCodec;->type:Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;", "FIELD:Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$TypeAndCodec;->codec:Lnet/minecraft/network/codec/StreamCodec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TypeAndCodec.class), TypeAndCodec.class, "type;codec", "FIELD:Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$TypeAndCodec;->type:Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;", "FIELD:Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$TypeAndCodec;->codec:Lnet/minecraft/network/codec/StreamCodec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TypeAndCodec.class, Object.class), TypeAndCodec.class, "type;codec", "FIELD:Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$TypeAndCodec;->type:Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;", "FIELD:Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$TypeAndCodec;->codec:Lnet/minecraft/network/codec/StreamCodec;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Type<T> type() {
            return this.type;
        }

        public StreamCodec<B, T> codec() {
            return this.codec;
        }
    }

    static <B extends FriendlyByteBuf> StreamCodec<B, CustomPacketPayload> codec(final FallbackProvider<B> fallbackProvider, List<TypeAndCodec<? super B, ?>> list) {
        final Map map = (Map) list.stream().collect(Collectors.toUnmodifiableMap($$0 -> {
            return $$0.type().id();
        }, (v0) -> {
            return v0.codec();
        }));
        return (StreamCodec<B, CustomPacketPayload>) new StreamCodec<B, CustomPacketPayload>() { // from class: net.minecraft.network.protocol.common.custom.CustomPacketPayload.1
            private StreamCodec<? super B, ? extends CustomPacketPayload> findCodec(Identifier $$02) {
                StreamCodec<? super B, ? extends CustomPacketPayload> $$1 = (StreamCodec) map.get($$02);
                if ($$1 != null) {
                    return $$1;
                }
                return fallbackProvider.create($$02);
            }

            /* JADX WARN: Incorrect types in method signature: <T::Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;>(TB;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type<TT;>;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V */
            private void writeCap(FriendlyByteBuf friendlyByteBuf, Type type, CustomPacketPayload $$2) {
                friendlyByteBuf.writeIdentifier(type.id());
                findCodec(type.id).encode(friendlyByteBuf, $$2);
            }

            /* JADX WARN: Incorrect types in method signature: (TB;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V */
            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(FriendlyByteBuf friendlyByteBuf, CustomPacketPayload $$1) {
                writeCap(friendlyByteBuf, $$1.type(), $$1);
            }

            /* JADX WARN: Incorrect types in method signature: (TB;)Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload; */
            @Override // net.minecraft.network.codec.StreamDecoder
            public CustomPacketPayload decode(FriendlyByteBuf friendlyByteBuf) {
                Identifier $$1 = friendlyByteBuf.readIdentifier();
                return findCodec($$1).decode(friendlyByteBuf);
            }
        };
    }
}
