package net.minecraft;

import com.mojang.jtracy.TracyClient;
import com.mojang.jtracy.Zone;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/TracingExecutor.class */
public final class TracingExecutor extends Record implements Executor {
    private final ExecutorService service;

    public TracingExecutor(ExecutorService $$0) {
        this.service = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TracingExecutor.class), TracingExecutor.class, "service", "FIELD:Lnet/minecraft/TracingExecutor;->service:Ljava/util/concurrent/ExecutorService;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TracingExecutor.class), TracingExecutor.class, "service", "FIELD:Lnet/minecraft/TracingExecutor;->service:Ljava/util/concurrent/ExecutorService;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TracingExecutor.class, Object.class), TracingExecutor.class, "service", "FIELD:Lnet/minecraft/TracingExecutor;->service:Ljava/util/concurrent/ExecutorService;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ExecutorService service() {
        return this.service;
    }

    public Executor forName(String $$0) {
        if (SharedConstants.IS_RUNNING_IN_IDE) {
            return $$1 -> {
                this.service.execute(() -> {
                    Thread $$2 = Thread.currentThread();
                    String $$3 = $$2.getName();
                    $$2.setName($$0);
                    try {
                        Zone $$4 = TracyClient.beginZone($$0, SharedConstants.IS_RUNNING_IN_IDE);
                        try {
                            $$1.run();
                            if ($$4 != null) {
                                $$4.close();
                            }
                        } finally {
                        }
                    } finally {
                        $$2.setName($$3);
                    }
                });
            };
        }
        if (TracyClient.isAvailable()) {
            return $$12 -> {
                this.service.execute(() -> {
                    Zone $$2 = TracyClient.beginZone($$0, SharedConstants.IS_RUNNING_IN_IDE);
                    try {
                        $$12.run();
                        if ($$2 != null) {
                            $$2.close();
                        }
                    } catch (Throwable th) {
                        if ($$2 != null) {
                            try {
                                $$2.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                });
            };
        }
        return this.service;
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable $$0) {
        this.service.execute(wrapUnnamed($$0));
    }

    public void shutdownAndAwait(long $$0, TimeUnit $$1) {
        boolean $$4;
        this.service.shutdown();
        try {
            $$4 = this.service.awaitTermination($$0, $$1);
        } catch (InterruptedException e) {
            $$4 = false;
        }
        if (!$$4) {
            this.service.shutdownNow();
        }
    }

    private static Runnable wrapUnnamed(Runnable $$0) {
        if (!TracyClient.isAvailable()) {
            return $$0;
        }
        return () -> {
            Zone $$1 = TracyClient.beginZone("task", SharedConstants.IS_RUNNING_IN_IDE);
            try {
                $$0.run();
                if ($$1 != null) {
                    $$1.close();
                }
            } catch (Throwable th) {
                if ($$1 != null) {
                    try {
                        $$1.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        };
    }
}
