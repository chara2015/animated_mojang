package net.labymod.api.client.gui.screen.state.recorder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/GenericTaskRecorderState.class */
public final class GenericTaskRecorderState extends Record implements RecorderState {
    private final Runnable task;

    public GenericTaskRecorderState(Runnable task) {
        this.task = task;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GenericTaskRecorderState.class), GenericTaskRecorderState.class, "task", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/GenericTaskRecorderState;->task:Ljava/lang/Runnable;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GenericTaskRecorderState.class), GenericTaskRecorderState.class, "task", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/GenericTaskRecorderState;->task:Ljava/lang/Runnable;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GenericTaskRecorderState.class, Object.class), GenericTaskRecorderState.class, "task", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/GenericTaskRecorderState;->task:Ljava/lang/Runnable;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Runnable task() {
        return this.task;
    }

    public void execute() {
        this.task.run();
    }

    @Override // net.labymod.api.client.gui.screen.state.recorder.RecorderState, java.lang.AutoCloseable
    public void close() {
    }
}
