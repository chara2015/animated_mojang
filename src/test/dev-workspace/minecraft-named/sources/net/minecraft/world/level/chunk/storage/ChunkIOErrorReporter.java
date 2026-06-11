package net.minecraft.world.level.chunk.storage;

import java.util.Objects;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.world.level.ChunkPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/storage/ChunkIOErrorReporter.class */
public interface ChunkIOErrorReporter {
    void reportChunkLoadFailure(Throwable th, RegionStorageInfo regionStorageInfo, ChunkPos chunkPos);

    void reportChunkSaveFailure(Throwable th, RegionStorageInfo regionStorageInfo, ChunkPos chunkPos);

    static ReportedException createMisplacedChunkReport(ChunkPos $$0, ChunkPos $$1) {
        CrashReport $$2 = CrashReport.forThrowable(new IllegalStateException("Retrieved chunk position " + String.valueOf($$0) + " does not match requested " + String.valueOf($$1)), "Chunk found in invalid location");
        CrashReportCategory $$3 = $$2.addCategory("Misplaced Chunk");
        Objects.requireNonNull($$0);
        $$3.setDetail("Stored Position", $$0::toString);
        return new ReportedException($$2);
    }

    default void reportMisplacedChunk(ChunkPos $$0, ChunkPos $$1, RegionStorageInfo $$2) {
        reportChunkLoadFailure(createMisplacedChunkReport($$0, $$1), $$2, $$1);
    }
}
