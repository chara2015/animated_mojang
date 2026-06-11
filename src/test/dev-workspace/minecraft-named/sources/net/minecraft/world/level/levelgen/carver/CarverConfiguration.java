package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/carver/CarverConfiguration.class */
public class CarverConfiguration extends ProbabilityFeatureConfiguration {
    public static final MapCodec<CarverConfiguration> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.floatRange(0.0f, 1.0f).fieldOf("probability").forGetter($$0 -> {
            return Float.valueOf($$0.probability);
        }), HeightProvider.CODEC.fieldOf("y").forGetter($$02 -> {
            return $$02.y;
        }), FloatProvider.CODEC.fieldOf("yScale").forGetter($$03 -> {
            return $$03.yScale;
        }), VerticalAnchor.CODEC.fieldOf("lava_level").forGetter($$04 -> {
            return $$04.lavaLevel;
        }), CarverDebugSettings.CODEC.optionalFieldOf("debug_settings", CarverDebugSettings.DEFAULT).forGetter($$05 -> {
            return $$05.debugSettings;
        }), RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("replaceable").forGetter($$06 -> {
            return $$06.replaceable;
        })).apply($$0, (v1, v2, v3, v4, v5, v6) -> {
            return new CarverConfiguration(v1, v2, v3, v4, v5, v6);
        });
    });
    public final HeightProvider y;
    public final FloatProvider yScale;
    public final VerticalAnchor lavaLevel;
    public final CarverDebugSettings debugSettings;
    public final HolderSet<Block> replaceable;

    public CarverConfiguration(float $$0, HeightProvider $$1, FloatProvider $$2, VerticalAnchor $$3, CarverDebugSettings $$4, HolderSet<Block> $$5) {
        super($$0);
        this.y = $$1;
        this.yScale = $$2;
        this.lavaLevel = $$3;
        this.debugSettings = $$4;
        this.replaceable = $$5;
    }
}
