package net.minecraft.world.level.levelgen.material;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseChunk;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/material/WorldGenMaterialRule.class */
public interface WorldGenMaterialRule {
    BlockState apply(NoiseChunk noiseChunk, int i, int i2, int i3);
}
