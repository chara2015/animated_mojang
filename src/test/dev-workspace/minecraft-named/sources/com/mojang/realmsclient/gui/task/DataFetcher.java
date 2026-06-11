package com.mojang.realmsclient.gui.task;

import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import net.minecraft.util.TimeSource;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/gui/task/DataFetcher.class */
public class DataFetcher {
    static final Logger LOGGER = LogUtils.getLogger();
    final Executor executor;
    final TimeUnit resolution;
    final TimeSource timeSource;

    public DataFetcher(Executor $$0, TimeUnit $$1, TimeSource $$2) {
        this.executor = $$0;
        this.resolution = $$1;
        this.timeSource = $$2;
    }

    public <T> Task<T> createTask(String $$0, Callable<T> $$1, Duration $$2, RepeatedDelayStrategy $$3) {
        long $$4 = this.resolution.convert($$2);
        if ($$4 == 0) {
            throw new IllegalArgumentException("Period of " + String.valueOf($$2) + " too short for selected resolution of " + String.valueOf(this.resolution));
        }
        return new Task<>($$0, $$1, $$4, $$3);
    }

    public Subscription createSubscription() {
        return new Subscription();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/gui/task/DataFetcher$ComputationResult.class */
    static final class ComputationResult<T> extends Record {
        private final Either<T, Exception> value;
        private final long time;

        ComputationResult(Either<T, Exception> $$0, long $$1) {
            this.value = $$0;
            this.time = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ComputationResult.class), ComputationResult.class, "value;time", "FIELD:Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;->value:Lcom/mojang/datafixers/util/Either;", "FIELD:Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;->time:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ComputationResult.class), ComputationResult.class, "value;time", "FIELD:Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;->value:Lcom/mojang/datafixers/util/Either;", "FIELD:Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;->time:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ComputationResult.class, Object.class), ComputationResult.class, "value;time", "FIELD:Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;->value:Lcom/mojang/datafixers/util/Either;", "FIELD:Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;->time:J").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Either<T, Exception> value() {
            return this.value;
        }

        public long time() {
            return this.time;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult.class */
    static final class SuccessfulComputationResult<T> extends Record {
        private final T value;
        private final long time;

        SuccessfulComputationResult(T $$0, long $$1) {
            this.value = $$0;
            this.time = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SuccessfulComputationResult.class), SuccessfulComputationResult.class, "value;time", "FIELD:Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult;->value:Ljava/lang/Object;", "FIELD:Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult;->time:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SuccessfulComputationResult.class), SuccessfulComputationResult.class, "value;time", "FIELD:Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult;->value:Ljava/lang/Object;", "FIELD:Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult;->time:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SuccessfulComputationResult.class, Object.class), SuccessfulComputationResult.class, "value;time", "FIELD:Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult;->value:Ljava/lang/Object;", "FIELD:Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult;->time:J").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public T value() {
            return this.value;
        }

        public long time() {
            return this.time;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/gui/task/DataFetcher$Task.class */
    public class Task<T> {
        private final String id;
        private final Callable<T> updater;
        private final long period;
        private final RepeatedDelayStrategy repeatStrategy;
        private CompletableFuture<ComputationResult<T>> pendingTask;
        SuccessfulComputationResult<T> lastResult;
        private long nextUpdate = -1;

        Task(String $$1, Callable<T> $$2, long $$3, RepeatedDelayStrategy $$4) {
            this.id = $$1;
            this.updater = $$2;
            this.period = $$3;
            this.repeatStrategy = $$4;
        }

        void updateIfNeeded(long $$0) {
            if (this.pendingTask != null) {
                ComputationResult<T> $$1 = this.pendingTask.getNow(null);
                if ($$1 == null) {
                    return;
                }
                this.pendingTask = null;
                long $$2 = ((ComputationResult) $$1).time;
                $$1.value().ifLeft($$12 -> {
                    this.lastResult = new SuccessfulComputationResult<>($$12, $$2);
                    this.nextUpdate = $$2 + (this.period * this.repeatStrategy.delayCyclesAfterSuccess());
                }).ifRight($$13 -> {
                    long $$22 = this.repeatStrategy.delayCyclesAfterFailure();
                    DataFetcher.LOGGER.warn("Failed to process task {}, will repeat after {} cycles", new Object[]{this.id, Long.valueOf($$22), $$13});
                    this.nextUpdate = $$2 + (this.period * $$22);
                });
            }
            if (this.nextUpdate <= $$0) {
                this.pendingTask = CompletableFuture.supplyAsync(() -> {
                    try {
                        T $$02 = this.updater.call();
                        long $$14 = DataFetcher.this.timeSource.get(DataFetcher.this.resolution);
                        return new ComputationResult(Either.left($$02), $$14);
                    } catch (Exception $$22) {
                        long $$3 = DataFetcher.this.timeSource.get(DataFetcher.this.resolution);
                        return new ComputationResult(Either.right($$22), $$3);
                    }
                }, DataFetcher.this.executor);
            }
        }

        public void reset() {
            this.pendingTask = null;
            this.lastResult = null;
            this.nextUpdate = -1L;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/gui/task/DataFetcher$SubscribedTask.class */
    class SubscribedTask<T> {
        private final Task<T> task;
        private final Consumer<T> output;
        private long lastCheckTime = -1;

        SubscribedTask(DataFetcher dataFetcher, Task<T> $$0, Consumer<T> $$1) {
            this.task = $$0;
            this.output = $$1;
        }

        void update(long $$0) {
            this.task.updateIfNeeded($$0);
            runCallbackIfNeeded();
        }

        void runCallbackIfNeeded() {
            SuccessfulComputationResult<T> $$0 = this.task.lastResult;
            if ($$0 != null && this.lastCheckTime < ((SuccessfulComputationResult) $$0).time) {
                this.output.accept(((SuccessfulComputationResult) $$0).value);
                this.lastCheckTime = ((SuccessfulComputationResult) $$0).time;
            }
        }

        void runCallback() {
            SuccessfulComputationResult<T> $$0 = this.task.lastResult;
            if ($$0 != null) {
                this.output.accept(((SuccessfulComputationResult) $$0).value);
                this.lastCheckTime = ((SuccessfulComputationResult) $$0).time;
            }
        }

        void reset() {
            this.task.reset();
            this.lastCheckTime = -1L;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/gui/task/DataFetcher$Subscription.class */
    public class Subscription {
        private final List<SubscribedTask<?>> subscriptions = new ArrayList();

        public Subscription() {
        }

        public <T> void subscribe(Task<T> $$0, Consumer<T> $$1) {
            SubscribedTask<?> subscribedTask = new SubscribedTask<>(DataFetcher.this, $$0, $$1);
            this.subscriptions.add(subscribedTask);
            subscribedTask.runCallbackIfNeeded();
        }

        public void forceUpdate() {
            for (SubscribedTask<?> $$0 : this.subscriptions) {
                $$0.runCallback();
            }
        }

        public void tick() {
            for (SubscribedTask<?> $$0 : this.subscriptions) {
                $$0.update(DataFetcher.this.timeSource.get(DataFetcher.this.resolution));
            }
        }

        public void reset() {
            for (SubscribedTask<?> $$0 : this.subscriptions) {
                $$0.reset();
            }
        }
    }
}
