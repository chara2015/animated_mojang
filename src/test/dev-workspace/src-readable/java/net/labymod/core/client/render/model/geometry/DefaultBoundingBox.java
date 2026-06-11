package net.labymod.core.client.render.model.geometry;

import net.labymod.api.client.render.model.geometry.BoundingBox;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/geometry/DefaultBoundingBox.class */
public class DefaultBoundingBox implements BoundingBox {
    private final float minX;
    private final float minY;
    private final float minZ;
    private final float maxX;
    private final float maxY;
    private final float maxZ;

    public DefaultBoundingBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    @NotNull
    public static DefaultBoundingBox fromOriginAndSize(float originX, float originY, float originZ, float width, float height, float depth) {
        return new DefaultBoundingBox(originX, originY, originZ, originX + width, originY + height, originZ + depth);
    }

    @Override // net.labymod.api.client.render.model.geometry.BoundingBox
    public float getMinX() {
        return this.minX;
    }

    @Override // net.labymod.api.client.render.model.geometry.BoundingBox
    public float getMinY() {
        return this.minY;
    }

    @Override // net.labymod.api.client.render.model.geometry.BoundingBox
    public float getMinZ() {
        return this.minZ;
    }

    @Override // net.labymod.api.client.render.model.geometry.BoundingBox
    public float getMaxX() {
        return this.maxX;
    }

    @Override // net.labymod.api.client.render.model.geometry.BoundingBox
    public float getMaxY() {
        return this.maxY;
    }

    @Override // net.labymod.api.client.render.model.geometry.BoundingBox
    public float getMaxZ() {
        return this.maxZ;
    }
}
