package net.minecraft.server.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.minecraft.util.StaticCache2D;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.Zone;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkDependencies;
import net.minecraft.world.level.chunk.status.ChunkPyramid;
import net.minecraft.world.level.chunk.status.ChunkStatus;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/ChunkGenerationTask.class */
public class ChunkGenerationTask {
    private final GeneratingChunkMap chunkMap;
    private final ChunkPos pos;
    public final ChunkStatus targetStatus;
    private volatile boolean markedForCancellation;
    private final StaticCache2D<GenerationChunkHolder> cache;
    private boolean needsGeneration;
    private ChunkStatus scheduledStatus = null;
    private final List<CompletableFuture<ChunkResult<ChunkAccess>>> scheduledLayer = new ArrayList();

    private ChunkGenerationTask(GeneratingChunkMap $$0, ChunkStatus $$1, ChunkPos $$2, StaticCache2D<GenerationChunkHolder> $$3) {
        this.chunkMap = $$0;
        this.targetStatus = $$1;
        this.pos = $$2;
        this.cache = $$3;
    }

    public static ChunkGenerationTask create(GeneratingChunkMap $$0, ChunkStatus $$1, ChunkPos $$2) {
        int $$3 = ChunkPyramid.GENERATION_PYRAMID.getStepTo($$1).getAccumulatedRadiusOf(ChunkStatus.EMPTY);
        StaticCache2D<GenerationChunkHolder> $$4 = StaticCache2D.create($$2.x, $$2.z, $$3, ($$12, $$22) -> {
            return $$0.acquireGeneration(ChunkPos.asLong($$12, $$22));
        });
        return new ChunkGenerationTask($$0, $$1, $$2, $$4);
    }

    public CompletableFuture<?> runUntilWait() {
        while (true) {
            CompletableFuture<?> $$0 = waitForScheduledLayer();
            if ($$0 != null) {
                return $$0;
            }
            if (this.markedForCancellation || this.scheduledStatus == this.targetStatus) {
                break;
            }
            scheduleNextLayer();
        }
        releaseClaim();
        return null;
    }

    private void scheduleNextLayer() {
        ChunkStatus $$2;
        if (this.scheduledStatus == null) {
            $$2 = ChunkStatus.EMPTY;
        } else if (!this.needsGeneration && this.scheduledStatus == ChunkStatus.EMPTY && !canLoadWithoutGeneration()) {
            this.needsGeneration = true;
            $$2 = ChunkStatus.EMPTY;
        } else {
            $$2 = ChunkStatus.getStatusList().get(this.scheduledStatus.getIndex() + 1);
        }
        scheduleLayer($$2, this.needsGeneration);
        this.scheduledStatus = $$2;
    }

    public void markForCancellation() {
        this.markedForCancellation = true;
    }

    private void releaseClaim() {
        GenerationChunkHolder $$0 = this.cache.get(this.pos.x, this.pos.z);
        $$0.removeTask(this);
        StaticCache2D<GenerationChunkHolder> staticCache2D = this.cache;
        GeneratingChunkMap generatingChunkMap = this.chunkMap;
        Objects.requireNonNull(generatingChunkMap);
        staticCache2D.forEach(generatingChunkMap::releaseGeneration);
    }

    private boolean canLoadWithoutGeneration() {
        if (this.targetStatus == ChunkStatus.EMPTY) {
            return true;
        }
        ChunkStatus $$0 = this.cache.get(this.pos.x, this.pos.z).getPersistedStatus();
        if ($$0 == null || $$0.isBefore(this.targetStatus)) {
            return false;
        }
        ChunkDependencies $$1 = ChunkPyramid.LOADING_PYRAMID.getStepTo(this.targetStatus).accumulatedDependencies();
        int $$2 = $$1.getRadius();
        for (int $$3 = this.pos.x - $$2; $$3 <= this.pos.x + $$2; $$3++) {
            for (int $$4 = this.pos.z - $$2; $$4 <= this.pos.z + $$2; $$4++) {
                int $$5 = this.pos.getChessboardDistance($$3, $$4);
                ChunkStatus $$6 = $$1.get($$5);
                ChunkStatus $$7 = this.cache.get($$3, $$4).getPersistedStatus();
                if ($$7 == null || $$7.isBefore($$6)) {
                    return false;
                }
            }
        }
        return true;
    }

    public GenerationChunkHolder getCenter() {
        return this.cache.get(this.pos.x, this.pos.z);
    }

    private void scheduleLayer(ChunkStatus $$0, boolean $$1) {
        Zone $$2 = Profiler.get().zone("scheduleLayer");
        try {
            Objects.requireNonNull($$0);
            $$2.addText($$0::getName);
            int $$3 = getRadiusForLayer($$0, $$1);
            for (int $$4 = this.pos.x - $$3; $$4 <= this.pos.x + $$3; $$4++) {
                for (int $$5 = this.pos.z - $$3; $$5 <= this.pos.z + $$3; $$5++) {
                    GenerationChunkHolder $$6 = this.cache.get($$4, $$5);
                    if (this.markedForCancellation || !scheduleChunkInLayer($$0, $$1, $$6)) {
                        if ($$2 != null) {
                            $$2.close();
                            return;
                        }
                        return;
                    }
                }
            }
            if ($$2 != null) {
                $$2.close();
            }
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

    private int getRadiusForLayer(ChunkStatus $$0, boolean $$1) {
        ChunkPyramid $$2 = $$1 ? ChunkPyramid.GENERATION_PYRAMID : ChunkPyramid.LOADING_PYRAMID;
        return $$2.getStepTo(this.targetStatus).getAccumulatedRadiusOf($$0);
    }

    private boolean scheduleChunkInLayer(ChunkStatus $$0, boolean $$1, GenerationChunkHolder $$2) {
        ChunkStatus $$3 = $$2.getPersistedStatus();
        boolean $$4 = $$3 != null && $$0.isAfter($$3);
        ChunkPyramid $$5 = $$4 ? ChunkPyramid.GENERATION_PYRAMID : ChunkPyramid.LOADING_PYRAMID;
        if ($$4 && !$$1) {
            throw new IllegalStateException("Can't load chunk, but didn't expect to need to generate");
        }
        CompletableFuture<ChunkResult<ChunkAccess>> $$6 = $$2.applyStep($$5.getStepTo($$0), this.chunkMap, this.cache);
        ChunkResult<ChunkAccess> $$7 = $$6.getNow(null);
        if ($$7 == null) {
            this.scheduledLayer.add($$6);
            return true;
        }
        if ($$7.isSuccess()) {
            return true;
        }
        markForCancellation();
        return false;
    }

    private CompletableFuture<?> waitForScheduledLayer() {
        while (!this.scheduledLayer.isEmpty()) {
            CompletableFuture<ChunkResult<ChunkAccess>> $$0 = (CompletableFuture) this.scheduledLayer.getLast();
            ChunkResult<ChunkAccess> $$1 = $$0.getNow(null);
            if ($$1 == null) {
                return $$0;
            }
            this.scheduledLayer.removeLast();
            if (!$$1.isSuccess()) {
                markForCancellation();
            }
        }
        return null;
    }
}
