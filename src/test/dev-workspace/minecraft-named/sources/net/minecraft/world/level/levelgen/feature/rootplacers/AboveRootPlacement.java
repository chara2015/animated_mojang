package net.minecraft.world.level.levelgen.feature.rootplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/rootplacers/AboveRootPlacement.class */
public final class AboveRootPlacement extends Record {
    private final BlockStateProvider aboveRootProvider;
    private final float aboveRootPlacementChance;
    public static final Codec<AboveRootPlacement> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(BlockStateProvider.CODEC.fieldOf("above_root_provider").forGetter($$0 -> {
            return $$0.aboveRootProvider;
        }), Codec.floatRange(0.0f, 1.0f).fieldOf("above_root_placement_chance").forGetter($$02 -> {
            return Float.valueOf($$02.aboveRootPlacementChance);
        })).apply($$0, (v1, v2) -> {
            return new AboveRootPlacement(v1, v2);
        });
    });

    public AboveRootPlacement(BlockStateProvider $$0, float $$1) {
        this.aboveRootProvider = $$0;
        this.aboveRootPlacementChance = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AboveRootPlacement.class), AboveRootPlacement.class, "aboveRootProvider;aboveRootPlacementChance", "FIELD:Lnet/minecraft/world/level/levelgen/feature/rootplacers/AboveRootPlacement;->aboveRootProvider:Lnet/minecraft/world/level/levelgen/feature/stateproviders/BlockStateProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/rootplacers/AboveRootPlacement;->aboveRootPlacementChance:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AboveRootPlacement.class), AboveRootPlacement.class, "aboveRootProvider;aboveRootPlacementChance", "FIELD:Lnet/minecraft/world/level/levelgen/feature/rootplacers/AboveRootPlacement;->aboveRootProvider:Lnet/minecraft/world/level/levelgen/feature/stateproviders/BlockStateProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/rootplacers/AboveRootPlacement;->aboveRootPlacementChance:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AboveRootPlacement.class, Object.class), AboveRootPlacement.class, "aboveRootProvider;aboveRootPlacementChance", "FIELD:Lnet/minecraft/world/level/levelgen/feature/rootplacers/AboveRootPlacement;->aboveRootProvider:Lnet/minecraft/world/level/levelgen/feature/stateproviders/BlockStateProvider;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/rootplacers/AboveRootPlacement;->aboveRootPlacementChance:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public BlockStateProvider aboveRootProvider() {
        return this.aboveRootProvider;
    }

    public float aboveRootPlacementChance() {
        return this.aboveRootPlacementChance;
    }
}
