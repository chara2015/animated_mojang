package net.labymod.api.laby3d.math;

import net.labymod.api.Laby;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.pool.ObjectPool;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionfc;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/math/JomlMath.class */
public final class JomlMath {
    private static final boolean FORWARD_VIEW = MinecraftVersions.V1_21.orNewer();

    public static Matrix4f cloneMatrix(Matrix4f other) {
        return new Matrix4f(other);
    }

    public static Matrix4f extractMatrix(Matrix4f other) {
        ObjectPool<Matrix4f> matrices = Laby.references().laby3D().matrices();
        return matrices.acquire().set(other);
    }

    public static Matrix4f flattenTo2D(Matrix4f src) {
        Matrix4f dst = Laby.references().laby3D().matrices().acquire();
        dst.m00(src.m00());
        dst.m01(src.m01());
        dst.m02(0.0f);
        dst.m03(0.0f);
        dst.m10(src.m10());
        dst.m11(src.m11());
        dst.m12(0.0f);
        dst.m13(0.0f);
        dst.m20(0.0f);
        dst.m21(0.0f);
        dst.m22(1.0f);
        dst.m23(0.0f);
        dst.m30(src.m30());
        dst.m31(src.m31());
        dst.m32(0.0f);
        dst.m33(1.0f);
        return dst;
    }

    public static float getYaw(Quaternionfc quat) {
        Vector3f forward = new Vector3f(0.0f, 0.0f, FORWARD_VIEW ? -1.0f : 1.0f);
        quat.transform(forward);
        float yawRadians = (float) Math.atan2(forward.x, forward.z);
        float yawDegrees = (float) Math.toDegrees(yawRadians);
        if (yawDegrees < 0.0f) {
            yawDegrees += 360.0f;
        }
        return yawDegrees;
    }

    public static float getPitch(Quaternionfc quat) {
        Vector3f forward = new Vector3f(0.0f, 0.0f, -1.0f);
        quat.transform(forward);
        float pitchRadians = (float) Math.atan2(forward.y, Math.sqrt((forward.x * forward.x) + (forward.z * forward.z)));
        return (float) Math.toDegrees(pitchRadians);
    }

    public static boolean isPureTranslation(Matrix4fc matrix) {
        return checkProperty(matrix, 8);
    }

    public static boolean isIdentity(Matrix4fc matrix) {
        return checkProperty(matrix, 4);
    }

    public static boolean isOrthonormal(Matrix4fc matrix) {
        return checkProperty(matrix, 16);
    }

    private static boolean checkPropertyRaw(Matrix4fc matrix, int flag) {
        return (matrix.properties() & flag) != 0;
    }

    public static boolean checkProperty(Matrix4fc matrix, int flag) {
        if (checkPropertyRaw(matrix, flag)) {
            return true;
        }
        if (matrix instanceof Matrix4f) {
            Matrix4f mat = (Matrix4f) matrix;
            mat.determineProperties();
            return checkPropertyRaw(matrix, flag);
        }
        return false;
    }
}
