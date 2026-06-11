package net.labymod.api.client.world.chunk;

import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.world.block.BlockState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/chunk/Chunk.class */
public interface Chunk {
    int getChunkX();

    int getChunkZ();

    int getAbsoluteBlockX(int i);

    int getAbsoluteBlockZ(int i);

    Heightmap heightmap(HeightmapType heightmapType);

    boolean isLoaded();

    BlockState getBlockState(int i, int i2, int i3);

    BlockEntity[] getBlockEntities();

    int getHeightBasedOnSection(int i);
}
