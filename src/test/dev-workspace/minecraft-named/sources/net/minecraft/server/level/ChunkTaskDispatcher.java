package net.minecraft.server.level;

import com.mojang.logging.LogUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import net.minecraft.SharedConstants;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkTaskPriorityQueue;
import net.minecraft.util.Unit;
import net.minecraft.util.thread.PriorityConsecutiveExecutor;
import net.minecraft.util.thread.StrictQueue;
import net.minecraft.util.thread.TaskScheduler;
import net.minecraft.world.level.ChunkPos;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/ChunkTaskDispatcher.class */
public class ChunkTaskDispatcher implements ChunkHolder.LevelChangeListener, AutoCloseable {
    public static final int DISPATCHER_PRIORITY_COUNT = 4;
    private static final Logger LOGGER = LogUtils.getLogger();
    private final ChunkTaskPriorityQueue queue;
    private final TaskScheduler<Runnable> executor;
    private final PriorityConsecutiveExecutor dispatcher;
    protected boolean sleeping = true;

    public ChunkTaskDispatcher(TaskScheduler<Runnable> $$0, Executor $$1) {
        this.queue = new ChunkTaskPriorityQueue($$0.name() + "_queue");
        this.executor = $$0;
        this.dispatcher = new PriorityConsecutiveExecutor(4, $$1, "dispatcher");
    }

    public boolean hasWork() {
        return this.dispatcher.hasWork() || this.queue.hasWork();
    }

    @Override // net.minecraft.server.level.ChunkHolder.LevelChangeListener
    public void onLevelChange(ChunkPos $$0, IntSupplier $$1, int $$2, IntConsumer $$3) {
        this.dispatcher.schedule(new StrictQueue.RunnableWithPriority(0, () -> {
            int $$4 = $$1.getAsInt();
            if (SharedConstants.DEBUG_VERBOSE_SERVER_EVENTS) {
                LOGGER.debug("RES {} {} -> {}", new Object[]{$$0, Integer.valueOf($$4), Integer.valueOf($$2)});
            }
            this.queue.resortChunkTasks($$4, $$0, $$2);
            $$3.accept($$2);
        }));
    }

    public void release(long $$0, Runnable $$1, boolean $$2) {
        this.dispatcher.schedule(new StrictQueue.RunnableWithPriority(1, () -> {
            this.queue.release($$0, $$2);
            onRelease($$0);
            if (this.sleeping) {
                this.sleeping = false;
                pollTask();
            }
            $$1.run();
        }));
    }

    public void submit(Runnable $$0, long $$1, IntSupplier $$2) {
        this.dispatcher.schedule(new StrictQueue.RunnableWithPriority(2, () -> {
            int $$3 = $$2.getAsInt();
            if (SharedConstants.DEBUG_VERBOSE_SERVER_EVENTS) {
                LOGGER.debug("SUB {} {} {} {}", new Object[]{new ChunkPos($$1), Integer.valueOf($$3), this.executor, this.queue});
            }
            this.queue.submit($$0, $$1, $$3);
            if (this.sleeping) {
                this.sleeping = false;
                pollTask();
            }
        }));
    }

    protected void pollTask() {
        this.dispatcher.schedule(new StrictQueue.RunnableWithPriority(3, () -> {
            ChunkTaskPriorityQueue.TasksForChunk $$0 = popTasks();
            if ($$0 == null) {
                this.sleeping = true;
            } else {
                scheduleForExecution($$0);
            }
        }));
    }

    protected void scheduleForExecution(ChunkTaskPriorityQueue.TasksForChunk $$0) {
        CompletableFuture.allOf((CompletableFuture[]) $$0.tasks().stream().map($$02 -> {
            return this.executor.scheduleWithResult($$1 -> {
                $$02.run();
                $$1.complete(Unit.INSTANCE);
            });
        }).toArray($$03 -> {
            return new CompletableFuture[$$03];
        })).thenAccept($$04 -> {
            pollTask();
        });
    }

    protected void onRelease(long $$0) {
    }

    protected ChunkTaskPriorityQueue.TasksForChunk popTasks() {
        return this.queue.pop();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.executor.close();
    }
}
