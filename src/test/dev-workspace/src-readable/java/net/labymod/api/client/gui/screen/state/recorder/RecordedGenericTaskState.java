package net.labymod.api.client.gui.screen.state.recorder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/RecordedGenericTaskState.class */
public final class RecordedGenericTaskState extends Record implements RecordedState<GenericTaskRecorderState> {
    private final GenericTaskRecorderState state;

    public RecordedGenericTaskState(GenericTaskRecorderState state) {
        this.state = state;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RecordedGenericTaskState.class), RecordedGenericTaskState.class, "state", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedGenericTaskState;->state:Lnet/labymod/api/client/gui/screen/state/recorder/GenericTaskRecorderState;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RecordedGenericTaskState.class), RecordedGenericTaskState.class, "state", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedGenericTaskState;->state:Lnet/labymod/api/client/gui/screen/state/recorder/GenericTaskRecorderState;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RecordedGenericTaskState.class, Object.class), RecordedGenericTaskState.class, "state", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedGenericTaskState;->state:Lnet/labymod/api/client/gui/screen/state/recorder/GenericTaskRecorderState;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @Override // net.labymod.api.client.gui.screen.state.recorder.RecordedState
    public GenericTaskRecorderState state() {
        return this.state;
    }

    @Override // net.labymod.api.client.gui.screen.state.recorder.RecordedState
    public void submit(RecordedStateContext context) {
        this.state.execute();
    }
}
