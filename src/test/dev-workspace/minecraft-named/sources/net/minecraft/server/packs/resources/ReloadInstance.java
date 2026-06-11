package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/ReloadInstance.class */
public interface ReloadInstance {
    CompletableFuture<?> done();

    float getActualProgress();

    default boolean isDone() {
        return done().isDone();
    }

    default void checkExceptions() {
        CompletableFuture<?> $$0 = done();
        if ($$0.isCompletedExceptionally()) {
            $$0.join();
        }
    }
}
