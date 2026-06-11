package net.minecraft.server.level;

import net.minecraft.world.level.TicketStorage;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/LoadingChunkTracker.class */
class LoadingChunkTracker extends ChunkTracker {
    private static final int MAX_LEVEL = ChunkLevel.MAX_LEVEL + 1;
    private final DistanceManager distanceManager;
    private final TicketStorage ticketStorage;

    public LoadingChunkTracker(DistanceManager $$0, TicketStorage $$1) {
        super(MAX_LEVEL + 1, 16, 256);
        this.distanceManager = $$0;
        this.ticketStorage = $$1;
        $$1.setLoadingChunkUpdatedListener(this::update);
    }

    @Override // net.minecraft.server.level.ChunkTracker
    protected int getLevelFromSource(long $$0) {
        return this.ticketStorage.getTicketLevelAt($$0, false);
    }

    @Override // net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint
    protected int getLevel(long $$0) {
        ChunkHolder $$1;
        if (!this.distanceManager.isChunkToRemove($$0) && ($$1 = this.distanceManager.getChunk($$0)) != null) {
            return $$1.getTicketLevel();
        }
        return MAX_LEVEL;
    }

    @Override // net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint
    protected void setLevel(long $$0, int $$1) {
        ChunkHolder $$2;
        ChunkHolder $$22 = this.distanceManager.getChunk($$0);
        int $$3 = $$22 == null ? MAX_LEVEL : $$22.getTicketLevel();
        if ($$3 != $$1 && ($$2 = this.distanceManager.updateChunkScheduling($$0, $$1, $$22, $$3)) != null) {
            this.distanceManager.chunksToUpdateFutures.add($$2);
        }
    }

    public int runDistanceUpdates(int $$0) {
        return runUpdates($$0);
    }
}
