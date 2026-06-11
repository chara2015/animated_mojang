package net.labymod.api.client.gui.screen.state.recorder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.gui.screen.state.ClearInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/ClearRecorderState.class */
public final class ClearRecorderState extends Record implements RecorderState {
    private final ClearInfo clearInfo;

    public ClearRecorderState(ClearInfo clearInfo) {
        this.clearInfo = clearInfo;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClearRecorderState.class), ClearRecorderState.class, "clearInfo", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/ClearRecorderState;->clearInfo:Lnet/labymod/api/client/gui/screen/state/ClearInfo;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClearRecorderState.class), ClearRecorderState.class, "clearInfo", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/ClearRecorderState;->clearInfo:Lnet/labymod/api/client/gui/screen/state/ClearInfo;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClearRecorderState.class, Object.class), ClearRecorderState.class, "clearInfo", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/ClearRecorderState;->clearInfo:Lnet/labymod/api/client/gui/screen/state/ClearInfo;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public ClearInfo clearInfo() {
        return this.clearInfo;
    }

    @Override // net.labymod.api.client.gui.screen.state.recorder.RecorderState, java.lang.AutoCloseable
    public void close() {
    }
}
