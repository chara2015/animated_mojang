package net.minecraft.util.random;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.Function;
import net.minecraft.SharedConstants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Util;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/random/Weighted.class */
public final class Weighted<T> extends Record {
    private final T value;
    private final int weight;
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Weighted.class), Weighted.class, "value;weight", "FIELD:Lnet/minecraft/util/random/Weighted;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/random/Weighted;->weight:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Weighted.class), Weighted.class, "value;weight", "FIELD:Lnet/minecraft/util/random/Weighted;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/random/Weighted;->weight:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Weighted.class, Object.class), Weighted.class, "value;weight", "FIELD:Lnet/minecraft/util/random/Weighted;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/random/Weighted;->weight:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public T value() {
        return this.value;
    }

    public int weight() {
        return this.weight;
    }

    public Weighted(T $$0, int $$1) {
        if ($$1 < 0) {
            throw ((IllegalArgumentException) Util.pauseInIde(new IllegalArgumentException("Weight should be >= 0")));
        }
        if ($$1 == 0 && SharedConstants.IS_RUNNING_IN_IDE) {
            LOGGER.warn("Found 0 weight, make sure this is intentional!");
        }
        this.value = $$0;
        this.weight = $$1;
    }

    public static <E> Codec<Weighted<E>> codec(Codec<E> $$0) {
        return codec($$0.fieldOf("data"));
    }

    public static <E> Codec<Weighted<E>> codec(MapCodec<E> $$0) {
        return RecordCodecBuilder.create($$1 -> {
            return $$1.group($$0.forGetter((v0) -> {
                return v0.value();
            }), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("weight").forGetter((v0) -> {
                return v0.weight();
            })).apply($$1, (v1, v2) -> {
                return new Weighted(v1, v2);
            });
        });
    }

    public static <B extends ByteBuf, T> StreamCodec<B, Weighted<T>> streamCodec(StreamCodec<B, T> $$0) {
        return StreamCodec.composite($$0, (v0) -> {
            return v0.value();
        }, ByteBufCodecs.VAR_INT, (v0) -> {
            return v0.weight();
        }, (v1, v2) -> {
            return new Weighted(v1, v2);
        });
    }

    public <U> Weighted<U> map(Function<T, U> $$0) {
        return new Weighted<>($$0.apply(value()), this.weight);
    }
}
