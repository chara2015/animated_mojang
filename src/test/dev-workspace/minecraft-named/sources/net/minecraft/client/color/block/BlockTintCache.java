package net.minecraft.client.color.block;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.lighting.ChunkSkyLightSources;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/color/block/BlockTintCache.class */
public class BlockTintCache {
    private static final int MAX_CACHE_ENTRIES = 256;
    private final ThreadLocal<LatestCacheInfo> latestChunkOnThread = ThreadLocal.withInitial(LatestCacheInfo::new);
    private final Long2ObjectLinkedOpenHashMap<CacheData> cache = new Long2ObjectLinkedOpenHashMap<>(256, 0.25f);
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ToIntFunction<BlockPos> source;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/color/block/BlockTintCache$CacheData.class */
    static class CacheData {
        private final Int2ObjectArrayMap<int[]> cache = new Int2ObjectArrayMap<>(16);
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private static final int BLOCKS_PER_LAYER = Mth.square(16);
        private volatile boolean invalidated;

        CacheData() {
        }

        public int[] getLayer(int $$0) {
            this.lock.readLock().lock();
            try {
                int[] $$1 = (int[]) this.cache.get($$0);
                if ($$1 != null) {
                    return $$1;
                }
                this.lock.readLock().unlock();
                this.lock.writeLock().lock();
                try {
                    int[] iArr = (int[]) this.cache.computeIfAbsent($$0, $$02 -> {
                        return allocateLayer();
                    });
                    this.lock.writeLock().unlock();
                    return iArr;
                } catch (Throwable th) {
                    this.lock.writeLock().unlock();
                    throw th;
                }
            } finally {
                this.lock.readLock().unlock();
            }
        }

        private int[] allocateLayer() {
            int[] $$0 = new int[BLOCKS_PER_LAYER];
            Arrays.fill($$0, -1);
            return $$0;
        }

        public boolean isInvalidated() {
            return this.invalidated;
        }

        public void invalidate() {
            this.invalidated = true;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/color/block/BlockTintCache$LatestCacheInfo.class */
    static class LatestCacheInfo {
        public int x = ChunkSkyLightSources.NEGATIVE_INFINITY;
        public int z = ChunkSkyLightSources.NEGATIVE_INFINITY;
        CacheData cache;

        private LatestCacheInfo() {
        }
    }

    public BlockTintCache(ToIntFunction<BlockPos> $$0) {
        this.source = $$0;
    }

    public int getColor(BlockPos $$0) {
        int $$1 = SectionPos.blockToSectionCoord($$0.getX());
        int $$2 = SectionPos.blockToSectionCoord($$0.getZ());
        LatestCacheInfo $$3 = this.latestChunkOnThread.get();
        if ($$3.x != $$1 || $$3.z != $$2 || $$3.cache == null || $$3.cache.isInvalidated()) {
            $$3.x = $$1;
            $$3.z = $$2;
            $$3.cache = findOrCreateChunkCache($$1, $$2);
        }
        int[] $$4 = $$3.cache.getLayer($$0.getY());
        int $$5 = $$0.getX() & 15;
        int $$6 = $$0.getZ() & 15;
        int $$7 = ($$6 << 4) | $$5;
        int $$8 = $$4[$$7];
        if ($$8 != -1) {
            return $$8;
        }
        int $$9 = this.source.applyAsInt($$0);
        $$4[$$7] = $$9;
        return $$9;
    }

    public void invalidateForChunk(int $$0, int $$1) {
        try {
            this.lock.writeLock().lock();
            for (int $$2 = -1; $$2 <= 1; $$2++) {
                for (int $$3 = -1; $$3 <= 1; $$3++) {
                    long $$4 = ChunkPos.asLong($$0 + $$2, $$1 + $$3);
                    CacheData $$5 = (CacheData) this.cache.remove($$4);
                    if ($$5 != null) {
                        $$5.invalidate();
                    }
                }
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public void invalidateAll() {
        try {
            this.lock.writeLock().lock();
            this.cache.values().forEach((v0) -> {
                v0.invalidate();
            });
            this.cache.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    private CacheData findOrCreateChunkCache(int $$0, int $$1) {
        CacheData $$6;
        long $$2 = ChunkPos.asLong($$0, $$1);
        this.lock.readLock().lock();
        try {
            CacheData $$3 = (CacheData) this.cache.get($$2);
            if ($$3 != null) {
                return $$3;
            }
            this.lock.readLock().unlock();
            this.lock.writeLock().lock();
            try {
                CacheData $$4 = (CacheData) this.cache.get($$2);
                if ($$4 != null) {
                    return $$4;
                }
                CacheData $$5 = new CacheData();
                if (this.cache.size() >= 256 && ($$6 = (CacheData) this.cache.removeFirst()) != null) {
                    $$6.invalidate();
                }
                this.cache.put($$2, $$5);
                this.lock.writeLock().unlock();
                return $$5;
            } finally {
                this.lock.writeLock().unlock();
            }
        } finally {
            this.lock.readLock().unlock();
        }
    }
}
