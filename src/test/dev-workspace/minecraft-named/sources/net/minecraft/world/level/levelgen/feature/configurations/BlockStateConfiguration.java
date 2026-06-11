package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/BlockStateConfiguration.class */
public class BlockStateConfiguration implements FeatureConfiguration {
    public static final Codec<BlockStateConfiguration> CODEC = BlockState.CODEC.fieldOf(StructureTemplate.BLOCK_TAG_STATE).xmap(BlockStateConfiguration::new, $$0 -> {
        return $$0.state;
    }).codec();
    public final BlockState state;

    public BlockStateConfiguration(BlockState $$0) {
        this.state = $$0;
    }
}
