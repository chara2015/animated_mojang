package net.minecraft.world.level.entity;

import net.minecraft.server.level.FullChunkStatus;
import net.minecraft.world.level.ChunkPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/entity/ChunkStatusUpdateListener.class */
@FunctionalInterface
public interface ChunkStatusUpdateListener {
    void onChunkStatusChange(ChunkPos chunkPos, FullChunkStatus fullChunkStatus);
}
