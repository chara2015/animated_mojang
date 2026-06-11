package net.minecraft.network.codec;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Function10;
import com.mojang.datafixers.util.Function11;
import com.mojang.datafixers.util.Function12;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import com.mojang.datafixers.util.Function6;
import com.mojang.datafixers.util.Function7;
import com.mojang.datafixers.util.Function8;
import com.mojang.datafixers.util.Function9;
import io.netty.buffer.ByteBuf;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/codec/StreamCodec.class */
public interface StreamCodec<B, V> extends StreamDecoder<B, V>, StreamEncoder<B, V> {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/codec/StreamCodec$CodecOperation.class */
    @FunctionalInterface
    public interface CodecOperation<B, S, T> {
        StreamCodec<B, T> apply(StreamCodec<B, S> streamCodec);
    }

    static <B, V> StreamCodec<B, V> of(final StreamEncoder<B, V> $$0, final StreamDecoder<B, V> $$1) {
        return new StreamCodec<B, V>() { // from class: net.minecraft.network.codec.StreamCodec.1
            @Override // net.minecraft.network.codec.StreamDecoder
            public V decode(B b) {
                return (V) $$1.decode(b);
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, V $$12) {
                $$0.encode($$02, $$12);
            }
        };
    }

    static <B, V> StreamCodec<B, V> ofMember(final StreamMemberEncoder<B, V> $$0, final StreamDecoder<B, V> $$1) {
        return new StreamCodec<B, V>() { // from class: net.minecraft.network.codec.StreamCodec.2
            @Override // net.minecraft.network.codec.StreamDecoder
            public V decode(B b) {
                return (V) $$1.decode(b);
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, V $$12) {
                $$0.encode($$12, $$02);
            }
        };
    }

    static <B, V> StreamCodec<B, V> unit(final V $$0) {
        return new StreamCodec<B, V>() { // from class: net.minecraft.network.codec.StreamCodec.3
            @Override // net.minecraft.network.codec.StreamDecoder
            public V decode(B b) {
                return (V) $$0;
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, V $$1) {
                if (!$$1.equals($$0)) {
                    throw new IllegalStateException("Can't encode '" + String.valueOf($$1) + "', expected '" + String.valueOf($$0) + "'");
                }
            }
        };
    }

    default <O> StreamCodec<B, O> apply(CodecOperation<B, V, O> $$0) {
        return $$0.apply(this);
    }

    default <O> StreamCodec<B, O> map(final Function<? super V, ? extends O> $$0, final Function<? super O, ? extends V> $$1) {
        return new StreamCodec<B, O>() { // from class: net.minecraft.network.codec.StreamCodec.4
            @Override // net.minecraft.network.codec.StreamDecoder
            public O decode(B b) {
                return (O) $$0.apply(StreamCodec.this.decode(b));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, O $$12) {
                StreamCodec.this.encode($$02, $$1.apply($$12));
            }
        };
    }

    default <O extends ByteBuf> StreamCodec<O, V> mapStream(final Function<O, ? extends B> function) {
        return (StreamCodec<O, V>) new StreamCodec<O, V>() { // from class: net.minecraft.network.codec.StreamCodec.5
            /* JADX WARN: Incorrect types in method signature: (TO;)TV; */
            @Override // net.minecraft.network.codec.StreamDecoder
            public Object decode(ByteBuf byteBuf) {
                return StreamCodec.this.decode(function.apply(byteBuf));
            }

            /* JADX WARN: Incorrect types in method signature: (TO;TV;)V */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(ByteBuf byteBuf, Object obj) {
                StreamCodec.this.encode(function.apply(byteBuf), obj);
            }
        };
    }

    default <U> StreamCodec<B, U> dispatch(final Function<? super U, ? extends V> $$0, final Function<? super V, ? extends StreamCodec<? super B, ? extends U>> $$1) {
        return new StreamCodec<B, U>() { // from class: net.minecraft.network.codec.StreamCodec.6
            @Override // net.minecraft.network.codec.StreamDecoder
            public U decode(B b) {
                return (U) ((StreamCodec) $$1.apply(StreamCodec.this.decode(b))).decode(b);
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, U $$12) {
                Object objApply = $$0.apply($$12);
                StreamCodec<B, U> $$3 = (StreamCodec) $$1.apply(objApply);
                StreamCodec.this.encode($$02, objApply);
                $$3.encode($$02, $$12);
            }
        };
    }

    static <B, C, T1> StreamCodec<B, C> composite(StreamCodec<? super B, T1> $$0, final Function<C, T1> $$1, final Function<T1, C> $$2) {
        return new StreamCodec<B, C>() { // from class: net.minecraft.network.codec.StreamCodec.7
            @Override // net.minecraft.network.codec.StreamDecoder
            public C decode(B b) {
                return (C) $$2.apply(StreamCodec.this.decode(b));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, C $$12) {
                StreamCodec.this.encode($$02, $$1.apply($$12));
            }
        };
    }

    static <B, C, T1, T2> StreamCodec<B, C> composite(StreamCodec<? super B, T1> $$0, final Function<C, T1> $$1, final StreamCodec<? super B, T2> $$2, final Function<C, T2> $$3, final BiFunction<T1, T2, C> $$4) {
        return new StreamCodec<B, C>() { // from class: net.minecraft.network.codec.StreamCodec.8
            @Override // net.minecraft.network.codec.StreamDecoder
            public C decode(B b) {
                return (C) $$4.apply(StreamCodec.this.decode(b), $$2.decode(b));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, C $$12) {
                StreamCodec.this.encode($$02, $$1.apply($$12));
                $$2.encode($$02, $$3.apply($$12));
            }
        };
    }

    static <B, C, T1, T2, T3> StreamCodec<B, C> composite(StreamCodec<? super B, T1> $$0, final Function<C, T1> $$1, final StreamCodec<? super B, T2> $$2, final Function<C, T2> $$3, final StreamCodec<? super B, T3> $$4, final Function<C, T3> $$5, final Function3<T1, T2, T3, C> $$6) {
        return new StreamCodec<B, C>() { // from class: net.minecraft.network.codec.StreamCodec.9
            @Override // net.minecraft.network.codec.StreamDecoder
            public C decode(B b) {
                return (C) $$6.apply(StreamCodec.this.decode(b), $$2.decode(b), $$4.decode(b));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, C $$12) {
                StreamCodec.this.encode($$02, $$1.apply($$12));
                $$2.encode($$02, $$3.apply($$12));
                $$4.encode($$02, $$5.apply($$12));
            }
        };
    }

    static <B, C, T1, T2, T3, T4> StreamCodec<B, C> composite(StreamCodec<? super B, T1> $$0, final Function<C, T1> $$1, final StreamCodec<? super B, T2> $$2, final Function<C, T2> $$3, final StreamCodec<? super B, T3> $$4, final Function<C, T3> $$5, final StreamCodec<? super B, T4> $$6, final Function<C, T4> $$7, final Function4<T1, T2, T3, T4, C> $$8) {
        return new StreamCodec<B, C>() { // from class: net.minecraft.network.codec.StreamCodec.10
            @Override // net.minecraft.network.codec.StreamDecoder
            public C decode(B b) {
                return (C) $$8.apply(StreamCodec.this.decode(b), $$2.decode(b), $$4.decode(b), $$6.decode(b));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, C $$12) {
                StreamCodec.this.encode($$02, $$1.apply($$12));
                $$2.encode($$02, $$3.apply($$12));
                $$4.encode($$02, $$5.apply($$12));
                $$6.encode($$02, $$7.apply($$12));
            }
        };
    }

    static <B, C, T1, T2, T3, T4, T5> StreamCodec<B, C> composite(StreamCodec<? super B, T1> $$0, final Function<C, T1> $$1, final StreamCodec<? super B, T2> $$2, final Function<C, T2> $$3, final StreamCodec<? super B, T3> $$4, final Function<C, T3> $$5, final StreamCodec<? super B, T4> $$6, final Function<C, T4> $$7, final StreamCodec<? super B, T5> $$8, final Function<C, T5> $$9, final Function5<T1, T2, T3, T4, T5, C> $$10) {
        return new StreamCodec<B, C>() { // from class: net.minecraft.network.codec.StreamCodec.11
            @Override // net.minecraft.network.codec.StreamDecoder
            public C decode(B b) {
                return (C) $$10.apply(StreamCodec.this.decode(b), $$2.decode(b), $$4.decode(b), $$6.decode(b), $$8.decode(b));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, C $$12) {
                StreamCodec.this.encode($$02, $$1.apply($$12));
                $$2.encode($$02, $$3.apply($$12));
                $$4.encode($$02, $$5.apply($$12));
                $$6.encode($$02, $$7.apply($$12));
                $$8.encode($$02, $$9.apply($$12));
            }
        };
    }

    static <B, C, T1, T2, T3, T4, T5, T6> StreamCodec<B, C> composite(StreamCodec<? super B, T1> $$0, final Function<C, T1> $$1, final StreamCodec<? super B, T2> $$2, final Function<C, T2> $$3, final StreamCodec<? super B, T3> $$4, final Function<C, T3> $$5, final StreamCodec<? super B, T4> $$6, final Function<C, T4> $$7, final StreamCodec<? super B, T5> $$8, final Function<C, T5> $$9, final StreamCodec<? super B, T6> $$10, final Function<C, T6> $$11, final Function6<T1, T2, T3, T4, T5, T6, C> $$12) {
        return new StreamCodec<B, C>() { // from class: net.minecraft.network.codec.StreamCodec.12
            @Override // net.minecraft.network.codec.StreamDecoder
            public C decode(B b) {
                return (C) $$12.apply(StreamCodec.this.decode(b), $$2.decode(b), $$4.decode(b), $$6.decode(b), $$8.decode(b), $$10.decode(b));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, C $$13) {
                StreamCodec.this.encode($$02, $$1.apply($$13));
                $$2.encode($$02, $$3.apply($$13));
                $$4.encode($$02, $$5.apply($$13));
                $$6.encode($$02, $$7.apply($$13));
                $$8.encode($$02, $$9.apply($$13));
                $$10.encode($$02, $$11.apply($$13));
            }
        };
    }

    static <B, C, T1, T2, T3, T4, T5, T6, T7> StreamCodec<B, C> composite(StreamCodec<? super B, T1> $$0, final Function<C, T1> $$1, final StreamCodec<? super B, T2> $$2, final Function<C, T2> $$3, final StreamCodec<? super B, T3> $$4, final Function<C, T3> $$5, final StreamCodec<? super B, T4> $$6, final Function<C, T4> $$7, final StreamCodec<? super B, T5> $$8, final Function<C, T5> $$9, final StreamCodec<? super B, T6> $$10, final Function<C, T6> $$11, final StreamCodec<? super B, T7> $$12, final Function<C, T7> $$13, final Function7<T1, T2, T3, T4, T5, T6, T7, C> $$14) {
        return new StreamCodec<B, C>() { // from class: net.minecraft.network.codec.StreamCodec.13
            @Override // net.minecraft.network.codec.StreamDecoder
            public C decode(B b) {
                return (C) $$14.apply(StreamCodec.this.decode(b), $$2.decode(b), $$4.decode(b), $$6.decode(b), $$8.decode(b), $$10.decode(b), $$12.decode(b));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, C $$15) {
                StreamCodec.this.encode($$02, $$1.apply($$15));
                $$2.encode($$02, $$3.apply($$15));
                $$4.encode($$02, $$5.apply($$15));
                $$6.encode($$02, $$7.apply($$15));
                $$8.encode($$02, $$9.apply($$15));
                $$10.encode($$02, $$11.apply($$15));
                $$12.encode($$02, $$13.apply($$15));
            }
        };
    }

    static <B, C, T1, T2, T3, T4, T5, T6, T7, T8> StreamCodec<B, C> composite(StreamCodec<? super B, T1> $$0, final Function<C, T1> $$1, final StreamCodec<? super B, T2> $$2, final Function<C, T2> $$3, final StreamCodec<? super B, T3> $$4, final Function<C, T3> $$5, final StreamCodec<? super B, T4> $$6, final Function<C, T4> $$7, final StreamCodec<? super B, T5> $$8, final Function<C, T5> $$9, final StreamCodec<? super B, T6> $$10, final Function<C, T6> $$11, final StreamCodec<? super B, T7> $$12, final Function<C, T7> $$13, final StreamCodec<? super B, T8> $$14, final Function<C, T8> $$15, final Function8<T1, T2, T3, T4, T5, T6, T7, T8, C> $$16) {
        return new StreamCodec<B, C>() { // from class: net.minecraft.network.codec.StreamCodec.14
            @Override // net.minecraft.network.codec.StreamDecoder
            public C decode(B b) {
                return (C) $$16.apply(StreamCodec.this.decode(b), $$2.decode(b), $$4.decode(b), $$6.decode(b), $$8.decode(b), $$10.decode(b), $$12.decode(b), $$14.decode(b));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, C $$17) {
                StreamCodec.this.encode($$02, $$1.apply($$17));
                $$2.encode($$02, $$3.apply($$17));
                $$4.encode($$02, $$5.apply($$17));
                $$6.encode($$02, $$7.apply($$17));
                $$8.encode($$02, $$9.apply($$17));
                $$10.encode($$02, $$11.apply($$17));
                $$12.encode($$02, $$13.apply($$17));
                $$14.encode($$02, $$15.apply($$17));
            }
        };
    }

    static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9> StreamCodec<B, C> composite(StreamCodec<? super B, T1> $$0, final Function<C, T1> $$1, final StreamCodec<? super B, T2> $$2, final Function<C, T2> $$3, final StreamCodec<? super B, T3> $$4, final Function<C, T3> $$5, final StreamCodec<? super B, T4> $$6, final Function<C, T4> $$7, final StreamCodec<? super B, T5> $$8, final Function<C, T5> $$9, final StreamCodec<? super B, T6> $$10, final Function<C, T6> $$11, final StreamCodec<? super B, T7> $$12, final Function<C, T7> $$13, final StreamCodec<? super B, T8> $$14, final Function<C, T8> $$15, final StreamCodec<? super B, T9> $$16, final Function<C, T9> $$17, final Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, C> $$18) {
        return new StreamCodec<B, C>() { // from class: net.minecraft.network.codec.StreamCodec.15
            @Override // net.minecraft.network.codec.StreamDecoder
            public C decode(B b) {
                return (C) $$18.apply(StreamCodec.this.decode(b), $$2.decode(b), $$4.decode(b), $$6.decode(b), $$8.decode(b), $$10.decode(b), $$12.decode(b), $$14.decode(b), $$16.decode(b));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, C $$19) {
                StreamCodec.this.encode($$02, $$1.apply($$19));
                $$2.encode($$02, $$3.apply($$19));
                $$4.encode($$02, $$5.apply($$19));
                $$6.encode($$02, $$7.apply($$19));
                $$8.encode($$02, $$9.apply($$19));
                $$10.encode($$02, $$11.apply($$19));
                $$12.encode($$02, $$13.apply($$19));
                $$14.encode($$02, $$15.apply($$19));
                $$16.encode($$02, $$17.apply($$19));
            }
        };
    }

    static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> StreamCodec<B, C> composite(StreamCodec<? super B, T1> $$0, final Function<C, T1> $$1, final StreamCodec<? super B, T2> $$2, final Function<C, T2> $$3, final StreamCodec<? super B, T3> $$4, final Function<C, T3> $$5, final StreamCodec<? super B, T4> $$6, final Function<C, T4> $$7, final StreamCodec<? super B, T5> $$8, final Function<C, T5> $$9, final StreamCodec<? super B, T6> $$10, final Function<C, T6> $$11, final StreamCodec<? super B, T7> $$12, final Function<C, T7> $$13, final StreamCodec<? super B, T8> $$14, final Function<C, T8> $$15, final StreamCodec<? super B, T9> $$16, final Function<C, T9> $$17, final StreamCodec<? super B, T10> $$18, final Function<C, T10> $$19, final Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, C> $$20) {
        return new StreamCodec<B, C>() { // from class: net.minecraft.network.codec.StreamCodec.16
            @Override // net.minecraft.network.codec.StreamDecoder
            public C decode(B b) {
                return (C) $$20.apply(StreamCodec.this.decode(b), $$2.decode(b), $$4.decode(b), $$6.decode(b), $$8.decode(b), $$10.decode(b), $$12.decode(b), $$14.decode(b), $$16.decode(b), $$18.decode(b));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, C $$110) {
                StreamCodec.this.encode($$02, $$1.apply($$110));
                $$2.encode($$02, $$3.apply($$110));
                $$4.encode($$02, $$5.apply($$110));
                $$6.encode($$02, $$7.apply($$110));
                $$8.encode($$02, $$9.apply($$110));
                $$10.encode($$02, $$11.apply($$110));
                $$12.encode($$02, $$13.apply($$110));
                $$14.encode($$02, $$15.apply($$110));
                $$16.encode($$02, $$17.apply($$110));
                $$18.encode($$02, $$19.apply($$110));
            }
        };
    }

    static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> StreamCodec<B, C> composite(StreamCodec<? super B, T1> $$0, final Function<C, T1> $$1, final StreamCodec<? super B, T2> $$2, final Function<C, T2> $$3, final StreamCodec<? super B, T3> $$4, final Function<C, T3> $$5, final StreamCodec<? super B, T4> $$6, final Function<C, T4> $$7, final StreamCodec<? super B, T5> $$8, final Function<C, T5> $$9, final StreamCodec<? super B, T6> $$10, final Function<C, T6> $$11, final StreamCodec<? super B, T7> $$12, final Function<C, T7> $$13, final StreamCodec<? super B, T8> $$14, final Function<C, T8> $$15, final StreamCodec<? super B, T9> $$16, final Function<C, T9> $$17, final StreamCodec<? super B, T10> $$18, final Function<C, T10> $$19, final StreamCodec<? super B, T11> $$20, final Function<C, T11> $$21, final Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, C> $$22) {
        return new StreamCodec<B, C>() { // from class: net.minecraft.network.codec.StreamCodec.17
            @Override // net.minecraft.network.codec.StreamDecoder
            public C decode(B b) {
                return (C) $$22.apply(StreamCodec.this.decode(b), $$2.decode(b), $$4.decode(b), $$6.decode(b), $$8.decode(b), $$10.decode(b), $$12.decode(b), $$14.decode(b), $$16.decode(b), $$18.decode(b), $$20.decode(b));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, C $$110) {
                StreamCodec.this.encode($$02, $$1.apply($$110));
                $$2.encode($$02, $$3.apply($$110));
                $$4.encode($$02, $$5.apply($$110));
                $$6.encode($$02, $$7.apply($$110));
                $$8.encode($$02, $$9.apply($$110));
                $$10.encode($$02, $$11.apply($$110));
                $$12.encode($$02, $$13.apply($$110));
                $$14.encode($$02, $$15.apply($$110));
                $$16.encode($$02, $$17.apply($$110));
                $$18.encode($$02, $$19.apply($$110));
                $$20.encode($$02, $$21.apply($$110));
            }
        };
    }

    static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> StreamCodec<B, C> composite(StreamCodec<? super B, T1> $$0, final Function<C, T1> $$1, final StreamCodec<? super B, T2> $$2, final Function<C, T2> $$3, final StreamCodec<? super B, T3> $$4, final Function<C, T3> $$5, final StreamCodec<? super B, T4> $$6, final Function<C, T4> $$7, final StreamCodec<? super B, T5> $$8, final Function<C, T5> $$9, final StreamCodec<? super B, T6> $$10, final Function<C, T6> $$11, final StreamCodec<? super B, T7> $$12, final Function<C, T7> $$13, final StreamCodec<? super B, T8> $$14, final Function<C, T8> $$15, final StreamCodec<? super B, T9> $$16, final Function<C, T9> $$17, final StreamCodec<? super B, T10> $$18, final Function<C, T10> $$19, final StreamCodec<? super B, T11> $$20, final Function<C, T11> $$21, final StreamCodec<? super B, T12> $$22, final Function<C, T12> $$23, final Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, C> $$24) {
        return new StreamCodec<B, C>() { // from class: net.minecraft.network.codec.StreamCodec.18
            @Override // net.minecraft.network.codec.StreamDecoder
            public C decode(B b) {
                return (C) $$24.apply(StreamCodec.this.decode(b), $$2.decode(b), $$4.decode(b), $$6.decode(b), $$8.decode(b), $$10.decode(b), $$12.decode(b), $$14.decode(b), $$16.decode(b), $$18.decode(b), $$20.decode(b), $$22.decode(b));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, C $$110) {
                StreamCodec.this.encode($$02, $$1.apply($$110));
                $$2.encode($$02, $$3.apply($$110));
                $$4.encode($$02, $$5.apply($$110));
                $$6.encode($$02, $$7.apply($$110));
                $$8.encode($$02, $$9.apply($$110));
                $$10.encode($$02, $$11.apply($$110));
                $$12.encode($$02, $$13.apply($$110));
                $$14.encode($$02, $$15.apply($$110));
                $$16.encode($$02, $$17.apply($$110));
                $$18.encode($$02, $$19.apply($$110));
                $$20.encode($$02, $$21.apply($$110));
                $$22.encode($$02, $$23.apply($$110));
            }
        };
    }

    static <B, T> StreamCodec<B, T> recursive(final UnaryOperator<StreamCodec<B, T>> $$0) {
        return new StreamCodec<B, T>() { // from class: net.minecraft.network.codec.StreamCodec.19
            private final Supplier<StreamCodec<B, T>> inner;

            {
                UnaryOperator unaryOperator = $$0;
                this.inner = Suppliers.memoize(() -> {
                    return (StreamCodec) unaryOperator.apply(this);
                });
            }

            @Override // net.minecraft.network.codec.StreamDecoder
            public T decode(B $$02) {
                return this.inner.get().decode($$02);
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(B $$02, T $$1) {
                this.inner.get().encode($$02, $$1);
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    default <S extends B> StreamCodec<S, V> cast() {
        return this;
    }
}
