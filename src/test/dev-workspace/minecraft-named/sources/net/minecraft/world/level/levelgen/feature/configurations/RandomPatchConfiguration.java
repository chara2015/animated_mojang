package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration.class */
public final class RandomPatchConfiguration extends Record implements FeatureConfiguration {
    private final int tries;
    private final int xzSpread;
    private final int ySpread;
    private final Holder<PlacedFeature> feature;
    public static final Codec<RandomPatchConfiguration> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter((v0) -> {
            return v0.tries();
        }), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").orElse(7).forGetter((v0) -> {
            return v0.xzSpread();
        }), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").orElse(3).forGetter((v0) -> {
            return v0.ySpread();
        }), PlacedFeature.CODEC.fieldOf("feature").forGetter((v0) -> {
            return v0.feature();
        })).apply($$0, (v1, v2, v3, v4) -> {
            return new RandomPatchConfiguration(v1, v2, v3, v4);
        });
    });

    public RandomPatchConfiguration(int $$0, int $$1, int $$2, Holder<PlacedFeature> $$3) {
        this.tries = $$0;
        this.xzSpread = $$1;
        this.ySpread = $$2;
        this.feature = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RandomPatchConfiguration.class), RandomPatchConfiguration.class, "tries;xzSpread;ySpread;feature", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;->tries:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;->xzSpread:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;->ySpread:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;->feature:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RandomPatchConfiguration.class), RandomPatchConfiguration.class, "tries;xzSpread;ySpread;feature", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;->tries:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;->xzSpread:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;->ySpread:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;->feature:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RandomPatchConfiguration.class, Object.class), RandomPatchConfiguration.class, "tries;xzSpread;ySpread;feature", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;->tries:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;->xzSpread:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;->ySpread:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;->feature:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int tries() {
        return this.tries;
    }

    public int xzSpread() {
        return this.xzSpread;
    }

    public int ySpread() {
        return this.ySpread;
    }

    public Holder<PlacedFeature> feature() {
        return this.feature;
    }
}
