package net.minecraft.world.level.chunk.storage;

import com.mojang.datafixers.DataFixer;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkPos;
import org.apache.commons.io.FileUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/storage/RecreatingSimpleRegionStorage.class */
public class RecreatingSimpleRegionStorage extends SimpleRegionStorage {
    private final IOWorker writeWorker;
    private final Path writeFolder;

    public RecreatingSimpleRegionStorage(RegionStorageInfo $$0, Path $$1, RegionStorageInfo $$2, Path $$3, DataFixer $$4, boolean $$5, DataFixTypes $$6, Supplier<LegacyTagFixer> $$7) {
        super($$0, $$1, $$4, $$5, $$6, $$7);
        this.writeFolder = $$3;
        this.writeWorker = new IOWorker($$2, $$3, $$5);
    }

    @Override // net.minecraft.world.level.chunk.storage.SimpleRegionStorage
    public CompletableFuture<Void> write(ChunkPos $$0, Supplier<CompoundTag> $$1) {
        markChunkDone($$0);
        return this.writeWorker.store($$0, $$1);
    }

    @Override // net.minecraft.world.level.chunk.storage.SimpleRegionStorage, java.lang.AutoCloseable
    public void close() throws Throwable {
        super.close();
        this.writeWorker.close();
        if (this.writeFolder.toFile().exists()) {
            FileUtils.deleteDirectory(this.writeFolder.toFile());
        }
    }
}
