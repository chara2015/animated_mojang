package net.labymod.api.util.concurrent.task;

import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/concurrent/task/Task.class */
public final class Task implements Runnable {
    private static final Logging LOGGER = Logging.getLogger();
    private final Supplier<String> debugLabel;
    private final TaskExecutor executor;
    private final Runnable runnable;
    private final int id;
    private final boolean shutdown;
    private final long delay;
    private final long repeat;
    private ScheduledFuture<?> future;
    private Thread currentTaskThread;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/concurrent/task/Task$Status.class */
    public enum Status {
        RUNNING,
        CANCELLED,
        FINISHED,
        NOT_STARTED
    }

    Task(Supplier<String> debugLabel, TaskExecutor executor, Runnable runnable, boolean shutdown, long delay, long repeat) {
        this.debugLabel = debugLabel;
        this.executor = executor;
        this.runnable = runnable;
        this.id = shutdown ? this.executor.getShutdownCounterId() : this.executor.getCounterId();
        this.shutdown = shutdown;
        this.delay = delay;
        this.repeat = repeat;
    }

    public static TaskBuilder builder(Runnable runnable) {
        return new TaskBuilder(runnable);
    }

    public static TaskBuilder builderShutdown(Runnable runnable) {
        return new TaskBuilder(runnable, true);
    }

    @Override // java.lang.Runnable
    public void run() {
        this.executor.getPool().execute(() -> {
            this.currentTaskThread = Thread.currentThread();
            try {
                try {
                    this.runnable.run();
                    if (this.repeat == 0) {
                        removeTask();
                    }
                    this.currentTaskThread = null;
                } catch (Throwable throwable) {
                    LOGGER.error("Caught exception in task {}:{}", this.debugLabel.get(), Integer.valueOf(getId()), throwable);
                    if (throwable instanceof InterruptedException) {
                        Thread.currentThread().interrupt();
                    }
                    if (this.repeat == 0) {
                        removeTask();
                    }
                    this.currentTaskThread = null;
                }
            } catch (Throwable th) {
                if (this.repeat == 0) {
                    removeTask();
                }
                this.currentTaskThread = null;
                throw th;
            }
        });
    }

    public void execute() {
        execute(this);
    }

    public void executeOn(Consumer<Runnable> action) {
        execute(() -> {
            action.accept(this.runnable);
        });
    }

    public void executeOnRenderThread() {
        executeOn(ThreadSafe::executeOnRenderThread);
    }

    private void execute(Runnable command) {
        ScheduledFuture<?> scheduledFutureScheduleAtFixedRate;
        if (this.repeat == 0) {
            scheduledFutureScheduleAtFixedRate = this.executor.getScheduledPool().schedule(command, this.delay, TimeUnit.MILLISECONDS);
        } else {
            scheduledFutureScheduleAtFixedRate = this.executor.getScheduledPool().scheduleAtFixedRate(command, this.delay, this.repeat, TimeUnit.MILLISECONDS);
        }
        this.future = scheduledFutureScheduleAtFixedRate;
    }

    public void forceExecute() {
        this.runnable.run();
    }

    public Status getStatus() {
        if (this.future == null) {
            return Status.NOT_STARTED;
        }
        if (this.future.isCancelled()) {
            return Status.CANCELLED;
        }
        if (this.future.isDone()) {
            return Status.FINISHED;
        }
        return Status.RUNNING;
    }

    public boolean isRunning() {
        return getStatus() == Status.RUNNING;
    }

    public void cancel() {
        if (this.future != null) {
            this.future.cancel(false);
            Thread taskThread = this.currentTaskThread;
            if (taskThread != null) {
                taskThread.interrupt();
            }
            removeTask();
        }
    }

    public int getId() {
        return this.id;
    }

    private void removeTask() {
        List<Task> tasks = this.shutdown ? this.executor.getShutdownTasks() : this.executor.getTasks();
        tasks.removeIf(task -> {
            return task == null || task.id == this.id;
        });
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task task = (Task) obj;
        return this.id == task.id;
    }

    public int hashCode() {
        return this.id;
    }
}
