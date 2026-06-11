package net.minecraft.world.level.biome;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouterData;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/TheEndBiomeSource.class */
public class TheEndBiomeSource extends BiomeSource {
    public static final MapCodec<TheEndBiomeSource> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(RegistryOps.retrieveElement(Biomes.THE_END), RegistryOps.retrieveElement(Biomes.END_HIGHLANDS), RegistryOps.retrieveElement(Biomes.END_MIDLANDS), RegistryOps.retrieveElement(Biomes.SMALL_END_ISLANDS), RegistryOps.retrieveElement(Biomes.END_BARRENS)).apply($$0, $$0.stable((v1, v2, v3, v4, v5) -> {
            return new TheEndBiomeSource(v1, v2, v3, v4, v5);
        }));
    });
    private final Holder<Biome> end;
    private final Holder<Biome> highlands;
    private final Holder<Biome> midlands;
    private final Holder<Biome> islands;
    private final Holder<Biome> barrens;

    public static TheEndBiomeSource create(HolderGetter<Biome> $$0) {
        return new TheEndBiomeSource($$0.getOrThrow(Biomes.THE_END), $$0.getOrThrow(Biomes.END_HIGHLANDS), $$0.getOrThrow(Biomes.END_MIDLANDS), $$0.getOrThrow(Biomes.SMALL_END_ISLANDS), $$0.getOrThrow(Biomes.END_BARRENS));
    }

    private TheEndBiomeSource(Holder<Biome> $$0, Holder<Biome> $$1, Holder<Biome> $$2, Holder<Biome> $$3, Holder<Biome> $$4) {
        this.end = $$0;
        this.highlands = $$1;
        this.midlands = $$2;
        this.islands = $$3;
        this.barrens = $$4;
    }

    @Override // net.minecraft.world.level.biome.BiomeSource
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return Stream.of((Object[]) new Holder[]{this.end, this.highlands, this.midlands, this.islands, this.barrens});
    }

    @Override // net.minecraft.world.level.biome.BiomeSource
    protected MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override // net.minecraft.world.level.biome.BiomeSource, net.minecraft.world.level.biome.BiomeResolver
    public Holder<Biome> getNoiseBiome(int $$0, int $$1, int $$2, Climate.Sampler $$3) {
        int $$4 = QuartPos.toBlock($$0);
        int $$5 = QuartPos.toBlock($$1);
        int $$6 = QuartPos.toBlock($$2);
        int $$7 = SectionPos.blockToSectionCoord($$4);
        int $$8 = SectionPos.blockToSectionCoord($$6);
        if ((((long) $$7) * ((long) $$7)) + (((long) $$8) * ((long) $$8)) <= NoiseRouterData.ISLAND_CHUNK_DISTANCE_SQR) {
            return this.end;
        }
        int $$9 = ((SectionPos.blockToSectionCoord($$4) * 2) + 1) * 8;
        int $$10 = ((SectionPos.blockToSectionCoord($$6) * 2) + 1) * 8;
        double $$11 = $$3.erosion().compute(new DensityFunction.SinglePointContext($$9, $$5, $$10));
        if ($$11 > 0.25d) {
            return this.highlands;
        }
        if ($$11 >= -0.0625d) {
            return this.midlands;
        }
        if ($$11 < -0.21875d) {
            return this.islands;
        }
        return this.barrens;
    }
}
