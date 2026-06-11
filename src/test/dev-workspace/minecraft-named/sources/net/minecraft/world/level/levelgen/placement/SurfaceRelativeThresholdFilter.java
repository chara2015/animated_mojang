package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.lighting.ChunkSkyLightSources;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/placement/SurfaceRelativeThresholdFilter.class */
public class SurfaceRelativeThresholdFilter extends PlacementFilter {
    public static final MapCodec<SurfaceRelativeThresholdFilter> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Heightmap.Types.CODEC.fieldOf("heightmap").forGetter($$0 -> {
            return $$0.heightmap;
        }), Codec.INT.optionalFieldOf("min_inclusive", Integer.valueOf(ChunkSkyLightSources.NEGATIVE_INFINITY)).forGetter($$02 -> {
            return Integer.valueOf($$02.minInclusive);
        }), Codec.INT.optionalFieldOf("max_inclusive", Integer.MAX_VALUE).forGetter($$03 -> {
            return Integer.valueOf($$03.maxInclusive);
        })).apply($$0, (v1, v2, v3) -> {
            return new SurfaceRelativeThresholdFilter(v1, v2, v3);
        });
    });
    private final Heightmap.Types heightmap;
    private final int minInclusive;
    private final int maxInclusive;

    private SurfaceRelativeThresholdFilter(Heightmap.Types $$0, int $$1, int $$2) {
        this.heightmap = $$0;
        this.minInclusive = $$1;
        this.maxInclusive = $$2;
    }

    public static SurfaceRelativeThresholdFilter of(Heightmap.Types $$0, int $$1, int $$2) {
        return new SurfaceRelativeThresholdFilter($$0, $$1, $$2);
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementFilter
    protected boolean shouldPlace(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
        long $$3 = $$0.getHeight(this.heightmap, $$2.getX(), $$2.getZ());
        long $$4 = $$3 + ((long) this.minInclusive);
        long $$5 = $$3 + ((long) this.maxInclusive);
        return $$4 <= ((long) $$2.getY()) && ((long) $$2.getY()) <= $$5;
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementModifier
    public PlacementModifierType<?> type() {
        return PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER;
    }
}
