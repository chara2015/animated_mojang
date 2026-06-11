package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/ResourceManagerReloadListener.class */
public interface ResourceManagerReloadListener extends PreparableReloadListener {
    void onResourceManagerReload(ResourceManager resourceManager);

    @Override // net.minecraft.server.packs.resources.PreparableReloadListener
    default CompletableFuture<Void> reload(PreparableReloadListener.SharedState $$0, Executor $$1, PreparableReloadListener.PreparationBarrier $$2, Executor $$3) {
        ResourceManager $$4 = $$0.resourceManager();
        return $$2.wait(Unit.INSTANCE).thenRunAsync(() -> {
            ProfilerFiller $$12 = Profiler.get();
            $$12.push(VibrationSystem.Data.NBT_TAG_KEY);
            onResourceManagerReload($$4);
            $$12.pop();
        }, $$3);
    }
}
