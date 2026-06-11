package net.labymod.api.client.gui.screen.state.offscreen;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/offscreen/OffscreenContext.class */
public final class OffscreenContext extends Record {
    private final CommandBuffer commandBuffer;

    public OffscreenContext(CommandBuffer commandBuffer) {
        this.commandBuffer = commandBuffer;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, OffscreenContext.class), OffscreenContext.class, "commandBuffer", "FIELD:Lnet/labymod/api/client/gui/screen/state/offscreen/OffscreenContext;->commandBuffer:Lnet/labymod/laby3d/api/pipeline/pass/CommandBuffer;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, OffscreenContext.class), OffscreenContext.class, "commandBuffer", "FIELD:Lnet/labymod/api/client/gui/screen/state/offscreen/OffscreenContext;->commandBuffer:Lnet/labymod/laby3d/api/pipeline/pass/CommandBuffer;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, OffscreenContext.class, Object.class), OffscreenContext.class, "commandBuffer", "FIELD:Lnet/labymod/api/client/gui/screen/state/offscreen/OffscreenContext;->commandBuffer:Lnet/labymod/laby3d/api/pipeline/pass/CommandBuffer;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public CommandBuffer commandBuffer() {
        return this.commandBuffer;
    }
}
