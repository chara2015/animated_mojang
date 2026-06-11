package net.minecraft.util.thread;

import com.google.common.collect.Queues;
import java.lang.Runnable;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/thread/StrictQueue.class */
public interface StrictQueue<T extends Runnable> {
    Runnable pop();

    boolean push(T t);

    boolean isEmpty();

    int size();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/thread/StrictQueue$QueueStrictQueue.class */
    public static final class QueueStrictQueue implements StrictQueue<Runnable> {
        private final Queue<Runnable> queue;

        public QueueStrictQueue(Queue<Runnable> $$0) {
            this.queue = $$0;
        }

        @Override // net.minecraft.util.thread.StrictQueue
        public Runnable pop() {
            return this.queue.poll();
        }

        @Override // net.minecraft.util.thread.StrictQueue
        public boolean push(Runnable $$0) {
            return this.queue.add($$0);
        }

        @Override // net.minecraft.util.thread.StrictQueue
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // net.minecraft.util.thread.StrictQueue
        public int size() {
            return this.queue.size();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/thread/StrictQueue$RunnableWithPriority.class */
    public static final class RunnableWithPriority extends Record implements Runnable {
        private final int priority;
        private final Runnable task;

        public RunnableWithPriority(int $$0, Runnable $$1) {
            this.priority = $$0;
            this.task = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RunnableWithPriority.class), RunnableWithPriority.class, "priority;task", "FIELD:Lnet/minecraft/util/thread/StrictQueue$RunnableWithPriority;->priority:I", "FIELD:Lnet/minecraft/util/thread/StrictQueue$RunnableWithPriority;->task:Ljava/lang/Runnable;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RunnableWithPriority.class), RunnableWithPriority.class, "priority;task", "FIELD:Lnet/minecraft/util/thread/StrictQueue$RunnableWithPriority;->priority:I", "FIELD:Lnet/minecraft/util/thread/StrictQueue$RunnableWithPriority;->task:Ljava/lang/Runnable;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RunnableWithPriority.class, Object.class), RunnableWithPriority.class, "priority;task", "FIELD:Lnet/minecraft/util/thread/StrictQueue$RunnableWithPriority;->priority:I", "FIELD:Lnet/minecraft/util/thread/StrictQueue$RunnableWithPriority;->task:Ljava/lang/Runnable;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int priority() {
            return this.priority;
        }

        public Runnable task() {
            return this.task;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.task.run();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/thread/StrictQueue$FixedPriorityQueue.class */
    public static final class FixedPriorityQueue implements StrictQueue<RunnableWithPriority> {
        private final Queue<Runnable>[] queues;
        private final AtomicInteger size = new AtomicInteger();

        public FixedPriorityQueue(int $$0) {
            this.queues = new Queue[$$0];
            for (int $$1 = 0; $$1 < $$0; $$1++) {
                this.queues[$$1] = Queues.newConcurrentLinkedQueue();
            }
        }

        @Override // net.minecraft.util.thread.StrictQueue
        public Runnable pop() {
            for (Queue<Runnable> $$0 : this.queues) {
                Runnable $$1 = $$0.poll();
                if ($$1 != null) {
                    this.size.decrementAndGet();
                    return $$1;
                }
            }
            return null;
        }

        @Override // net.minecraft.util.thread.StrictQueue
        public boolean push(RunnableWithPriority $$0) {
            int $$1 = $$0.priority;
            if ($$1 >= this.queues.length || $$1 < 0) {
                throw new IndexOutOfBoundsException(String.format(Locale.ROOT, "Priority %d not supported. Expected range [0-%d]", Integer.valueOf($$1), Integer.valueOf(this.queues.length - 1)));
            }
            this.queues[$$1].add($$0);
            this.size.incrementAndGet();
            return true;
        }

        @Override // net.minecraft.util.thread.StrictQueue
        public boolean isEmpty() {
            return this.size.get() == 0;
        }

        @Override // net.minecraft.util.thread.StrictQueue
        public int size() {
            return this.size.get();
        }
    }
}
