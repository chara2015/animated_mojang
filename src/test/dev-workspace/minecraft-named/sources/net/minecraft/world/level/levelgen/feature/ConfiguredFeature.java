package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/ConfiguredFeature.class */
public final class ConfiguredFeature<FC extends FeatureConfiguration, F extends Feature<FC>> extends Record {
    private final F feature;
    private final FC config;
    public static final Codec<ConfiguredFeature<?, ?>> DIRECT_CODEC = BuiltInRegistries.FEATURE.byNameCodec().dispatch($$0 -> {
        return $$0.feature;
    }, (v0) -> {
        return v0.configuredCodec();
    });
    public static final Codec<Holder<ConfiguredFeature<?, ?>>> CODEC = RegistryFileCodec.create(Registries.CONFIGURED_FEATURE, DIRECT_CODEC);
    public static final Codec<HolderSet<ConfiguredFeature<?, ?>>> LIST_CODEC = RegistryCodecs.homogeneousList(Registries.CONFIGURED_FEATURE, DIRECT_CODEC);

    public ConfiguredFeature(F $$0, FC $$1) {
        this.feature = $$0;
        this.config = $$1;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ConfiguredFeature.class), ConfiguredFeature.class, "feature;config", "FIELD:Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature;->feature:Lnet/minecraft/world/level/levelgen/feature/Feature;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature;->config:Lnet/minecraft/world/level/levelgen/feature/configurations/FeatureConfiguration;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ConfiguredFeature.class, Object.class), ConfiguredFeature.class, "feature;config", "FIELD:Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature;->feature:Lnet/minecraft/world/level/levelgen/feature/Feature;", "FIELD:Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature;->config:Lnet/minecraft/world/level/levelgen/feature/configurations/FeatureConfiguration;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public F feature() {
        return this.feature;
    }

    public FC config() {
        return this.config;
    }

    public boolean place(WorldGenLevel $$0, ChunkGenerator $$1, RandomSource $$2, BlockPos $$3) {
        return this.feature.place(this.config, $$0, $$1, $$2, $$3);
    }

    public Stream<ConfiguredFeature<?, ?>> getFeatures() {
        return Stream.concat(Stream.of(this), this.config.getFeatures());
    }

    @Override // java.lang.Record
    public String toString() {
        return "Configured: " + String.valueOf(this.feature) + ": " + String.valueOf(this.config);
    }
}
