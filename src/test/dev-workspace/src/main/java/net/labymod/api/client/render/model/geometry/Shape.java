package net.labymod.api.client.render.model.geometry;

import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/Shape.class */
public interface Shape {
    @NotNull
    ShapeQuad[] getQuads();

    @NotNull
    BoundingBox getBounds();

    @NotNull
    ShapeType getType();

    default boolean hasVisibleQuads() {
        for (ShapeQuad quad : getQuads()) {
            if (quad != null && quad.isVisible()) {
                return true;
            }
        }
        return false;
    }
}
