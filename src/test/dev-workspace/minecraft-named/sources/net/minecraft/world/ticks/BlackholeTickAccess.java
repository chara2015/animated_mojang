package net.minecraft.world.ticks;

import net.minecraft.core.BlockPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/ticks/BlackholeTickAccess.class */
public class BlackholeTickAccess {
    private static final TickContainerAccess<Object> CONTAINER_BLACKHOLE = new TickContainerAccess<Object>() { // from class: net.minecraft.world.ticks.BlackholeTickAccess.1
        @Override // net.minecraft.world.ticks.TickAccess
        public void schedule(ScheduledTick<Object> $$0) {
        }

        @Override // net.minecraft.world.ticks.TickAccess
        public boolean hasScheduledTick(BlockPos $$0, Object $$1) {
            return false;
        }

        @Override // net.minecraft.world.ticks.TickAccess
        public int count() {
            return 0;
        }
    };
    private static final LevelTickAccess<Object> LEVEL_BLACKHOLE = new LevelTickAccess<Object>() { // from class: net.minecraft.world.ticks.BlackholeTickAccess.2
        @Override // net.minecraft.world.ticks.TickAccess
        public void schedule(ScheduledTick<Object> $$0) {
        }

        @Override // net.minecraft.world.ticks.TickAccess
        public boolean hasScheduledTick(BlockPos $$0, Object $$1) {
            return false;
        }

        @Override // net.minecraft.world.ticks.LevelTickAccess
        public boolean willTickThisTick(BlockPos $$0, Object $$1) {
            return false;
        }

        @Override // net.minecraft.world.ticks.TickAccess
        public int count() {
            return 0;
        }
    };

    public static <T> TickContainerAccess<T> emptyContainer() {
        return (TickContainerAccess<T>) CONTAINER_BLACKHOLE;
    }

    public static <T> LevelTickAccess<T> emptyLevelList() {
        return (LevelTickAccess<T>) LEVEL_BLACKHOLE;
    }
}
