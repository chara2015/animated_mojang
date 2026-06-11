package net.labymod.api.client.gui.screen.state.recorder;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.state.recorder.MeshRecorder;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.shaders.block.DynamicTransformsUniformBlockData;
import net.labymod.api.laby3d.shaders.block.ProjectionUniformBlockData;
import net.labymod.api.laby3d.util.matrix.CachedOrthoProjectionMatrix;
import net.labymod.api.thirdparty.LabySentry;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.debugger.ScopedGroup;
import net.labymod.laby3d.api.pipeline.LoadOp;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.resource.AssetId;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/MeshRenderer.class */
@ApiStatus.Internal
public final class MeshRenderer {
    private static final Logging LOGGER = Logging.getLogger();
    private static final AssetId GUI_PASS = AssetId.of("labymod", "pass/gui");
    private static final Matrix4f MODEL_VIEW_MATRIX = new Matrix4f().translate(0.0f, 0.0f, -11000.0f);
    private final CachedOrthoProjectionMatrix<Matrix4f> guiProjectionMatrix = CachedOrthoProjectionMatrix.simple(1000.0f, 11000.0f, true);
    private final Laby3D laby3D;

    public MeshRenderer(Laby3D laby3D) {
        this.laby3D = laby3D;
    }

    public void render(List<MeshRecorder.RenderStep> renderSteps, List<RecordedState<?>> recordedStates) {
        if (renderSteps.isEmpty()) {
            return;
        }
        RenderDevice renderDevice = this.laby3D.renderDevice();
        Window window = Laby.labyAPI().minecraft().minecraftWindow();
        this.laby3D.globals().screenSize().set(window.getRawSize());
        long duration = TimeUtil.getNanos() - Laby.labyAPI().startupTime();
        long millis = duration / 1000000;
        float time = millis / 1000.0f;
        this.laby3D.globals().gameTime().set(Float.valueOf(time));
        ProjectionUniformBlockData projectionData = new ProjectionUniformBlockData();
        projectionData.projectionMatrix().set(createProjectionMatrix());
        DynamicTransformsUniformBlockData dynamicTransformsData = new DynamicTransformsUniformBlockData();
        dynamicTransformsData.modelViewMatrix().set(MODEL_VIEW_MATRIX);
        dynamicTransformsData.setLineWidth(1.0f);
        try {
            ScopedGroup scopedGroup = renderDevice.debugger().scopedGroup(GUI_PASS.toString());
            for (int index = 0; index < renderSteps.size(); index++) {
                try {
                    MeshRecorder.RenderStep step = renderSteps.get(index);
                    RenderTarget target = step.target();
                    CommandBuffer cmd = renderDevice.createCommandBuffer(1);
                    int finalIndex = index;
                    cmd.beginPass(target, LoadOp.LOAD, () -> {
                        return "GUI pass " + finalIndex;
                    });
                    RecordedStateContext context = new RecordedStateContext(this.laby3D, cmd, projectionData, dynamicTransformsData, target);
                    try {
                        for (int stateIndex = step.start(); stateIndex < step.end(); stateIndex++) {
                            RecordedState<?> recordedState = recordedStates.get(stateIndex);
                            recordedState.submit(context);
                        }
                        cmd.disableScissor();
                        cmd.endPass();
                        cmd.submit();
                        cmd.close();
                    } catch (Throwable th) {
                        cmd.close();
                        throw th;
                    }
                } finally {
                }
            }
            if (scopedGroup != null) {
                scopedGroup.close();
            }
        } catch (Throwable throwable) {
            LOGGER.error("An error occurred while rendering the GUI meshes.", throwable);
            LabySentry.capture(throwable);
        }
    }

    private Matrix4f createProjectionMatrix() {
        Window window = Laby.labyAPI().minecraft().minecraftWindow();
        return this.guiProjectionMatrix.getCached(window.getRawWidth() / window.getScale(), window.getRawHeight() / window.getScale());
    }
}
