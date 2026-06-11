package net.minecraft.world.ticks;

import net.minecraft.core.BlockPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/ticks/TickAccess.class */
public interface TickAccess<T> {
    void schedule(ScheduledTick<T> scheduledTick);

    boolean hasScheduledTick(BlockPos blockPos, T t);

    int count();
}
