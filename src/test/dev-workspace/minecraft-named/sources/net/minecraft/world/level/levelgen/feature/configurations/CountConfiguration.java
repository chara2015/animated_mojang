package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/CountConfiguration.class */
public class CountConfiguration implements FeatureConfiguration {
    public static final Codec<CountConfiguration> CODEC = IntProvider.codec(0, 256).fieldOf("count").xmap(CountConfiguration::new, (v0) -> {
        return v0.count();
    }).codec();
    private final IntProvider count;

    public CountConfiguration(int $$0) {
        this.count = ConstantInt.of($$0);
    }

    public CountConfiguration(IntProvider $$0) {
        this.count = $$0;
    }

    public IntProvider count() {
        return this.count;
    }
}
