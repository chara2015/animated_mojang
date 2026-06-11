package net.minecraft.world.level.chunk.storage;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.util.ExceptionCollector;
import net.minecraft.util.FileUtil;
import net.minecraft.world.level.ChunkPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/storage/RegionFileStorage.class */
public final class RegionFileStorage implements AutoCloseable {
    public static final String ANVIL_EXTENSION = ".mca";
    private static final int MAX_CACHE_SIZE = 256;
    private final Long2ObjectLinkedOpenHashMap<RegionFile> regionCache = new Long2ObjectLinkedOpenHashMap<>();
    private final RegionStorageInfo info;
    private final Path folder;
    private final boolean sync;

    RegionFileStorage(RegionStorageInfo $$0, Path $$1, boolean $$2) {
        this.folder = $$1;
        this.sync = $$2;
        this.info = $$0;
    }

    private RegionFile getRegionFile(ChunkPos $$0) throws IOException {
        long $$1 = ChunkPos.asLong($$0.getRegionX(), $$0.getRegionZ());
        RegionFile $$2 = (RegionFile) this.regionCache.getAndMoveToFirst($$1);
        if ($$2 != null) {
            return $$2;
        }
        if (this.regionCache.size() >= 256) {
            ((RegionFile) this.regionCache.removeLast()).close();
        }
        FileUtil.createDirectoriesSafe(this.folder);
        Path $$3 = this.folder.resolve("r." + $$0.getRegionX() + "." + $$0.getRegionZ() + ".mca");
        RegionFile $$4 = new RegionFile(this.info, $$3, this.folder, this.sync);
        this.regionCache.putAndMoveToFirst($$1, $$4);
        return $$4;
    }

    public CompoundTag read(ChunkPos $$0) throws IOException {
        RegionFile $$1 = getRegionFile($$0);
        DataInputStream $$2 = $$1.getChunkDataInputStream($$0);
        if ($$2 != null) {
            try {
                CompoundTag compoundTag = NbtIo.read($$2);
                if ($$2 != null) {
                    $$2.close();
                }
                return compoundTag;
            } catch (Throwable th) {
                if ($$2 != null) {
                    try {
                        $$2.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if ($$2 != null) {
            $$2.close();
        }
        return null;
    }

    public void scanChunk(ChunkPos $$0, StreamTagVisitor $$1) throws IOException {
        RegionFile $$2 = getRegionFile($$0);
        DataInputStream $$3 = $$2.getChunkDataInputStream($$0);
        if ($$3 != null) {
            try {
                NbtIo.parse($$3, $$1, NbtAccounter.unlimitedHeap());
            } catch (Throwable th) {
                if ($$3 != null) {
                    try {
                        $$3.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if ($$3 != null) {
            $$3.close();
        }
    }

    protected void write(ChunkPos $$0, CompoundTag $$1) throws IOException {
        if (SharedConstants.DEBUG_DONT_SAVE_WORLD) {
            return;
        }
        RegionFile $$2 = getRegionFile($$0);
        if ($$1 == null) {
            $$2.clear($$0);
            return;
        }
        DataOutputStream $$3 = $$2.getChunkDataOutputStream($$0);
        try {
            NbtIo.write($$1, $$3);
            if ($$3 != null) {
                $$3.close();
            }
        } catch (Throwable th) {
            if ($$3 != null) {
                try {
                    $$3.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() throws Throwable {
        ExceptionCollector<IOException> $$0 = new ExceptionCollector<>();
        ObjectIterator it = this.regionCache.values().iterator();
        while (it.hasNext()) {
            RegionFile $$1 = (RegionFile) it.next();
            try {
                $$1.close();
            } catch (IOException $$2) {
                $$0.add($$2);
            }
        }
        $$0.throwIfPresent();
    }

    public void flush() throws IOException {
        ObjectIterator it = this.regionCache.values().iterator();
        while (it.hasNext()) {
            RegionFile $$0 = (RegionFile) it.next();
            $$0.flush();
        }
    }

    public RegionStorageInfo info() {
        return this.info;
    }
}
