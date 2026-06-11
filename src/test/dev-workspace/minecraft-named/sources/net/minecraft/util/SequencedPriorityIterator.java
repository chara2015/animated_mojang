package net.minecraft.util;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Queues;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Deque;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/SequencedPriorityIterator.class */
public final class SequencedPriorityIterator<T> extends AbstractIterator<T> {
    private static final int MIN_PRIO = Integer.MIN_VALUE;
    private Deque<T> highestPrioQueue = null;
    private int highestPrio = Integer.MIN_VALUE;
    private final Int2ObjectMap<Deque<T>> queuesByPriority = new Int2ObjectOpenHashMap();

    public void add(T $$0, int $$1) {
        if ($$1 == this.highestPrio && this.highestPrioQueue != null) {
            this.highestPrioQueue.addLast($$0);
            return;
        }
        Deque<T> $$2 = (Deque) this.queuesByPriority.computeIfAbsent($$1, $$02 -> {
            return Queues.newArrayDeque();
        });
        $$2.addLast($$0);
        if ($$1 >= this.highestPrio) {
            this.highestPrioQueue = $$2;
            this.highestPrio = $$1;
        }
    }

    protected T computeNext() {
        if (this.highestPrioQueue == null) {
            return (T) endOfData();
        }
        T tRemoveFirst = this.highestPrioQueue.removeFirst();
        if (tRemoveFirst == null) {
            return (T) endOfData();
        }
        if (this.highestPrioQueue.isEmpty()) {
            switchCacheToNextHighestPrioQueue();
        }
        return tRemoveFirst;
    }

    private void switchCacheToNextHighestPrioQueue() {
        int $$0 = Integer.MIN_VALUE;
        Deque<T> $$1 = null;
        ObjectIterator it = Int2ObjectMaps.fastIterable(this.queuesByPriority).iterator();
        while (it.hasNext()) {
            Int2ObjectMap.Entry<Deque<T>> $$2 = (Int2ObjectMap.Entry) it.next();
            Deque<T> $$3 = (Deque) $$2.getValue();
            int $$4 = $$2.getIntKey();
            if ($$4 > $$0 && !$$3.isEmpty()) {
                $$0 = $$4;
                $$1 = $$3;
                if ($$4 == this.highestPrio - 1) {
                    break;
                }
            }
        }
        this.highestPrio = $$0;
        this.highestPrioQueue = $$1;
    }
}
