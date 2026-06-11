package net.labymod.api.client.render.draw.batch;

import net.labymod.api.client.render.draw.builder.RectangleBuilder;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/batch/BatchRectangleRenderer.class */
@Referenceable
public interface BatchRectangleRenderer extends BatchRenderer<BatchRectangleRenderer>, RectangleBuilder<BatchRectangleRenderer> {
    BatchRectangleRenderer beginBatch(Stack stack);

    BatchRectangleRenderer outline(int i, int i2, float f);

    void zOffset(float f);

    BatchRectangleRenderer build(float f, float f2, float f3, float f4, int i);

    @Deprecated
    default BatchRectangleRenderer renderOutline(float left, float top, float right, float bottom, int outerColor, int innerColor) {
        return pos(left, top, right, bottom).outline(outerColor, innerColor, 1.0f);
    }

    @Deprecated
    default BatchRectangleRenderer renderOutline(float left, float top, float right, float bottom, int outerColor, int innerColor, float thickness) {
        return pos(left, top, right, bottom).outline(outerColor, innerColor, thickness);
    }
}
