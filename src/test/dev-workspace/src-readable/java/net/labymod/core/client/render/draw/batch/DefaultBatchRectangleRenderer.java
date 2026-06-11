package net.labymod.core.client.render.draw.batch;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.models.Implements;
import net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder;
import net.labymod.laby3d.api.pipeline.RenderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/batch/DefaultBatchRectangleRenderer.class */
@Singleton
@Implements(BatchRectangleRenderer.class)
public class DefaultBatchRectangleRenderer extends DefaultRectangleBuilder<BatchRectangleRenderer> implements BatchRectangleRenderer {
    private final RectangleRenderContext rectangleRenderContext;

    @Inject
    public DefaultBatchRectangleRenderer(RectangleRenderContext rectangleRenderContext) {
        this.rectangleRenderContext = rectangleRenderContext;
    }

    @Override // net.labymod.api.client.render.draw.batch.BatchRectangleRenderer
    public BatchRectangleRenderer beginBatch(Stack stack) {
        this.rectangleRenderContext.begin(stack);
        return this;
    }

    @Override // net.labymod.api.client.render.draw.batch.BatchRectangleRenderer
    public BatchRectangleRenderer outline(int outerColor, int innerColor, float thickness) {
        this.rectangleRenderContext.renderOutline(this.x, this.y, this.x + this.width, this.y + this.height, thickness, outerColor, innerColor);
        return this;
    }

    @Override // net.labymod.api.client.render.draw.batch.BatchRenderer
    public void upload() {
        this.rectangleRenderContext.uploadToBuffer();
        this.rectangleRenderContext.zOffset(0.0f);
    }

    @Override // net.labymod.api.client.render.draw.batch.BatchRenderer
    public void upload(RenderState renderState) {
        this.rectangleRenderContext.uploadToBuffer(renderState);
        this.rectangleRenderContext.zOffset(0.0f);
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder, net.labymod.api.client.render.draw.builder.RectangleBuilder
    public BatchRectangleRenderer rounded(float leftTopRadius, float rightTopRadius, float leftBottomRadius, float rightBottomRadius) {
        throw new UnsupportedOperationException("Cannot render rounded rectangles with the MultiRectangleRenderer");
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder, net.labymod.api.client.render.draw.builder.RectangleBuilder
    public BatchRectangleRenderer lowerEdgeSoftness(float softness) {
        throw new UnsupportedOperationException("Cannot render rounded rectangles with the MultiRectangleRenderer");
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder, net.labymod.api.client.render.draw.builder.RectangleBuilder
    public BatchRectangleRenderer upperEdgeSoftness(float softness) {
        throw new UnsupportedOperationException("Cannot render rounded rectangles with the MultiRectangleRenderer");
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder, net.labymod.api.client.render.draw.builder.RectangleBuilder
    public BatchRectangleRenderer borderThickness(float thickness) {
        throw new UnsupportedOperationException("Cannot render rounded rectangles with the MultiRectangleRenderer");
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder, net.labymod.api.client.render.draw.builder.RectangleBuilder
    public BatchRectangleRenderer borderSoftness(float softness) {
        throw new UnsupportedOperationException("Cannot render rounded rectangles with the MultiRectangleRenderer");
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder, net.labymod.api.client.render.draw.builder.RectangleBuilder
    public BatchRectangleRenderer borderColor(int color) {
        throw new UnsupportedOperationException("Cannot render rounded rectangles with the MultiRectangleRenderer");
    }

    @Override // net.labymod.api.client.render.draw.batch.BatchRenderer
    public BatchRectangleRenderer build() {
        validateBuilder();
        if (this.topColor.isSet()) {
            this.rectangleRenderContext.renderGradientVertically(this.x, this.y, this.x + this.width, this.y + this.height, this.topColor.getValue(), this.bottomColor.getValue());
        } else if (this.leftColor.isSet()) {
            this.rectangleRenderContext.renderGradientHorizontally(this.x, this.y, this.x + this.width, this.y + this.height, this.leftColor.getValue(), this.rightColor.getValue());
        } else {
            this.rectangleRenderContext.render(this.x, this.y, this.x + this.width, this.y + this.height, this.color);
        }
        resetBuilder();
        return this;
    }

    @Override // net.labymod.api.client.render.draw.batch.BatchRectangleRenderer
    public BatchRectangleRenderer build(float left, float top, float right, float bottom, int color) {
        this.rectangleRenderContext.render(left, top, right, bottom, color);
        return this;
    }

    @Override // net.labymod.api.client.render.draw.batch.BatchRectangleRenderer
    public void zOffset(float offset) {
        this.rectangleRenderContext.zOffset(offset);
    }
}
