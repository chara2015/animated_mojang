package net.minecraft.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.network.VarInt;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/codec/IdDispatchCodec.class */
public class IdDispatchCodec<B extends ByteBuf, V, T> implements StreamCodec<B, V> {
    private static final int UNKNOWN_TYPE = -1;
    private final Function<V, ? extends T> typeGetter;
    private final List<Entry<B, V, T>> byId;
    private final Object2IntMap<T> toId;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/codec/IdDispatchCodec$DontDecorateException.class */
    public interface DontDecorateException {
    }

    IdDispatchCodec(Function<V, ? extends T> $$0, List<Entry<B, V, T>> $$1, Object2IntMap<T> $$2) {
        this.typeGetter = $$0;
        this.byId = $$1;
        this.toId = $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.DecoderException */
    @Override // net.minecraft.network.codec.StreamDecoder
    public V decode(B $$0) throws Exception {
        int $$1 = VarInt.read($$0);
        if ($$1 < 0 || $$1 >= this.byId.size()) {
            throw new DecoderException("Received unknown packet id " + $$1);
        }
        Entry<B, V, T> $$2 = this.byId.get($$1);
        try {
            return ((Entry) $$2).serializer.decode($$0);
        } catch (Exception $$3) {
            if ($$3 instanceof DontDecorateException) {
                throw $$3;
            }
            throw new DecoderException("Failed to decode packet '" + String.valueOf(((Entry) $$2).type) + "'", $$3);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
    @Override // net.minecraft.network.codec.StreamEncoder
    public void encode(B $$0, V $$1) throws Exception {
        T $$2 = this.typeGetter.apply($$1);
        int $$3 = this.toId.getOrDefault($$2, -1);
        if ($$3 == -1) {
            throw new EncoderException("Sending unknown packet '" + String.valueOf($$2) + "'");
        }
        VarInt.write($$0, $$3);
        Entry<B, V, T> $$4 = this.byId.get($$3);
        try {
            ((Entry) $$4).serializer.encode($$0, $$1);
        } catch (Exception $$6) {
            if ($$6 instanceof DontDecorateException) {
                throw $$6;
            }
            throw new EncoderException("Failed to encode packet '" + String.valueOf($$2) + "'", $$6);
        }
    }

    public static <B extends ByteBuf, V, T> Builder<B, V, T> builder(Function<V, ? extends T> $$0) {
        return new Builder<>($$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/codec/IdDispatchCodec$Builder.class */
    public static class Builder<B extends ByteBuf, V, T> {
        private final List<Entry<B, V, T>> entries = new ArrayList();
        private final Function<V, ? extends T> typeGetter;

        Builder(Function<V, ? extends T> $$0) {
            this.typeGetter = $$0;
        }

        public Builder<B, V, T> add(T $$0, StreamCodec<? super B, ? extends V> $$1) {
            this.entries.add(new Entry<>($$1, $$0));
            return this;
        }

        public IdDispatchCodec<B, V, T> build() {
            Object2IntOpenHashMap<T> $$0 = new Object2IntOpenHashMap<>();
            $$0.defaultReturnValue(-2);
            for (Entry<B, V, T> $$1 : this.entries) {
                int $$2 = $$0.size();
                int $$3 = $$0.putIfAbsent(((Entry) $$1).type, $$2);
                if ($$3 != -2) {
                    throw new IllegalStateException("Duplicate registration for type " + String.valueOf(((Entry) $$1).type));
                }
            }
            return new IdDispatchCodec<>(this.typeGetter, List.copyOf(this.entries), $$0);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/codec/IdDispatchCodec$Entry.class */
    static final class Entry<B, V, T> extends Record {
        private final StreamCodec<? super B, ? extends V> serializer;
        private final T type;

        Entry(StreamCodec<? super B, ? extends V> $$0, T $$1) {
            this.serializer = $$0;
            this.type = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Entry.class), Entry.class, "serializer;type", "FIELD:Lnet/minecraft/network/codec/IdDispatchCodec$Entry;->serializer:Lnet/minecraft/network/codec/StreamCodec;", "FIELD:Lnet/minecraft/network/codec/IdDispatchCodec$Entry;->type:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Entry.class), Entry.class, "serializer;type", "FIELD:Lnet/minecraft/network/codec/IdDispatchCodec$Entry;->serializer:Lnet/minecraft/network/codec/StreamCodec;", "FIELD:Lnet/minecraft/network/codec/IdDispatchCodec$Entry;->type:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Entry.class, Object.class), Entry.class, "serializer;type", "FIELD:Lnet/minecraft/network/codec/IdDispatchCodec$Entry;->serializer:Lnet/minecraft/network/codec/StreamCodec;", "FIELD:Lnet/minecraft/network/codec/IdDispatchCodec$Entry;->type:Ljava/lang/Object;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public StreamCodec<? super B, ? extends V> serializer() {
            return this.serializer;
        }

        public T type() {
            return this.type;
        }
    }
}
