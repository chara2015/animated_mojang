package net.minecraft.server.packs.resources;

import com.google.common.base.Stopwatch;
import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.server.packs.resources.SimpleReloadInstance;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/ProfiledReloadInstance.class */
public class ProfiledReloadInstance extends SimpleReloadInstance<State> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Stopwatch total;

    public static ReloadInstance of(ResourceManager $$0, List<PreparableReloadListener> $$1, Executor $$2, Executor $$3, CompletableFuture<Unit> $$4) {
        ProfiledReloadInstance $$5 = new ProfiledReloadInstance($$1);
        $$5.startTasks($$2, $$3, $$0, $$1, ($$12, $$22, $$32, $$42, $$52) -> {
            AtomicLong $$6 = new AtomicLong();
            AtomicLong $$7 = new AtomicLong();
            AtomicLong $$8 = new AtomicLong();
            AtomicLong $$9 = new AtomicLong();
            CompletableFuture<Void> $$10 = $$32.reload($$12, profiledExecutor($$42, $$6, $$7, $$32.getName()), $$22, profiledExecutor($$52, $$8, $$9, $$32.getName()));
            return $$10.thenApplyAsync($$52 -> {
                LOGGER.debug("Finished reloading {}", $$32.getName());
                return new State($$32.getName(), $$6, $$7, $$8, $$9);
            }, $$3);
        }, $$4);
        return $$5;
    }

    private ProfiledReloadInstance(List<PreparableReloadListener> $$0) {
        super($$0);
        this.total = Stopwatch.createUnstarted();
        this.total.start();
    }

    @Override // net.minecraft.server.packs.resources.SimpleReloadInstance
    protected CompletableFuture<List<State>> prepareTasks(Executor $$0, Executor $$1, ResourceManager $$2, List<PreparableReloadListener> $$3, SimpleReloadInstance.StateFactory<State> $$4, CompletableFuture<?> $$5) {
        return super.prepareTasks($$0, $$1, $$2, $$3, $$4, $$5).thenApplyAsync(this::finish, $$1);
    }

    private static Executor profiledExecutor(Executor $$0, AtomicLong $$1, AtomicLong $$2, String $$3) {
        return $$4 -> {
            $$0.execute(() -> {
                ProfilerFiller $$4 = Profiler.get();
                $$4.push($$3);
                long $$5 = Util.getNanos();
                $$4.run();
                $$1.addAndGet(Util.getNanos() - $$5);
                $$2.incrementAndGet();
                $$4.pop();
            });
        };
    }

    private List<State> finish(List<State> $$0) {
        this.total.stop();
        long $$1 = 0;
        LOGGER.info("Resource reload finished after {} ms", Long.valueOf(this.total.elapsed(TimeUnit.MILLISECONDS)));
        for (State $$2 : $$0) {
            long $$3 = TimeUnit.NANOSECONDS.toMillis($$2.preparationNanos.get());
            long $$4 = $$2.preparationCount.get();
            long $$5 = TimeUnit.NANOSECONDS.toMillis($$2.reloadNanos.get());
            long $$6 = $$2.reloadCount.get();
            long $$7 = $$3 + $$5;
            long $$8 = $$4 + $$6;
            String $$9 = $$2.name;
            LOGGER.info("{} took approximately {} tasks/{} ms ({} tasks/{} ms preparing, {} tasks/{} ms applying)", new Object[]{$$9, Long.valueOf($$8), Long.valueOf($$7), Long.valueOf($$4), Long.valueOf($$3), Long.valueOf($$6), Long.valueOf($$5)});
            $$1 += $$5;
        }
        LOGGER.info("Total blocking time: {} ms", Long.valueOf($$1));
        return $$0;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/resources/ProfiledReloadInstance$State.class */
    public static final class State extends Record {
        private final String name;
        private final AtomicLong preparationNanos;
        private final AtomicLong preparationCount;
        private final AtomicLong reloadNanos;
        private final AtomicLong reloadCount;

        public State(String $$0, AtomicLong $$1, AtomicLong $$2, AtomicLong $$3, AtomicLong $$4) {
            this.name = $$0;
            this.preparationNanos = $$1;
            this.preparationCount = $$2;
            this.reloadNanos = $$3;
            this.reloadCount = $$4;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, State.class), State.class, "name;preparationNanos;preparationCount;reloadNanos;reloadCount", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->preparationNanos:Ljava/util/concurrent/atomic/AtomicLong;", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->preparationCount:Ljava/util/concurrent/atomic/AtomicLong;", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->reloadNanos:Ljava/util/concurrent/atomic/AtomicLong;", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->reloadCount:Ljava/util/concurrent/atomic/AtomicLong;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, State.class), State.class, "name;preparationNanos;preparationCount;reloadNanos;reloadCount", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->preparationNanos:Ljava/util/concurrent/atomic/AtomicLong;", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->preparationCount:Ljava/util/concurrent/atomic/AtomicLong;", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->reloadNanos:Ljava/util/concurrent/atomic/AtomicLong;", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->reloadCount:Ljava/util/concurrent/atomic/AtomicLong;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, State.class, Object.class), State.class, "name;preparationNanos;preparationCount;reloadNanos;reloadCount", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->preparationNanos:Ljava/util/concurrent/atomic/AtomicLong;", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->preparationCount:Ljava/util/concurrent/atomic/AtomicLong;", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->reloadNanos:Ljava/util/concurrent/atomic/AtomicLong;", "FIELD:Lnet/minecraft/server/packs/resources/ProfiledReloadInstance$State;->reloadCount:Ljava/util/concurrent/atomic/AtomicLong;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String name() {
            return this.name;
        }

        public AtomicLong preparationNanos() {
            return this.preparationNanos;
        }

        public AtomicLong preparationCount() {
            return this.preparationCount;
        }

        public AtomicLong reloadNanos() {
            return this.reloadNanos;
        }

        public AtomicLong reloadCount() {
            return this.reloadCount;
        }
    }
}
