package net.minecraft.server.level;

import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.TicketStorage;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/SimulationChunkTracker.class */
public class SimulationChunkTracker extends ChunkTracker {
    public static final int MAX_LEVEL = 33;
    protected final Long2ByteMap chunks;
    private final TicketStorage ticketStorage;

    public SimulationChunkTracker(TicketStorage $$0) {
        super(34, 16, 256);
        this.chunks = new Long2ByteOpenHashMap();
        this.ticketStorage = $$0;
        $$0.setSimulationChunkUpdatedListener(this::update);
        this.chunks.defaultReturnValue((byte) 33);
    }

    @Override // net.minecraft.server.level.ChunkTracker
    protected int getLevelFromSource(long $$0) {
        return this.ticketStorage.getTicketLevelAt($$0, true);
    }

    public int getLevel(ChunkPos $$0) {
        return getLevel($$0.toLong());
    }

    @Override // net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint
    protected int getLevel(long $$0) {
        return this.chunks.get($$0);
    }

    @Override // net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint
    protected void setLevel(long $$0, int $$1) {
        if ($$1 >= 33) {
            this.chunks.remove($$0);
        } else {
            this.chunks.put($$0, (byte) $$1);
        }
    }

    public void runAllUpdates() {
        runUpdates(Integer.MAX_VALUE);
    }
}
