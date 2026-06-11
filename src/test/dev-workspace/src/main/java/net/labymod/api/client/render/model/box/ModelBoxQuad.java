package net.labymod.api.client.render.model.box;

import net.labymod.api.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/box/ModelBoxQuad.class */
@Deprecated(forRemoval = true, since = "4.3.55")
public interface ModelBoxQuad {
    @NotNull
    ModelBoxVertex[] getVertices();

    @NotNull
    Vector3f getNormal();

    @NotNull
    Direction getDirection();

    boolean isVisible();

    void setVisible(boolean z);

    float getMinU();

    float getMinV();

    float getMaxU();

    float getMaxV();
}
