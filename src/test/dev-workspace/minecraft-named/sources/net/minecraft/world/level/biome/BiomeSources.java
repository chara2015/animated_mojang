package net.minecraft.world.level.biome;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/biome/BiomeSources.class */
public class BiomeSources {
    public static MapCodec<? extends BiomeSource> bootstrap(Registry<MapCodec<? extends BiomeSource>> $$0) {
        Registry.register((Registry<? super MapCodec<FixedBiomeSource>>) $$0, "fixed", FixedBiomeSource.CODEC);
        Registry.register((Registry<? super MapCodec<MultiNoiseBiomeSource>>) $$0, "multi_noise", MultiNoiseBiomeSource.CODEC);
        Registry.register((Registry<? super MapCodec<CheckerboardColumnBiomeSource>>) $$0, "checkerboard", CheckerboardColumnBiomeSource.CODEC);
        return (MapCodec) Registry.register((Registry<? super MapCodec<TheEndBiomeSource>>) $$0, "the_end", TheEndBiomeSource.CODEC);
    }
}
