package net.labymod.api.client.gui.screen.state.offscreen;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.offscreen.OffscreenRenderState;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.debug.DebugFlags;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.laby3d.util.matrix.CachedOrthoProjectionMatrix;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.laby3d.api.pipeline.DepthConventionHolder;
import net.labymod.laby3d.api.pipeline.LoadOp;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.pipeline.target.RenderTargetDescription;
import net.labymod.laby3d.api.pipeline.target.attachment.AttachmentType;
import net.labymod.laby3d.api.pipeline.target.attachment.ClearValue;
import net.labymod.laby3d.api.pipeline.target.attachment.RenderTargetAttachmentDescription;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/offscreen/OffscreenRenderer.class */
public abstract class OffscreenRenderer<T extends OffscreenRenderState> {
    private static final CachedOrthoProjectionMatrix<Matrix4f> OFFSCREEN_PROJECTION_MATRIX = CachedOrthoProjectionMatrix.simple(0.5f, 11000.0f, false);
    private static final Matrix4f MODEL_VIEW_MATRIX = new Matrix4f().setTranslation(0.0f, 0.0f, -11000.0f);
    protected final Laby3D laby3D = Laby.references().laby3D();
    private final String name = getClass().getSimpleName();
    private RenderTarget renderTarget;

    protected abstract void renderToTexture(OffscreenContext offscreenContext, T t, Stack stack);

    public void prepareAll(ScreenCanvas canvas, List<T> states, float guiScale) {
        for (T state : states) {
            prepare(canvas, state, guiScale);
        }
    }

    public void close() {
        if (this.renderTarget != null) {
            this.renderTarget.close();
            this.renderTarget = null;
        }
    }

    public void prepare(ScreenCanvas canvas, T t, float guiScale) {
        Rectangle bounds = t.bounds();
        int width = MathHelper.ceil(bounds.getWidth() * guiScale);
        int height = MathHelper.ceil(bounds.getHeight() * guiScale);
        if (requiresPreparation(width, height)) {
            if (this.renderTarget != null) {
                this.renderTarget.close();
            }
            this.renderTarget = createStandardTarget(this.name, VertexDescriptions.COLOR_NAME, "Depth", width, height);
        }
        this.renderTarget.clear(true);
        Stack stack = Stack.create((StackProvider) new DefaultStackProvider());
        stack.translate(width / 2.0f, getTranslateY(t, height, MathHelper.ceil(guiScale)), 10000.0f);
        float stateScale = guiScale * t.getScale();
        stack.scale(stateScale, stateScale, -stateScale);
        CommandBuffer cmd = this.laby3D.renderDevice().createCommandBuffer(1);
        try {
            RenderUtil.OffscreenScope ignored = RenderUtil.pushOffscreen(this.renderTarget, OFFSCREEN_PROJECTION_MATRIX.getCached(width, height), MODEL_VIEW_MATRIX);
            try {
                cmd.beginPass(this.renderTarget, LoadOp.LOAD);
                renderToTexture(new OffscreenContext(cmd), t, stack);
                cmd.endPass();
                cmd.submit();
                if (ignored != null) {
                    ignored.close();
                }
                if (cmd != null) {
                    cmd.close();
                }
                blitTexture(canvas, t);
            } finally {
            }
        } catch (Throwable th) {
            if (cmd != null) {
                try {
                    cmd.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    protected void blitTexture(ScreenCanvas canvas, T t) {
        RenderTarget pooled = this.laby3D.renderTargetPool().acquire(this.renderTarget);
        this.laby3D.copyTextureToTexture(this.renderTarget, pooled, false);
        blitSlice(canvas, t, pooled, 0.0f, 0.0f, 1.0f, 1.0f);
        if (DebugFlags.VISUAL_OFFSCREEN_RENDERER_AREA) {
            Rectangle bounds = t.bounds();
            float left = bounds.getLeft() + 1.0f;
            float top = bounds.getTop() + 1.0f;
            float right = bounds.getRight() - 1.0f;
            float bottom = bounds.getBottom() - 1.0f;
            canvas.submitAbsoluteOutlineRect(left, top, right, bottom, 1.0f, -2005436417, -2005436417);
            canvas.submitAbsoluteRect(left, top, right, bottom, 1148680191);
        }
    }

    protected void blitSlice(ScreenCanvas canvas, OffscreenRenderState state, RenderTarget source, float minU, float minV, float maxU, float maxV) {
        RenderState renderState;
        canvas.setPose(state.pose());
        Rectangle bounds = state.bounds();
        RoundedData roundedData = state.getRoundedData();
        if (roundedData == null) {
            renderState = RenderStates.GUI_TEXTURED_PREMULTIPLIED_ALPHA;
        } else {
            renderState = RenderStates.GUI_TEXTURED_PREMULTIPLIED_ALPHA_ROUNDED;
        }
        RenderState renderState2 = renderState;
        canvas.submitGuiBlit(GuiMaterial.textured(renderState2, source.findColorTexture(0)), bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), minU, minV, maxU, maxV, -1, roundedData, state.getScissorArea());
        canvas.setPose(null);
    }

    protected RenderTarget createStandardTarget(String name, String colorName, String depthName, int width, int height) {
        return this.laby3D.renderDevice().createTarget(name, RenderTargetDescription.builder().addColorAttachment(RenderTargetAttachmentDescription.builder().setName(colorName).setClearValue(ClearValue.color(0.0f, 0.0f, 0.0f, 0.0f)).setFormat(DeviceTexture.Format.R8G8B8A8_UNORM).setType(AttachmentType.COLOR).setSamplerDescription(builder -> {
            builder.setFilter(SamplerDescription.Filter.LINEAR);
        }).build()).setDepthAttachment(RenderTargetAttachmentDescription.builder().setName(depthName).setClearValue(ClearValue.depth(DepthConventionHolder.get().clearDepth())).setFormat(DeviceTexture.Format.D32_FLOAT).setType(AttachmentType.DEPTH).setSamplerDescription(builder2 -> {
            builder2.setFilter(SamplerDescription.Filter.LINEAR);
        }).build()).setSize(width, height).build());
    }

    protected float getTranslateY(T t, int height, int guiScale) {
        return height;
    }

    private boolean requiresPreparation(int width, int height) {
        return (this.renderTarget != null && this.renderTarget.width() == width && this.renderTarget.height() == height) ? false : true;
    }
}
