package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/BlockPileConfiguration.class */
public class BlockPileConfiguration implements FeatureConfiguration {
    public static final Codec<BlockPileConfiguration> CODEC = BlockStateProvider.CODEC.fieldOf("state_provider").xmap(BlockPileConfiguration::new, $$0 -> {
        return $$0.stateProvider;
    }).codec();
    public final BlockStateProvider stateProvider;

    public BlockPileConfiguration(BlockStateProvider $$0) {
        this.stateProvider = $$0;
    }
}
