package net.labymod.v1_8_9.laby3d;

import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.util.MatrixTracker;
import net.labymod.api.laby3d.GameTransformations;
import net.labymod.api.models.Implements;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/laby3d/VersionedGameTransformations.class */
@Singleton
@Implements(GameTransformations.class)
public class VersionedGameTransformations implements GameTransformations {
    private static final Vector3f LIGHT_0_DIRECTION = new Vector3f(0.2f, 1.0f, -0.7f).normalize();
    private static final Vector3f LIGHT_1_DIRECTION = new Vector3f(-0.2f, 1.0f, 0.7f).normalize();
    private final Matrix4f viewMatrix = new Matrix4f();

    @Override // net.labymod.api.laby3d.GameTransformations
    public Matrix4f modelViewMatrix() {
        return MatrixTracker.MODEL_VIEW_MATRIX.pose();
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Matrix4f projectionMatrix() {
        return MatrixTracker.PROJECTION_MATRIX.pose();
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Matrix4f textureMatrix() {
        return MatrixTracker.getCurrentTextureMatrix().pose();
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Matrix4f viewMatrix() {
        return this.viewMatrix;
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Vector3f light0Direction() {
        Matrix4f matrix4f = modelViewMatrix();
        return matrix4f.transformDirection(LIGHT_0_DIRECTION, new Vector3f());
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Vector3f light1Direction() {
        Matrix4f matrix4f = modelViewMatrix();
        return matrix4f.transformDirection(LIGHT_1_DIRECTION, new Vector3f());
    }
}
