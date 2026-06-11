package net.minecraft.world.level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/WorldDataConfiguration.class */
public final class WorldDataConfiguration extends Record {
    private final DataPackConfig dataPacks;
    private final FeatureFlagSet enabledFeatures;
    public static final String ENABLED_FEATURES_ID = "enabled_features";
    public static final MapCodec<WorldDataConfiguration> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(DataPackConfig.CODEC.lenientOptionalFieldOf("DataPacks", DataPackConfig.DEFAULT).forGetter((v0) -> {
            return v0.dataPacks();
        }), FeatureFlags.CODEC.lenientOptionalFieldOf(ENABLED_FEATURES_ID, FeatureFlags.DEFAULT_FLAGS).forGetter((v0) -> {
            return v0.enabledFeatures();
        })).apply($$0, WorldDataConfiguration::new);
    });
    public static final Codec<WorldDataConfiguration> CODEC = MAP_CODEC.codec();
    public static final WorldDataConfiguration DEFAULT = new WorldDataConfiguration(DataPackConfig.DEFAULT, FeatureFlags.DEFAULT_FLAGS);

    public WorldDataConfiguration(DataPackConfig $$0, FeatureFlagSet $$1) {
        this.dataPacks = $$0;
        this.enabledFeatures = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WorldDataConfiguration.class), WorldDataConfiguration.class, "dataPacks;enabledFeatures", "FIELD:Lnet/minecraft/world/level/WorldDataConfiguration;->dataPacks:Lnet/minecraft/world/level/DataPackConfig;", "FIELD:Lnet/minecraft/world/level/WorldDataConfiguration;->enabledFeatures:Lnet/minecraft/world/flag/FeatureFlagSet;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WorldDataConfiguration.class), WorldDataConfiguration.class, "dataPacks;enabledFeatures", "FIELD:Lnet/minecraft/world/level/WorldDataConfiguration;->dataPacks:Lnet/minecraft/world/level/DataPackConfig;", "FIELD:Lnet/minecraft/world/level/WorldDataConfiguration;->enabledFeatures:Lnet/minecraft/world/flag/FeatureFlagSet;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WorldDataConfiguration.class, Object.class), WorldDataConfiguration.class, "dataPacks;enabledFeatures", "FIELD:Lnet/minecraft/world/level/WorldDataConfiguration;->dataPacks:Lnet/minecraft/world/level/DataPackConfig;", "FIELD:Lnet/minecraft/world/level/WorldDataConfiguration;->enabledFeatures:Lnet/minecraft/world/flag/FeatureFlagSet;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public DataPackConfig dataPacks() {
        return this.dataPacks;
    }

    public FeatureFlagSet enabledFeatures() {
        return this.enabledFeatures;
    }

    public WorldDataConfiguration expandFeatures(FeatureFlagSet $$0) {
        return new WorldDataConfiguration(this.dataPacks, this.enabledFeatures.join($$0));
    }
}
