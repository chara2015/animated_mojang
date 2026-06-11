package net.labymod.api.client.render.model.box;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/box/ModelBoxVertex.class */
@Deprecated(forRemoval = true, since = "4.3.55")
public interface ModelBoxVertex {
    @NotNull
    Vector3f getPosition();

    @NotNull
    Vector2f getUV();

    @NotNull
    ModelBoxVertex remap(float f, float f2);

    @NotNull
    ModelBoxVertex remap(@NotNull Vector2f vector2f);
}
