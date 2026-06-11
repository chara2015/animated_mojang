package net.minecraft.util.thread;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Queues;
import com.mojang.jtracy.TracyClient;
import com.mojang.jtracy.Zone;
import com.mojang.logging.LogUtils;
import java.lang.Runnable;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.CheckReturnValue;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsRegistry;
import net.minecraft.util.profiling.metrics.ProfilerMeasured;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/thread/BlockableEventLoop.class */
public abstract class BlockableEventLoop<R extends Runnable> implements ProfilerMeasured, TaskScheduler<R>, Executor {
    public static final long BLOCK_TIME_NANOS = 100000;
    private final String name;
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Queue<R> pendingRunnables = Queues.newConcurrentLinkedQueue();
    private int blockingCount;

    protected abstract boolean shouldRun(R r);

    protected abstract Thread getRunningThread();

    protected BlockableEventLoop(String $$0) {
        this.name = $$0;
        MetricsRegistry.INSTANCE.add(this);
    }

    public boolean isSameThread() {
        return Thread.currentThread() == getRunningThread();
    }

    protected boolean scheduleExecutables() {
        return !isSameThread();
    }

    public int getPendingTasksCount() {
        return this.pendingRunnables.size();
    }

    @Override // net.minecraft.util.thread.TaskScheduler
    public String name() {
        return this.name;
    }

    public <V> CompletableFuture<V> submit(Supplier<V> $$0) {
        if (scheduleExecutables()) {
            return CompletableFuture.supplyAsync($$0, this);
        }
        return CompletableFuture.completedFuture($$0.get());
    }

    private CompletableFuture<Void> submitAsync(Runnable $$0) {
        return CompletableFuture.supplyAsync(() -> {
            $$0.run();
            return null;
        }, this);
    }

    @CheckReturnValue
    public CompletableFuture<Void> submit(Runnable $$0) {
        if (scheduleExecutables()) {
            return submitAsync($$0);
        }
        $$0.run();
        return CompletableFuture.completedFuture(null);
    }

    public void executeBlocking(Runnable $$0) {
        if (!isSameThread()) {
            submitAsync($$0).join();
        } else {
            $$0.run();
        }
    }

    public void schedule(R $$0) {
        this.pendingRunnables.add($$0);
        LockSupport.unpark(getRunningThread());
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable $$0) throws Exception {
        R $$1 = wrapRunnable($$0);
        if (scheduleExecutables()) {
            schedule($$1);
        } else {
            doRunTask($$1);
        }
    }

    public void executeIfPossible(Runnable $$0) throws Exception {
        execute($$0);
    }

    protected void dropAllTasks() {
        this.pendingRunnables.clear();
    }

    protected void runAllTasks() {
        while (pollTask()) {
        }
    }

    protected boolean shouldRunAllTasks() {
        return this.blockingCount > 0;
    }

    public boolean pollTask() throws Exception {
        R $$0 = this.pendingRunnables.peek();
        if ($$0 == null) {
            return false;
        }
        if (!shouldRunAllTasks() && !shouldRun($$0)) {
            return false;
        }
        doRunTask(this.pendingRunnables.remove());
        return true;
    }

    public void managedBlock(BooleanSupplier $$0) {
        this.blockingCount++;
        while (!$$0.getAsBoolean()) {
            try {
                if (!pollTask()) {
                    waitForTasks();
                }
            } finally {
                this.blockingCount--;
            }
        }
    }

    protected void waitForTasks() {
        Thread.yield();
        LockSupport.parkNanos("waiting for tasks", BLOCK_TIME_NANOS);
    }

    protected void doRunTask(R $$0) throws Exception {
        try {
            Zone $$1 = TracyClient.beginZone("Task", SharedConstants.IS_RUNNING_IN_IDE);
            try {
                $$0.run();
                if ($$1 != null) {
                    $$1.close();
                }
            } finally {
            }
        } catch (Exception $$2) {
            LOGGER.error(LogUtils.FATAL_MARKER, "Error executing task on {}", name(), $$2);
            if (isNonRecoverable($$2)) {
                throw $$2;
            }
        }
    }

    @Override // net.minecraft.util.profiling.metrics.ProfilerMeasured
    public List<MetricSampler> profiledMetrics() {
        return ImmutableList.of(MetricSampler.create(this.name + "-pending-tasks", MetricCategory.EVENT_LOOPS, this::getPendingTasksCount));
    }

    public static boolean isNonRecoverable(Throwable $$0) {
        if (!($$0 instanceof ReportedException)) {
            return ($$0 instanceof OutOfMemoryError) || ($$0 instanceof StackOverflowError);
        }
        ReportedException $$1 = (ReportedException) $$0;
        return isNonRecoverable($$1.getCause());
    }
}
