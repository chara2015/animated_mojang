package net.minecraft.world.level.levelgen.structure.pools;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.Function;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/pools/DimensionPadding.class */
public final class DimensionPadding extends Record {
    private final int bottom;
    private final int top;
    private static final Codec<DimensionPadding> RECORD_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ExtraCodecs.NON_NEGATIVE_INT.lenientOptionalFieldOf(PartNames.BOTTOM, 0).forGetter($$0 -> {
            return Integer.valueOf($$0.bottom);
        }), ExtraCodecs.NON_NEGATIVE_INT.lenientOptionalFieldOf("top", 0).forGetter($$02 -> {
            return Integer.valueOf($$02.top);
        })).apply($$0, (v1, v2) -> {
            return new DimensionPadding(v1, v2);
        });
    });
    public static final Codec<DimensionPadding> CODEC = Codec.either(ExtraCodecs.NON_NEGATIVE_INT, RECORD_CODEC).xmap($$0 -> {
        return (DimensionPadding) $$0.map((v1) -> {
            return new DimensionPadding(v1);
        }, Function.identity());
    }, $$02 -> {
        return $$02.hasEqualTopAndBottom() ? Either.left(Integer.valueOf($$02.bottom)) : Either.right($$02);
    });
    public static final DimensionPadding ZERO = new DimensionPadding(0);

    public DimensionPadding(int $$0, int $$1) {
        this.bottom = $$0;
        this.top = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DimensionPadding.class), DimensionPadding.class, "bottom;top", "FIELD:Lnet/minecraft/world/level/levelgen/structure/pools/DimensionPadding;->bottom:I", "FIELD:Lnet/minecraft/world/level/levelgen/structure/pools/DimensionPadding;->top:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DimensionPadding.class), DimensionPadding.class, "bottom;top", "FIELD:Lnet/minecraft/world/level/levelgen/structure/pools/DimensionPadding;->bottom:I", "FIELD:Lnet/minecraft/world/level/levelgen/structure/pools/DimensionPadding;->top:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DimensionPadding.class, Object.class), DimensionPadding.class, "bottom;top", "FIELD:Lnet/minecraft/world/level/levelgen/structure/pools/DimensionPadding;->bottom:I", "FIELD:Lnet/minecraft/world/level/levelgen/structure/pools/DimensionPadding;->top:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int bottom() {
        return this.bottom;
    }

    public int top() {
        return this.top;
    }

    public DimensionPadding(int $$0) {
        this($$0, $$0);
    }

    public boolean hasEqualTopAndBottom() {
        return this.top == this.bottom;
    }
}
