package net.labymod.api.client.gui.screen.state.recorder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.Consumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/CommandTaskRecorderState.class */
public final class CommandTaskRecorderState extends Record implements RecorderState {
    private final Consumer<RecordedStateContext> task;

    public CommandTaskRecorderState(Consumer<RecordedStateContext> task) {
        this.task = task;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CommandTaskRecorderState.class), CommandTaskRecorderState.class, "task", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/CommandTaskRecorderState;->task:Ljava/util/function/Consumer;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CommandTaskRecorderState.class), CommandTaskRecorderState.class, "task", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/CommandTaskRecorderState;->task:Ljava/util/function/Consumer;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CommandTaskRecorderState.class, Object.class), CommandTaskRecorderState.class, "task", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/CommandTaskRecorderState;->task:Ljava/util/function/Consumer;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Consumer<RecordedStateContext> task() {
        return this.task;
    }

    public void execute(RecordedStateContext context) {
        this.task.accept(context);
    }

    @Override // net.labymod.api.client.gui.screen.state.recorder.RecorderState, java.lang.AutoCloseable
    public void close() {
    }
}
