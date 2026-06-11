package net.labymod.api.util.bounds;

import net.labymod.api.Laby;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4fc;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/bounds/Rectangle.class */
public interface Rectangle {
    public static final Rectangle EMPTY = absolute(0.0f, 0.0f, 0.0f, 0.0f);

    float getLeft();

    float getTop();

    float getRight();

    float getBottom();

    default float getX() {
        return getLeft();
    }

    default float getY() {
        return getTop();
    }

    default float getWidth() {
        return getRight() - getLeft();
    }

    default float getHeight() {
        return getBottom() - getTop();
    }

    default float getMaxX() {
        return getRight();
    }

    default float getMaxY() {
        return getBottom();
    }

    default float getCenterX() {
        return getLeft() + (getWidth() / 2.0f);
    }

    default float getCenterY() {
        return getTop() + (getHeight() / 2.0f);
    }

    @NotNull
    default MutableRectangle copy() {
        return new DefaultRectangle(this);
    }

    default boolean isOverlapping(@NotNull Rectangle r) {
        return isOverlapping(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    default boolean isOverlapping(float x, float y, float width, float height) {
        return isOverlappingHorizontally(x, width) && isOverlappingVertically(y, height);
    }

    default boolean isOverlappingBy(@NotNull Rectangle r, float percentage) {
        return isOverlappingBy(r.getX(), r.getY(), r.getWidth(), r.getHeight(), percentage);
    }

    default boolean isOverlappingBy(float x, float y, float width, float height, float percentage) {
        float hor = getOverlappingPercentageHorizontally(x, width);
        float ver = getOverlappingPercentageVertically(y, height);
        return hor * ver >= percentage;
    }

    default boolean isOverlappingVertically(@NotNull Rectangle r) {
        return isOverlappingVertically(r.getY(), r.getHeight());
    }

    default boolean isOverlappingVertically(float y, float height) {
        return getTop() < y + height && getY() + getHeight() >= y;
    }

    default boolean isOverlappingVerticallyBy(@NotNull Rectangle r, float percentage) {
        return isOverlappingVerticallyBy(r.getY(), r.getHeight(), percentage);
    }

    default boolean isOverlappingVerticallyBy(float y, float height, float percentage) {
        return getOverlappingPercentageVertically(y, height) >= percentage;
    }

    default float getOverlappingPercentageVertically(@NotNull Rectangle r) {
        return getOverlappingPercentageVertically(r.getY(), r.getHeight());
    }

    default float getOverlappingPercentageVertically(float y, float height) {
        float bottom = y + height;
        float overlappingHeight = Math.min(getBottom() - y, bottom - getTop());
        return overlappingHeight / getHeight();
    }

    default boolean isOverlappingHorizontally(@NotNull Rectangle r) {
        return isOverlappingHorizontally(r.getX(), r.getWidth());
    }

    default boolean isOverlappingHorizontally(float x, float width) {
        return getLeft() < x + width && getLeft() + getWidth() >= x;
    }

    default boolean isOverlappingHorizontallyBy(@NotNull Rectangle r, float percentage) {
        return isOverlappingHorizontallyBy(r.getX(), r.getWidth(), percentage);
    }

    default boolean isOverlappingHorizontallyBy(float x, float width, float percentage) {
        return getOverlappingPercentageHorizontally(x, width) >= percentage;
    }

    default float getOverlappingPercentageHorizontally(@NotNull Rectangle r) {
        return getOverlappingPercentageHorizontally(r.getX(), r.getWidth());
    }

    default float getOverlappingPercentageHorizontally(float x, float width) {
        float right = x + width;
        float overlappingWidth = Math.min(getRight() - x, right - getLeft());
        return overlappingWidth / getWidth();
    }

    default boolean isInRectangle(Rectangle rectangle) {
        return isInRectangle(rectangle.getX(), rectangle.getY()) && isInRectangle(rectangle.getRight(), rectangle.getBottom());
    }

    default boolean isInRectangle(float x, float y) {
        return isXInRectangle(x) && isYInRectangle(y);
    }

    default boolean isInRectangle(Point point) {
        return isInRectangle(point.getX(), point.getY());
    }

    default boolean encompasses(Rectangle other) {
        return other.getLeft() >= getLeft() && other.getTop() >= getTop() && other.getRight() <= getRight() && other.getBottom() <= getBottom();
    }

    default boolean intersects(Rectangle other) {
        return getLeft() < other.getRight() && getRight() > other.getLeft() && getTop() < other.getBottom() && getBottom() > other.getTop();
    }

    default boolean isInRelativeRectangle(float x, float y) {
        return x >= getX() && x < getX() + getWidth() && y >= getY() && y < getY() + getHeight();
    }

    default boolean isInRelativeRectangle(Point point) {
        return isInRelativeRectangle(point.getX(), point.getY());
    }

    default boolean isXInRectangle(float x) {
        return x >= getLeft() && x < getRight();
    }

    default boolean isYInRectangle(float y) {
        return y >= getTop() && y < getBottom();
    }

    default boolean isInverted() {
        return getWidth() < 0.0f || getHeight() < 0.0f;
    }

    default boolean isRoundedPositionEqual(@NotNull Rectangle other) {
        float ox = other.getX();
        float oy = other.getY();
        float tx = getX();
        float ty = getY();
        return ((float) Math.round(ox)) == ((float) Math.round(tx)) && ((float) Math.round(oy)) == ((float) Math.round(ty));
    }

    default boolean isRoundedSizeEqual(@NotNull Rectangle other) {
        float ow = other.getWidth();
        float oh = other.getHeight();
        float tw = getWidth();
        float th = getHeight();
        return ((float) Math.round(ow)) == ((float) Math.round(tw)) && ((float) Math.round(oh)) == ((float) Math.round(th));
    }

    default boolean equalsBounds(@NotNull Rectangle other) {
        return Math.abs(getLeft() - other.getLeft()) < 0.001f && Math.abs(getTop() - other.getTop()) < 0.001f && Math.abs(getRight() - other.getRight()) < 0.001f && Math.abs(getBottom() - other.getBottom()) < 0.001f;
    }

    default float distanceSquared(@NotNull Rectangle other) {
        if (other.isOverlapping(this)) {
            return 0.0f;
        }
        float dx = 0.0f;
        float dy = 0.0f;
        if (getLeft() > other.getRight()) {
            dx = getLeft() - other.getRight();
        } else if (getRight() < other.getLeft()) {
            dx = other.getLeft() - getRight();
        }
        if (getTop() > other.getBottom()) {
            dy = getTop() - other.getBottom();
        } else if (getBottom() < other.getTop()) {
            dy = other.getTop() - getBottom();
        }
        return (dx * dx) + (dy * dy);
    }

    default float distanceSquared(@NotNull Point point) {
        return distanceSquared(point.getX(), point.getY());
    }

    default float distanceSquared(float x, float y) {
        return MathHelper.square(getCenterX() - x) + MathHelper.square(getCenterY() - y);
    }

    default Rectangle lerp(Rectangle destination, float progress) {
        float left = MathHelper.lerp(destination.getLeft(), getLeft(), progress);
        float top = MathHelper.lerp(destination.getTop(), getTop(), progress);
        float right = MathHelper.lerp(destination.getRight(), getRight(), progress);
        float bottom = MathHelper.lerp(destination.getBottom(), getBottom(), progress);
        return new DefaultRectangle(left, top, right, bottom);
    }

    default boolean hasSize() {
        return getWidth() > 0.0f && getHeight() > 0.0f;
    }

    @Nullable
    default Rectangle intersection(Rectangle other) {
        float left = Math.max(getLeft(), other.getLeft());
        float top = Math.max(getTop(), other.getTop());
        float right = Math.min(getRight(), other.getRight());
        float bottom = Math.min(getBottom(), other.getBottom());
        if (left >= right || top >= bottom) {
            return null;
        }
        return relative(left, top, right - left, bottom - top);
    }

    default Rectangle transformedAabb(Matrix4fc matrix) {
        Vector3f temp = Laby.references().laby3D().vector3fs().acquire();
        matrix.transformPosition(getLeft(), getTop(), 0.0f, temp);
        float minX = temp.x();
        float minY = temp.y();
        matrix.transformPosition(getRight(), getTop(), 0.0f, temp);
        float minX2 = Math.min(minX, temp.x());
        float maxX = Math.max(minX, temp.x());
        float minY2 = Math.min(minY, temp.y());
        float maxY = Math.max(minY, temp.y());
        matrix.transformPosition(getLeft(), getBottom(), 0.0f, temp);
        float minX3 = Math.min(minX2, temp.x());
        float maxX2 = Math.max(maxX, temp.x());
        float minY3 = Math.min(minY2, temp.y());
        float maxY2 = Math.max(maxY, temp.y());
        matrix.transformPosition(getRight(), getBottom(), 0.0f, temp);
        float minX4 = Math.min(minX3, temp.x());
        float maxX3 = Math.max(maxX2, temp.x());
        float minY4 = Math.min(minY3, temp.y());
        return relative(minX4, minY4, maxX3 - minX4, Math.max(maxY2, temp.y()) - minY4);
    }

    static MutableRectangle absolute(float left, float top, float right, float bottom) {
        return new DefaultRectangle(left, top, right, bottom);
    }

    static MutableRectangle relative(float x, float y, float width, float height) {
        return new DefaultRectangle(x, y, x + width, y + height);
    }

    static MutableRectangle extendable() {
        return new DefaultRectangle(Float.MAX_VALUE, Float.MAX_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
    }
}
