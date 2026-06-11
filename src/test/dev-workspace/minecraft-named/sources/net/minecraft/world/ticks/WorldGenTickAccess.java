package net.minecraft.world.ticks;

import java.util.function.Function;
import net.minecraft.core.BlockPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/ticks/WorldGenTickAccess.class */
public class WorldGenTickAccess<T> implements LevelTickAccess<T> {
    private final Function<BlockPos, TickContainerAccess<T>> containerGetter;

    public WorldGenTickAccess(Function<BlockPos, TickContainerAccess<T>> $$0) {
        this.containerGetter = $$0;
    }

    @Override // net.minecraft.world.ticks.TickAccess
    public boolean hasScheduledTick(BlockPos $$0, T $$1) {
        return this.containerGetter.apply($$0).hasScheduledTick($$0, $$1);
    }

    @Override // net.minecraft.world.ticks.TickAccess
    public void schedule(ScheduledTick<T> $$0) {
        this.containerGetter.apply($$0.pos()).schedule($$0);
    }

    @Override // net.minecraft.world.ticks.LevelTickAccess
    public boolean willTickThisTick(BlockPos $$0, T $$1) {
        return false;
    }

    @Override // net.minecraft.world.ticks.TickAccess
    public int count() {
        return 0;
    }
}
