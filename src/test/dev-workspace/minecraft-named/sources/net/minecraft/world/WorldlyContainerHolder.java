package net.minecraft.world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/WorldlyContainerHolder.class */
public interface WorldlyContainerHolder {
    WorldlyContainer getContainer(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos);
}
