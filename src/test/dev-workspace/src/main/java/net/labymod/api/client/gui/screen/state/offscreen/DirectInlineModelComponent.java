package net.labymod.api.client.gui.screen.state.offscreen;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.offscreen.DynamicOffscreenRenderState;
import net.labymod.api.client.gui.screen.state.recorder.CommandTaskRecorderState;
import net.labymod.api.client.gui.screen.state.recorder.RecordedStateContext;
import net.labymod.api.client.gui.screen.state.recorder.RecorderState;
import net.labymod.api.client.gui.screen.state.states.commands.GuiCommandComponent;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.laby3d.util.matrix.CachedOrthoProjectionMatrix;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/offscreen/DirectInlineModelComponent.class */
@ApiStatus.Internal
public class DirectInlineModelComponent extends GuiCommandComponent {
    private static final CachedOrthoProjectionMatrix<Matrix4f> OFFSCREEN_PROJECTION_MATRIX = CachedOrthoProjectionMatrix.simple(0.5f, 11000.0f, false);
    private static final Matrix4f MODEL_VIEW_MATRIX = new Matrix4f().setTranslation(0.0f, 0.0f, -11000.0f);
    private final Matrix4f pose;
    private final Rectangle bounds;
    private final float scale;

    @Nullable
    private final RoundedData roundedData;
    private final DynamicOffscreenRenderState.DynamicRenderer renderer;

    public DirectInlineModelComponent(Matrix4f pose, Rectangle bounds, float scale, @Nullable RoundedData roundedData, DynamicOffscreenRenderState.DynamicRenderer renderer) {
        this.pose = new Matrix4f(pose);
        this.bounds = bounds;
        this.scale = scale;
        this.roundedData = roundedData;
        this.renderer = renderer;
    }

    @Override // net.labymod.api.client.gui.screen.state.states.commands.GuiCommandComponent
    public boolean shouldRecordMesh() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.state.states.commands.GuiCommandComponent
    public RecorderState createState() {
        return new CommandTaskRecorderState(this::run);
    }

    private void run(RecordedStateContext context) {
        CommandBuffer cmd = context.commandBuffer();
        Window window = Laby.labyAPI().minecraft().minecraftWindow();
        float guiScale = window.getScale();
        int guiScaleInt = MathHelper.ceil(guiScale);
        float rawHeight = window.getRawHeight();
        Vector3f topLeft = this.pose.transformPosition(this.bounds.getLeft(), this.bounds.getTop(), 0.0f, new Vector3f());
        Vector3f bottomRight = this.pose.transformPosition(this.bounds.getRight(), this.bounds.getBottom(), 0.0f, new Vector3f());
        float pxLeft = topLeft.x * guiScale;
        float pxTop = topLeft.y * guiScale;
        float pxRight = bottomRight.x * guiScale;
        float pxBottom = bottomRight.y * guiScale;
        int scissorX = (int) pxLeft;
        int scissorY = (int) (rawHeight - pxBottom);
        int scissorWidth = (int) Math.max(0.0f, pxRight - pxLeft);
        int scissorHeight = (int) Math.max(0.0f, pxBottom - pxTop);
        if (scissorWidth <= 0 || scissorHeight <= 0) {
            return;
        }
        cmd.setScissor(scissorX, scissorY, scissorWidth, scissorHeight);
        RenderTarget mainTarget = context.renderTarget();
        int targetWidth = mainTarget.width();
        int targetHeight = mainTarget.height();
        int boundsPixelHeight = MathHelper.ceil(this.bounds.getHeight() * guiScale);
        float translateY = this.renderer.getTranslateY(boundsPixelHeight, guiScaleInt);
        float pxCenterX = (pxLeft + pxRight) * 0.5f;
        float pxTranslateY = (targetHeight - pxBottom) + (translateY - boundsPixelHeight);
        Stack stack = Stack.create((StackProvider) new DefaultStackProvider());
        stack.translate(pxCenterX, pxTranslateY, 10000.0f);
        float stateScale = guiScale * this.scale;
        stack.scale(stateScale, -stateScale, -stateScale);
        try {
            RenderUtil.OffscreenScope ignored = RenderUtil.pushOffscreen(mainTarget, OFFSCREEN_PROJECTION_MATRIX.getCached(targetWidth, targetHeight), MODEL_VIEW_MATRIX);
            try {
                this.renderer.render(null, new OffscreenContext(cmd), stack);
                if (ignored != null) {
                    ignored.close();
                }
            } finally {
            }
        } finally {
            cmd.disableScissor();
        }
    }
}
