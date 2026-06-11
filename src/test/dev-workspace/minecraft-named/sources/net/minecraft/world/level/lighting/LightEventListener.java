package net.minecraft.world.level.lighting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/lighting/LightEventListener.class */
public interface LightEventListener {
    void checkBlock(BlockPos blockPos);

    boolean hasLightWork();

    int runLightUpdates();

    void updateSectionStatus(SectionPos sectionPos, boolean z);

    void setLightEnabled(ChunkPos chunkPos, boolean z);

    void propagateLightSources(ChunkPos chunkPos);

    default void updateSectionStatus(BlockPos $$0, boolean $$1) {
        updateSectionStatus(SectionPos.of($$0), $$1);
    }
}
