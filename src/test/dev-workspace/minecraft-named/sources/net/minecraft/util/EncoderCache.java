package net.minecraft.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.nbt.Tag;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/EncoderCache.class */
public class EncoderCache {
    final LoadingCache<Key<?, ?>, DataResult<?>> cache;

    public EncoderCache(int $$0) {
        this.cache = CacheBuilder.newBuilder().maximumSize($$0).concurrencyLevel(1).softValues().build(new CacheLoader<Key<?, ?>, DataResult<?>>(this) { // from class: net.minecraft.util.EncoderCache.1
            public DataResult<?> load(Key<?, ?> $$02) {
                return $$02.resolve();
            }
        });
    }

    public <A> Codec<A> wrap(final Codec<A> $$0) {
        return new Codec<A>() { // from class: net.minecraft.util.EncoderCache.2
            public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> $$02, T $$1) {
                return $$0.decode($$02, $$1);
            }

            public <T> DataResult<T> encode(A $$02, DynamicOps<T> $$1, T $$2) {
                return ((DataResult) EncoderCache.this.cache.getUnchecked(new Key($$0, $$02, $$1))).map($$03 -> {
                    if ($$03 instanceof Tag) {
                        Tag $$12 = (Tag) $$03;
                        return $$12.copy();
                    }
                    return $$03;
                });
            }
        };
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/EncoderCache$Key.class */
    static final class Key<A, T> extends Record {
        private final Codec<A> codec;
        private final A value;
        private final DynamicOps<T> ops;

        Key(Codec<A> $$0, A $$1, DynamicOps<T> $$2) {
            this.codec = $$0;
            this.value = $$1;
            this.ops = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Key.class), Key.class, "codec;value;ops", "FIELD:Lnet/minecraft/util/EncoderCache$Key;->codec:Lcom/mojang/serialization/Codec;", "FIELD:Lnet/minecraft/util/EncoderCache$Key;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/EncoderCache$Key;->ops:Lcom/mojang/serialization/DynamicOps;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public Codec<A> codec() {
            return this.codec;
        }

        public A value() {
            return this.value;
        }

        public DynamicOps<T> ops() {
            return this.ops;
        }

        public DataResult<T> resolve() {
            return this.codec.encodeStart(this.ops, this.value);
        }

        @Override // java.lang.Record
        public boolean equals(Object $$0) {
            if (this == $$0) {
                return true;
            }
            if (!($$0 instanceof Key)) {
                return false;
            }
            Key<?, ?> $$1 = (Key) $$0;
            return this.codec == $$1.codec && this.value.equals($$1.value) && this.ops.equals($$1.ops);
        }

        @Override // java.lang.Record
        public int hashCode() {
            int $$0 = System.identityHashCode(this.codec);
            return (31 * ((31 * $$0) + this.value.hashCode())) + this.ops.hashCode();
        }
    }
}
