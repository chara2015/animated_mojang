package net.labymod.api.client.render.model.geometry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/ShapeQuad.class */
public interface ShapeQuad {
    @NotNull
    ShapeVertex[] getVertices();

    @NotNull
    Vector3f getNormal();

    boolean isVisible();

    void setVisible(boolean z);

    @Nullable
    UVBounds getUVBounds();
}
