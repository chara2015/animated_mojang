package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/lighting/LeveledPriorityQueue.class */
public class LeveledPriorityQueue {
    private final int levelCount;
    private final LongLinkedOpenHashSet[] queues;
    private int firstQueuedLevel;

    public LeveledPriorityQueue(int $$0, final int $$1) {
        this.levelCount = $$0;
        this.queues = new LongLinkedOpenHashSet[$$0];
        for (int $$2 = 0; $$2 < $$0; $$2++) {
            this.queues[$$2] = new LongLinkedOpenHashSet($$1, 0.5f) { // from class: net.minecraft.world.level.lighting.LeveledPriorityQueue.1
                protected void rehash(int $$02) {
                    if ($$02 > $$1) {
                        super.rehash($$02);
                    }
                }
            };
        }
        this.firstQueuedLevel = $$0;
    }

    public long removeFirstLong() {
        LongLinkedOpenHashSet $$0 = this.queues[this.firstQueuedLevel];
        long $$1 = $$0.removeFirstLong();
        if ($$0.isEmpty()) {
            checkFirstQueuedLevel(this.levelCount);
        }
        return $$1;
    }

    public boolean isEmpty() {
        return this.firstQueuedLevel >= this.levelCount;
    }

    public void dequeue(long $$0, int $$1, int $$2) {
        LongLinkedOpenHashSet $$3 = this.queues[$$1];
        $$3.remove($$0);
        if ($$3.isEmpty() && this.firstQueuedLevel == $$1) {
            checkFirstQueuedLevel($$2);
        }
    }

    public void enqueue(long $$0, int $$1) {
        this.queues[$$1].add($$0);
        if (this.firstQueuedLevel > $$1) {
            this.firstQueuedLevel = $$1;
        }
    }

    private void checkFirstQueuedLevel(int $$0) {
        int $$1 = this.firstQueuedLevel;
        this.firstQueuedLevel = $$0;
        for (int $$2 = $$1 + 1; $$2 < $$0; $$2++) {
            if (!this.queues[$$2].isEmpty()) {
                this.firstQueuedLevel = $$2;
                return;
            }
        }
    }
}
