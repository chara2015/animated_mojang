package net.minecraft.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/LevelWriter.class */
public interface LevelWriter {
    boolean setBlock(BlockPos blockPos, BlockState blockState, @Block.UpdateFlags int i, int i2);

    boolean removeBlock(BlockPos blockPos, boolean z);

    boolean destroyBlock(BlockPos blockPos, boolean z, Entity entity, int i);

    default boolean setBlock(BlockPos $$0, BlockState $$1, @Block.UpdateFlags int $$2) {
        return setBlock($$0, $$1, $$2, 512);
    }

    default boolean destroyBlock(BlockPos $$0, boolean $$1) {
        return destroyBlock($$0, $$1, null);
    }

    default boolean destroyBlock(BlockPos $$0, boolean $$1, Entity $$2) {
        return destroyBlock($$0, $$1, $$2, 512);
    }

    default boolean addFreshEntity(Entity $$0) {
        return false;
    }
}
