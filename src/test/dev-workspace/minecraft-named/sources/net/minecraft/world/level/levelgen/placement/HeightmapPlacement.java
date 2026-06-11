package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/placement/HeightmapPlacement.class */
public class HeightmapPlacement extends PlacementModifier {
    public static final MapCodec<HeightmapPlacement> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Heightmap.Types.CODEC.fieldOf("heightmap").forGetter($$0 -> {
            return $$0.heightmap;
        })).apply($$0, HeightmapPlacement::new);
    });
    private final Heightmap.Types heightmap;

    private HeightmapPlacement(Heightmap.Types $$0) {
        this.heightmap = $$0;
    }

    public static HeightmapPlacement onHeightmap(Heightmap.Types $$0) {
        return new HeightmapPlacement($$0);
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementModifier
    public Stream<BlockPos> getPositions(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
        int $$3 = $$2.getX();
        int $$4 = $$2.getZ();
        int $$5 = $$0.getHeight(this.heightmap, $$3, $$4);
        if ($$5 > $$0.getMinY()) {
            return Stream.of(new BlockPos($$3, $$5, $$4));
        }
        return Stream.of((Object[]) new BlockPos[0]);
    }

    @Override // net.minecraft.world.level.levelgen.placement.PlacementModifier
    public PlacementModifierType<?> type() {
        return PlacementModifierType.HEIGHTMAP;
    }
}
