package net.labymod.api.util.bounds;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/bounds/MutableRectangle.class */
public interface MutableRectangle extends Rectangle {
    void setLeft(float f);

    void setTop(float f);

    void setRight(float f);

    void setBottom(float f);

    default void setBounds(float left, float top, float right, float bottom) {
        setLeft(left);
        setTop(top);
        setRight(right);
        setBottom(bottom);
    }

    default void setSize(float size) {
        setSize(size, size);
    }

    default void setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
    }

    default void setPosition(Point position) {
        setPosition(position.getX(), position.getY());
    }

    default void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    default void setX(float x) {
        setRight(x + getWidth());
        setLeft(x);
    }

    default void setY(float y) {
        setBottom(y + getHeight());
        setTop(y);
    }

    default void setRightX(float rightX) {
        setLeft(rightX - getWidth());
        setRight(rightX);
    }

    default void setBottomY(float bottomY) {
        setTop(bottomY - getHeight());
        setBottom(bottomY);
    }

    default void setWidth(float width) {
        setRight(getLeft() + width);
    }

    default void setLeftWidth(float width) {
        setLeft(getRight() - width);
    }

    default void setHeight(float height) {
        setBottom(getTop() + height);
    }

    default void setTopHeight(float height) {
        setTop(getBottom() - height);
    }

    default void crop(@NotNull Rectangle rectangle) {
        setLeft(Math.max(getLeft(), rectangle.getLeft()));
        setTop(Math.max(getTop(), rectangle.getTop()));
        setRight(Math.min(getRight(), rectangle.getRight()));
        setBottom(Math.min(getBottom(), rectangle.getBottom()));
    }

    default void extend(@NotNull Rectangle rectangle) {
        setLeft(Math.min(getLeft(), rectangle.getLeft()));
        setTop(Math.min(getTop(), rectangle.getTop()));
        setRight(Math.max(getRight(), rectangle.getRight()));
        setBottom(Math.max(getBottom(), rectangle.getBottom()));
    }

    default void extendTransformed(@NotNull Rectangle rectangle, @NotNull Matrix4f pose) {
        float l = rectangle.getLeft();
        float t = rectangle.getTop();
        float r = rectangle.getRight();
        float b = rectangle.getBottom();
        Vector3f transformed = new Vector3f();
        pose.transformPosition(l, t, 0.0f, transformed);
        extendPoint(transformed.x, transformed.y);
        pose.transformPosition(r, t, 0.0f, transformed);
        extendPoint(transformed.x, transformed.y);
        pose.transformPosition(l, b, 0.0f, transformed);
        extendPoint(transformed.x, transformed.y);
        pose.transformPosition(r, b, 0.0f, transformed);
        extendPoint(transformed.x, transformed.y);
    }

    default void extendPoint(float x, float y) {
        setLeft(Math.min(getLeft(), x));
        setTop(Math.min(getTop(), y));
        setRight(Math.max(getRight(), x));
        setBottom(Math.max(getBottom(), y));
    }

    default void set(@NotNull Rectangle rectangle) {
        setBounds(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom());
    }

    @NotNull
    default MutableRectangle shift(float x, float y) {
        setLeft(getLeft() + x);
        setTop(getTop() + y);
        setRight(getRight() + x);
        setBottom(getBottom() + y);
        return this;
    }

    @NotNull
    default MutableRectangle scale(float x, float y) {
        setWidth(getWidth() * x);
        setHeight(getHeight() * y);
        return this;
    }

    @NotNull
    default MutableRectangle expand(float padding) {
        setBounds(getLeft() - padding, getTop() - padding, getRight() + padding, getBottom() + padding);
        return this;
    }

    @NotNull
    default MutableRectangle expand(float paddingX, float paddingY) {
        setBounds(getLeft() - paddingX, getTop() - paddingY, getRight() + paddingX, getBottom() + paddingY);
        return this;
    }

    @NotNull
    default MutableRectangle expand(@NotNull FloatVector2 padding) {
        return expand(padding.getX(), padding.getY());
    }

    @NotNull
    default MutableRectangle shrink(float padding) {
        return expand(-padding);
    }

    default void apply(Stack stack) {
        float left = getLeft();
        float top = getTop();
        float right = getRight();
        float bottom = getBottom();
        FloatMatrix4 matrix = stack.getProvider().getPosition();
        setLeft(matrix.transformVectorX(left, top, 0.0f));
        setTop(matrix.transformVectorY(left, top, 0.0f));
        setRight(matrix.transformVectorX(right, bottom, 0.0f));
        setBottom(matrix.transformVectorY(right, bottom, 0.0f));
    }

    default void add(float x, float y) {
        setX(getLeft() + x);
        setY(getTop() + y);
    }

    default void add(Rectangle rectangle) {
        setX(getLeft() + rectangle.getLeft());
        setY(getTop() + rectangle.getTop());
    }

    default void subtract(Rectangle rectangle) {
        setX(getLeft() - rectangle.getLeft());
        setY(getTop() - rectangle.getTop());
    }

    default void shiftToBounds(Rectangle boundaries) {
        if (getRight() > boundaries.getRight()) {
            setX(boundaries.getRight() - getWidth());
        }
        if (getBottom() > boundaries.getBottom()) {
            setY(boundaries.getBottom() - getHeight());
        }
        if (getLeft() < boundaries.getLeft()) {
            setX(boundaries.getLeft());
        }
        if (getTop() < boundaries.getTop()) {
            setY(boundaries.getTop());
        }
    }
}
