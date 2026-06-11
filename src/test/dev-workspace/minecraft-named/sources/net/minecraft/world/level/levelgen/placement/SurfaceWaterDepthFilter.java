package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/placement/SurfaceWaterDepthFilter.class */
public class SurfaceWaterDepthFilter extends PlacementFilter {
    public static final MapCodec<SurfaceWaterDepthFilter> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.INT.fieldOf("max_water_depth").forGetter($$0 -> {
            return Integer.valueOf($$0.maxWaterDepth);
        })).apply($$0, (v1) -> {
            return new SurfaceWaterDepthFilter(v1);
        });
    });
    private final int maxWaterDepth;

    private SurfaceWaterDepthFilter(int $$0) {
        this.maxWaterDepth = $$0;
    }

    public static SurfaceWaterDepthFilter forMaxDepth(int $$0) {
        return new SurfaceWaterDepthFilter($$0);
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementFilter
    protected boolean shouldPlace(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
        int $$3 = $$0.getHeight(Heightmap.Types.OCEAN_FLOOR, $$2.getX(), $$2.getZ());
        int $$4 = $$0.getHeight(Heightmap.Types.WORLD_SURFACE, $$2.getX(), $$2.getZ());
        return $$4 - $$3 <= this.maxWaterDepth;
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementModifier
    public PlacementModifierType<?> type() {
        return PlacementModifierType.SURFACE_WATER_DEPTH_FILTER;
    }
}
