package net.labymod.api.util.concurrent.task;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.util.time.DateUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/concurrent/task/TaskBuilder.class */
public final class TaskBuilder {
    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    private final TaskExecutor executor;
    private final Runnable runnable;
    private final boolean shutdown;
    private Supplier<String> debugLabel;
    private long delay;
    private long repeat;

    TaskBuilder(Runnable runnable) {
        this(runnable, false);
    }

    TaskBuilder(Runnable runnable, boolean shutdown) {
        this.executor = Laby.labyAPI().taskExecutor();
        this.runnable = runnable;
        this.shutdown = shutdown;
    }

    public TaskBuilder delay(long time, TimeUnit unit) {
        this.delay = unit.toMillis(time);
        return this;
    }

    public TaskBuilder repeat(long time, TimeUnit unit) {
        this.repeat = unit.toMillis(time);
        return this;
    }

    @Contract("_ -> this")
    public TaskBuilder delayUntil(@NotNull Instant instant) {
        this.delay = computeDelay(instant);
        return this;
    }

    @VisibleForTesting
    static long computeDelay(@NotNull Instant instant) {
        return Math.max(0L, instant.toEpochMilli() - System.currentTimeMillis());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.time.ZonedDateTime] */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.time.ZonedDateTime] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3 */
    @VisibleForTesting
    @NotNull
    static Instant nextOccurrence(@NotNull LocalTime localTime, @NotNull ZoneId zoneId) {
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(zoneId);
        ?? AtZone = zonedDateTimeNow.toLocalDate().atTime(localTime).atZone(zoneId);
        boolean zIsAfter = AtZone.isAfter(zonedDateTimeNow);
        ?? PlusDays = AtZone;
        if (!zIsAfter) {
            PlusDays = AtZone.plusDays(1L);
        }
        return PlusDays.toInstant();
    }

    @Contract("_ -> this")
    public TaskBuilder delayUntil(@NotNull LocalTime timeOfDay) {
        return delayUntil(timeOfDay, DateUtil.DEFAULT_TIMEZONE);
    }

    @Contract("_,_ -> this")
    public TaskBuilder delayUntil(@NotNull LocalTime timeOfDay, @NotNull ZoneId zoneId) {
        return delayUntil(nextOccurrence(timeOfDay, zoneId));
    }

    public TaskBuilder clearDelay() {
        this.delay = 0L;
        return this;
    }

    public TaskBuilder clearRepeat() {
        this.repeat = 0L;
        return this;
    }

    public TaskBuilder debugLabel(Supplier<String> debugLabel) {
        this.debugLabel = debugLabel;
        return this;
    }

    public Task build() {
        Task buildTask = new Task(this.debugLabel == null ? () -> {
            return "Task" + COUNTER.getAndIncrement();
        } : this.debugLabel, this.executor, this.runnable, this.shutdown, this.delay, this.repeat);
        List<Task> tasks = new ArrayList<>(this.shutdown ? this.executor.getShutdownTasks() : this.executor.getTasks());
        tasks.add(buildTask);
        this.executor.updateTasks(tasks, this.shutdown);
        return buildTask;
    }
}
