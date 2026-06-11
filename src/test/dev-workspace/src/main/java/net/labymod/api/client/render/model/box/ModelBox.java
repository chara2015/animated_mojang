package net.labymod.api.client.render.model.box;

import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/box/ModelBox.class */
@Deprecated(forRemoval = true, since = "4.3.55")
public interface ModelBox {
    float getMinX();

    float getMinY();

    float getMinZ();

    float getMaxX();

    float getMaxY();

    float getMaxZ();

    float getWidth();

    float getHeight();

    float getDepth();

    boolean isMirror();

    @NotNull
    ModelBoxQuad[] getQuads();
}
