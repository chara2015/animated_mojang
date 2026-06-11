package net.labymod.api.util.bounds.area;

import net.labymod.api.util.bounds.DefaultRectangle;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/bounds/area/RectangleArea.class */
public class RectangleArea extends DefaultRectangle {
    private final RectangleAreaPosition position;

    public RectangleArea(@NotNull RectangleAreaPosition position) {
        this.position = position;
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public boolean isXInRectangle(float x) {
        return x >= getLeft() && x <= getRight();
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public boolean isYInRectangle(float y) {
        return y >= getTop() && y <= getBottom();
    }

    @NotNull
    public RectangleAreaPosition position() {
        return this.position;
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public FloatVector2 absoluteToRelative(float x, float y) {
        FloatVector2 anchorPoint = this.position.anchorPoint(this);
        return new FloatVector2(x - anchorPoint.getX(), y - anchorPoint.getY());
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public FloatVector2 relativeToAbsolute(float x, float y) {
        FloatVector2 anchorPoint = this.position.anchorPoint(this);
        return new FloatVector2(x + anchorPoint.getX(), y + anchorPoint.getY());
    }
}
