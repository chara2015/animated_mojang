package net.labymod.core.client.world.canvas;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.canvas.Canvas;
import net.labymod.api.client.world.canvas.CanvasMeta;
import net.labymod.api.client.world.canvas.CanvasRenderer;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.signobject.object.SignObject;
import net.labymod.api.client.world.signobject.object.SignObject3D;
import net.labymod.api.client.world.signobject.object.SignObject3DRenderer;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/canvas/DefaultCanvas.class */
public class DefaultCanvas extends SignObject3D<CanvasMeta> implements Canvas, SignObject3DRenderer<CanvasMeta> {
    private static final MutableMouse MOUSE = new MutableMouse(-1.0d, -1.0d);
    private static final float OVERLAY_Z_OFFSET = 0.3f;
    private final FloatVector3 renderOffset;
    private CanvasRenderer renderer;

    public DefaultCanvas(CanvasMeta meta, SignObjectPosition position) {
        super(meta, position);
        this.renderOffset = meta.template().renderOffset();
        set3DRenderer(this);
    }

    @Override // net.labymod.api.client.world.canvas.Canvas
    public CanvasRenderer renderer() {
        return this.renderer;
    }

    @Override // net.labymod.api.client.world.canvas.Canvas
    public void setRenderer(CanvasRenderer renderer) {
        this.renderer = renderer;
    }

    @Override // net.labymod.api.client.world.signobject.object.AbstractSignObject, net.labymod.api.client.world.signobject.object.SignObject
    public void dispose() {
        super.dispose();
        if (this.renderer != null) {
            this.renderer.dispose(this);
        }
    }

    @Override // net.labymod.api.client.world.signobject.object.SignObject3DRenderer
    public void render3D(Stack stack, SignObject<CanvasMeta> object, double x, double y, double z, float tickDelta) {
        if (this.renderer == null) {
            return;
        }
        stack.push();
        stack.translate(x + 0.5d, y, z + 0.5d);
        stack.rotate(object.position().rotationYaw(), 0.0f, 1.0f, 0.0f);
        stack.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        stack.translate(0.5f, 0.0f, 0.5f);
        Laby.references().renderEnvironmentContext().setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
        stack.translate(this.renderOffset.getX(), -this.renderOffset.getY(), this.renderOffset.getZ() + 0.042f);
        stack.scale(-0.06295f, 0.06295f, 0.06295f);
        stack.push();
        stack.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        stack.translate(((-getWidth()) / 2.0f) - 7.9411764f, 0.0f, 0.0f);
        wrapRender(CanvasRenderer.CanvasSide.BACK, () -> {
            this.renderer.render2D(stack, MOUSE, this, CanvasRenderer.CanvasSide.BACK, tickDelta);
        });
        stack.pop();
        stack.push();
        stack.translate(((-getWidth()) / 2.0f) + 7.9411764f, 0.0f, 0.0f);
        wrapRender(CanvasRenderer.CanvasSide.FRONT, () -> {
            this.renderer.render2D(stack, MOUSE, this, CanvasRenderer.CanvasSide.FRONT, tickDelta);
        });
        stack.pop();
        if (this.renderer.hasOverlay()) {
            stack.push();
            stack.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            stack.translate(((-getWidth()) / 2.0f) - 7.9411764f, 0.0f, OVERLAY_Z_OFFSET);
            wrapRender(CanvasRenderer.CanvasSide.BACK, () -> {
                this.renderer.renderOverlay2D(stack, MOUSE, this, CanvasRenderer.CanvasSide.BACK, tickDelta);
            });
            stack.pop();
            stack.push();
            stack.translate(((-getWidth()) / 2.0f) + 7.9411764f, 0.0f, OVERLAY_Z_OFFSET);
            wrapRender(CanvasRenderer.CanvasSide.FRONT, () -> {
                this.renderer.renderOverlay2D(stack, MOUSE, this, CanvasRenderer.CanvasSide.FRONT, tickDelta);
            });
            stack.pop();
        }
        stack.pop();
    }

    private void wrapRender(CanvasRenderer.CanvasSide side, Runnable runnable) {
        Laby.labyAPI().gfxRenderPipeline().overlappingTranslator().translateOverlappingElements((object, matrix) -> {
            matrix.translate(0.0f, 0.0f, 0.01f);
        }, runnable);
    }
}
