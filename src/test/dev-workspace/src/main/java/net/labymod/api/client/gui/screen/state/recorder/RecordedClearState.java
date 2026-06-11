package net.labymod.api.client.gui.screen.state.recorder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.gui.screen.state.ClearInfo;
import net.labymod.laby3d.api.pipeline.LoadOp;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.pass.PassDescriptor;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/RecordedClearState.class */
public final class RecordedClearState extends Record implements RecordedState<ClearRecorderState> {
    private final ClearRecorderState state;

    public RecordedClearState(ClearRecorderState state) {
        this.state = state;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RecordedClearState.class), RecordedClearState.class, "state", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedClearState;->state:Lnet/labymod/api/client/gui/screen/state/recorder/ClearRecorderState;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RecordedClearState.class), RecordedClearState.class, "state", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedClearState;->state:Lnet/labymod/api/client/gui/screen/state/recorder/ClearRecorderState;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RecordedClearState.class, Object.class), RecordedClearState.class, "state", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedClearState;->state:Lnet/labymod/api/client/gui/screen/state/recorder/ClearRecorderState;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @Override // net.labymod.api.client.gui.screen.state.recorder.RecordedState
    public ClearRecorderState state() {
        return this.state;
    }

    @Override // net.labymod.api.client.gui.screen.state.recorder.RecordedState
    public void submit(RecordedStateContext context) {
        ClearInfo clearInfo = this.state.clearInfo();
        if (clearInfo.isClearColor() || clearInfo.isClearDepth() || clearInfo.isClearStencil()) {
            CommandBuffer cmd = context.commandBuffer();
            RenderTarget target = context.renderTarget();
            cmd.endPass();
            PassDescriptor desc = cmd.passDescriptor();
            if (clearInfo.isClearColor()) {
                desc.clearColor(0, clearInfo.red(), clearInfo.green(), clearInfo.blue(), clearInfo.alpha());
            }
            if (clearInfo.isClearDepth() && clearInfo.isClearStencil()) {
                desc.clearDepthStencil(clearInfo.depth(), clearInfo.stencil());
            } else if (clearInfo.isClearDepth()) {
                desc.clearDepth(clearInfo.depth());
            } else if (clearInfo.isClearStencil()) {
                desc.clearStencil(clearInfo.stencil());
            }
            cmd.beginPass(target, LoadOp.LOAD);
        }
    }
}
