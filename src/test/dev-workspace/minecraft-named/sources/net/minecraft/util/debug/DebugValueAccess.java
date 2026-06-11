package net.minecraft.util.debug;

import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debug/DebugValueAccess.class */
public interface DebugValueAccess {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debug/DebugValueAccess$EventVisitor.class */
    @FunctionalInterface
    public interface EventVisitor<T> {
        void accept(T t, int i, int i2);
    }

    <T> void forEachChunk(DebugSubscription<T> debugSubscription, BiConsumer<ChunkPos, T> biConsumer);

    <T> T getChunkValue(DebugSubscription<T> debugSubscription, ChunkPos chunkPos);

    <T> void forEachBlock(DebugSubscription<T> debugSubscription, BiConsumer<BlockPos, T> biConsumer);

    <T> T getBlockValue(DebugSubscription<T> debugSubscription, BlockPos blockPos);

    <T> void forEachEntity(DebugSubscription<T> debugSubscription, BiConsumer<Entity, T> biConsumer);

    <T> T getEntityValue(DebugSubscription<T> debugSubscription, Entity entity);

    <T> void forEachEvent(DebugSubscription<T> debugSubscription, EventVisitor<T> eventVisitor);
}
