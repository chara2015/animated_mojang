package net.labymod.v1_12_2.client.util;

import java.nio.FloatBuffer;
import javax.inject.Singleton;
import net.labymod.api.models.Implements;
import net.labymod.api.util.Buffers;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix4;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/util/VersionedGameMathMapper.class */
@Singleton
@Implements(GameMathMapper.class)
public class VersionedGameMathMapper implements GameMathMapper {
    private static final FloatBuffer MATRIX4_BUFFER = Buffers.createFloatBuffer(16);
    private static final FloatBuffer MATRIX3_BUFFER = Buffers.createFloatBuffer(12);
    private static final FloatMatrix4 MATRIX4F_IDENTITY = new FloatMatrix4().identity();
    private static final FloatMatrix3 MATRIX3F_IDENTITY = new FloatMatrix3().identity();
    private static final Quaternion QUATERNION_IDENTITY = new Quaternion();
    private static final AxisAlignedBoundingBox BOX_IDENTITY = new AxisAlignedBoundingBox();

    @Override // net.labymod.api.util.math.GameMathMapper
    public FloatMatrix4 fromMatrix4f(Object matrix) {
        if (matrix instanceof Matrix4f) {
            Matrix4f mojangMat = (Matrix4f) matrix;
            return _fromMatrix4f(mojangMat);
        }
        if (matrix instanceof org.joml.Matrix4f) {
            org.joml.Matrix4f jomlMat = (org.joml.Matrix4f) matrix;
            return new FloatMatrix4(jomlMat);
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
        return _fromMatrix3f((Matrix3f) matrix);
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
        _applyFloatMatrix4(source, (Matrix4f) destination);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyMatrix4f(Object source, FloatMatrix4 destination) {
        if (!(source instanceof Matrix4f)) {
            return;
        }
        _applyMatrix4f((Matrix4f) source, destination);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyFloatMatrix3(FloatMatrix3 source, Object destination) {
        if (!(destination instanceof Matrix3f)) {
            return;
        }
        _applyFloatMatrix3(source, (Matrix3f) destination);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyMatrix3f(Object source, FloatMatrix3 destination) {
        if (!(source instanceof Matrix3f)) {
            return;
        }
        _applyMatrix3f((Matrix3f) source, destination);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public Quaternion fromQuaternion(Object quaternion) {
        if (!(quaternion instanceof org.lwjgl.util.vector.Quaternion)) {
            return QUATERNION_IDENTITY;
        }
        org.lwjgl.util.vector.Quaternion gameQuaternion = (org.lwjgl.util.vector.Quaternion) quaternion;
        return new Quaternion(gameQuaternion.getX(), gameQuaternion.getY(), gameQuaternion.getZ(), gameQuaternion.getW());
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toQuaternion(Quaternion quaternion) {
        return (T) new org.lwjgl.util.vector.Quaternion(quaternion.getX(), quaternion.getY(), quaternion.getZ(), quaternion.getW());
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public AxisAlignedBoundingBox fromAABB(Object aabb) {
        if (!(aabb instanceof bhb)) {
            return BOX_IDENTITY;
        }
        bhb gameAABB = (bhb) aabb;
        return new AxisAlignedBoundingBox(gameAABB.a, gameAABB.b, gameAABB.c, gameAABB.d, gameAABB.e, gameAABB.f);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toAABB(AxisAlignedBoundingBox axisAlignedBoundingBox) {
        return (T) new bhb(axisAlignedBoundingBox.getMinX(), axisAlignedBoundingBox.getMinY(), axisAlignedBoundingBox.getMinZ(), axisAlignedBoundingBox.getMaxX(), axisAlignedBoundingBox.getMaxY(), axisAlignedBoundingBox.getMaxZ());
    }

    private Matrix4f _toMatrix4f(FloatMatrix4 matrix) {
        Matrix4f matrix4f = new Matrix4f();
        _applyFloatMatrix4(matrix, matrix4f);
        return matrix4f;
    }

    private Matrix3f _toMatrix3f(FloatMatrix3 matrix) {
        Matrix3f matrix3f = new Matrix3f();
        _applyFloatMatrix3(matrix, matrix3f);
        return matrix3f;
    }

    private FloatMatrix4 _fromMatrix4f(Matrix4f matrix) {
        FloatMatrix4 matrix4 = new FloatMatrix4();
        _applyMatrix4f(matrix, matrix4);
        return matrix4;
    }

    private FloatMatrix3 _fromMatrix3f(Matrix3f matrix) {
        FloatMatrix3 matrix3 = new FloatMatrix3();
        _applyMatrix3f(matrix, matrix3);
        return matrix3;
    }

    private void _applyFloatMatrix3(FloatMatrix3 source, Matrix3f destination) {
        MATRIX3_BUFFER.rewind();
        source.store(MATRIX3_BUFFER);
        destination.load(MATRIX3_BUFFER);
    }

    private void _applyMatrix3f(Matrix3f source, FloatMatrix3 destination) {
        MATRIX3_BUFFER.rewind();
        source.store(MATRIX3_BUFFER);
        destination.load(MATRIX3_BUFFER);
    }

    private void _applyMatrix4f(Matrix4f source, FloatMatrix4 destination) {
        MATRIX4_BUFFER.rewind();
        source.store(MATRIX4_BUFFER);
        destination.load(MATRIX4_BUFFER);
    }

    private void _applyFloatMatrix4(FloatMatrix4 source, Matrix4f destination) {
        MATRIX4_BUFFER.rewind();
        source.store(MATRIX4_BUFFER);
        destination.load(MATRIX4_BUFFER);
    }
}
