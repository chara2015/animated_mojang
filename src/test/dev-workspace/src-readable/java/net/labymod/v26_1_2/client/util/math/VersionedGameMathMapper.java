package net.labymod.v26_1_2.client.util.math;

import javax.inject.Singleton;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix3f;
import org.joml.Matrix3x2f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/util/math/VersionedGameMathMapper.class */
@Singleton
@Implements(GameMathMapper.class)
public class VersionedGameMathMapper implements GameMathMapper {
    private static final FloatMatrix4 MATRIX4F_IDENTITY = new FloatMatrix4().identity();
    private static final FloatMatrix3 MATRIX3F_IDENTITY = new FloatMatrix3().identity();
    private static final Quaternion QUATERNION_IDENTITY = new Quaternion();
    private static final AxisAlignedBoundingBox BOX_IDENTITY = new AxisAlignedBoundingBox();
    private static final FloatMatrix3 SHARED_MATRIX3F = new FloatMatrix3();
    private static final FloatMatrix4 SHARED_MATRIX4F = new FloatMatrix4();
    private static final Matrix3f MOJANG_SHARED_MATRIX3F = new Matrix3f();
    private static final Matrix4f MOJANG_SHARED_MATRIX4F = new Matrix4f();

    @Override // net.labymod.api.util.math.GameMathMapper
    public FloatMatrix4 fromMatrix4f(Object matrix) {
        if (matrix instanceof Matrix4f) {
            Matrix4f matrix4f = (Matrix4f) matrix;
            return new FloatMatrix4(matrix4f);
        }
        if (matrix instanceof Matrix3x2f) {
            Matrix3x2f matrix3x2f = (Matrix3x2f) matrix;
            float[] matrix4fArray = new float[16];
            matrix3x2f.get4x4(matrix4fArray);
            MOJANG_SHARED_MATRIX4F.set(matrix4fArray);
            return _fromMatrix4f(MOJANG_SHARED_MATRIX4F);
        }
        return MATRIX4F_IDENTITY;
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toMatrix4f(FloatMatrix4 floatMatrix4) {
        return (T) _toMatrix4f(floatMatrix4);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public FloatMatrix3 fromMatrix3f(Object matrix) {
        if (!(matrix instanceof Matrix3f)) {
            return MATRIX3F_IDENTITY;
        }
        Matrix3f matrix3f = (Matrix3f) matrix;
        return _fromMatrix3f(matrix3f);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toMatrix3f(FloatMatrix3 floatMatrix3) {
        return (T) _toMatrix3f(floatMatrix3);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyFloatMatrix4(FloatMatrix4 source, Object destination) {
        if (!(destination instanceof Matrix4f)) {
            return;
        }
        Matrix4f matrix4f = (Matrix4f) destination;
        _applyFloatMatrix4(source, matrix4f);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyMatrix4f(Object source, FloatMatrix4 destination) {
        if (!(source instanceof Matrix4f)) {
            return;
        }
        Matrix4f matrix4f = (Matrix4f) source;
        _applyMatrix4f(matrix4f, destination);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyFloatMatrix3(FloatMatrix3 source, Object destination) {
        if (!(destination instanceof Matrix3f)) {
            return;
        }
        Matrix3f matrix3f = (Matrix3f) destination;
        _applyFloatMatrix3(source, matrix3f);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyMatrix3f(Object source, FloatMatrix3 destination) {
        if (!(source instanceof Matrix3f)) {
            return;
        }
        Matrix3f matrix3f = (Matrix3f) source;
        _applyMatrix3f(matrix3f, destination);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public Quaternion fromQuaternion(Object quaternion) {
        if (!(quaternion instanceof Quaternionf)) {
            return QUATERNION_IDENTITY;
        }
        Quaternionf gameQuaternion = (Quaternionf) quaternion;
        return new Quaternion(gameQuaternion.x(), gameQuaternion.y(), gameQuaternion.z(), gameQuaternion.w());
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toQuaternion(Quaternion quaternion) {
        return (T) new Quaternionf(quaternion.getX(), quaternion.getY(), quaternion.getZ(), quaternion.getW());
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public AxisAlignedBoundingBox fromAABB(Object aabb) {
        if (!(aabb instanceof AABB)) {
            return BOX_IDENTITY;
        }
        AABB gameAABB = (AABB) aabb;
        return new AxisAlignedBoundingBox(gameAABB.minX, gameAABB.minY, gameAABB.minZ, gameAABB.maxX, gameAABB.maxY, gameAABB.maxZ);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toAABB(AxisAlignedBoundingBox axisAlignedBoundingBox) {
        return (T) new AABB(axisAlignedBoundingBox.getMinX(), axisAlignedBoundingBox.getMinY(), axisAlignedBoundingBox.getMinZ(), axisAlignedBoundingBox.getMaxX(), axisAlignedBoundingBox.getMaxY(), axisAlignedBoundingBox.getMaxZ());
    }

    private Matrix4f _toMatrix4f(FloatMatrix4 matrix) {
        MOJANG_SHARED_MATRIX4F.identity();
        _applyFloatMatrix4(matrix, MOJANG_SHARED_MATRIX4F);
        return MOJANG_SHARED_MATRIX4F;
    }

    private Matrix3f _toMatrix3f(FloatMatrix3 matrix) {
        MOJANG_SHARED_MATRIX3F.identity();
        _applyFloatMatrix3(matrix, MOJANG_SHARED_MATRIX3F);
        return MOJANG_SHARED_MATRIX3F;
    }

    private FloatMatrix4 _fromMatrix4f(Matrix4f matrix) {
        SHARED_MATRIX4F.identity();
        _applyMatrix4f(matrix, SHARED_MATRIX4F);
        return SHARED_MATRIX4F;
    }

    private FloatMatrix3 _fromMatrix3f(Matrix3f matrix) {
        SHARED_MATRIX3F.identity();
        _applyMatrix3f(matrix, SHARED_MATRIX3F);
        return SHARED_MATRIX3F;
    }

    private void _applyFloatMatrix3(FloatMatrix3 source, Matrix3f destination) {
        destination.set(source.getM00(), source.getM10(), source.getM20(), source.getM01(), source.getM11(), source.getM21(), source.getM02(), source.getM12(), source.getM22());
    }

    private void _applyMatrix3f(Matrix3f source, FloatMatrix3 destination) {
        destination.set(source.m00(), source.m10(), source.m20(), source.m01(), source.m11(), source.m21(), source.m02(), source.m12(), source.m22());
    }

    private void _applyMatrix4f(Matrix4f source, FloatMatrix4 destination) {
        destination.set(source.m00(), source.m10(), source.m20(), source.m30(), source.m01(), source.m11(), source.m21(), source.m31(), source.m02(), source.m12(), source.m22(), source.m32(), source.m03(), source.m13(), source.m23(), source.m33());
    }

    private void _applyFloatMatrix4(FloatMatrix4 source, Matrix4f destination) {
        destination.set(source.getM00(), source.getM10(), source.getM20(), source.getM30(), source.getM01(), source.getM11(), source.getM21(), source.getM31(), source.getM02(), source.getM12(), source.getM22(), source.getM32(), source.getM03(), source.getM13(), source.getM23(), source.getM33());
    }
}
