package net.minecraft.world.level.storage;

import net.minecraft.world.level.storage.LevelData;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/WritableLevelData.class */
public interface WritableLevelData extends LevelData {
    void setSpawn(LevelData.RespawnData respawnData);
}
