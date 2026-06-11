package net.minecraft.util;

import com.mojang.logging.LogUtils;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/FutureChain.class */
public class FutureChain implements TaskChainer, AutoCloseable {
    private static final Logger LOGGER = LogUtils.getLogger();
    private CompletableFuture<?> head = CompletableFuture.completedFuture(null);
    private final Executor executor;
    private volatile boolean closed;

    public FutureChain(Executor $$0) {
        this.executor = $$0;
    }

    @Override // net.minecraft.util.TaskChainer
    public <T> void append(CompletableFuture<T> $$0, Consumer<T> $$1) {
        this.head = this.head.thenCombine((CompletionStage) $$0, ($$02, $$12) -> {
            return $$12;
        }).thenAcceptAsync((Consumer<? super V>) $$13 -> {
            if (!this.closed) {
                $$1.accept($$13);
            }
        }, this.executor).exceptionally($$03 -> {
            if ($$03 instanceof CompletionException) {
                CompletionException $$14 = (CompletionException) $$03;
                $$03 = $$14.getCause();
            }
            if ($$03 instanceof CancellationException) {
                CancellationException $$2 = (CancellationException) $$03;
                throw $$2;
            }
            LOGGER.error("Chain link failed, continuing to next one", $$03);
            return null;
        });
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.closed = true;
    }
}
