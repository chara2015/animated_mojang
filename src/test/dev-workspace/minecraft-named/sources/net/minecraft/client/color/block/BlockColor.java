package net.minecraft.client.color.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/color/block/BlockColor.class */
public interface BlockColor {
    int getColor(BlockState blockState, BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, int i);
}
