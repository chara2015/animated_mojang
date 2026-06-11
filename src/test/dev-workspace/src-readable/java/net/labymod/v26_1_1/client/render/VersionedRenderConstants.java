package net.labymod.v26_1_1.client.render;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.render.RenderConstants;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/render/VersionedRenderConstants.class */
@Singleton
@Implements(RenderConstants.class)
public class VersionedRenderConstants implements RenderConstants {
    private static final float MODEL_SCALE = 16.0f;
    private static final float NAME_TAG_SCALE = 0.025f;
    private static final float CAMERA_MOVEMENT_SCALE = -0.6f;

    @Inject
    public VersionedRenderConstants() {
    }

    @Override // net.labymod.api.client.render.RenderConstants
    public float modelScale() {
        return MODEL_SCALE;
    }

    @Override // net.labymod.api.client.render.RenderConstants
    public float nameTagScale() {
        return NAME_TAG_SCALE;
    }

    @Override // net.labymod.api.client.render.RenderConstants
    public float cameraMovementScale() {
        return CAMERA_MOVEMENT_SCALE;
    }
}
