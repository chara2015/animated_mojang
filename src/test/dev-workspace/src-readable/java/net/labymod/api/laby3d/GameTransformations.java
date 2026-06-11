package net.labymod.api.laby3d;

import net.labymod.api.reference.annotation.Referenceable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/GameTransformations.class */
@Referenceable
public interface GameTransformations {
    Matrix4f modelViewMatrix();

    Matrix4f projectionMatrix();

    Matrix4f textureMatrix();

    Matrix4f viewMatrix();

    default Matrix4f guiProjectionMatrix() {
        return projectionMatrix();
    }

    default Vector3f light0Direction() {
        return GameLighting.DIFFUSE_LIGHT_0;
    }

    default Vector3f light1Direction() {
        return GameLighting.DIFFUSE_LIGHT_1;
    }
}
