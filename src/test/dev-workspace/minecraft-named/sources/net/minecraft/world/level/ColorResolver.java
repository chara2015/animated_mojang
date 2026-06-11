package net.minecraft.world.level;

import net.minecraft.world.level.biome.Biome;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/ColorResolver.class */
@FunctionalInterface
public interface ColorResolver {
    int getColor(Biome biome, double d, double d2);
}
