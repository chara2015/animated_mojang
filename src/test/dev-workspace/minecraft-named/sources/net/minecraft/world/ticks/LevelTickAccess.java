package net.minecraft.world.ticks;

import net.minecraft.core.BlockPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/ticks/LevelTickAccess.class */
public interface LevelTickAccess<T> extends TickAccess<T> {
    boolean willTickThisTick(BlockPos blockPos, T t);
}
