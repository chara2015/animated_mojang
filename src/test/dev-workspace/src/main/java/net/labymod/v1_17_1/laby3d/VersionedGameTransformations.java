package net.labymod.v1_17_1.laby3d;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.inject.Singleton;
import net.labymod.api.laby3d.GameTransformations;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.util.math.MathHelper;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/laby3d/VersionedGameTransformations.class */
@Singleton
@Implements(GameTransformations.class)
public class VersionedGameTransformations implements GameTransformations {
    private static final GameMathMapper MAPPER = MathHelper.mapper();
    private final Matrix4f viewMatrix = new Matrix4f();

    @Override // net.labymod.api.laby3d.GameTransformations
    public Matrix4f modelViewMatrix() {
        return MAPPER.toJomlMatrix4f(RenderSystem.getModelViewMatrix());
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Matrix4f projectionMatrix() {
        return MAPPER.toJomlMatrix4f(RenderSystem.getProjectionMatrix());
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Matrix4f textureMatrix() {
        return MAPPER.toJomlMatrix4f(RenderSystem.getTextureMatrix());
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Matrix4f viewMatrix() {
        return this.viewMatrix;
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Vector3f light0Direction() {
        k direction = RenderSystem.shaderLightDirections[0];
        return new Vector3f(direction.a(), direction.b(), direction.c());
    }

    @Override // net.labymod.api.laby3d.GameTransformations
    public Vector3f light1Direction() {
        k direction = RenderSystem.shaderLightDirections[1];
        return new Vector3f(direction.a(), direction.b(), direction.c());
    }
}
