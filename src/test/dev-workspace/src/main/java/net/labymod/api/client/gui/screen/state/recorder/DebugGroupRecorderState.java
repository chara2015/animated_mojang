package net.labymod.api.client.gui.screen.state.recorder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/DebugGroupRecorderState.class */
public final class DebugGroupRecorderState extends Record implements RecorderState {
    private final String name;

    public DebugGroupRecorderState(String name) {
        this.name = name;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DebugGroupRecorderState.class), DebugGroupRecorderState.class, "name", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/DebugGroupRecorderState;->name:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DebugGroupRecorderState.class), DebugGroupRecorderState.class, "name", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/DebugGroupRecorderState;->name:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DebugGroupRecorderState.class, Object.class), DebugGroupRecorderState.class, "name", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/DebugGroupRecorderState;->name:Ljava/lang/String;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    @Override // net.labymod.api.client.gui.screen.state.recorder.RecorderState, java.lang.AutoCloseable
    public void close() {
    }
}
