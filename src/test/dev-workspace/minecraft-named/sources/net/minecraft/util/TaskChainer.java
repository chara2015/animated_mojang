package net.minecraft.util;

import com.mojang.logging.LogUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/TaskChainer.class */
@FunctionalInterface
public interface TaskChainer {
    public static final Logger LOGGER = LogUtils.getLogger();

    <T> void append(CompletableFuture<T> completableFuture, Consumer<T> consumer);

    static TaskChainer immediate(final Executor $$0) {
        return new TaskChainer() { // from class: net.minecraft.util.TaskChainer.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // net.minecraft.util.TaskChainer
            public <T> void append(CompletableFuture<T> $$02, Consumer<T> consumer) {
                $$02.thenAcceptAsync((Consumer) consumer, $$0).exceptionally($$03 -> {
                    LOGGER.error("Task failed", $$03);
                    return null;
                });
            }
        };
    }

    default void append(Runnable $$0) {
        append(CompletableFuture.completedFuture(null), $$1 -> {
            $$0.run();
        });
    }
}
