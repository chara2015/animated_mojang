package net.minecraft.server.packs.resources;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/SimpleReloadInstance.class */
public class SimpleReloadInstance<S> implements ReloadInstance {
    private static final int PREPARATION_PROGRESS_WEIGHT = 2;
    private static final int EXTRA_RELOAD_PROGRESS_WEIGHT = 2;
    private static final int LISTENER_PROGRESS_WEIGHT = 1;
    private CompletableFuture<List<S>> allDone;
    final Set<PreparableReloadListener> preparingListeners;
    private final int listenerCount;
    final CompletableFuture<Unit> allPreparations = new CompletableFuture<>();
    private final AtomicInteger startedTasks = new AtomicInteger();
    private final AtomicInteger finishedTasks = new AtomicInteger();
    private final AtomicInteger startedReloads = new AtomicInteger();
    private final AtomicInteger finishedReloads = new AtomicInteger();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/SimpleReloadInstance$StateFactory.class */
    @FunctionalInterface
    protected interface StateFactory<S> {
        public static final StateFactory<Void> SIMPLE = ($$0, $$1, $$2, $$3, $$4) -> {
            return $$2.reload($$0, $$3, $$1, $$4);
        };

        CompletableFuture<S> create(PreparableReloadListener.SharedState sharedState, PreparableReloadListener.PreparationBarrier preparationBarrier, PreparableReloadListener preparableReloadListener, Executor executor, Executor executor2);
    }

    public static ReloadInstance of(ResourceManager $$0, List<PreparableReloadListener> $$1, Executor $$2, Executor $$3, CompletableFuture<Unit> $$4) {
        SimpleReloadInstance<Void> $$5 = new SimpleReloadInstance<>($$1);
        $$5.startTasks($$2, $$3, $$0, $$1, StateFactory.SIMPLE, $$4);
        return $$5;
    }

    protected SimpleReloadInstance(List<PreparableReloadListener> $$0) {
        this.listenerCount = $$0.size();
        this.preparingListeners = new HashSet($$0);
    }

    protected void startTasks(Executor $$0, Executor $$1, ResourceManager $$2, List<PreparableReloadListener> $$3, StateFactory<S> $$4, CompletableFuture<?> $$5) {
        this.allDone = prepareTasks($$0, $$1, $$2, $$3, $$4, $$5);
    }

    protected CompletableFuture<List<S>> prepareTasks(Executor $$0, Executor $$1, ResourceManager $$2, List<PreparableReloadListener> $$3, StateFactory<S> $$4, CompletableFuture<?> $$5) {
        Executor $$6 = $$12 -> {
            this.startedTasks.incrementAndGet();
            $$0.execute(() -> {
                $$12.run();
                this.finishedTasks.incrementAndGet();
            });
        };
        Executor $$7 = $$13 -> {
            this.startedReloads.incrementAndGet();
            $$1.execute(() -> {
                $$13.run();
                this.finishedReloads.incrementAndGet();
            });
        };
        this.startedTasks.incrementAndGet();
        AtomicInteger atomicInteger = this.finishedTasks;
        Objects.requireNonNull(atomicInteger);
        $$5.thenRun(atomicInteger::incrementAndGet);
        PreparableReloadListener.SharedState $$8 = new PreparableReloadListener.SharedState($$2);
        $$3.forEach($$14 -> {
            $$14.prepareSharedState($$8);
        });
        CompletableFuture<?> $$9 = $$5;
        List<CompletableFuture<S>> $$10 = new ArrayList<>();
        for (PreparableReloadListener $$11 : $$3) {
            PreparableReloadListener.PreparationBarrier $$122 = createBarrierForListener($$11, $$9, $$1);
            CompletableFuture<?> completableFutureCreate = $$4.create($$8, $$122, $$11, $$6, $$7);
            $$10.add(completableFutureCreate);
            $$9 = completableFutureCreate;
        }
        return Util.sequenceFailFast($$10);
    }

    private PreparableReloadListener.PreparationBarrier createBarrierForListener(final PreparableReloadListener $$0, final CompletableFuture<?> $$1, final Executor $$2) {
        return new PreparableReloadListener.PreparationBarrier() { // from class: net.minecraft.server.packs.resources.SimpleReloadInstance.1
            @Override // net.minecraft.server.packs.resources.PreparableReloadListener.PreparationBarrier
            public <T> CompletableFuture<T> wait(T t) {
                Executor executor = $$2;
                PreparableReloadListener preparableReloadListener = $$0;
                executor.execute(() -> {
                    SimpleReloadInstance.this.preparingListeners.remove(preparableReloadListener);
                    if (SimpleReloadInstance.this.preparingListeners.isEmpty()) {
                        SimpleReloadInstance.this.allPreparations.complete(Unit.INSTANCE);
                    }
                });
                return (CompletableFuture<T>) SimpleReloadInstance.this.allPreparations.thenCombine((CompletionStage) $$1, ($$12, $$22) -> {
                    return t;
                });
            }
        };
    }

    @Override // net.minecraft.server.packs.resources.ReloadInstance
    public CompletableFuture<?> done() {
        return (CompletableFuture) Objects.requireNonNull(this.allDone, "not started");
    }

    @Override // net.minecraft.server.packs.resources.ReloadInstance
    public float getActualProgress() {
        int $$0 = this.listenerCount - this.preparingListeners.size();
        float $$1 = weightProgress(this.finishedTasks.get(), this.finishedReloads.get(), $$0);
        float $$2 = weightProgress(this.startedTasks.get(), this.startedReloads.get(), this.listenerCount);
        return $$1 / $$2;
    }

    private static int weightProgress(int $$0, int $$1, int $$2) {
        return ($$0 * 2) + ($$1 * 2) + ($$2 * 1);
    }

    public static ReloadInstance create(ResourceManager $$0, List<PreparableReloadListener> $$1, Executor $$2, Executor $$3, CompletableFuture<Unit> $$4, boolean $$5) {
        if ($$5) {
            return ProfiledReloadInstance.of($$0, $$1, $$2, $$3, $$4);
        }
        return of($$0, $$1, $$2, $$3, $$4);
    }
}
