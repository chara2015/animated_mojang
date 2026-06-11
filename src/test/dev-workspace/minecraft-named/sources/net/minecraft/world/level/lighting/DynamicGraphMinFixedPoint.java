package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import java.util.function.LongPredicate;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/lighting/DynamicGraphMinFixedPoint.class */
public abstract class DynamicGraphMinFixedPoint {
    public static final long SOURCE = Long.MAX_VALUE;
    private static final int NO_COMPUTED_LEVEL = 255;
    protected final int levelCount;
    private final LeveledPriorityQueue priorityQueue;
    private final Long2ByteMap computedLevels;
    private volatile boolean hasWork;

    protected abstract int getComputedLevel(long j, long j2, int i);

    protected abstract void checkNeighborsAfterUpdate(long j, int i, boolean z);

    protected abstract int getLevel(long j);

    protected abstract void setLevel(long j, int i);

    protected abstract int computeLevelFromNeighbor(long j, long j2, int i);

    protected DynamicGraphMinFixedPoint(int $$0, int $$1, final int $$2) {
        if ($$0 >= 254) {
            throw new IllegalArgumentException("Level count must be < 254.");
        }
        this.levelCount = $$0;
        this.priorityQueue = new LeveledPriorityQueue($$0, $$1);
        this.computedLevels = new Long2ByteOpenHashMap($$2, 0.5f) { // from class: net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint.1
            protected void rehash(int $$02) {
                if ($$02 > $$2) {
                    super.rehash($$02);
                }
            }
        };
        this.computedLevels.defaultReturnValue((byte) -1);
    }

    protected void removeFromQueue(long $$0) {
        int $$1 = this.computedLevels.remove($$0) & 255;
        if ($$1 == 255) {
            return;
        }
        int $$2 = getLevel($$0);
        int $$3 = calculatePriority($$2, $$1);
        this.priorityQueue.dequeue($$0, $$3, this.levelCount);
        this.hasWork = !this.priorityQueue.isEmpty();
    }

    public void removeIf(LongPredicate $$0) {
        LongArrayList longArrayList = new LongArrayList();
        this.computedLevels.keySet().forEach($$2 -> {
            if ($$0.test($$2)) {
                longArrayList.add($$2);
            }
        });
        longArrayList.forEach(this::removeFromQueue);
    }

    private int calculatePriority(int $$0, int $$1) {
        return Math.min(Math.min($$0, $$1), this.levelCount - 1);
    }

    protected void checkNode(long $$0) {
        checkEdge($$0, $$0, this.levelCount - 1, false);
    }

    protected void checkEdge(long $$0, long $$1, int $$2, boolean $$3) {
        checkEdge($$0, $$1, $$2, getLevel($$1), this.computedLevels.get($$1) & 255, $$3);
        this.hasWork = !this.priorityQueue.isEmpty();
    }

    private void checkEdge(long $$0, long $$1, int $$2, int $$3, int $$4, boolean $$5) {
        int $$8;
        if (isSource($$1)) {
            return;
        }
        int $$22 = Mth.clamp($$2, 0, this.levelCount - 1);
        int $$32 = Mth.clamp($$3, 0, this.levelCount - 1);
        boolean $$6 = $$4 == 255;
        if ($$6) {
            $$4 = $$32;
        }
        if ($$5) {
            $$8 = Math.min($$4, $$22);
        } else {
            $$8 = Mth.clamp(getComputedLevel($$1, $$0, $$22), 0, this.levelCount - 1);
        }
        int $$9 = calculatePriority($$32, $$4);
        if ($$32 == $$8) {
            if (!$$6) {
                this.priorityQueue.dequeue($$1, $$9, this.levelCount);
                this.computedLevels.remove($$1);
                return;
            }
            return;
        }
        int $$10 = calculatePriority($$32, $$8);
        if ($$9 != $$10 && !$$6) {
            this.priorityQueue.dequeue($$1, $$9, $$10);
        }
        this.priorityQueue.enqueue($$1, $$10);
        this.computedLevels.put($$1, (byte) $$8);
    }

    protected final void checkNeighbor(long $$0, long $$1, int $$2, boolean $$3) {
        int $$8;
        int $$4 = this.computedLevels.get($$1) & 255;
        int $$5 = Mth.clamp(computeLevelFromNeighbor($$0, $$1, $$2), 0, this.levelCount - 1);
        if ($$3) {
            checkEdge($$0, $$1, $$5, getLevel($$1), $$4, $$3);
            return;
        }
        boolean $$6 = $$4 == 255;
        if ($$6) {
            $$8 = Mth.clamp(getLevel($$1), 0, this.levelCount - 1);
        } else {
            $$8 = $$4;
        }
        if ($$5 == $$8) {
            checkEdge($$0, $$1, this.levelCount - 1, $$6 ? $$8 : getLevel($$1), $$4, $$3);
        }
    }

    protected final boolean hasWork() {
        return this.hasWork;
    }

    protected final int runUpdates(int $$0) {
        if (this.priorityQueue.isEmpty()) {
            return $$0;
        }
        while (!this.priorityQueue.isEmpty() && $$0 > 0) {
            $$0--;
            long $$1 = this.priorityQueue.removeFirstLong();
            int $$2 = Mth.clamp(getLevel($$1), 0, this.levelCount - 1);
            int $$3 = this.computedLevels.remove($$1) & 255;
            if ($$3 < $$2) {
                setLevel($$1, $$3);
                checkNeighborsAfterUpdate($$1, $$3, true);
            } else if ($$3 > $$2) {
                setLevel($$1, this.levelCount - 1);
                if ($$3 != this.levelCount - 1) {
                    this.priorityQueue.enqueue($$1, calculatePriority(this.levelCount - 1, $$3));
                    this.computedLevels.put($$1, (byte) $$3);
                }
                checkNeighborsAfterUpdate($$1, $$2, false);
            }
        }
        this.hasWork = !this.priorityQueue.isEmpty();
        return $$0;
    }

    public int getQueueSize() {
        return this.computedLevels.size();
    }

    protected boolean isSource(long $$0) {
        return $$0 == SOURCE;
    }
}
