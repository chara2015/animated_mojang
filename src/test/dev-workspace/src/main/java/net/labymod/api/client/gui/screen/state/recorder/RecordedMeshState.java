package net.labymod.api.client.gui.screen.state.recorder;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.state.ClipShape;
import net.labymod.api.client.gui.screen.state.DrawCommandContext;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.pipeline.material.TextureMaterial;
import net.labymod.api.laby3d.shaders.block.ClipDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.ClipDataUniformBlockData;
import net.labymod.api.laby3d.shaders.block.GlobalsUniformBlock;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.laby3d.api.buffers.DeviceBuffer;
import net.labymod.laby3d.api.mesh.Mesh;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/RecordedMeshState.class */
public final class RecordedMeshState extends Record implements RecordedState<MeshRecorderState> {
    private final DeviceBuffer vertexBuffer;
    private final MeshRecorderState state;
    private final Mesh mesh;
    private final int offset;
    private static final ClipDataUniformBlockData CLIP_DATA = new ClipDataUniformBlockData();

    public RecordedMeshState(DeviceBuffer vertexBuffer, MeshRecorderState state, Mesh mesh, int offset) {
        this.vertexBuffer = vertexBuffer;
        this.state = state;
        this.mesh = mesh;
        this.offset = offset;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RecordedMeshState.class), RecordedMeshState.class, "vertexBuffer;state;mesh;offset", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedMeshState;->vertexBuffer:Lnet/labymod/laby3d/api/buffers/DeviceBuffer;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedMeshState;->state:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedMeshState;->mesh:Lnet/labymod/laby3d/api/mesh/Mesh;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedMeshState;->offset:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RecordedMeshState.class), RecordedMeshState.class, "vertexBuffer;state;mesh;offset", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedMeshState;->vertexBuffer:Lnet/labymod/laby3d/api/buffers/DeviceBuffer;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedMeshState;->state:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedMeshState;->mesh:Lnet/labymod/laby3d/api/mesh/Mesh;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedMeshState;->offset:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RecordedMeshState.class, Object.class), RecordedMeshState.class, "vertexBuffer;state;mesh;offset", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedMeshState;->vertexBuffer:Lnet/labymod/laby3d/api/buffers/DeviceBuffer;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedMeshState;->state:Lnet/labymod/api/client/gui/screen/state/recorder/MeshRecorderState;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedMeshState;->mesh:Lnet/labymod/laby3d/api/mesh/Mesh;", "FIELD:Lnet/labymod/api/client/gui/screen/state/recorder/RecordedMeshState;->offset:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public DeviceBuffer vertexBuffer() {
        return this.vertexBuffer;
    }

    @Override // net.labymod.api.client.gui.screen.state.recorder.RecordedState
    public MeshRecorderState state() {
        return this.state;
    }

    public Mesh mesh() {
        return this.mesh;
    }

    public int offset() {
        return this.offset;
    }

    @Override // net.labymod.api.client.gui.screen.state.recorder.RecordedState
    public void submit(RecordedStateContext context) {
        CommandBuffer cmd = context.commandBuffer();
        Laby3D laby3D = context.laby3D();
        MeshRecorderState state = this.state;
        applyScissor(cmd);
        applyClipShape(cmd, laby3D);
        cmd.bindPipeline(state.material().renderState());
        cmd.bindUniformBlock("Projection", laby3D.projection());
        cmd.bindUniformBlockData("Projection", context.projectionData());
        cmd.bindUniformBlock(GlobalsUniformBlock.NAME, laby3D.globals());
        cmd.bindUniformBlock("DynamicTransforms", laby3D.dynamicTransforms());
        cmd.bindUniformBlockData("DynamicTransforms", context.dynamicTransformsData());
        DrawCommandContext drawCommandContext = new DrawCommandContext(cmd, context.projectionData(), context.dynamicTransformsData());
        state.consumeCommand(drawCommandContext);
        TextureMaterial[] textureMaterials = state.material().textureMaterials();
        for (int textureIndex = 0; textureIndex < textureMaterials.length; textureIndex++) {
            cmd.bindTexture(textureIndex, textureMaterials[textureIndex].textureView());
        }
        cmd.draw(mesh());
    }

    private void applyScissor(CommandBuffer cmd) {
        ScissorArea scissorArea = this.state.scissorArea();
        if (scissorArea == null) {
            cmd.disableScissor();
            return;
        }
        Rectangle bounds = scissorArea.bounds();
        Window window = Laby.labyAPI().minecraft().minecraftWindow();
        float height = window.getRawHeight();
        float scale = window.getScale();
        float scissorX = bounds.getLeft() * scale;
        float scissorY = height - (bounds.getBottom() * scale);
        float scissorWidth = bounds.getWidth() * scale;
        float scissorHeight = bounds.getHeight() * scale;
        cmd.setScissor((int) scissorX, (int) scissorY, (int) Math.max(0.0f, scissorWidth), (int) Math.max(0.0f, scissorHeight));
    }

    private void applyClipShape(CommandBuffer cmd, Laby3D laby3D) {
        ClipShape clip = this.state.clipShape();
        ClipDataUniformBlockData clipData = CLIP_DATA;
        if (clip != null) {
            clipData.clipBounds().set(clip.left(), clip.top(), clip.right(), clip.bottom());
            clipData.clipCornerRadius().set(clip.bottomRightRadius(), clip.topRightRadius(), clip.bottomLeftRadius(), clip.topLeftRadius());
            clipData.setClipEnabled(1.0f);
        } else {
            clipData.setClipEnabled(0.0f);
        }
        cmd.bindUniformBlock(ClipDataUniformBlock.NAME, laby3D.clipData());
        cmd.bindUniformBlockData(ClipDataUniformBlock.NAME, clipData);
    }
}
