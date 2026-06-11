package net.labymod.api.client.render.matrix;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatMatrix4;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/matrix/StackProvider.class */
public interface StackProvider {
    void translate(float f, float f2, float f3);

    void rotate(float f, float f2, float f3, float f4);

    void rotateRadians(float f, float f2, float f3, float f4);

    void scale(float f, float f2, float f3);

    void mul(Matrix4f matrix4f);

    void push();

    void pop();

    void identity();

    Matrix4f getPose();

    Matrix3f getNormal();

    @Nullable
    Object getPoseStack();

    int index();

    default Vector3f transformNormal(Vector3fc sourceNormal, Vector3f dest) {
        return transformNormal(sourceNormal.x(), sourceNormal.y(), sourceNormal.z(), dest);
    }

    default Vector3f transformNormal(float x, float y, float z, Vector3f dest) {
        return getNormal().transform(x, y, z, dest);
    }

    @Deprecated(forRemoval = true, since = "4.3.0")
    default FloatMatrix4 getPosition() {
        return MathHelper.mapper().fromMatrix4f(getPose());
    }

    default boolean isOpenGlStack() {
        return false;
    }
}
