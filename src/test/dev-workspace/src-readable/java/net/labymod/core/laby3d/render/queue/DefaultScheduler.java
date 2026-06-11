package net.labymod.core.laby3d.render.queue;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import net.labymod.api.laby3d.render.queue.Scheduler;
import net.labymod.api.laby3d.render.queue.SortHint;
import net.labymod.api.laby3d.render.queue.Submission;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/DefaultScheduler.class */
public class DefaultScheduler implements Scheduler {
    private final NavigableMap<Key, Submission> submissions = new TreeMap();

    @Override // net.labymod.api.laby3d.render.queue.Scheduler
    public void schedule(int order, Submission submission) {
        this.submissions.put(createKey(order, submission), submission);
    }

    @Override // net.labymod.api.laby3d.render.queue.Scheduler
    public List<Submission> drainInOrder() {
        List<Submission> ordered = List.copyOf(this.submissions.values());
        this.submissions.clear();
        return ordered;
    }

    @Override // net.labymod.api.laby3d.render.queue.Scheduler
    public void clear() {
        this.submissions.clear();
    }

    private Key createKey(int order, Submission submission) {
        float sortValue = 0.0f;
        if (submission instanceof SortHint) {
            SortHint hint = (SortHint) submission;
            sortValue = hint.sortValue();
        }
        return new Key(order, sortValue, submission.sequenceId());
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/render/queue/DefaultScheduler$Key.class */
    static final class Key extends Record implements Comparable<Key> {
        private final int order;
        private final float sortValue;
        private final long sequence;

        Key(int order, float sortValue, long sequence) {
            this.order = order;
            this.sortValue = sortValue;
            this.sequence = sequence;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Key.class), Key.class, "order;sortValue;sequence", "FIELD:Lnet/labymod/core/laby3d/render/queue/DefaultScheduler$Key;->order:I", "FIELD:Lnet/labymod/core/laby3d/render/queue/DefaultScheduler$Key;->sortValue:F", "FIELD:Lnet/labymod/core/laby3d/render/queue/DefaultScheduler$Key;->sequence:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Key.class), Key.class, "order;sortValue;sequence", "FIELD:Lnet/labymod/core/laby3d/render/queue/DefaultScheduler$Key;->order:I", "FIELD:Lnet/labymod/core/laby3d/render/queue/DefaultScheduler$Key;->sortValue:F", "FIELD:Lnet/labymod/core/laby3d/render/queue/DefaultScheduler$Key;->sequence:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Key.class, Object.class), Key.class, "order;sortValue;sequence", "FIELD:Lnet/labymod/core/laby3d/render/queue/DefaultScheduler$Key;->order:I", "FIELD:Lnet/labymod/core/laby3d/render/queue/DefaultScheduler$Key;->sortValue:F", "FIELD:Lnet/labymod/core/laby3d/render/queue/DefaultScheduler$Key;->sequence:J").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public int order() {
            return this.order;
        }

        public float sortValue() {
            return this.sortValue;
        }

        public long sequence() {
            return this.sequence;
        }

        @Override // java.lang.Comparable
        public int compareTo(@NotNull Key o) {
            int result = Integer.compare(this.order, o.order);
            if (result != 0) {
                return result;
            }
            int result2 = Float.compare(this.sortValue, o.sortValue);
            if (result2 != 0) {
                return result2;
            }
            return Long.compare(this.sequence, o.sequence);
        }
    }
}
