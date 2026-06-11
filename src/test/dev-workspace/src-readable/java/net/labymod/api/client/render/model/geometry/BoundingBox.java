package net.labymod.api.client.render.model.geometry;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/BoundingBox.class */
public interface BoundingBox {
    float getMinX();

    float getMinY();

    float getMinZ();

    float getMaxX();

    float getMaxY();

    float getMaxZ();

    default float getWidth() {
        return Math.abs(getMaxX() - getMinX());
    }

    default float getHeight() {
        return Math.abs(getMaxY() - getMinY());
    }

    default float getDepth() {
        return Math.abs(getMaxZ() - getMinZ());
    }

    @NotNull
    default Vector3f getMin() {
        return new Vector3f(getMinX(), getMinY(), getMinZ());
    }

    @NotNull
    default Vector3f getMax() {
        return new Vector3f(getMaxX(), getMaxY(), getMaxZ());
    }

    @NotNull
    default Vector3f getCenter() {
        return new Vector3f((getMinX() + getMaxX()) / 2.0f, (getMinY() + getMaxY()) / 2.0f, (getMinZ() + getMaxZ()) / 2.0f);
    }

    default boolean contains(float x, float y, float z) {
        return x >= getMinX() && x <= getMaxX() && y >= getMinY() && y <= getMaxY() && z >= getMinZ() && z <= getMaxZ();
    }

    default boolean intersects(@NotNull BoundingBox other) {
        return getMinX() <= other.getMaxX() && getMaxX() >= other.getMinX() && getMinY() <= other.getMaxY() && getMaxY() >= other.getMinY() && getMinZ() <= other.getMaxZ() && getMaxZ() >= other.getMinZ();
    }
}
