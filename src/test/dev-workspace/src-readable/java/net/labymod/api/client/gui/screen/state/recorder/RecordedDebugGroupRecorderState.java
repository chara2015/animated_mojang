package net.labymod.api.client.gui.screen.state.recorder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.laby3d.api.debugger.DeviceDebugger;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/RecordedDebugGroupRecorderState.class */
public final class RecordedDebugGroupRecorderState extends Record implements RecordedState<DebugGroupRecorderState> {
    private final DebugGroupRecorderState state;

    public RecordedDebugGroupRecorderState(DebugGroupRecorderState state) {
        this.state = state;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RecordedDebugGroupRecorderState.class), RecordedDebugGroupRecorderState.class, "state", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedDebugGroupRecorderState;->state:Lnet/labymod/api/client/gui/screen/state/recorder/DebugGroupRecorderState;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RecordedDebugGroupRecorderState.class), RecordedDebugGroupRecorderState.class, "state", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedDebugGroupRecorderState;->state:Lnet/labymod/api/client/gui/screen/state/recorder/DebugGroupRecorderState;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RecordedDebugGroupRecorderState.class, Object.class), RecordedDebugGroupRecorderState.class, "state", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedDebugGroupRecorderState;->state:Lnet/labymod/api/client/gui/screen/state/recorder/DebugGroupRecorderState;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @Override // net.labymod.api.client.gui.screen.state.recorder.RecordedState
    public DebugGroupRecorderState state() {
        return this.state;
    }

    @Override // net.labymod.api.client.gui.screen.state.recorder.RecordedState
    public void submit(RecordedStateContext context) {
        DeviceDebugger debugger = context.renderDevice().debugger();
        String name = this.state.name();
        if (name == null) {
            debugger.popDebugGroup();
        } else {
            debugger.pushDebugGroup(name);
        }
    }
}
