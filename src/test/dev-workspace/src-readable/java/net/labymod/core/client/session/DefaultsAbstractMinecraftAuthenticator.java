package net.labymod.core.client.session;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import net.labymod.api.client.session.MinecraftAuthenticator;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/session/DefaultsAbstractMinecraftAuthenticator.class */
public abstract class DefaultsAbstractMinecraftAuthenticator implements MinecraftAuthenticator {
    private static final long LOGIN_DELAY = TimeUnit.SECONDS.toMillis(3);
    protected final List<AuthTask> authenticators = new CopyOnWriteArrayList();
    private long nextLogin = -1;

    public DefaultsAbstractMinecraftAuthenticator() {
        Task.builder(() -> {
            Runnable authenticator;
            if ((this.nextLogin == -1 || TimeUtil.getCurrentTimeMillis() >= this.nextLogin) && (authenticator = takeAuthenticator()) != null) {
                authenticator.run();
                this.nextLogin = TimeUtil.getCurrentTimeMillis() + LOGIN_DELAY;
            }
        }).delay(500L, TimeUnit.MILLISECONDS).repeat(500L, TimeUnit.MILLISECONDS).build().execute();
    }

    private Runnable takeAuthenticator() {
        if (this.authenticators.isEmpty()) {
            return null;
        }
        List<AuthTask> tasks = new LinkedList<>(this.authenticators);
        tasks.sort(Comparator.comparingInt(value -> {
            return value.priority;
        }));
        AuthTask task = tasks.get(0);
        this.authenticators.remove(task);
        return task.runnable;
    }

    protected CompletableFuture<Boolean> queueTask(int priority, Consumer<CompletableFuture<Boolean>> task) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        this.authenticators.add(new AuthTask(priority, () -> {
            task.accept(future);
        }));
        return future;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/session/DefaultsAbstractMinecraftAuthenticator$AuthTask.class */
    private static final class AuthTask extends Record {
        private final int priority;
        private final Runnable runnable;

        private AuthTask(int priority, Runnable runnable) {
            this.priority = priority;
            this.runnable = runnable;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AuthTask.class), AuthTask.class, "priority;runnable", "FIELD:Lnet/labymod/core/client/session/DefaultsAbstractMinecraftAuthenticator$AuthTask;->priority:I", "FIELD:Lnet/labymod/core/client/session/DefaultsAbstractMinecraftAuthenticator$AuthTask;->runnable:Ljava/lang/Runnable;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AuthTask.class), AuthTask.class, "priority;runnable", "FIELD:Lnet/labymod/core/client/session/DefaultsAbstractMinecraftAuthenticator$AuthTask;->priority:I", "FIELD:Lnet/labymod/core/client/session/DefaultsAbstractMinecraftAuthenticator$AuthTask;->runnable:Ljava/lang/Runnable;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AuthTask.class, Object.class), AuthTask.class, "priority;runnable", "FIELD:Lnet/labymod/core/client/session/DefaultsAbstractMinecraftAuthenticator$AuthTask;->priority:I", "FIELD:Lnet/labymod/core/client/session/DefaultsAbstractMinecraftAuthenticator$AuthTask;->runnable:Ljava/lang/Runnable;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public int priority() {
            return this.priority;
        }

        public Runnable runnable() {
            return this.runnable;
        }
    }
}
