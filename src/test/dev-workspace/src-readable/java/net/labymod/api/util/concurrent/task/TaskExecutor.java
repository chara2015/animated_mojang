package net.labymod.api.util.concurrent.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.util.io.LabyExecutors;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/concurrent/task/TaskExecutor.class */
public class TaskExecutor {
    private final AtomicInteger counter = new AtomicInteger();
    private final AtomicInteger shutdownCounter = new AtomicInteger();
    private final ExecutorService pool = LabyExecutors.newSingleThreadExecutor("TaskPool#%d");
    private final ScheduledExecutorService scheduledPool = LabyExecutors.newSingleThreadScheduledExecutor("TaskScheduledPool#%d");
    private List<Task> tasks = new ArrayList();
    private List<Task> shutdownTasks = new ArrayList();

    public int getCounterId() {
        return this.counter.incrementAndGet();
    }

    public int getShutdownCounterId() {
        return this.shutdownCounter.incrementAndGet();
    }

    @ApiStatus.Internal
    public ExecutorService getPool() {
        return this.pool;
    }

    @ApiStatus.Internal
    public ScheduledExecutorService getScheduledPool() {
        return this.scheduledPool;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public List<Task> getShutdownTasks() {
        return this.shutdownTasks;
    }

    public Optional<Task> getTask(int id) {
        for (Task task : this.tasks) {
            if (task.getId() == id) {
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }

    public Optional<Task> getShutdownTask(int id) {
        for (Task task : this.shutdownTasks) {
            if (task.getId() == id) {
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }

    public void cancelTask(Task task) {
        task.cancel();
    }

    public void shutdown() {
        for (Task shutdownTask : this.shutdownTasks) {
            shutdownTask.forceExecute();
        }
        this.scheduledPool.shutdown();
        this.pool.shutdown();
    }

    void updateTasks(List<Task> tasks, boolean shutdown) {
        if (shutdown) {
            this.shutdownTasks = tasks;
        } else {
            this.tasks = tasks;
        }
    }
}
