package net.minecraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.Comparable;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.Function;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/InclusiveRange.class */
public final class InclusiveRange<T extends Comparable<T>> extends Record {
    private final T minInclusive;
    private final T maxInclusive;
    public static final Codec<InclusiveRange<Integer>> INT = codec(Codec.INT);

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, InclusiveRange.class), InclusiveRange.class, "minInclusive;maxInclusive", "FIELD:Lnet/minecraft/util/InclusiveRange;->minInclusive:Ljava/lang/Comparable;", "FIELD:Lnet/minecraft/util/InclusiveRange;->maxInclusive:Ljava/lang/Comparable;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, InclusiveRange.class, Object.class), InclusiveRange.class, "minInclusive;maxInclusive", "FIELD:Lnet/minecraft/util/InclusiveRange;->minInclusive:Ljava/lang/Comparable;", "FIELD:Lnet/minecraft/util/InclusiveRange;->maxInclusive:Ljava/lang/Comparable;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public T minInclusive() {
        return this.minInclusive;
    }

    public T maxInclusive() {
        return this.maxInclusive;
    }

    public static <T extends Comparable<T>> Codec<InclusiveRange<T>> codec(Codec<T> $$0) {
        return ExtraCodecs.intervalCodec($$0, "min_inclusive", "max_inclusive", InclusiveRange::create, (v0) -> {
            return v0.minInclusive();
        }, (v0) -> {
            return v0.maxInclusive();
        });
    }

    public static <T extends Comparable<T>> Codec<InclusiveRange<T>> codec(Codec<T> $$0, T $$1, T $$2) {
        return codec($$0).validate($$22 -> {
            if ($$22.minInclusive().compareTo($$1) < 0) {
                return DataResult.error(() -> {
                    return "Range limit too low, expected at least " + String.valueOf($$1) + " [" + String.valueOf($$22.minInclusive()) + "-" + String.valueOf($$22.maxInclusive()) + "]";
                });
            }
            if ($$22.maxInclusive().compareTo($$2) > 0) {
                return DataResult.error(() -> {
                    return "Range limit too high, expected at most " + String.valueOf($$2) + " [" + String.valueOf($$22.minInclusive()) + "-" + String.valueOf($$22.maxInclusive()) + "]";
                });
            }
            return DataResult.success($$22);
        });
    }

    public static <T extends Comparable<T>> DataResult<InclusiveRange<T>> create(T $$0, T $$1) {
        if ($$0.compareTo($$1) <= 0) {
            return DataResult.success(new InclusiveRange($$0, $$1));
        }
        return DataResult.error(() -> {
            return "min_inclusive must be less than or equal to max_inclusive";
        });
    }

    public InclusiveRange(T $$0, T $$1) {
        if ($$0.compareTo($$1) <= 0) {
            this.minInclusive = $$0;
            this.maxInclusive = $$1;
            return;
        }
        throw new IllegalArgumentException("min_inclusive must be less than or equal to max_inclusive");
    }

    public InclusiveRange(T $$0) {
        this($$0, $$0);
    }

    public <S extends Comparable<S>> InclusiveRange<S> map(Function<? super T, ? extends S> function) {
        return new InclusiveRange<>(function.apply(this.minInclusive), function.apply(this.maxInclusive));
    }

    public boolean isValueInRange(T $$0) {
        return $$0.compareTo(this.minInclusive) >= 0 && $$0.compareTo(this.maxInclusive) <= 0;
    }

    public boolean contains(InclusiveRange<T> $$0) {
        return $$0.minInclusive().compareTo(this.minInclusive) >= 0 && $$0.maxInclusive.compareTo(this.maxInclusive) <= 0;
    }

    @Override // java.lang.Record
    public String toString() {
        return "[" + String.valueOf(this.minInclusive) + ", " + String.valueOf(this.maxInclusive) + "]";
    }
}
