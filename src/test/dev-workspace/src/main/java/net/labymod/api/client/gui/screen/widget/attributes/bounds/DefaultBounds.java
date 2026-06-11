package net.labymod.api.client.gui.screen.widget.attributes.bounds;

import net.labymod.api.util.bounds.DefaultRectangle;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.util.bounds.RectangleState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/bounds/DefaultBounds.class */
public interface DefaultBounds extends Bounds {
    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setOuterWidth(float width, ModifyReason reason) {
        setWidth((width - getOffset(BoundsType.OUTER, OffsetSide.LEFT)) - getOffset(BoundsType.OUTER, OffsetSide.RIGHT), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setOuterHeight(float height, ModifyReason reason) {
        setHeight((height - getOffset(BoundsType.OUTER, OffsetSide.TOP)) - getOffset(BoundsType.OUTER, OffsetSide.BOTTOM), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setMiddleWidth(float width, ModifyReason reason) {
        setWidth((width - getOffset(BoundsType.MIDDLE, OffsetSide.LEFT)) - getOffset(BoundsType.MIDDLE, OffsetSide.RIGHT), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setMiddleHeight(float height, ModifyReason reason) {
        setHeight((height - getOffset(BoundsType.MIDDLE, OffsetSide.TOP)) - getOffset(BoundsType.MIDDLE, OffsetSide.BOTTOM), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setOuterX(float x, ModifyReason reason) {
        setX(x + getOffset(BoundsType.OUTER, OffsetSide.LEFT), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setOuterY(float y, ModifyReason reason) {
        setY(y + getOffset(BoundsType.OUTER, OffsetSide.TOP), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setOuterLeft(float left, ModifyReason reason) {
        setLeft(left + getOffset(BoundsType.OUTER, OffsetSide.LEFT), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setOuterTop(float top, ModifyReason reason) {
        setTop(top + getOffset(BoundsType.OUTER, OffsetSide.TOP), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setOuterRight(float right, ModifyReason reason) {
        setRight(right - getOffset(BoundsType.OUTER, OffsetSide.RIGHT), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setOuterBottom(float bottom, ModifyReason reason) {
        setBottom(bottom - getOffset(BoundsType.OUTER, OffsetSide.BOTTOM), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setOuterPosition(float x, float y, ModifyReason reason) {
        setOuterX(x, reason);
        setOuterY(y, reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setOuterSize(float width, float height, ModifyReason reason) {
        setOuterWidth(width, reason);
        setOuterHeight(height, reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setMiddleLeft(float left, ModifyReason reason) {
        setLeft(left, BoundsType.MIDDLE, reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setMiddleTop(float top, ModifyReason reason) {
        setTop(top, BoundsType.MIDDLE, reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setMiddleRight(float right, ModifyReason reason) {
        setRight(right, BoundsType.MIDDLE, reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setMiddleBottom(float bottom, ModifyReason reason) {
        setBottom(bottom, BoundsType.MIDDLE, reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setX(float x, BoundsType type, ModifyReason reason) {
        setX(x + getOffset(type, OffsetSide.LEFT), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setY(float y, BoundsType type, ModifyReason reason) {
        setY(y + getOffset(type, OffsetSide.TOP), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setRightX(float x, BoundsType type, ModifyReason reason) {
        setRightX(x - getOffset(type, OffsetSide.RIGHT), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setBottomY(float y, BoundsType type, ModifyReason reason) {
        setBottomY(y - getOffset(type, OffsetSide.BOTTOM), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setLeft(float left, BoundsType type, ModifyReason reason) {
        setLeft(left + getOffset(type, OffsetSide.LEFT), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setTop(float top, BoundsType type, ModifyReason reason) {
        setTop(top + getOffset(type, OffsetSide.TOP), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setBottom(float bottom, BoundsType type, ModifyReason reason) {
        setBottom(bottom - getOffset(type, OffsetSide.BOTTOM), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setRight(float right, BoundsType type, ModifyReason reason) {
        setRight(right - getOffset(type, OffsetSide.RIGHT), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setPosition(float x, float y, BoundsType type, ModifyReason reason) {
        setX(x, type, reason);
        setY(y, type, reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setSize(float width, float height, BoundsType type, ModifyReason reason) {
        setWidth(width, type, reason);
        setHeight(height, type, reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setWidth(float width, BoundsType type, ModifyReason reason) {
        setWidth((width - getOffset(type, OffsetSide.LEFT)) - getOffset(type, OffsetSide.RIGHT), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default void setHeight(float height, BoundsType type, ModifyReason reason) {
        setHeight((height - getOffset(type, OffsetSide.TOP)) - getOffset(type, OffsetSide.BOTTOM), reason);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getX(BoundsType type) {
        return getLeft(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getY(BoundsType type) {
        return getTop(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getWidth(BoundsType type) {
        return getRight(type) - getLeft(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getHeight(BoundsType type) {
        return getBottom(type) - getTop(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getLeft(BoundsType type) {
        return getLeft() - getOffset(type, OffsetSide.LEFT);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getTop(BoundsType type) {
        return getTop() - getOffset(type, OffsetSide.TOP);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getRight(BoundsType type) {
        return getRight() + getOffset(type, OffsetSide.RIGHT);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getBottom(BoundsType type) {
        return getBottom() + getOffset(type, OffsetSide.BOTTOM);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getMaxX(BoundsType type) {
        return getRight(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getMaxY(BoundsType type) {
        return getBottom(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getCenterX(BoundsType type) {
        return getLeft(type) + (getWidth(type) / 2.0f);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getCenterY(BoundsType type) {
        return getTop(type) + (getHeight(type) / 2.0f);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getOffset(BoundsType type, RectangleState state) {
        switch (state) {
            case LEFT:
                return getOffset(type, OffsetSide.LEFT);
            case TOP:
                return getOffset(type, OffsetSide.TOP);
            case BOTTOM:
                return getOffset(type, OffsetSide.BOTTOM);
            case RIGHT:
                return getOffset(type, OffsetSide.RIGHT);
            case WIDTH:
                return getHorizontalOffset(type);
            case HEIGHT:
                return getVerticalOffset(type);
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(state));
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getHorizontalOffset(BoundsType type) {
        return getOffset(type, OffsetSide.LEFT) + getOffset(type, OffsetSide.RIGHT);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default float getVerticalOffset(BoundsType type) {
        return getOffset(type, OffsetSide.TOP) + getOffset(type, OffsetSide.BOTTOM);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default boolean isInRectangle(BoundsType type, Point point) {
        return isInRectangle(type, point.getX(), point.getY());
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default boolean isInRectangle(BoundsType type, float x, float y) {
        return isXInRectangle(type, x) && isYInRectangle(type, y);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default boolean isXInRectangle(BoundsType type, float x) {
        return x >= getLeft(type) && x <= getRight(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default boolean isYInRectangle(BoundsType type, float y) {
        return y >= getTop(type) && y <= getBottom(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default MutableRectangle copy(BoundsType type, MutableRectangle destination) {
        destination.setBounds(getLeft(type), getTop(type), getRight(type), getBottom(type));
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds
    default MutableRectangle copy(BoundsType type) {
        return copy(type, new DefaultRectangle());
    }
}
