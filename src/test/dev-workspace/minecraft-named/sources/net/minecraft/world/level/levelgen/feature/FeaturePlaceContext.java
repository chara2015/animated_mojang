package net.minecraft.world.level.levelgen.feature;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/FeaturePlaceContext.class */
public class FeaturePlaceContext<FC extends FeatureConfiguration> {
    private final Optional<ConfiguredFeature<?, ?>> topFeature;
    private final WorldGenLevel level;
    private final ChunkGenerator chunkGenerator;
    private final RandomSource random;
    private final BlockPos origin;
    private final FC config;

    public FeaturePlaceContext(Optional<ConfiguredFeature<?, ?>> $$0, WorldGenLevel $$1, ChunkGenerator $$2, RandomSource $$3, BlockPos $$4, FC $$5) {
        this.topFeature = $$0;
        this.level = $$1;
        this.chunkGenerator = $$2;
        this.random = $$3;
        this.origin = $$4;
        this.config = $$5;
    }

    public Optional<ConfiguredFeature<?, ?>> topFeature() {
        return this.topFeature;
    }

    public WorldGenLevel level() {
        return this.level;
    }

    public ChunkGenerator chunkGenerator() {
        return this.chunkGenerator;
    }

    public RandomSource random() {
        return this.random;
    }

    public BlockPos origin() {
        return this.origin;
    }

    public FC config() {
        return this.config;
    }
}
