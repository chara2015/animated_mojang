package net.labymod.v1_21_1.laby3d;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.inject.Singleton;
import net.labymod.api.laby3d.GameTransformations;
import net.labymod.api.models.Implements;
import net.labymod.v1_21_1.client.util.MinecraftUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/laby3d/VersionedGameTransformations.class */
@Singleton
@Implements(GameTransformations.class)
public class VersionedGameTransformations implements GameTransformations {
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
        return MinecraftUtil.getViewMatrix();
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
