package net.labymod.api.laby3d.pipeline.material;

import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.resource.AssetId;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/pipeline/material/Material.class */
public interface Material {
    RenderState renderState();

    TextureMaterial[] textureMaterials();

    default AssetId id() {
        return renderState().id();
    }

    default boolean supportsBlending() {
        return renderState().blendFunction().isPresent();
    }
}
