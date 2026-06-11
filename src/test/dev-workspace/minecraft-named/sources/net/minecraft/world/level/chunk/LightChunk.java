package net.minecraft.world.level.chunk;

import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.ChunkSkyLightSources;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/LightChunk.class */
public interface LightChunk extends BlockGetter {
    void findBlockLightSources(BiConsumer<BlockPos, BlockState> biConsumer);

    ChunkSkyLightSources getSkyLightSources();
}
