package net.minecraft.util.thread;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/thread/ParallelMapTransform.class */
public class ParallelMapTransform {
    private static final int DEFAULT_TASKS_PER_THREAD = 16;

    public static <K, U, V> CompletableFuture<Map<K, V>> schedule(Map<K, U> $$0, BiFunction<K, U, V> $$1, int $$2, Executor $$3) {
        SplitterBase<K, U, V> batchedTaskSplitter;
        int $$4 = $$0.size();
        if ($$4 == 0) {
            return CompletableFuture.completedFuture(Map.of());
        }
        if ($$4 == 1) {
            Map.Entry<K, U> $$5 = $$0.entrySet().iterator().next();
            K $$6 = $$5.getKey();
            U $$7 = $$5.getValue();
            return CompletableFuture.supplyAsync(() -> {
                Object objApply = $$1.apply($$6, $$7);
                return objApply != null ? Map.of($$6, objApply) : Map.of();
            }, $$3);
        }
        if ($$4 <= $$2) {
            batchedTaskSplitter = new SingleTaskSplitter<>($$1, $$4);
        } else {
            batchedTaskSplitter = new BatchedTaskSplitter<>($$1, $$4, $$2);
        }
        SplitterBase<K, U, V> $$8 = batchedTaskSplitter;
        return $$8.scheduleTasks($$0, $$3);
    }

    public static <K, U, V> CompletableFuture<Map<K, V>> schedule(Map<K, U> $$0, BiFunction<K, U, V> $$1, Executor $$2) {
        int $$3 = Util.maxAllowedExecutorThreads() * 16;
        return schedule($$0, $$1, $$3, $$2);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/thread/ParallelMapTransform$Container.class */
    static final class Container<K, U, V> extends Record {
        private final BiFunction<K, U, V> operation;
        private final Object[] keys;
        private final Object[] values;

        private Container(BiFunction<K, U, V> $$0, Object[] $$1, Object[] $$2) {
            this.operation = $$0;
            this.keys = $$1;
            this.values = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Container.class), Container.class, "operation;keys;values", "FIELD:Lnet/minecraft/util/thread/ParallelMapTransform$Container;->operation:Ljava/util/function/BiFunction;", "FIELD:Lnet/minecraft/util/thread/ParallelMapTransform$Container;->keys:[Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/thread/ParallelMapTransform$Container;->values:[Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Container.class), Container.class, "operation;keys;values", "FIELD:Lnet/minecraft/util/thread/ParallelMapTransform$Container;->operation:Ljava/util/function/BiFunction;", "FIELD:Lnet/minecraft/util/thread/ParallelMapTransform$Container;->keys:[Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/thread/ParallelMapTransform$Container;->values:[Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Container.class, Object.class), Container.class, "operation;keys;values", "FIELD:Lnet/minecraft/util/thread/ParallelMapTransform$Container;->operation:Ljava/util/function/BiFunction;", "FIELD:Lnet/minecraft/util/thread/ParallelMapTransform$Container;->keys:[Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/thread/ParallelMapTransform$Container;->values:[Ljava/lang/Object;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public BiFunction<K, U, V> operation() {
            return this.operation;
        }

        public Object[] keys() {
            return this.keys;
        }

        public Object[] values() {
            return this.values;
        }

        public Container(BiFunction<K, U, V> $$0, int $$1) {
            this($$0, new Object[$$1], new Object[$$1]);
        }

        public void put(int $$0, K $$1, U $$2) {
            this.keys[$$0] = $$1;
            this.values[$$0] = $$2;
        }

        private K key(int i) {
            return (K) this.keys[i];
        }

        private V output(int i) {
            return (V) this.values[i];
        }

        private U input(int i) {
            return (U) this.values[i];
        }

        public void applyOperation(int $$0) {
            this.values[$$0] = this.operation.apply(key($$0), input($$0));
        }

        public void copyOut(int $$0, Map<K, V> $$1) {
            V $$2 = output($$0);
            if ($$2 != null) {
                K $$3 = key($$0);
                $$1.put($$3, $$2);
            }
        }

        public int size() {
            return this.keys.length;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/thread/ParallelMapTransform$SplitterBase.class */
    static abstract class SplitterBase<K, U, V> {
        private int lastScheduledIndex;
        private int currentIndex;
        private final CompletableFuture<?>[] tasks;
        private int batchIndex;
        private final Container<K, U, V> container;
        static final /* synthetic */ boolean $assertionsDisabled;

        protected abstract int batchSize(int i);

        protected abstract CompletableFuture<?> scheduleBatch(Container<K, U, V> container, int i, int i2, Executor executor);

        protected abstract CompletableFuture<Map<K, V>> scheduleFinalOperation(CompletableFuture<?> completableFuture, Container<K, U, V> container);

        static {
            $assertionsDisabled = !ParallelMapTransform.class.desiredAssertionStatus();
        }

        SplitterBase(BiFunction<K, U, V> $$0, int $$1, int $$2) {
            this.container = new Container<>($$0, $$1);
            this.tasks = new CompletableFuture[$$2];
        }

        private int pendingBatchSize() {
            return this.currentIndex - this.lastScheduledIndex;
        }

        public CompletableFuture<Map<K, V>> scheduleTasks(Map<K, U> $$0, Executor $$1) {
            $$0.forEach(($$12, $$2) -> {
                Container<K, U, V> container = this.container;
                int i = this.currentIndex;
                this.currentIndex = i + 1;
                container.put(i, $$12, $$2);
                if (pendingBatchSize() == batchSize(this.batchIndex)) {
                    CompletableFuture<?>[] completableFutureArr = this.tasks;
                    int i2 = this.batchIndex;
                    this.batchIndex = i2 + 1;
                    completableFutureArr[i2] = scheduleBatch(this.container, this.lastScheduledIndex, this.currentIndex, $$1);
                    this.lastScheduledIndex = this.currentIndex;
                }
            });
            if (!$assertionsDisabled && this.currentIndex != this.container.size()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.lastScheduledIndex != this.currentIndex) {
                throw new AssertionError();
            }
            if ($assertionsDisabled || this.batchIndex == this.tasks.length) {
                return scheduleFinalOperation(CompletableFuture.allOf(this.tasks), this.container);
            }
            throw new AssertionError();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/thread/ParallelMapTransform$SingleTaskSplitter.class */
    static class SingleTaskSplitter<K, U, V> extends SplitterBase<K, U, V> {
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !ParallelMapTransform.class.desiredAssertionStatus();
        }

        SingleTaskSplitter(BiFunction<K, U, V> $$0, int $$1) {
            super($$0, $$1, $$1);
        }

        @Override // net.minecraft.util.thread.ParallelMapTransform.SplitterBase
        protected int batchSize(int $$0) {
            return 1;
        }

        @Override // net.minecraft.util.thread.ParallelMapTransform.SplitterBase
        protected CompletableFuture<?> scheduleBatch(Container<K, U, V> $$0, int $$1, int $$2, Executor $$3) {
            if ($assertionsDisabled || $$1 + 1 == $$2) {
                return CompletableFuture.runAsync(() -> {
                    $$0.applyOperation($$1);
                }, $$3);
            }
            throw new AssertionError();
        }

        @Override // net.minecraft.util.thread.ParallelMapTransform.SplitterBase
        protected CompletableFuture<Map<K, V>> scheduleFinalOperation(CompletableFuture<?> completableFuture, Container<K, U, V> container) {
            return completableFuture.thenApply($$1 -> {
                HashMap map = new HashMap(container.size());
                for (int $$3 = 0; $$3 < container.size(); $$3++) {
                    container.copyOut($$3, map);
                }
                return map;
            });
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/thread/ParallelMapTransform$BatchedTaskSplitter.class */
    static class BatchedTaskSplitter<K, U, V> extends SplitterBase<K, U, V> {
        private final Map<K, V> result;
        private final int batchSize;
        private final int firstUndersizedBatchIndex;
        static final /* synthetic */ boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !ParallelMapTransform.class.desiredAssertionStatus();
        }

        BatchedTaskSplitter(BiFunction<K, U, V> $$0, int $$1, int $$2) {
            super($$0, $$1, $$2);
            this.result = new HashMap($$1);
            this.batchSize = Mth.positiveCeilDiv($$1, $$2);
            int $$3 = this.batchSize * $$2;
            int $$4 = $$3 - $$1;
            this.firstUndersizedBatchIndex = $$2 - $$4;
            if ($assertionsDisabled) {
                return;
            }
            if (this.firstUndersizedBatchIndex <= 0 || this.firstUndersizedBatchIndex > $$2) {
                throw new AssertionError();
            }
        }

        @Override // net.minecraft.util.thread.ParallelMapTransform.SplitterBase
        protected CompletableFuture<?> scheduleBatch(Container<K, U, V> $$0, int $$1, int $$2, Executor $$3) {
            int $$4 = $$2 - $$1;
            if ($assertionsDisabled || $$4 == this.batchSize || $$4 == this.batchSize - 1) {
                return CompletableFuture.runAsync(createTask(this.result, $$1, $$2, $$0), $$3);
            }
            throw new AssertionError();
        }

        @Override // net.minecraft.util.thread.ParallelMapTransform.SplitterBase
        protected int batchSize(int $$0) {
            return $$0 < this.firstUndersizedBatchIndex ? this.batchSize : this.batchSize - 1;
        }

        private static <K, U, V> Runnable createTask(Map<K, V> $$0, int $$1, int $$2, Container<K, U, V> $$3) {
            return () -> {
                for (int $$4 = $$1; $$4 < $$2; $$4++) {
                    $$3.applyOperation($$4);
                }
                synchronized ($$0) {
                    for (int $$5 = $$1; $$5 < $$2; $$5++) {
                        $$3.copyOut($$5, $$0);
                    }
                }
            };
        }

        @Override // net.minecraft.util.thread.ParallelMapTransform.SplitterBase
        protected CompletableFuture<Map<K, V>> scheduleFinalOperation(CompletableFuture<?> completableFuture, Container<K, U, V> container) {
            Map<K, V> map = this.result;
            return completableFuture.thenApply($$1 -> {
                return map;
            });
        }
    }
}
