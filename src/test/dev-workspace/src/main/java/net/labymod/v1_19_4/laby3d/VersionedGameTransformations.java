package net.labymod.v1_19_4.laby3d;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.inject.Singleton;
import net.labymod.api.laby3d.GameTransformations;
import net.labymod.api.models.Implements;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/laby3d/VersionedGameTransformations.class */
@Singleton
@Implements(GameTransformations.class)
public class VersionedGameTransformations implements GameTransformations {
    private final Matrix4f viewMatrix = new Matrix4f();

    @Override // net.labymod.api.laby3d.GameTransformations
    public Matrix4f modelViewMatrix() {
        return RenderSystem.getModelViewMatrix();
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Matrix4f projectionMatrix() {
        return RenderSystem.getProjectionMatrix();
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Matrix4f textureMatrix() {
        return RenderSystem.getTextureMatrix();
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Matrix4f viewMatrix() {
        return this.viewMatrix;
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Vector3f light0Direction() {
        return RenderSystem.shaderLightDirections[0];
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Vector3f light1Direction() {
        return RenderSystem.shaderLightDirections[1];
    }
}
