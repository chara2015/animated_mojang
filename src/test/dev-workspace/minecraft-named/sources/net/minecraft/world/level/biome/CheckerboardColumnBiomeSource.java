package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Climate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/CheckerboardColumnBiomeSource.class */
public class CheckerboardColumnBiomeSource extends BiomeSource {
    public static final MapCodec<CheckerboardColumnBiomeSource> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter($$0 -> {
            return $$0.allowedBiomes;
        }), Codec.intRange(0, 62).fieldOf("scale").orElse(2).forGetter($$02 -> {
            return Integer.valueOf($$02.size);
        })).apply($$0, (v1, v2) -> {
            return new CheckerboardColumnBiomeSource(v1, v2);
        });
    });
    private final HolderSet<Biome> allowedBiomes;
    private final int bitShift;
    private final int size;

    public CheckerboardColumnBiomeSource(HolderSet<Biome> $$0, int $$1) {
        this.allowedBiomes = $$0;
        this.bitShift = $$1 + 2;
        this.size = $$1;
    }

    @Override // net.minecraft.world.level.biome.BiomeSource
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return this.allowedBiomes.stream();
    }

    @Override // net.minecraft.world.level.biome.BiomeSource
    protected MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override // net.minecraft.world.level.biome.BiomeSource, net.minecraft.world.level.biome.BiomeResolver
    public Holder<Biome> getNoiseBiome(int $$0, int $$1, int $$2, Climate.Sampler $$3) {
        return this.allowedBiomes.get(Math.floorMod(($$0 >> this.bitShift) + ($$2 >> this.bitShift), this.allowedBiomes.size()));
    }
}
