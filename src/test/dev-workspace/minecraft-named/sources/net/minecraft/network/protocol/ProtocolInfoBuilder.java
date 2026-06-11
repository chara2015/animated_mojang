package net.minecraft.network.protocol;

import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.ClientboundPacketListener;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.PacketListener;
import net.minecraft.network.ProtocolInfo;
import net.minecraft.network.ServerboundPacketListener;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Unit;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/ProtocolInfoBuilder.class */
public class ProtocolInfoBuilder<T extends PacketListener, B extends ByteBuf, C> {
    final ConnectionProtocol protocol;
    final PacketFlow flow;
    private final List<CodecEntry<T, ?, B, C>> codecs = new ArrayList();
    private BundlerInfo bundlerInfo;

    public ProtocolInfoBuilder(ConnectionProtocol $$0, PacketFlow $$1) {
        this.protocol = $$0;
        this.flow = $$1;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/ProtocolInfoBuilder$CodecEntry.class */
    static final class CodecEntry<T extends PacketListener, P extends Packet<? super T>, B extends ByteBuf, C> extends Record {
        private final PacketType<P> type;
        private final StreamCodec<? super B, P> serializer;
        private final CodecModifier<B, P, C> modifier;

        CodecEntry(PacketType<P> $$0, StreamCodec<? super B, P> $$1, CodecModifier<B, P, C> $$2) {
            this.type = $$0;
            this.serializer = $$1;
            this.modifier = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CodecEntry.class), CodecEntry.class, "type;serializer;modifier", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$CodecEntry;->type:Lnet/minecraft/network/protocol/PacketType;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$CodecEntry;->serializer:Lnet/minecraft/network/codec/StreamCodec;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$CodecEntry;->modifier:Lnet/minecraft/network/protocol/CodecModifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CodecEntry.class), CodecEntry.class, "type;serializer;modifier", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$CodecEntry;->type:Lnet/minecraft/network/protocol/PacketType;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$CodecEntry;->serializer:Lnet/minecraft/network/codec/StreamCodec;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$CodecEntry;->modifier:Lnet/minecraft/network/protocol/CodecModifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CodecEntry.class, Object.class), CodecEntry.class, "type;serializer;modifier", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$CodecEntry;->type:Lnet/minecraft/network/protocol/PacketType;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$CodecEntry;->serializer:Lnet/minecraft/network/codec/StreamCodec;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$CodecEntry;->modifier:Lnet/minecraft/network/protocol/CodecModifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public PacketType<P> type() {
            return this.type;
        }

        public StreamCodec<? super B, P> serializer() {
            return this.serializer;
        }

        public CodecModifier<B, P, C> modifier() {
            return this.modifier;
        }

        public void addToBuilder(ProtocolCodecBuilder<ByteBuf, T> protocolCodecBuilder, Function<ByteBuf, B> function, C c) {
            StreamCodec<? super B, P> streamCodecApply;
            if (this.modifier != null) {
                streamCodecApply = this.modifier.apply(this.serializer, c);
            } else {
                streamCodecApply = this.serializer;
            }
            protocolCodecBuilder.add(this.type, streamCodecApply.mapStream(function));
        }
    }

    public <P extends Packet<? super T>> ProtocolInfoBuilder<T, B, C> addPacket(PacketType<P> $$0, StreamCodec<? super B, P> $$1) {
        this.codecs.add(new CodecEntry<>($$0, $$1, null));
        return this;
    }

    public <P extends Packet<? super T>> ProtocolInfoBuilder<T, B, C> addPacket(PacketType<P> $$0, StreamCodec<? super B, P> $$1, CodecModifier<B, P, C> $$2) {
        this.codecs.add(new CodecEntry<>($$0, $$1, $$2));
        return this;
    }

    public <P extends BundlePacket<? super T>, D extends BundleDelimiterPacket<? super T>> ProtocolInfoBuilder<T, B, C> withBundlePacket(PacketType<P> $$0, Function<Iterable<Packet<? super T>>, P> $$1, D $$2) {
        StreamCodec<ByteBuf, D> $$3 = StreamCodec.unit($$2);
        this.codecs.add(new CodecEntry<>($$2.type(), $$3, null));
        this.bundlerInfo = BundlerInfo.createForPacket($$0, $$1, $$2);
        return this;
    }

    StreamCodec<ByteBuf, Packet<? super T>> buildPacketCodec(Function<ByteBuf, B> function, List<CodecEntry<T, ?, B, C>> list, C c) {
        ProtocolCodecBuilder<ByteBuf, T> protocolCodecBuilder = new ProtocolCodecBuilder<>(this.flow);
        Iterator<CodecEntry<T, ?, B, C>> it = list.iterator();
        while (it.hasNext()) {
            it.next().addToBuilder(protocolCodecBuilder, function, c);
        }
        return protocolCodecBuilder.build();
    }

    private static ProtocolInfo.Details buildDetails(final ConnectionProtocol $$0, final PacketFlow $$1, final List<? extends CodecEntry<?, ?, ?, ?>> $$2) {
        return new ProtocolInfo.Details() { // from class: net.minecraft.network.protocol.ProtocolInfoBuilder.1
            @Override // net.minecraft.network.ProtocolInfo.Details
            public ConnectionProtocol id() {
                return $$0;
            }

            @Override // net.minecraft.network.ProtocolInfo.Details
            public PacketFlow flow() {
                return $$1;
            }

            @Override // net.minecraft.network.ProtocolInfo.Details
            public void listPackets(ProtocolInfo.Details.PacketVisitor packetVisitor) {
                for (int $$12 = 0; $$12 < $$2.size(); $$12++) {
                    CodecEntry<?, ?, ?, ?> $$22 = (CodecEntry) $$2.get($$12);
                    packetVisitor.accept(((CodecEntry) $$22).type, $$12);
                }
            }
        };
    }

    public SimpleUnboundProtocol<T, B> buildUnbound(final C c) {
        final List listCopyOf = List.copyOf(this.codecs);
        final BundlerInfo bundlerInfo = this.bundlerInfo;
        final ProtocolInfo.Details detailsBuildDetails = buildDetails(this.protocol, this.flow, listCopyOf);
        return (SimpleUnboundProtocol<T, B>) new SimpleUnboundProtocol<T, B>() { // from class: net.minecraft.network.protocol.ProtocolInfoBuilder.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // net.minecraft.network.protocol.SimpleUnboundProtocol
            public ProtocolInfo<T> bind(Function<ByteBuf, B> $$0) {
                return new Implementation(ProtocolInfoBuilder.this.protocol, ProtocolInfoBuilder.this.flow, ProtocolInfoBuilder.this.buildPacketCodec($$0, listCopyOf, c), bundlerInfo);
            }

            @Override // net.minecraft.network.ProtocolInfo.DetailsProvider
            public ProtocolInfo.Details details() {
                return detailsBuildDetails;
            }
        };
    }

    public UnboundProtocol<T, B, C> buildUnbound() {
        final List listCopyOf = List.copyOf(this.codecs);
        final BundlerInfo bundlerInfo = this.bundlerInfo;
        final ProtocolInfo.Details detailsBuildDetails = buildDetails(this.protocol, this.flow, listCopyOf);
        return (UnboundProtocol<T, B, C>) new UnboundProtocol<T, B, C>() { // from class: net.minecraft.network.protocol.ProtocolInfoBuilder.3
            @Override // net.minecraft.network.protocol.UnboundProtocol
            public ProtocolInfo<T> bind(Function<ByteBuf, B> $$0, C $$1) {
                return new Implementation(ProtocolInfoBuilder.this.protocol, ProtocolInfoBuilder.this.flow, ProtocolInfoBuilder.this.buildPacketCodec($$0, listCopyOf, $$1), bundlerInfo);
            }

            @Override // net.minecraft.network.ProtocolInfo.DetailsProvider
            public ProtocolInfo.Details details() {
                return detailsBuildDetails;
            }
        };
    }

    private static <L extends PacketListener, B extends ByteBuf> SimpleUnboundProtocol<L, B> protocol(ConnectionProtocol connectionProtocol, PacketFlow packetFlow, Consumer<ProtocolInfoBuilder<L, B, Unit>> consumer) {
        ProtocolInfoBuilder<L, B, Unit> protocolInfoBuilder = new ProtocolInfoBuilder<>(connectionProtocol, packetFlow);
        consumer.accept(protocolInfoBuilder);
        return protocolInfoBuilder.buildUnbound(Unit.INSTANCE);
    }

    public static <T extends ServerboundPacketListener, B extends ByteBuf> SimpleUnboundProtocol<T, B> serverboundProtocol(ConnectionProtocol $$0, Consumer<ProtocolInfoBuilder<T, B, Unit>> $$1) {
        return protocol($$0, PacketFlow.SERVERBOUND, $$1);
    }

    public static <T extends ClientboundPacketListener, B extends ByteBuf> SimpleUnboundProtocol<T, B> clientboundProtocol(ConnectionProtocol $$0, Consumer<ProtocolInfoBuilder<T, B, Unit>> $$1) {
        return protocol($$0, PacketFlow.CLIENTBOUND, $$1);
    }

    private static <L extends PacketListener, B extends ByteBuf, C> UnboundProtocol<L, B, C> contextProtocol(ConnectionProtocol connectionProtocol, PacketFlow packetFlow, Consumer<ProtocolInfoBuilder<L, B, C>> consumer) {
        ProtocolInfoBuilder<L, B, C> protocolInfoBuilder = new ProtocolInfoBuilder<>(connectionProtocol, packetFlow);
        consumer.accept(protocolInfoBuilder);
        return protocolInfoBuilder.buildUnbound();
    }

    public static <T extends ServerboundPacketListener, B extends ByteBuf, C> UnboundProtocol<T, B, C> contextServerboundProtocol(ConnectionProtocol $$0, Consumer<ProtocolInfoBuilder<T, B, C>> $$1) {
        return contextProtocol($$0, PacketFlow.SERVERBOUND, $$1);
    }

    public static <T extends ClientboundPacketListener, B extends ByteBuf, C> UnboundProtocol<T, B, C> contextClientboundProtocol(ConnectionProtocol $$0, Consumer<ProtocolInfoBuilder<T, B, C>> $$1) {
        return contextProtocol($$0, PacketFlow.CLIENTBOUND, $$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/ProtocolInfoBuilder$Implementation.class */
    static final class Implementation<L extends PacketListener> extends Record implements ProtocolInfo<L> {
        private final ConnectionProtocol id;
        private final PacketFlow flow;
        private final StreamCodec<ByteBuf, Packet<? super L>> codec;
        private final BundlerInfo bundlerInfo;

        Implementation(ConnectionProtocol $$0, PacketFlow $$1, StreamCodec<ByteBuf, Packet<? super L>> $$2, BundlerInfo $$3) {
            this.id = $$0;
            this.flow = $$1;
            this.codec = $$2;
            this.bundlerInfo = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Implementation.class), Implementation.class, "id;flow;codec;bundlerInfo", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$Implementation;->id:Lnet/minecraft/network/ConnectionProtocol;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$Implementation;->flow:Lnet/minecraft/network/protocol/PacketFlow;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$Implementation;->codec:Lnet/minecraft/network/codec/StreamCodec;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$Implementation;->bundlerInfo:Lnet/minecraft/network/protocol/BundlerInfo;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Implementation.class), Implementation.class, "id;flow;codec;bundlerInfo", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$Implementation;->id:Lnet/minecraft/network/ConnectionProtocol;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$Implementation;->flow:Lnet/minecraft/network/protocol/PacketFlow;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$Implementation;->codec:Lnet/minecraft/network/codec/StreamCodec;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$Implementation;->bundlerInfo:Lnet/minecraft/network/protocol/BundlerInfo;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Implementation.class, Object.class), Implementation.class, "id;flow;codec;bundlerInfo", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$Implementation;->id:Lnet/minecraft/network/ConnectionProtocol;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$Implementation;->flow:Lnet/minecraft/network/protocol/PacketFlow;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$Implementation;->codec:Lnet/minecraft/network/codec/StreamCodec;", "FIELD:Lnet/minecraft/network/protocol/ProtocolInfoBuilder$Implementation;->bundlerInfo:Lnet/minecraft/network/protocol/BundlerInfo;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.network.ProtocolInfo
        public ConnectionProtocol id() {
            return this.id;
        }

        @Override // net.minecraft.network.ProtocolInfo
        public PacketFlow flow() {
            return this.flow;
        }

        @Override // net.minecraft.network.ProtocolInfo
        public StreamCodec<ByteBuf, Packet<? super L>> codec() {
            return this.codec;
        }

        @Override // net.minecraft.network.ProtocolInfo
        public BundlerInfo bundlerInfo() {
            return this.bundlerInfo;
        }
    }
}
