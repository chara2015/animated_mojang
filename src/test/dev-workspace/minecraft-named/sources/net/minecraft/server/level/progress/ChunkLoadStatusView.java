package net.minecraft.server.level.progress;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.status.ChunkStatus;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/progress/ChunkLoadStatusView.class */
public interface ChunkLoadStatusView {
    void moveTo(ResourceKey<Level> resourceKey, ChunkPos chunkPos);

    ChunkStatus get(int i, int i2);

    int radius();
}
