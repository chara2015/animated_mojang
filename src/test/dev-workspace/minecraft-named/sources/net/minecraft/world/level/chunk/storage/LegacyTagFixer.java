package net.minecraft.world.level.chunk.storage;

import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/storage/LegacyTagFixer.class */
@FunctionalInterface
public interface LegacyTagFixer {
    public static final Supplier<LegacyTagFixer> EMPTY = () -> {
        return $$0 -> {
            return $$0;
        };
    };

    CompoundTag applyFix(CompoundTag compoundTag);

    default void markChunkDone(ChunkPos $$0) {
    }

    default int targetDataVersion() {
        return -1;
    }
}
