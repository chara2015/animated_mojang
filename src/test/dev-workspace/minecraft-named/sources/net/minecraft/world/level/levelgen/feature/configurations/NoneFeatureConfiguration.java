package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/NoneFeatureConfiguration.class */
public class NoneFeatureConfiguration implements FeatureConfiguration {
    public static final NoneFeatureConfiguration INSTANCE = new NoneFeatureConfiguration();
    public static final Codec<NoneFeatureConfiguration> CODEC = MapCodec.unitCodec(INSTANCE);
}
