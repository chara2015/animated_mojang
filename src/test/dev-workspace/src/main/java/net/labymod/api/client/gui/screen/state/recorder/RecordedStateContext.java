package net.labymod.api.client.gui.screen.state.recorder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.shaders.block.DynamicTransformsUniformBlockData;
import net.labymod.api.laby3d.shaders.block.ProjectionUniformBlockData;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/RecordedStateContext.class */
public final class RecordedStateContext extends Record {
    private final Laby3D laby3D;
    private final CommandBuffer commandBuffer;
    private final ProjectionUniformBlockData projectionData;
    private final DynamicTransformsUniformBlockData dynamicTransformsData;
    private final RenderTarget renderTarget;

    public RecordedStateContext(Laby3D laby3D, CommandBuffer commandBuffer, ProjectionUniformBlockData projectionData, DynamicTransformsUniformBlockData dynamicTransformsData, RenderTarget renderTarget) {
        this.laby3D = laby3D;
        this.commandBuffer = commandBuffer;
        this.projectionData = projectionData;
        this.dynamicTransformsData = dynamicTransformsData;
        this.renderTarget = renderTarget;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RecordedStateContext.class), RecordedStateContext.class, "laby3D;commandBuffer;projectionData;dynamicTransformsData;renderTarget", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->laby3D:Lnet/labymod/api/laby3d/Laby3D;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->commandBuffer:Lnet/labymod/laby3d/api/pipeline/pass/CommandBuffer;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->projectionData:Lnet/labymod/api/laby3d/shaders/block/ProjectionUniformBlockData;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->dynamicTransformsData:Lnet/labymod/api/laby3d/shaders/block/DynamicTransformsUniformBlockData;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->renderTarget:Lnet/labymod/laby3d/api/pipeline/target/RenderTarget;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RecordedStateContext.class), RecordedStateContext.class, "laby3D;commandBuffer;projectionData;dynamicTransformsData;renderTarget", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->laby3D:Lnet/labymod/api/laby3d/Laby3D;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->commandBuffer:Lnet/labymod/laby3d/api/pipeline/pass/CommandBuffer;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->projectionData:Lnet/labymod/api/laby3d/shaders/block/ProjectionUniformBlockData;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->dynamicTransformsData:Lnet/labymod/api/laby3d/shaders/block/DynamicTransformsUniformBlockData;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->renderTarget:Lnet/labymod/laby3d/api/pipeline/target/RenderTarget;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RecordedStateContext.class, Object.class), RecordedStateContext.class, "laby3D;commandBuffer;projectionData;dynamicTransformsData;renderTarget", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->laby3D:Lnet/labymod/api/laby3d/Laby3D;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->commandBuffer:Lnet/labymod/laby3d/api/pipeline/pass/CommandBuffer;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->projectionData:Lnet/labymod/api/laby3d/shaders/block/ProjectionUniformBlockData;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->dynamicTransformsData:Lnet/labymod/api/laby3d/shaders/block/DynamicTransformsUniformBlockData;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedStateContext;->renderTarget:Lnet/labymod/laby3d/api/pipeline/target/RenderTarget;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Laby3D laby3D() {
        return this.laby3D;
    }

    public CommandBuffer commandBuffer() {
        return this.commandBuffer;
    }

    public ProjectionUniformBlockData projectionData() {
        return this.projectionData;
    }

    public DynamicTransformsUniformBlockData dynamicTransformsData() {
        return this.dynamicTransformsData;
    }

    public RenderTarget renderTarget() {
        return this.renderTarget;
    }

    public RenderDevice renderDevice() {
        return this.laby3D.renderDevice();
    }
}
