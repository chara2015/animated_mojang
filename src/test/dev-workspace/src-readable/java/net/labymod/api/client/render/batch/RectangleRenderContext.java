package net.labymod.api.client.render.batch;

import java.awt.Color;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/batch/RectangleRenderContext.class */
@Referenceable
public interface RectangleRenderContext extends RenderContext<RectangleRenderContext> {
    RectangleRenderContext begin(Stack stack);

    RectangleRenderContext render(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8);

    RectangleRenderContext renderGradientHorizontally(float f, float f2, float f3, float f4, int i, int i2);

    RectangleRenderContext renderGradientHorizontally(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12);

    RectangleRenderContext renderGradientVertically(float f, float f2, float f3, float f4, int i, int i2);

    RectangleRenderContext renderGradientVertically(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12);

    RectangleRenderContext renderGradient(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17, float f18, float f19, float f20);

    RectangleRenderContext render(float f, float f2, float f3, float f4, int i);

    RectangleRenderContext render(float f, float f2, float f3, float f4, Color color);

    RectangleRenderContext renderOutline(float f, float f2, float f3, float f4, float f5, int i, int i2);

    default RectangleRenderContext renderGradientHorizontally(Rectangle rectangle, int leftColor, int rightColor) {
        return renderGradientHorizontally(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), leftColor, rightColor);
    }

    default RectangleRenderContext renderGradientVertically(Rectangle rectangle, int topColor, int bottomColor) {
        return renderGradientVertically(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), topColor, bottomColor);
    }

    default RectangleRenderContext render(Rectangle rectangle, int color) {
        return render(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), color);
    }

    default RectangleRenderContext renderBackgroundGradient(float left, float top, float right, float bottom) {
        return renderGradientVertically(left, top, right, bottom, -1072689136, -804253680);
    }

    default RectangleRenderContext uploadToBuffer() {
        uploadToBuffer(RenderStates.GUI);
        return this;
    }
}
