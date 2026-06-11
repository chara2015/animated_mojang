package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexSorting;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.CrashReport;
import net.minecraft.TracingExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.SectionBufferBuilderPool;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Util;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.Zone;
import net.minecraft.util.thread.ConsecutiveExecutor;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/chunk/SectionRenderDispatcher.class */
public class SectionRenderDispatcher {
    private final CompileTaskDynamicQueue compileQueue = new CompileTaskDynamicQueue();
    private final Queue<Runnable> toUpload = Queues.newConcurrentLinkedQueue();
    final Executor mainThreadUploadExecutor;
    final Queue<SectionMesh> toClose;
    final SectionBufferBuilderPack fixedBuffers;
    private final SectionBufferBuilderPool bufferPool;
    volatile boolean closed;
    private final ConsecutiveExecutor consecutiveExecutor;
    private final TracingExecutor executor;
    ClientLevel level;
    final LevelRenderer renderer;
    Vec3 cameraPosition;
    final SectionCompiler sectionCompiler;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/chunk/SectionRenderDispatcher$SectionTaskResult.class */
    enum SectionTaskResult {
        SUCCESSFUL,
        CANCELLED
    }

    public SectionRenderDispatcher(ClientLevel $$0, LevelRenderer $$1, TracingExecutor $$2, RenderBuffers $$3, BlockRenderDispatcher $$4, BlockEntityRenderDispatcher $$5) {
        Queue<Runnable> queue = this.toUpload;
        Objects.requireNonNull(queue);
        this.mainThreadUploadExecutor = (v1) -> {
            r1.add(v1);
        };
        this.toClose = Queues.newConcurrentLinkedQueue();
        this.cameraPosition = Vec3.ZERO;
        this.level = $$0;
        this.renderer = $$1;
        this.fixedBuffers = $$3.fixedBufferPack();
        this.bufferPool = $$3.sectionBufferPool();
        this.executor = $$2;
        this.consecutiveExecutor = new ConsecutiveExecutor($$2, "Section Renderer");
        this.consecutiveExecutor.schedule(this::runTask);
        this.sectionCompiler = new SectionCompiler($$4, $$5);
    }

    public void setLevel(ClientLevel $$0) {
        this.level = $$0;
    }

    private void runTask() {
        RenderSection.CompileTask $$0;
        if (this.closed || this.bufferPool.isEmpty() || ($$0 = this.compileQueue.poll(this.cameraPosition)) == null) {
            return;
        }
        SectionBufferBuilderPack $$1 = (SectionBufferBuilderPack) Objects.requireNonNull(this.bufferPool.acquire());
        CompletableFuture.supplyAsync(() -> {
            return $$0.doTask($$1);
        }, this.executor.forName($$0.name())).thenCompose($$02 -> {
            return $$02;
        }).whenComplete(($$2, $$3) -> {
            if ($$3 != null) {
                Minecraft.getInstance().delayCrash(CrashReport.forThrowable($$3, "Batching sections"));
            } else {
                $$0.isCompleted.set(true);
                this.consecutiveExecutor.schedule(() -> {
                    if ($$2 == SectionTaskResult.SUCCESSFUL) {
                        $$1.clearAll();
                    } else {
                        $$1.discardAll();
                    }
                    this.bufferPool.release($$1);
                    runTask();
                });
            }
        });
    }

    public void setCameraPosition(Vec3 $$0) {
        this.cameraPosition = $$0;
    }

    public void uploadAllPendingUploads() {
        while (true) {
            Runnable $$0 = this.toUpload.poll();
            if ($$0 == null) {
                break;
            } else {
                $$0.run();
            }
        }
        while (true) {
            SectionMesh $$1 = this.toClose.poll();
            if ($$1 != null) {
                $$1.close();
            } else {
                return;
            }
        }
    }

    public void rebuildSectionSync(RenderSection $$0, RenderRegionCache $$1) {
        $$0.compileSync($$1);
    }

    public void schedule(RenderSection.CompileTask $$0) {
        if (this.closed) {
            return;
        }
        this.consecutiveExecutor.schedule(() -> {
            if (this.closed) {
                return;
            }
            this.compileQueue.add($$0);
            runTask();
        });
    }

    public void clearCompileQueue() {
        this.compileQueue.clear();
    }

    public boolean isQueueEmpty() {
        return this.compileQueue.size() == 0 && this.toUpload.isEmpty();
    }

    public void dispose() {
        this.closed = true;
        clearCompileQueue();
        uploadAllPendingUploads();
    }

    @VisibleForDebug
    public String getStats() {
        return String.format(Locale.ROOT, "pC: %03d, pU: %02d, aB: %02d", Integer.valueOf(this.compileQueue.size()), Integer.valueOf(this.toUpload.size()), Integer.valueOf(this.bufferPool.getFreeBufferCount()));
    }

    @VisibleForDebug
    public int getCompileQueueSize() {
        return this.compileQueue.size();
    }

    @VisibleForDebug
    public int getToUpload() {
        return this.toUpload.size();
    }

    @VisibleForDebug
    public int getFreeBufferCount() {
        return this.bufferPool.getFreeBufferCount();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection.class */
    public class RenderSection {
        public static final int SIZE = 16;
        public final int index;
        private RebuildTask lastRebuildTask;
        private ResortTransparencyTask lastResortTransparencyTask;
        private AABB bb;
        private boolean playerChanged;
        private long uploadedTime;
        private long fadeDuration;
        private boolean wasPreviouslyEmpty;
        public final AtomicReference<SectionMesh> sectionMesh = new AtomicReference<>(CompiledSectionMesh.UNCOMPILED);
        private boolean dirty = true;
        volatile long sectionNode = SectionPos.asLong(-1, -1, -1);
        final BlockPos.MutableBlockPos renderOrigin = new BlockPos.MutableBlockPos(-1, -1, -1);

        public RenderSection(int $$1, long $$2) {
            this.index = $$1;
            setSectionNode($$2);
        }

        public float getVisibility(long $$0) {
            long $$1 = $$0 - this.uploadedTime;
            if ($$1 >= this.fadeDuration) {
                return 1.0f;
            }
            return $$1 / this.fadeDuration;
        }

        public void setFadeDuration(long $$0) {
            this.fadeDuration = $$0;
        }

        public void setWasPreviouslyEmpty(boolean $$0) {
            this.wasPreviouslyEmpty = $$0;
        }

        public boolean wasPreviouslyEmpty() {
            return this.wasPreviouslyEmpty;
        }

        private boolean doesChunkExistAt(long $$0) {
            ChunkAccess $$1 = SectionRenderDispatcher.this.level.getChunk(SectionPos.x($$0), SectionPos.z($$0), ChunkStatus.FULL, false);
            return $$1 != null && SectionRenderDispatcher.this.level.getLightEngine().lightOnInColumn(SectionPos.getZeroNode($$0));
        }

        public boolean hasAllNeighbors() {
            return doesChunkExistAt(SectionPos.offset(this.sectionNode, Direction.WEST)) && doesChunkExistAt(SectionPos.offset(this.sectionNode, Direction.NORTH)) && doesChunkExistAt(SectionPos.offset(this.sectionNode, Direction.EAST)) && doesChunkExistAt(SectionPos.offset(this.sectionNode, Direction.SOUTH)) && doesChunkExistAt(SectionPos.offset(this.sectionNode, -1, 0, -1)) && doesChunkExistAt(SectionPos.offset(this.sectionNode, -1, 0, 1)) && doesChunkExistAt(SectionPos.offset(this.sectionNode, 1, 0, -1)) && doesChunkExistAt(SectionPos.offset(this.sectionNode, 1, 0, 1));
        }

        public AABB getBoundingBox() {
            return this.bb;
        }

        public CompletableFuture<Void> upload(Map<ChunkSectionLayer, MeshData> $$0, CompiledSectionMesh $$1) {
            if (SectionRenderDispatcher.this.closed) {
                $$0.values().forEach((v0) -> {
                    v0.close();
                });
                return CompletableFuture.completedFuture(null);
            }
            return CompletableFuture.runAsync(() -> {
                $$0.forEach(($$12, $$2) -> {
                    Zone $$3 = Profiler.get().zone("Upload Section Layer");
                    try {
                        $$1.uploadMeshLayer($$12, $$2, this.sectionNode);
                        $$2.close();
                        if ($$3 != null) {
                            $$3.close();
                        }
                        if (this.uploadedTime == 0) {
                            this.uploadedTime = Util.getMillis();
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
                });
            }, SectionRenderDispatcher.this.mainThreadUploadExecutor);
        }

        public CompletableFuture<Void> uploadSectionIndexBuffer(CompiledSectionMesh $$0, ByteBufferBuilder.Result $$1, ChunkSectionLayer $$2) {
            if (SectionRenderDispatcher.this.closed) {
                $$1.close();
                return CompletableFuture.completedFuture(null);
            }
            return CompletableFuture.runAsync(() -> {
                Zone $$3 = Profiler.get().zone("Upload Section Indices");
                try {
                    $$0.uploadLayerIndexBuffer($$2, $$1, this.sectionNode);
                    $$1.close();
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
            }, SectionRenderDispatcher.this.mainThreadUploadExecutor);
        }

        public void setSectionNode(long $$0) {
            reset();
            this.sectionNode = $$0;
            int $$1 = SectionPos.sectionToBlockCoord(SectionPos.x($$0));
            int $$2 = SectionPos.sectionToBlockCoord(SectionPos.y($$0));
            int $$3 = SectionPos.sectionToBlockCoord(SectionPos.z($$0));
            this.renderOrigin.set($$1, $$2, $$3);
            this.bb = new AABB($$1, $$2, $$3, $$1 + 16, $$2 + 16, $$3 + 16);
        }

        public SectionMesh getSectionMesh() {
            return this.sectionMesh.get();
        }

        public void reset() {
            cancelTasks();
            this.sectionMesh.getAndSet(CompiledSectionMesh.UNCOMPILED).close();
            this.dirty = true;
            this.uploadedTime = 0L;
            this.wasPreviouslyEmpty = false;
        }

        public BlockPos getRenderOrigin() {
            return this.renderOrigin;
        }

        public long getSectionNode() {
            return this.sectionNode;
        }

        public void setDirty(boolean $$0) {
            boolean $$1 = this.dirty;
            this.dirty = true;
            this.playerChanged = $$0 | ($$1 && this.playerChanged);
        }

        public void setNotDirty() {
            this.dirty = false;
            this.playerChanged = false;
        }

        public boolean isDirty() {
            return this.dirty;
        }

        public boolean isDirtyFromPlayer() {
            return this.dirty && this.playerChanged;
        }

        public long getNeighborSectionNode(Direction $$0) {
            return SectionPos.offset(this.sectionNode, $$0);
        }

        public void resortTransparency(SectionRenderDispatcher $$0) {
            SectionMesh sectionMesh = getSectionMesh();
            if (sectionMesh instanceof CompiledSectionMesh) {
                CompiledSectionMesh $$1 = (CompiledSectionMesh) sectionMesh;
                this.lastResortTransparencyTask = new ResortTransparencyTask($$1);
                $$0.schedule(this.lastResortTransparencyTask);
            }
        }

        public boolean hasTranslucentGeometry() {
            return getSectionMesh().hasTranslucentGeometry();
        }

        public boolean transparencyResortingScheduled() {
            return (this.lastResortTransparencyTask == null || this.lastResortTransparencyTask.isCompleted.get()) ? false : true;
        }

        protected void cancelTasks() {
            if (this.lastRebuildTask != null) {
                this.lastRebuildTask.cancel();
                this.lastRebuildTask = null;
            }
            if (this.lastResortTransparencyTask != null) {
                this.lastResortTransparencyTask.cancel();
                this.lastResortTransparencyTask = null;
            }
        }

        public CompileTask createCompileTask(RenderRegionCache $$0) {
            cancelTasks();
            RenderSectionRegion $$1 = $$0.createRegion(SectionRenderDispatcher.this.level, this.sectionNode);
            boolean $$2 = this.sectionMesh.get() != CompiledSectionMesh.UNCOMPILED;
            this.lastRebuildTask = new RebuildTask($$1, $$2);
            return this.lastRebuildTask;
        }

        public void rebuildSectionAsync(RenderRegionCache $$0) {
            CompileTask $$1 = createCompileTask($$0);
            SectionRenderDispatcher.this.schedule($$1);
        }

        public void compileSync(RenderRegionCache $$0) {
            CompileTask $$1 = createCompileTask($$0);
            $$1.doTask(SectionRenderDispatcher.this.fixedBuffers);
        }

        void setSectionMesh(SectionMesh $$0) {
            SectionMesh $$1 = this.sectionMesh.getAndSet($$0);
            SectionRenderDispatcher.this.toClose.add($$1);
            SectionRenderDispatcher.this.renderer.addRecentlyCompiledSection(this);
        }

        VertexSorting createVertexSorting(SectionPos $$0) {
            Vec3 $$1 = SectionRenderDispatcher.this.cameraPosition;
            return VertexSorting.byDistance((float) ($$1.x - ((double) $$0.minBlockX())), (float) ($$1.y - ((double) $$0.minBlockY())), (float) ($$1.z - ((double) $$0.minBlockZ())));
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection$RebuildTask.class */
        class RebuildTask extends CompileTask {
            protected final RenderSectionRegion region;

            public RebuildTask(RenderSectionRegion $$0, boolean $$1) {
                super($$1);
                this.region = $$0;
            }

            @Override // net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask
            protected String name() {
                return "rend_chk_rebuild";
            }

            @Override // net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask
            public CompletableFuture<SectionTaskResult> doTask(SectionBufferBuilderPack $$0) {
                if (this.isCancelled.get()) {
                    return CompletableFuture.completedFuture(SectionTaskResult.CANCELLED);
                }
                long $$1 = RenderSection.this.sectionNode;
                SectionPos $$2 = SectionPos.of($$1);
                if (this.isCancelled.get()) {
                    return CompletableFuture.completedFuture(SectionTaskResult.CANCELLED);
                }
                Zone $$3 = Profiler.get().zone("Compile Section");
                try {
                    SectionCompiler.Results $$5 = SectionRenderDispatcher.this.sectionCompiler.compile($$2, this.region, RenderSection.this.createVertexSorting($$2), $$0);
                    if ($$3 != null) {
                        $$3.close();
                    }
                    TranslucencyPointOfView $$6 = TranslucencyPointOfView.of(SectionRenderDispatcher.this.cameraPosition, $$1);
                    if (this.isCancelled.get()) {
                        $$5.release();
                        return CompletableFuture.completedFuture(SectionTaskResult.CANCELLED);
                    }
                    CompiledSectionMesh $$7 = new CompiledSectionMesh($$6, $$5);
                    CompletableFuture<Void> $$8 = RenderSection.this.upload($$5.renderedLayers, $$7);
                    return $$8.handle(($$12, $$22) -> {
                        if ($$22 != null && !($$22 instanceof CancellationException) && !($$22 instanceof InterruptedException)) {
                            Minecraft.getInstance().delayCrash(CrashReport.forThrowable($$22, "Rendering section"));
                        }
                        if (this.isCancelled.get() || SectionRenderDispatcher.this.closed) {
                            SectionRenderDispatcher.this.toClose.add($$7);
                            return SectionTaskResult.CANCELLED;
                        }
                        RenderSection.this.setSectionMesh($$7);
                        return SectionTaskResult.SUCCESSFUL;
                    });
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

            @Override // net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask
            public void cancel() {
                if (this.isCancelled.compareAndSet(false, true)) {
                    RenderSection.this.setDirty(false);
                }
            }
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection$ResortTransparencyTask.class */
        class ResortTransparencyTask extends CompileTask {
            private final CompiledSectionMesh compiledSectionMesh;

            public ResortTransparencyTask(CompiledSectionMesh $$0) {
                super(true);
                this.compiledSectionMesh = $$0;
            }

            @Override // net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask
            protected String name() {
                return "rend_chk_sort";
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
            @Override // net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask
            public CompletableFuture<SectionTaskResult> doTask(SectionBufferBuilderPack $$0) throws MatchException {
                if (this.isCancelled.get()) {
                    return CompletableFuture.completedFuture(SectionTaskResult.CANCELLED);
                }
                MeshData.SortState $$1 = this.compiledSectionMesh.getTransparencyState();
                if ($$1 == null || this.compiledSectionMesh.isEmpty(ChunkSectionLayer.TRANSLUCENT)) {
                    return CompletableFuture.completedFuture(SectionTaskResult.CANCELLED);
                }
                long $$2 = RenderSection.this.sectionNode;
                VertexSorting $$3 = RenderSection.this.createVertexSorting(SectionPos.of($$2));
                TranslucencyPointOfView $$4 = TranslucencyPointOfView.of(SectionRenderDispatcher.this.cameraPosition, $$2);
                if (!this.compiledSectionMesh.isDifferentPointOfView($$4) && !$$4.isAxisAligned()) {
                    return CompletableFuture.completedFuture(SectionTaskResult.CANCELLED);
                }
                ByteBufferBuilder.Result $$5 = $$1.buildSortedIndexBuffer($$0.buffer(ChunkSectionLayer.TRANSLUCENT), $$3);
                if ($$5 == null) {
                    return CompletableFuture.completedFuture(SectionTaskResult.CANCELLED);
                }
                if (this.isCancelled.get()) {
                    $$5.close();
                    return CompletableFuture.completedFuture(SectionTaskResult.CANCELLED);
                }
                CompletableFuture<Void> $$6 = RenderSection.this.uploadSectionIndexBuffer(this.compiledSectionMesh, $$5, ChunkSectionLayer.TRANSLUCENT);
                return $$6.handle(($$12, $$22) -> {
                    if ($$22 != null && !($$22 instanceof CancellationException) && !($$22 instanceof InterruptedException)) {
                        Minecraft.getInstance().delayCrash(CrashReport.forThrowable($$22, "Rendering section"));
                    }
                    if (this.isCancelled.get()) {
                        return SectionTaskResult.CANCELLED;
                    }
                    this.compiledSectionMesh.setTranslucencyPointOfView($$4);
                    return SectionTaskResult.SUCCESSFUL;
                });
            }

            @Override // net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask
            public void cancel() {
                this.isCancelled.set(true);
            }
        }

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection$CompileTask.class */
        public abstract class CompileTask {
            protected final AtomicBoolean isCancelled = new AtomicBoolean(false);
            protected final AtomicBoolean isCompleted = new AtomicBoolean(false);
            protected final boolean isRecompile;

            public abstract CompletableFuture<SectionTaskResult> doTask(SectionBufferBuilderPack sectionBufferBuilderPack);

            public abstract void cancel();

            protected abstract String name();

            public CompileTask(boolean $$1) {
                this.isRecompile = $$1;
            }

            public boolean isRecompile() {
                return this.isRecompile;
            }

            public BlockPos getRenderOrigin() {
                return RenderSection.this.renderOrigin;
            }
        }
    }
}
