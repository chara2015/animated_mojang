package net.minecraft.world.level.chunk;

import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LightLayer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/LightChunkGetter.class */
public interface LightChunkGetter {
    LightChunk getChunkForLighting(int i, int i2);

    BlockGetter getLevel();

    default void onLightUpdate(LightLayer $$0, SectionPos $$1) {
    }
}
