package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.WorldGenLevel;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/blockpredicates/InsideWorldBoundsPredicate.class */
public class InsideWorldBoundsPredicate implements BlockPredicate {
    public static final MapCodec<InsideWorldBoundsPredicate> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Vec3i.offsetCodec(16).optionalFieldOf("offset", BlockPos.ZERO).forGetter($$0 -> {
            return $$0.offset;
        })).apply($$0, InsideWorldBoundsPredicate::new);
    });
    private final Vec3i offset;

    public InsideWorldBoundsPredicate(Vec3i $$0) {
        this.offset = $$0;
    }

    @Override // java.util.function.BiPredicate
    public boolean test(WorldGenLevel $$0, BlockPos $$1) {
        return !$$0.isOutsideBuildHeight($$1.offset(this.offset));
    }

    @Override // net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
    public BlockPredicateType<?> type() {
        return BlockPredicateType.INSIDE_WORLD_BOUNDS;
    }
}
