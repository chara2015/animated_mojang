package net.labymod.api.client.render.draw;

import java.awt.Color;
import java.util.function.Consumer;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.render.draw.builder.DirectRendererBuilder;
import net.labymod.api.client.render.draw.builder.RectangleBuilder;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/RectangleRenderer.class */
@Referenceable
public interface RectangleRenderer extends RectangleBuilder<RectangleRenderer>, DirectRendererBuilder {
    BatchRectangleRenderer beginBatch(Stack stack);

    void renderOutline(Stack stack, float f, float f2, float f3, float f4, int i, int i2, float f5);

    void renderRectangle(Stack stack, Rectangle rectangle, int i);

    void renderRectangle(Stack stack, Rectangle rectangle, Color color);

    void renderRectangle(Stack stack, float f, float f2, float f3, float f4, int i);

    void renderRectangle(Stack stack, float f, float f2, float f3, float f4, Color color);

    void setupGradient(Stack stack, Consumer<RectangleRenderContext> consumer);

    void setupSimple(Consumer<RectangleRenderContext> consumer);

    default void renderVerticalLine(Stack stack, float x, float y, float size, int color) {
        if (size < y) {
            y = size;
            size = y;
        }
        renderRectangle(stack, x, y + 1.0f, x + 1.0f, size, color);
    }

    default void renderHorizontalLine(Stack stack, float x, float y, float size, int color) {
        if (size < x) {
            x = size;
            size = x;
        }
        renderRectangle(stack, x, size, y + 1.0f, size + 1.0f, color);
    }

    default void renderOutline(Stack stack, Rectangle rectangle, int color, float thickness) {
        renderOutline(stack, rectangle, color, color, thickness);
    }

    default void renderOutline(Stack stack, Rectangle rectangle, int outerColor, int innerColor, float thickness) {
        renderOutline(stack, rectangle.getX(), rectangle.getY(), rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight(), outerColor, innerColor, thickness);
    }

    default void renderOutline(Stack stack, float left, float top, float right, float bottom, int color, float thickness) {
        renderOutline(stack, left, top, right, bottom, color, color, thickness);
    }
}
