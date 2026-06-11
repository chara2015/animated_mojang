package net.minecraft.world.entity.variant;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/variant/MoonBrightnessCheck.class */
public final class MoonBrightnessCheck extends Record implements SpawnCondition {
    private final MinMaxBounds.Doubles range;
    public static final MapCodec<MoonBrightnessCheck> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(MinMaxBounds.Doubles.CODEC.fieldOf("range").forGetter((v0) -> {
            return v0.range();
        })).apply($$0, MoonBrightnessCheck::new);
    });

    public MoonBrightnessCheck(MinMaxBounds.Doubles $$0) {
        this.range = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MoonBrightnessCheck.class), MoonBrightnessCheck.class, "range", "FIELD:Lnet/minecraft/world/entity/variant/MoonBrightnessCheck;->range:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MoonBrightnessCheck.class), MoonBrightnessCheck.class, "range", "FIELD:Lnet/minecraft/world/entity/variant/MoonBrightnessCheck;->range:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MoonBrightnessCheck.class, Object.class), MoonBrightnessCheck.class, "range", "FIELD:Lnet/minecraft/world/entity/variant/MoonBrightnessCheck;->range:Lnet/minecraft/advancements/criterion/MinMaxBounds$Doubles;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public MinMaxBounds.Doubles range() {
        return this.range;
    }

    @Override // java.util.function.Predicate
    public boolean test(SpawnContext $$0) {
        MoonPhase $$1 = (MoonPhase) $$0.environmentAttributes().getValue(EnvironmentAttributes.MOON_PHASE, Vec3.atCenterOf($$0.pos()));
        float $$2 = DimensionType.MOON_BRIGHTNESS_PER_PHASE[$$1.index()];
        return this.range.matches($$2);
    }

    @Override // net.minecraft.world.entity.variant.SpawnCondition
    public MapCodec<MoonBrightnessCheck> codec() {
        return MAP_CODEC;
    }
}
