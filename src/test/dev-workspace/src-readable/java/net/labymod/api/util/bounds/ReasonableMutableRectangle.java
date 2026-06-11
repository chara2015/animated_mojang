package net.labymod.api.util.bounds;

import java.util.Map;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/bounds/ReasonableMutableRectangle.class */
public interface ReasonableMutableRectangle extends Rectangle {
    void setLeft(float f, ModifyReason modifyReason);

    void setTop(float f, ModifyReason modifyReason);

    void setRight(float f, ModifyReason modifyReason);

    void setBottom(float f, ModifyReason modifyReason);

    void recordModifications();

    void stopRecordingModifications();

    @NotNull
    Map<RectangleState, RectangleModification> lastModifications();

    default void setBounds(float left, float top, float right, float bottom, ModifyReason reason) {
        setLeft(left, reason);
        setTop(top, reason);
        setRight(right, reason);
        setBottom(bottom, reason);
    }

    default void setSize(float size, ModifyReason reason) {
        setSize(size, size, reason);
    }

    default void setSize(float width, float height, ModifyReason reason) {
        setWidth(width, reason);
        setHeight(height, reason);
    }

    default void setPosition(float x, float y, ModifyReason reason) {
        setX(x, reason);
        setY(y, reason);
    }

    default void setX(float x, ModifyReason reason) {
        setRight(x + getWidth(), reason);
        setLeft(x, reason);
    }

    default void setY(float y, ModifyReason reason) {
        setBottom(y + getHeight(), reason);
        setTop(y, reason);
    }

    default void setRightX(float rightX, ModifyReason reason) {
        setLeft(rightX - getWidth(), reason);
        setRight(rightX, reason);
    }

    default void setBottomY(float bottomY, ModifyReason reason) {
        setTop(bottomY - getHeight(), reason);
        setBottom(bottomY, reason);
    }

    default void setWidth(float width, ModifyReason reason) {
        setRight(getLeft() + width, reason);
    }

    default void setLeftWidth(float width, ModifyReason reason) {
        setLeft(getRight() - width, reason);
    }

    default void setHeight(float height, ModifyReason reason) {
        setBottom(getTop() + height, reason);
    }

    default void setTopHeight(float height, ModifyReason reason) {
        setTop(getBottom() - height, reason);
    }

    default void crop(@NotNull Rectangle rectangle, ModifyReason reason) {
        setLeft(Math.max(getLeft(), rectangle.getLeft()), reason);
        setTop(Math.max(getTop(), rectangle.getTop()), reason);
        setRight(Math.min(getRight(), rectangle.getRight()), reason);
        setBottom(Math.min(getBottom(), rectangle.getBottom()), reason);
    }

    default void extend(@NotNull Rectangle rectangle, ModifyReason reason) {
        setLeft(Math.min(getLeft(), rectangle.getLeft()), reason);
        setTop(Math.min(getTop(), rectangle.getTop()), reason);
        setRight(Math.max(getRight(), rectangle.getRight()), reason);
        setBottom(Math.max(getBottom(), rectangle.getBottom()), reason);
    }

    default void set(@NotNull Rectangle rectangle, ModifyReason reason) {
        setBounds(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), reason);
    }

    @NotNull
    default ReasonableMutableRectangle shift(float x, float y, ModifyReason reason) {
        setLeft(getLeft() + x, reason);
        setTop(getTop() + y, reason);
        setRight(getRight() + x, reason);
        setBottom(getBottom() + y, reason);
        return this;
    }

    @NotNull
    default ReasonableMutableRectangle expand(float padding, ModifyReason reason) {
        setBounds(getLeft() - padding, getTop() - padding, getRight() + padding, getBottom() + padding, reason);
        return this;
    }

    @NotNull
    default ReasonableMutableRectangle expand(float paddingX, float paddingY, ModifyReason reason) {
        setBounds(getLeft() - paddingX, getTop() - paddingY, getRight() + paddingX, getBottom() + paddingY, reason);
        return this;
    }

    @NotNull
    default ReasonableMutableRectangle expand(@NotNull FloatVector2 padding, ModifyReason reason) {
        return expand(padding.getX(), padding.getY(), reason);
    }

    @NotNull
    default ReasonableMutableRectangle shrink(float padding, ModifyReason reason) {
        return expand(-padding, reason);
    }

    default void apply(Stack stack, ModifyReason reason) {
        float left = getLeft();
        float top = getTop();
        float right = getRight();
        float bottom = getBottom();
        FloatMatrix4 matrix = stack.getProvider().getPosition();
        setLeft(matrix.transformVectorX(left, top, 0.0f), reason);
        setTop(matrix.transformVectorY(left, top, 0.0f), reason);
        setRight(matrix.transformVectorX(right, bottom, 0.0f), reason);
        setBottom(matrix.transformVectorY(right, bottom, 0.0f), reason);
    }

    default void add(float x, float y, ModifyReason reason) {
        setX(getLeft() + x, reason);
        setY(getTop() + y, reason);
    }

    default void add(Rectangle rectangle, ModifyReason reason) {
        setX(getLeft() + rectangle.getLeft(), reason);
        setY(getTop() + rectangle.getTop(), reason);
    }

    default void subtract(Rectangle rectangle, ModifyReason reason) {
        setX(getLeft() - rectangle.getLeft(), reason);
        setY(getTop() - rectangle.getTop(), reason);
    }

    default void shiftToBounds(Rectangle boundaries, ModifyReason reason, boolean outer) {
        if (getRight() > boundaries.getRight()) {
            if (outer) {
                setRight(boundaries.getRight(), reason);
            } else {
                setX(boundaries.getRight() - getWidth(), reason);
            }
        }
        if (getBottom() > boundaries.getBottom()) {
            if (outer) {
                setBottom(boundaries.getBottom(), reason);
            } else {
                setY(boundaries.getBottom() - getHeight(), reason);
            }
        }
        if (getLeft() < boundaries.getLeft()) {
            setX(boundaries.getLeft(), reason);
        }
        if (getTop() < boundaries.getTop()) {
            setY(boundaries.getTop(), reason);
        }
    }
}
