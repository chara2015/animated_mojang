package net.labymod.api.event.client.lifecycle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Path;
import net.labymod.api.event.Event;
import net.labymod.api.event.LabyEvent;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/lifecycle/ShutdownEvent.class */
@LabyEvent(background = true)
public final class ShutdownEvent extends Record implements Event {
    private final Stage stage;
    private final Context context;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/lifecycle/ShutdownEvent$Context.class */
    public interface Context {
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ShutdownEvent.class), ShutdownEvent.class, "stage;context", "FIELD:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent;->stage:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent$Stage;", "FIELD:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent;->context:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent$Context;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ShutdownEvent.class), ShutdownEvent.class, "stage;context", "FIELD:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent;->stage:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent$Stage;", "FIELD:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent;->context:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent$Context;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ShutdownEvent.class, Object.class), ShutdownEvent.class, "stage;context", "FIELD:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent;->stage:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent$Stage;", "FIELD:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent;->context:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent$Context;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Stage stage() {
        return this.stage;
    }

    public Context context() {
        return this.context;
    }

    public ShutdownEvent(Stage stage, Context context) {
        this.stage = stage;
        this.context = context;
    }

    public boolean isCrash() {
        return this.stage == Stage.CRASH;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/lifecycle/ShutdownEvent$Stage.class */
    public enum Stage {
        PRE(false),
        POST(true),
        CRASH(true);

        private final boolean destroyResources;

        Stage(boolean destroyResources) {
            this.destroyResources = destroyResources;
        }

        public boolean destroyResources() {
            return this.destroyResources;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/lifecycle/ShutdownEvent$NormalContext.class */
    public static final class NormalContext extends Record implements Context {
        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, NormalContext.class), NormalContext.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, NormalContext.class), NormalContext.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, NormalContext.class, Object.class), NormalContext.class, "").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/lifecycle/ShutdownEvent$CrashContext.class */
    public static final class CrashContext extends Record implements Context {

        @Nullable
        private final Throwable cause;

        @Nullable
        private final Path crashReport;

        public CrashContext(@Nullable Throwable cause, @Nullable Path crashReport) {
            this.cause = cause;
            this.crashReport = crashReport;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CrashContext.class), CrashContext.class, "cause;crashReport", "FIELD:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent$CrashContext;->cause:Ljava/lang/Throwable;", "FIELD:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent$CrashContext;->crashReport:Ljava/nio/file/Path;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CrashContext.class), CrashContext.class, "cause;crashReport", "FIELD:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent$CrashContext;->cause:Ljava/lang/Throwable;", "FIELD:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent$CrashContext;->crashReport:Ljava/nio/file/Path;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CrashContext.class, Object.class), CrashContext.class, "cause;crashReport", "FIELD:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent$CrashContext;->cause:Ljava/lang/Throwable;", "FIELD:Lnet/labymod/api/event/client/lifecycle/ShutdownEvent$CrashContext;->crashReport:Ljava/nio/file/Path;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        @Nullable
        public Throwable cause() {
            return this.cause;
        }

        @Nullable
        public Path crashReport() {
            return this.crashReport;
        }
    }
}
