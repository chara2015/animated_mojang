package net.labymod.api.client.gui.screen.widget.util;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.math.vector.FloatVector2;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/util/WidgetTransformer.class */
public class WidgetTransformer {
    private final AbstractWidget<?> widget;
    private final FloatVector2 pivot = new FloatVector2();

    public WidgetTransformer(AbstractWidget<?> widget) {
        this.widget = widget;
    }

    public float transformMouseX(float mouseX, float pivotX) {
        return ((mouseX - (this.widget.translateX().get().floatValue() + pivotX)) / this.widget.scaleX().get().floatValue()) + pivotX;
    }

    public float transformMouseY(float mouseY, float pivotY) {
        return ((mouseY - (this.widget.translateY().get().floatValue() + pivotY)) / this.widget.scaleY().get().floatValue()) + pivotY;
    }

    public FloatVector2 getPivot() {
        Bounds bounds = this.widget.bounds();
        this.pivot.set(getAlignmentX(bounds), getAlignmentY(bounds));
        return this.pivot;
    }

    public void applyStackManipulation(ScreenContext context, float x, float y, FloatVector2 pivot) {
        float offsetX = pivot.getX();
        float offsetY = pivot.getY();
        context.translate(this.widget.translateX().get().floatValue() + x, this.widget.translateY().get().floatValue() + y, this.widget.zIndex().get().floatValue());
        context.translate(offsetX, offsetY, 0.0f);
        context.scale(this.widget.scaleX().get().floatValue(), this.widget.scaleY().get().floatValue(), 1.0f);
        context.translate(-offsetX, -offsetY, 0.0f);
        context.translate(-x, -y, 0.0f);
    }

    private float getAlignmentX(Bounds bounds) {
        switch (this.widget.alignmentX().get()) {
            case CENTER:
                return bounds.getWidth(BoundsType.OUTER) / 2.0f;
            case RIGHT:
                return bounds.getWidth(BoundsType.OUTER);
            default:
                return 0.0f;
        }
    }

    private float getAlignmentY(Bounds bounds) {
        switch (this.widget.alignmentY().get()) {
            case CENTER:
                return bounds.getHeight(BoundsType.OUTER) / 2.0f;
            case BOTTOM:
                return bounds.getHeight(BoundsType.OUTER);
            default:
                return 0.0f;
        }
    }
}
