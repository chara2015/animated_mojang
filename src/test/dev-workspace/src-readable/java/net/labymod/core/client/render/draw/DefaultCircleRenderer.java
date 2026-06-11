package net.labymod.core.client.render.draw;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.client.render.draw.CircleRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.client.render.draw.builder.DefaultCircleBuilder;
import net.labymod.laby3d.api.pipeline.RenderState;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/DefaultCircleRenderer.class */
@Singleton
@Implements(CircleRenderer.class)
public class DefaultCircleRenderer extends DefaultCircleBuilder<CircleRenderer> implements CircleRenderer {
    private static final boolean CUSTOM_MODEL_VIEW_MATRIX = MinecraftVersions.V1_13.orNewer();
    private static final float CIRCLE_SEGMENT_STEP = 0.0027777778f;
    private static final float DEFAULT_STARTING_ANGLE = 0.5f;
    private final RectangleRenderContext context;

    @Inject
    public DefaultCircleRenderer(RectangleRenderContext rectangleRenderContext) {
        this.context = rectangleRenderContext;
    }

    @Override // net.labymod.api.client.render.draw.builder.DirectRendererBuilder
    public void render(Stack stack) {
        render(stack, RenderStates.CIRCLE);
    }

    @Override // net.labymod.api.client.render.draw.builder.DirectRendererBuilder
    public void render(Stack stack, RenderState renderState) {
        validateBuilder();
        renderCircle(stack, renderState);
        resetBuilder();
    }

    private void renderCircle(Stack stack, RenderState renderState) {
        Matrix4f pose = stack.getProvider().getPose();
        Blaze3DGlStatePipeline glStatePipeline = Laby.gfx().blaze3DGlStatePipeline();
        applyMatrix(stack, glStatePipeline, pose);
        this.context.begin(stack);
        FloatVector3 transform = stack.transformVector(this.x, this.y, 0.0f);
        transform.getX();
        transform.getY();
        float radius = this.outerRadius;
        this.context.render(this.x - radius, this.y - radius, this.x + radius, this.y + radius, this.color);
        this.context.uploadToBuffer(renderState, command -> {
        });
        resetMatrix(stack, glStatePipeline);
    }

    private void applyMatrix(Stack stack, Blaze3DGlStatePipeline glStatePipeline, Matrix4f pose) {
        if (!CUSTOM_MODEL_VIEW_MATRIX) {
            return;
        }
        stack.push();
        stack.identity();
        Stack modelViewMatrix = glStatePipeline.getModelViewStack();
        modelViewMatrix.push();
        modelViewMatrix.mul(pose);
        glStatePipeline.applyModelViewMatrix();
    }

    private void resetMatrix(Stack stack, Blaze3DGlStatePipeline glStatePipeline) {
        if (!CUSTOM_MODEL_VIEW_MATRIX) {
            return;
        }
        Stack modelViewMatrix = glStatePipeline.getModelViewStack();
        modelViewMatrix.pop();
        glStatePipeline.applyModelViewMatrix();
        stack.pop();
    }
}
