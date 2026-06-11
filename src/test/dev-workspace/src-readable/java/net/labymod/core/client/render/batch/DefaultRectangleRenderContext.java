package net.labymod.core.client.render.batch;

import java.awt.Color;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.client.render.batch.RenderContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.models.Implements;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/batch/DefaultRectangleRenderContext.class */
@Singleton
@Implements(RectangleRenderContext.class)
public class DefaultRectangleRenderContext extends DefaultRenderContext<RectangleRenderContext> implements RectangleRenderContext {
    private final Laby3D laby3D = Laby.references().laby3D();
    private BufferBuilder builder;
    private Stack stack;

    @Inject
    public DefaultRectangleRenderContext() {
    }

    @Override // net.labymod.api.client.render.batch.RectangleRenderContext
    public RectangleRenderContext begin(Stack stack) {
        this.stack = stack;
        this.builder = this.laby3D.begin(DrawingMode.QUADS, VertexDescriptions.POSITION_UV_COLOR);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.RectangleRenderContext
    public RectangleRenderContext render(float left, float top, float right, float bottom, float red, float green, float blue, float alpha) {
        setupVertexBuilder(left, top, right, bottom, alpha, red, green, blue);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.RectangleRenderContext
    public RectangleRenderContext renderGradientHorizontally(float left, float top, float right, float bottom, int leftColor, int rightColor) {
        ColorFormat colorFormat = ColorFormat.ARGB32;
        return renderGradientHorizontally(left, top, right, bottom, colorFormat.normalizedAlpha(leftColor), colorFormat.normalizedRed(leftColor), colorFormat.normalizedGreen(leftColor), colorFormat.normalizedBlue(leftColor), colorFormat.normalizedAlpha(rightColor), colorFormat.normalizedRed(rightColor), colorFormat.normalizedGreen(rightColor), colorFormat.normalizedBlue(rightColor));
    }

    @Override // net.labymod.api.client.render.batch.RectangleRenderContext
    public RectangleRenderContext renderGradientHorizontally(float left, float top, float right, float bottom, float leftAlpha, float leftRed, float leftGreen, float leftBlue, float rightAlpha, float rightRed, float rightGreen, float rightBlue) {
        setupGradientVertexBuilderHorizontally(left, top, right, bottom, leftAlpha, leftRed, leftGreen, leftBlue, rightAlpha, rightRed, rightGreen, rightBlue);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.RectangleRenderContext
    public RectangleRenderContext renderGradientVertically(float left, float top, float right, float bottom, int topColor, int bottomColor) {
        ColorFormat colorFormat = ColorFormat.ARGB32;
        return renderGradientVertically(left, top, right, bottom, colorFormat.normalizedAlpha(topColor), colorFormat.normalizedRed(topColor), colorFormat.normalizedGreen(topColor), colorFormat.normalizedBlue(topColor), colorFormat.normalizedAlpha(bottomColor), colorFormat.normalizedRed(bottomColor), colorFormat.normalizedGreen(bottomColor), colorFormat.normalizedBlue(bottomColor));
    }

    @Override // net.labymod.api.client.render.batch.RectangleRenderContext
    public RectangleRenderContext renderGradientVertically(float left, float top, float right, float bottom, float topAlpha, float topRed, float topGreen, float topBlue, float bottomAlpha, float bottomRed, float bottomGreen, float bottomBlue) {
        setupGradientVertexBuilderVertically(left, top, right, bottom, topAlpha, topRed, topGreen, topBlue, bottomAlpha, bottomRed, bottomGreen, bottomBlue);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.RectangleRenderContext
    public RectangleRenderContext renderGradient(float left, float top, float right, float bottom, float leftTopAlpha, float leftTopRed, float leftTopGreen, float leftTopBlue, float rightTopAlpha, float rightTopRed, float rightTopGreen, float rightTopBlue, float leftBottomAlpha, float leftBottomRed, float leftBottomGreen, float leftBottomBlue, float rightBottomAlpha, float rightBottomRed, float rightBottomGreen, float rightBottomBlue) {
        setupGradientVertexBuilder(left, top, right, bottom, leftTopAlpha, leftTopRed, leftTopGreen, leftTopBlue, rightTopAlpha, rightTopRed, rightTopGreen, rightTopBlue, leftBottomAlpha, leftBottomRed, leftBottomGreen, leftBottomBlue, rightBottomAlpha, rightBottomRed, rightBottomGreen, rightBottomBlue);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.RectangleRenderContext
    public RectangleRenderContext render(float left, float top, float right, float bottom, int color) {
        ColorFormat colorFormat = ColorFormat.ARGB32;
        float alpha = colorFormat.normalizedAlpha(color);
        float red = colorFormat.normalizedRed(color);
        float green = colorFormat.normalizedGreen(color);
        float blue = colorFormat.normalizedBlue(color);
        setupVertexBuilder(left, top, right, bottom, alpha, red, green, blue);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.RectangleRenderContext
    public RectangleRenderContext render(float left, float top, float right, float bottom, Color color) {
        float alpha = color.getAlpha() / 255.0f;
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        setupVertexBuilder(left, top, right, bottom, alpha, red, green, blue);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.RectangleRenderContext
    public RectangleRenderContext renderOutline(float left, float top, float right, float bottom, float thickness, int innerColor, int outerColor) {
        Matrix4f pose = this.stack.getProvider().getPose();
        float zOffset = this.zOffset;
        this.builder.addVertex(pose, left - thickness, bottom + thickness, zOffset).setBlankUv().setColor(outerColor).addVertex(pose, left, bottom, zOffset).setBlankUv().setColor(innerColor).addVertex(pose, left, top, zOffset).setBlankUv().setColor(innerColor).addVertex(pose, left - thickness, top - thickness, zOffset).setBlankUv().setColor(outerColor);
        this.builder.addVertex(pose, right, bottom, zOffset).setBlankUv().setColor(innerColor).addVertex(pose, right + thickness, bottom + thickness, zOffset).setBlankUv().setColor(outerColor).addVertex(pose, right + thickness, top - thickness, zOffset).setBlankUv().setColor(outerColor).addVertex(pose, right, top, zOffset).setBlankUv().setColor(innerColor);
        this.builder.addVertex(pose, left, top, zOffset).setBlankUv().setColor(innerColor).addVertex(pose, right, top, zOffset).setBlankUv().setColor(innerColor).addVertex(pose, right + thickness, top - thickness, zOffset).setBlankUv().setColor(outerColor).addVertex(pose, left - thickness, top - thickness, zOffset).setBlankUv().setColor(outerColor);
        this.builder.addVertex(pose, left - thickness, bottom + thickness, zOffset).setBlankUv().setColor(outerColor).addVertex(pose, right + thickness, bottom + thickness, zOffset).setBlankUv().setColor(outerColor).addVertex(pose, right, bottom, zOffset).setBlankUv().setColor(innerColor).addVertex(pose, left, bottom, zOffset).setBlankUv().setColor(innerColor);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.RenderContext
    public RenderContext<RectangleRenderContext> uploadToBuffer(RenderState renderState) {
        drawImmediate(this.builder, renderState);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.RenderContext
    public RenderContext<RectangleRenderContext> uploadToBuffer(RenderState renderState, Consumer<CommandBuffer> commandConsumer) {
        drawImmediate(this.builder, renderState, commandConsumer);
        return this;
    }

    private void setupVertexBuilder(float left, float top, float right, float bottom, float alpha, float red, float green, float blue) {
        if (left < right) {
            left = right;
            right = left;
        }
        if (top < bottom) {
            top = bottom;
            bottom = top;
        }
        Matrix4f pose = this.stack.getProvider().getPose();
        float offset = this.zOffset;
        this.builder.addVertex(pose, left, bottom, offset).setBlankUv().setColor(red, green, blue, alpha);
        this.builder.addVertex(pose, right, bottom, offset).setBlankUv().setColor(red, green, blue, alpha);
        this.builder.addVertex(pose, right, top, offset).setBlankUv().setColor(red, green, blue, alpha);
        this.builder.addVertex(pose, left, top, offset).setBlankUv().setColor(red, green, blue, alpha);
    }

    private void setupGradientVertexBuilderHorizontally(float left, float top, float right, float bottom, float leftAlpha, float leftRed, float leftGreen, float leftBlue, float rightAlpha, float rightRed, float rightGreen, float rightBlue) {
        Matrix4f pose = this.stack.getProvider().getPose();
        float offset = this.zOffset;
        this.builder.addVertex(pose, left, bottom, offset).setBlankUv().setColor(leftRed, leftGreen, leftBlue, leftAlpha);
        this.builder.addVertex(pose, right, bottom, offset).setBlankUv().setColor(rightRed, rightGreen, rightBlue, rightAlpha);
        this.builder.addVertex(pose, right, top, offset).setBlankUv().setColor(rightRed, rightGreen, rightBlue, rightAlpha);
        this.builder.addVertex(pose, left, top, offset).setBlankUv().setColor(leftRed, leftGreen, leftBlue, leftAlpha);
    }

    private void setupGradientVertexBuilderVertically(float left, float top, float right, float bottom, float topAlpha, float topRed, float topGreen, float topBlue, float bottomAlpha, float bottomRed, float bottomGreen, float bottomBlue) {
        Matrix4f pose = this.stack.getProvider().getPose();
        float offset = this.zOffset;
        this.builder.addVertex(pose, left, bottom, offset).setBlankUv().setColor(bottomRed, bottomGreen, bottomBlue, bottomAlpha);
        this.builder.addVertex(pose, right, bottom, offset).setBlankUv().setColor(bottomRed, bottomGreen, bottomBlue, bottomAlpha);
        this.builder.addVertex(pose, right, top, offset).setBlankUv().setColor(topRed, topGreen, topBlue, topAlpha);
        this.builder.addVertex(pose, left, top, offset).setBlankUv().setColor(topRed, topGreen, topBlue, topAlpha);
    }

    private void setupGradientVertexBuilder(float left, float top, float right, float bottom, float leftTopAlpha, float leftTopRed, float leftTopGreen, float leftTopBlue, float rightTopAlpha, float rightTopRed, float rightTopGreen, float rightTopBlue, float leftBottomAlpha, float leftBottomRed, float leftBottomGreen, float leftBottomBlue, float rightBottomAlpha, float rightBottomRed, float rightBottomGreen, float rightBottomBlue) {
        Matrix4f pose = this.stack.getProvider().getPose();
        float offset = this.zOffset;
        this.builder.addVertex(pose, left, bottom, offset).setBlankUv().setColor(leftBottomRed, leftBottomGreen, leftBottomBlue, leftBottomAlpha);
        this.builder.addVertex(pose, right, bottom, offset).setBlankUv().setColor(rightBottomRed, rightBottomGreen, rightBottomBlue, rightBottomAlpha);
        this.builder.addVertex(pose, right, top, offset).setBlankUv().setColor(rightTopRed, rightTopGreen, rightTopBlue, rightTopAlpha);
        this.builder.addVertex(pose, left, top, offset).setBlankUv().setColor(leftTopRed, leftTopGreen, leftTopBlue, leftTopAlpha);
    }
}
