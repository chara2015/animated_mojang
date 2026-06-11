package net.minecraft.server.jsonrpc.internalapi;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.minecraft.server.dedicated.DedicatedServer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/jsonrpc/internalapi/MinecraftExecutorServiceImpl.class */
public class MinecraftExecutorServiceImpl implements MinecraftExecutorService {
    private final DedicatedServer server;

    public MinecraftExecutorServiceImpl(DedicatedServer $$0) {
        this.server = $$0;
    }

    @Override // net.minecraft.server.jsonrpc.internalapi.MinecraftExecutorService
    public <V> CompletableFuture<V> submit(Supplier<V> $$0) {
        return this.server.submit($$0);
    }

    @Override // net.minecraft.server.jsonrpc.internalapi.MinecraftExecutorService
    public CompletableFuture<Void> submit(Runnable $$0) {
        return this.server.submit($$0);
    }
}
