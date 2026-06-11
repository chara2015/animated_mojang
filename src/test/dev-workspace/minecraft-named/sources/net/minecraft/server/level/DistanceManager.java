package net.minecraft.server.level;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMaps;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongConsumer;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.SharedConstants;
import net.minecraft.core.SectionPos;
import net.minecraft.util.TriState;
import net.minecraft.util.thread.TaskScheduler;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.TicketStorage;
import net.minecraft.world.level.chunk.LevelChunk;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/DistanceManager.class */
public abstract class DistanceManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    static final int PLAYER_TICKET_LEVEL = ChunkLevel.byStatus(FullChunkStatus.ENTITY_TICKING);
    private final LoadingChunkTracker loadingChunkTracker;
    private final SimulationChunkTracker simulationChunkTracker;
    final TicketStorage ticketStorage;
    final ThrottlingChunkTaskDispatcher ticketDispatcher;
    final Executor mainThreadExecutor;
    final Long2ObjectMap<ObjectSet<ServerPlayer>> playersPerChunk = new Long2ObjectOpenHashMap();
    private final FixedPlayerDistanceChunkTracker naturalSpawnChunkCounter = new FixedPlayerDistanceChunkTracker(8);
    private final PlayerTicketTracker playerTicketManager = new PlayerTicketTracker(32);
    protected final Set<ChunkHolder> chunksToUpdateFutures = new ReferenceOpenHashSet();
    final LongSet ticketsToRelease = new LongOpenHashSet();
    private int simulationDistance = 10;

    protected abstract boolean isChunkToRemove(long j);

    protected abstract ChunkHolder getChunk(long j);

    protected abstract ChunkHolder updateChunkScheduling(long j, int i, ChunkHolder chunkHolder, int i2);

    protected DistanceManager(TicketStorage $$0, Executor $$1, Executor $$2) {
        this.ticketStorage = $$0;
        this.loadingChunkTracker = new LoadingChunkTracker(this, $$0);
        this.simulationChunkTracker = new SimulationChunkTracker($$0);
        TaskScheduler<Runnable> $$3 = TaskScheduler.wrapExecutor("player ticket throttler", $$2);
        this.ticketDispatcher = new ThrottlingChunkTaskDispatcher($$3, $$1, 4);
        this.mainThreadExecutor = $$2;
    }

    public boolean runAllUpdates(ChunkMap $$0) {
        this.naturalSpawnChunkCounter.runAllUpdates();
        this.simulationChunkTracker.runAllUpdates();
        this.playerTicketManager.runAllUpdates();
        int $$1 = Integer.MAX_VALUE - this.loadingChunkTracker.runDistanceUpdates(Integer.MAX_VALUE);
        boolean $$2 = $$1 != 0;
        if ($$2 && SharedConstants.DEBUG_VERBOSE_SERVER_EVENTS) {
            LOGGER.debug("DMU {}", Integer.valueOf($$1));
        }
        if (!this.chunksToUpdateFutures.isEmpty()) {
            for (ChunkHolder $$3 : this.chunksToUpdateFutures) {
                $$3.updateHighestAllowedStatus($$0);
            }
            for (ChunkHolder $$4 : this.chunksToUpdateFutures) {
                $$4.updateFutures($$0, this.mainThreadExecutor);
            }
            this.chunksToUpdateFutures.clear();
            return true;
        }
        if (!this.ticketsToRelease.isEmpty()) {
            LongIterator $$5 = this.ticketsToRelease.iterator();
            while ($$5.hasNext()) {
                long $$6 = $$5.nextLong();
                if (this.ticketStorage.getTickets($$6).stream().anyMatch($$02 -> {
                    return $$02.getType() == TicketType.PLAYER_LOADING;
                })) {
                    ChunkHolder $$7 = $$0.getUpdatingChunkIfPresent($$6);
                    if ($$7 == null) {
                        throw new IllegalStateException();
                    }
                    CompletableFuture<ChunkResult<LevelChunk>> $$8 = $$7.getEntityTickingChunkFuture();
                    $$8.thenAccept($$12 -> {
                        this.mainThreadExecutor.execute(() -> {
                            this.ticketDispatcher.release($$6, () -> {
                            }, false);
                        });
                    });
                }
            }
            this.ticketsToRelease.clear();
        }
        return $$2;
    }

    public void addPlayer(SectionPos $$0, ServerPlayer $$1) {
        ChunkPos $$2 = $$0.chunk();
        long $$3 = $$2.toLong();
        ((ObjectSet) this.playersPerChunk.computeIfAbsent($$3, $$02 -> {
            return new ObjectOpenHashSet();
        })).add($$1);
        this.naturalSpawnChunkCounter.update($$3, 0, true);
        this.playerTicketManager.update($$3, 0, true);
        this.ticketStorage.addTicket(new Ticket(TicketType.PLAYER_SIMULATION, getPlayerTicketLevel()), $$2);
    }

    public void removePlayer(SectionPos $$0, ServerPlayer $$1) {
        ChunkPos $$2 = $$0.chunk();
        long $$3 = $$2.toLong();
        ObjectSet<ServerPlayer> $$4 = (ObjectSet) this.playersPerChunk.get($$3);
        $$4.remove($$1);
        if ($$4.isEmpty()) {
            this.playersPerChunk.remove($$3);
            this.naturalSpawnChunkCounter.update($$3, Integer.MAX_VALUE, false);
            this.playerTicketManager.update($$3, Integer.MAX_VALUE, false);
            this.ticketStorage.removeTicket(new Ticket(TicketType.PLAYER_SIMULATION, getPlayerTicketLevel()), $$2);
        }
    }

    private int getPlayerTicketLevel() {
        return Math.max(0, ChunkLevel.byStatus(FullChunkStatus.ENTITY_TICKING) - this.simulationDistance);
    }

    public boolean inEntityTickingRange(long $$0) {
        return ChunkLevel.isEntityTicking(this.simulationChunkTracker.getLevel($$0));
    }

    public boolean inBlockTickingRange(long $$0) {
        return ChunkLevel.isBlockTicking(this.simulationChunkTracker.getLevel($$0));
    }

    public int getChunkLevel(long $$0, boolean $$1) {
        if ($$1) {
            return this.simulationChunkTracker.getLevel($$0);
        }
        return this.loadingChunkTracker.getLevel($$0);
    }

    protected void updatePlayerTickets(int $$0) {
        this.playerTicketManager.updateViewDistance($$0);
    }

    public void updateSimulationDistance(int $$0) {
        if ($$0 != this.simulationDistance) {
            this.simulationDistance = $$0;
            this.ticketStorage.replaceTicketLevelOfType(getPlayerTicketLevel(), TicketType.PLAYER_SIMULATION);
        }
    }

    public int getNaturalSpawnChunkCount() {
        this.naturalSpawnChunkCounter.runAllUpdates();
        return this.naturalSpawnChunkCounter.chunks.size();
    }

    public TriState hasPlayersNearby(long $$0) {
        this.naturalSpawnChunkCounter.runAllUpdates();
        int $$1 = this.naturalSpawnChunkCounter.getLevel($$0);
        if ($$1 <= NaturalSpawner.INSCRIBED_SQUARE_SPAWN_DISTANCE_CHUNK) {
            return TriState.TRUE;
        }
        if ($$1 > 8) {
            return TriState.FALSE;
        }
        return TriState.DEFAULT;
    }

    public void forEachEntityTickingChunk(LongConsumer $$0) {
        ObjectIterator it = Long2ByteMaps.fastIterable(this.simulationChunkTracker.chunks).iterator();
        while (it.hasNext()) {
            Long2ByteMap.Entry $$1 = (Long2ByteMap.Entry) it.next();
            byte $$2 = $$1.getByteValue();
            long $$3 = $$1.getLongKey();
            if (ChunkLevel.isEntityTicking($$2)) {
                $$0.accept($$3);
            }
        }
    }

    public LongIterator getSpawnCandidateChunks() {
        this.naturalSpawnChunkCounter.runAllUpdates();
        return this.naturalSpawnChunkCounter.chunks.keySet().iterator();
    }

    public String getDebugStatus() {
        return this.ticketDispatcher.getDebugStatus();
    }

    public boolean hasTickets() {
        return this.ticketStorage.hasTickets();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/DistanceManager$FixedPlayerDistanceChunkTracker.class */
    class FixedPlayerDistanceChunkTracker extends ChunkTracker {
        protected final Long2ByteMap chunks;
        protected final int maxDistance;

        protected FixedPlayerDistanceChunkTracker(int $$0) {
            super($$0 + 2, 16, 256);
            this.chunks = new Long2ByteOpenHashMap();
            this.maxDistance = $$0;
            this.chunks.defaultReturnValue((byte) ($$0 + 2));
        }

        @Override // net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint
        protected int getLevel(long $$0) {
            return this.chunks.get($$0);
        }

        @Override // net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint
        protected void setLevel(long $$0, int $$1) {
            byte $$3;
            if ($$1 > this.maxDistance) {
                $$3 = this.chunks.remove($$0);
            } else {
                $$3 = this.chunks.put($$0, (byte) $$1);
            }
            onLevelChange($$0, $$3, $$1);
        }

        protected void onLevelChange(long $$0, int $$1, int $$2) {
        }

        @Override // net.minecraft.server.level.ChunkTracker
        protected int getLevelFromSource(long $$0) {
            return havePlayer($$0) ? 0 : Integer.MAX_VALUE;
        }

        private boolean havePlayer(long $$0) {
            ObjectSet<ServerPlayer> $$1 = (ObjectSet) DistanceManager.this.playersPerChunk.get($$0);
            return ($$1 == null || $$1.isEmpty()) ? false : true;
        }

        public void runAllUpdates() {
            runUpdates(Integer.MAX_VALUE);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/DistanceManager$PlayerTicketTracker.class */
    class PlayerTicketTracker extends FixedPlayerDistanceChunkTracker {
        private int viewDistance;
        private final Long2IntMap queueLevels;
        private final LongSet toUpdate;

        protected PlayerTicketTracker(int $$0) {
            super($$0);
            this.queueLevels = Long2IntMaps.synchronize(new Long2IntOpenHashMap());
            this.toUpdate = new LongOpenHashSet();
            this.viewDistance = 0;
            this.queueLevels.defaultReturnValue($$0 + 2);
        }

        @Override // net.minecraft.server.level.DistanceManager.FixedPlayerDistanceChunkTracker
        protected void onLevelChange(long $$0, int $$1, int $$2) {
            this.toUpdate.add($$0);
        }

        public void updateViewDistance(int $$0) {
            ObjectIterator it = this.chunks.long2ByteEntrySet().iterator();
            while (it.hasNext()) {
                Long2ByteMap.Entry $$1 = (Long2ByteMap.Entry) it.next();
                byte $$2 = $$1.getByteValue();
                long $$3 = $$1.getLongKey();
                onLevelChange($$3, $$2, haveTicketFor($$2), $$2 <= $$0);
            }
            this.viewDistance = $$0;
        }

        private void onLevelChange(long $$0, int $$1, boolean $$2, boolean $$3) {
            if ($$2 != $$3) {
                Ticket $$4 = new Ticket(TicketType.PLAYER_LOADING, DistanceManager.PLAYER_TICKET_LEVEL);
                if ($$3) {
                    DistanceManager.this.ticketDispatcher.submit(() -> {
                        DistanceManager.this.mainThreadExecutor.execute(() -> {
                            if (haveTicketFor(getLevel($$0))) {
                                DistanceManager.this.ticketStorage.addTicket($$0, $$4);
                                DistanceManager.this.ticketsToRelease.add($$0);
                            } else {
                                DistanceManager.this.ticketDispatcher.release($$0, () -> {
                                }, false);
                            }
                        });
                    }, $$0, () -> {
                        return $$1;
                    });
                } else {
                    DistanceManager.this.ticketDispatcher.release($$0, () -> {
                        DistanceManager.this.mainThreadExecutor.execute(() -> {
                            DistanceManager.this.ticketStorage.removeTicket($$0, $$4);
                        });
                    }, true);
                }
            }
        }

        @Override // net.minecraft.server.level.DistanceManager.FixedPlayerDistanceChunkTracker
        public void runAllUpdates() {
            super.runAllUpdates();
            if (!this.toUpdate.isEmpty()) {
                LongIterator $$0 = this.toUpdate.iterator();
                while ($$0.hasNext()) {
                    long $$1 = $$0.nextLong();
                    int $$2 = this.queueLevels.get($$1);
                    int $$3 = getLevel($$1);
                    if ($$2 != $$3) {
                        DistanceManager.this.ticketDispatcher.onLevelChange(new ChunkPos($$1), () -> {
                            return this.queueLevels.get($$1);
                        }, $$3, $$12 -> {
                            if ($$12 >= this.queueLevels.defaultReturnValue()) {
                                this.queueLevels.remove($$1);
                            } else {
                                this.queueLevels.put($$1, $$12);
                            }
                        });
                        onLevelChange($$1, $$3, haveTicketFor($$2), haveTicketFor($$3));
                    }
                }
                this.toUpdate.clear();
            }
        }

        private boolean haveTicketFor(int $$0) {
            return $$0 <= this.viewDistance;
        }
    }
}
