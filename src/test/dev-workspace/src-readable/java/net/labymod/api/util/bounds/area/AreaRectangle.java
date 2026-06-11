package net.labymod.api.util.bounds.area;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.util.bounds.MutableRectangle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/bounds/area/AreaRectangle.class */
public class AreaRectangle implements MutableRectangle {
    private final Map<RectangleAreaPosition, RectangleArea> areas = new HashMap();
    private float left;
    private float top;
    private float right;
    private float bottom;

    public AreaRectangle() {
        updateAreaBounds();
    }

    @NotNull
    public RectangleArea getArea(RectangleAreaPosition position) {
        return this.areas.get(position);
    }

    @Nullable
    public RectangleArea getArea(float x, float y) {
        for (RectangleArea area : this.areas.values()) {
            if (area.isInRectangle(x, y)) {
                return area;
            }
        }
        return null;
    }

    public Collection<RectangleArea> getAreas() {
        return Collections.unmodifiableCollection(this.areas.values());
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getLeft() {
        return this.left;
    }

    @Override // net.labymod.api.util.bounds.MutableRectangle
    public void setLeft(float left) {
        this.left = left;
        updateAreaBounds();
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getTop() {
        return this.top;
    }

    @Override // net.labymod.api.util.bounds.MutableRectangle
    public void setTop(float top) {
        this.top = top;
        updateAreaBounds();
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getRight() {
        return this.right;
    }

    @Override // net.labymod.api.util.bounds.MutableRectangle
    public void setRight(float right) {
        this.right = right;
        updateAreaBounds();
    }

    @Override // net.labymod.api.util.bounds.Rectangle
    public float getBottom() {
        return this.bottom;
    }

    @Override // net.labymod.api.util.bounds.MutableRectangle
    public void setBottom(float bottom) {
        this.bottom = bottom;
        updateAreaBounds();
    }

    private void updateAreaBounds() {
        for (int yIndex = 0; yIndex < 3; yIndex++) {
            for (int xIndex = 0; xIndex < 3; xIndex++) {
                RectangleAreaPosition position = RectangleAreaPosition.getPosition(xIndex, yIndex);
                if (position != null) {
                    float areaWidth = getWidth() / 3.0f;
                    float areaHeight = getHeight() / 3.0f;
                    float areaX = getX() + (areaWidth * xIndex);
                    float areaY = getY() + (areaHeight * yIndex);
                    RectangleArea area = this.areas.computeIfAbsent(position, RectangleArea::new);
                    area.setBounds(areaX, areaY, areaX + areaWidth, areaY + areaHeight);
                }
            }
        }
    }
}
