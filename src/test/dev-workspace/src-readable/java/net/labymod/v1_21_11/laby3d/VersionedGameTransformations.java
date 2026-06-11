package net.labymod.v1_21_11.laby3d;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.inject.Singleton;
import net.labymod.api.laby3d.GameLightingStorage;
import net.labymod.api.laby3d.GameTransformations;
import net.labymod.api.models.Implements;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/laby3d/VersionedGameTransformations.class */
@Singleton
@Implements(GameTransformations.class)
public class VersionedGameTransformations implements GameTransformations {
    private final Matrix4f projectionMatrix = new Matrix4f();
    private final Matrix4f guiProjectionMatrix = new Matrix4f();

    public Matrix4f modelViewMatrix() {
        return RenderSystem.getModelViewMatrix();
    }

    public Matrix4f projectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4f guiProjectionMatrix() {
        return this.guiProjectionMatrix;
    }

    public Matrix4f textureMatrix() {
        return new Matrix4f();
    }

    public Matrix4f viewMatrix() {
        return MinecraftUtil.getViewMatrix();
    }

    public Vector3f light0Direction() {
        return GameLightingStorage.INSTANCE.getLightDirections(GameLightingStorage.Entry.LEVEL).light0Direction();
    }

    public Vector3f light1Direction() {
        return GameLightingStorage.INSTANCE.getLightDirections(GameLightingStorage.Entry.LEVEL).light1Direction();
    }
}
