package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.valueproviders.IntProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration.class */
public final class SculkPatchConfiguration extends Record implements FeatureConfiguration {
    private final int chargeCount;
    private final int amountPerCharge;
    private final int spreadAttempts;
    private final int growthRounds;
    private final int spreadRounds;
    private final IntProvider extraRareGrowths;
    private final float catalystChance;
    public static final Codec<SculkPatchConfiguration> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.intRange(1, 32).fieldOf("charge_count").forGetter((v0) -> {
            return v0.chargeCount();
        }), Codec.intRange(1, 500).fieldOf("amount_per_charge").forGetter((v0) -> {
            return v0.amountPerCharge();
        }), Codec.intRange(1, 64).fieldOf("spread_attempts").forGetter((v0) -> {
            return v0.spreadAttempts();
        }), Codec.intRange(0, 8).fieldOf("growth_rounds").forGetter((v0) -> {
            return v0.growthRounds();
        }), Codec.intRange(0, 8).fieldOf("spread_rounds").forGetter((v0) -> {
            return v0.spreadRounds();
        }), IntProvider.CODEC.fieldOf("extra_rare_growths").forGetter((v0) -> {
            return v0.extraRareGrowths();
        }), Codec.floatRange(0.0f, 1.0f).fieldOf("catalyst_chance").forGetter((v0) -> {
            return v0.catalystChance();
        })).apply($$0, (v1, v2, v3, v4, v5, v6, v7) -> {
            return new SculkPatchConfiguration(v1, v2, v3, v4, v5, v6, v7);
        });
    });

    public SculkPatchConfiguration(int $$0, int $$1, int $$2, int $$3, int $$4, IntProvider $$5, float $$6) {
        this.chargeCount = $$0;
        this.amountPerCharge = $$1;
        this.spreadAttempts = $$2;
        this.growthRounds = $$3;
        this.spreadRounds = $$4;
        this.extraRareGrowths = $$5;
        this.catalystChance = $$6;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SculkPatchConfiguration.class), SculkPatchConfiguration.class, "chargeCount;amountPerCharge;spreadAttempts;growthRounds;spreadRounds;extraRareGrowths;catalystChance", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->chargeCount:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->amountPerCharge:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->spreadAttempts:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->growthRounds:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->spreadRounds:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->extraRareGrowths:Lnet/minecraft/util/valueproviders/IntProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->catalystChance:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SculkPatchConfiguration.class), SculkPatchConfiguration.class, "chargeCount;amountPerCharge;spreadAttempts;growthRounds;spreadRounds;extraRareGrowths;catalystChance", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->chargeCount:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->amountPerCharge:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->spreadAttempts:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->growthRounds:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->spreadRounds:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->extraRareGrowths:Lnet/minecraft/util/valueproviders/IntProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->catalystChance:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SculkPatchConfiguration.class, Object.class), SculkPatchConfiguration.class, "chargeCount;amountPerCharge;spreadAttempts;growthRounds;spreadRounds;extraRareGrowths;catalystChance", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->chargeCount:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->amountPerCharge:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->spreadAttempts:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->growthRounds:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->spreadRounds:I", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->extraRareGrowths:Lnet/minecraft/util/valueproviders/IntProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;->catalystChance:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int chargeCount() {
        return this.chargeCount;
    }

    public int amountPerCharge() {
        return this.amountPerCharge;
    }

    public int spreadAttempts() {
        return this.spreadAttempts;
    }

    public int growthRounds() {
        return this.growthRounds;
    }

    public int spreadRounds() {
        return this.spreadRounds;
    }

    public IntProvider extraRareGrowths() {
        return this.extraRareGrowths;
    }

    public float catalystChance() {
        return this.catalystChance;
    }
}
