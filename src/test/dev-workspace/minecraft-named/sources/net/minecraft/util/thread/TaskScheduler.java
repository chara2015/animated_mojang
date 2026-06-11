package net.minecraft.util.thread;

import java.lang.Runnable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/thread/TaskScheduler.class */
public interface TaskScheduler<R extends Runnable> extends AutoCloseable {
    String name();

    void schedule(R r);

    R wrapRunnable(Runnable runnable);

    @Override // java.lang.AutoCloseable
    default void close() {
    }

    default <Source> CompletableFuture<Source> scheduleWithResult(Consumer<CompletableFuture<Source>> $$0) {
        CompletableFuture<Source> $$1 = new CompletableFuture<>();
        schedule(wrapRunnable(() -> {
            $$0.accept($$1);
        }));
        return $$1;
    }

    static TaskScheduler<Runnable> wrapExecutor(final String $$0, final Executor $$1) {
        return new TaskScheduler<Runnable>() { // from class: net.minecraft.util.thread.TaskScheduler.1
            @Override // net.minecraft.util.thread.TaskScheduler
            public String name() {
                return $$0;
            }

            @Override // net.minecraft.util.thread.TaskScheduler
            public void schedule(Runnable $$02) {
                $$1.execute($$02);
            }

            @Override // net.minecraft.util.thread.TaskScheduler
            public Runnable wrapRunnable(Runnable $$02) {
                return $$02;
            }

            public String toString() {
                return $$0;
            }
        };
    }
}
