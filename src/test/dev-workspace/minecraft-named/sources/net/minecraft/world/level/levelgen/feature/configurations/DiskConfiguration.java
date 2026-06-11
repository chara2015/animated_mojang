package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration.class */
public final class DiskConfiguration extends Record implements FeatureConfiguration {
    private final RuleBasedBlockStateProvider stateProvider;
    private final BlockPredicate target;
    private final IntProvider radius;
    private final int halfHeight;
    public static final Codec<DiskConfiguration> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(RuleBasedBlockStateProvider.CODEC.fieldOf("state_provider").forGetter((v0) -> {
            return v0.stateProvider();
        }), BlockPredicate.CODEC.fieldOf(JigsawBlockEntity.TARGET).forGetter((v0) -> {
            return v0.target();
        }), IntProvider.codec(0, 8).fieldOf("radius").forGetter((v0) -> {
            return v0.radius();
        }), Codec.intRange(0, 4).fieldOf("half_height").forGetter((v0) -> {
            return v0.halfHeight();
        })).apply($$0, (v1, v2, v3, v4) -> {
            return new DiskConfiguration(v1, v2, v3, v4);
        });
    });

    public DiskConfiguration(RuleBasedBlockStateProvider $$0, BlockPredicate $$1, IntProvider $$2, int $$3) {
        this.stateProvider = $$0;
        this.target = $$1;
        this.radius = $$2;
        this.halfHeight = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DiskConfiguration.class), DiskConfiguration.class, "stateProvider;target;radius;halfHeight", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;->stateProvider:Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;->target:Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;->radius:Lnet/minecraft/util/valueproviders/IntProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;->halfHeight:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DiskConfiguration.class), DiskConfiguration.class, "stateProvider;target;radius;halfHeight", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;->stateProvider:Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;->target:Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;->radius:Lnet/minecraft/util/valueproviders/IntProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;->halfHeight:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DiskConfiguration.class, Object.class), DiskConfiguration.class, "stateProvider;target;radius;halfHeight", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;->stateProvider:Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;->target:Lnet/minecraft/world/level/levelgen/blockpredicates/BlockPredicate;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;->radius:Lnet/minecraft/util/valueproviders/IntProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;->halfHeight:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public RuleBasedBlockStateProvider stateProvider() {
        return this.stateProvider;
    }

    public BlockPredicate target() {
        return this.target;
    }

    public IntProvider radius() {
        return this.radius;
    }

    public int halfHeight() {
        return this.halfHeight;
    }
}
