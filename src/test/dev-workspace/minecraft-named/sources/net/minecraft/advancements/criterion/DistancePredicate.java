package net.minecraft.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/criterion/DistancePredicate.class */
public final class DistancePredicate extends Record {
    private final MinMaxBounds.Doubles x;
    private final MinMaxBounds.Doubles y;
    private final MinMaxBounds.Doubles z;
    private final MinMaxBounds.Doubles horizontal;
    private final MinMaxBounds.Doubles absolute;
    public static final Codec<DistancePredicate> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(MinMaxBounds.Doubles.CODEC.optionalFieldOf("x", MinMaxBounds.Doubles.ANY).forGetter((v0) -> {
            return v0.x();
        }), MinMaxBounds.Doubles.CODEC.optionalFieldOf("y", MinMaxBounds.Doubles.ANY).forGetter((v0) -> {
            return v0.y();
        }), MinMaxBounds.Doubles.CODEC.optionalFieldOf("z", MinMaxBounds.Doubles.ANY).forGetter((v0) -> {
            return v0.z();
        }), MinMaxBounds.Doubles.CODEC.optionalFieldOf("horizontal", MinMaxBounds.Doubles.ANY).forGetter((v0) -> {
            return v0.horizontal();
        }), MinMaxBounds.Doubles.CODEC.optionalFieldOf("absolute", MinMaxBounds.Doubles.ANY).forGetter((v0) -> {
            return v0.absolute();
        })).apply($$0, DistancePredicate::new);
    });

    public DistancePredicate(MinMaxBounds.Doubles $$0, MinMaxBounds.Doubles $$1, MinMaxBounds.Doubles $$2, MinMaxBounds.Doubles $$3, MinMaxBounds.Doubles $$4) {
        this.x = $$0;
        this.y = $$1;
        this.z = $$2;
        this.horizontal = $$3;
        this.absolute = $$4;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DistancePredicate.class), DistancePredicate.class, "x;y;z;horizontal;absolute", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->x:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->y:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->z:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->horizontal:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->absolute:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DistancePredicate.class), DistancePredicate.class, "x;y;z;horizontal;absolute", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->x:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->y:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->z:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->horizontal:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->absolute:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DistancePredicate.class, Object.class), DistancePredicate.class, "x;y;z;horizontal;absolute", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->x:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->y:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->z:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->horizontal:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;", "FIELD:Lnet/minecraft/advancements/criterion/DistancePredicate;->absolute:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public MinMaxBounds.Doubles x() {
        return this.x;
    }

    public MinMaxBounds.Doubles y() {
        return this.y;
    }

    public MinMaxBounds.Doubles z() {
        return this.z;
    }

    public MinMaxBounds.Doubles horizontal() {
        return this.horizontal;
    }

    public MinMaxBounds.Doubles absolute() {
        return this.absolute;
    }

    public static DistancePredicate horizontal(MinMaxBounds.Doubles $$0) {
        return new DistancePredicate(MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, $$0, MinMaxBounds.Doubles.ANY);
    }

    public static DistancePredicate vertical(MinMaxBounds.Doubles $$0) {
        return new DistancePredicate(MinMaxBounds.Doubles.ANY, $$0, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY);
    }

    public static DistancePredicate absolute(MinMaxBounds.Doubles $$0) {
        return new DistancePredicate(MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, MinMaxBounds.Doubles.ANY, $$0);
    }

    public boolean matches(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
        float $$6 = (float) ($$0 - $$3);
        float $$7 = (float) ($$1 - $$4);
        float $$8 = (float) ($$2 - $$5);
        if (!this.x.matches(Mth.abs($$6)) || !this.y.matches(Mth.abs($$7)) || !this.z.matches(Mth.abs($$8)) || !this.horizontal.matchesSqr(($$6 * $$6) + ($$8 * $$8)) || !this.absolute.matchesSqr(($$6 * $$6) + ($$7 * $$7) + ($$8 * $$8))) {
            return false;
        }
        return true;
    }
}
