package net.minecraft.server.jsonrpc.internalapi;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/internalapi/MinecraftExecutorService.class */
public interface MinecraftExecutorService {
    <V> CompletableFuture<V> submit(Supplier<V> supplier);

    CompletableFuture<Void> submit(Runnable runnable);
}
