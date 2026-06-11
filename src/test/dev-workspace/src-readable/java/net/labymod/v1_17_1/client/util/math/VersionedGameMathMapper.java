package net.labymod.v1_17_1.client.util.math;

import java.nio.FloatBuffer;
import javax.inject.Singleton;
import net.labymod.api.models.Implements;
import net.labymod.api.util.Buffers;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix4;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/util/math/VersionedGameMathMapper.class */
@Singleton
@Implements(GameMathMapper.class)
public class VersionedGameMathMapper implements GameMathMapper {
    private static final FloatBuffer MATRIX4_BUFFER = Buffers.createFloatBuffer(16);
    private static final FloatBuffer MATRIX3_BUFFER = Buffers.createFloatBuffer(12);
    private static final FloatMatrix4 MATRIX4F_IDENTITY = new FloatMatrix4().identity();
    private static final FloatMatrix3 MATRIX3F_IDENTITY = new FloatMatrix3().identity();
    private static final Quaternion QUATERNION_IDENTITY = new Quaternion();
    private static final AxisAlignedBoundingBox BOX_IDENTITY = new AxisAlignedBoundingBox();
    private static final FloatMatrix3 SHARED_MATRIX3F = new FloatMatrix3();
    private static final FloatMatrix4 SHARED_MATRIX4F = new FloatMatrix4();
    private static final c MOJANG_SHARED_MATRIX3F = new c();
    private static final d MOJANG_SHARED_MATRIX4F = new d();

    @Override // net.labymod.api.util.math.GameMathMapper
    public FloatMatrix4 fromMatrix4f(Object matrix) {
        if (matrix instanceof d) {
            d mojangMat = (d) matrix;
            return _fromMatrix4f(mojangMat);
        }
        if (matrix instanceof Matrix4f) {
            Matrix4f jomlMat = (Matrix4f) matrix;
            return new FloatMatrix4(jomlMat);
        }
        return MATRIX4F_IDENTITY;
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public Matrix3f toJomlMatrix3f(Object matrix) {
        if (!(matrix instanceof c)) {
            return new Matrix3f();
        }
        c matrix3f = (c) matrix;
        matrix3f.d(MATRIX3_BUFFER);
        Matrix3f mat = new Matrix3f();
        mat.set(MATRIX3_BUFFER);
        return mat;
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public Matrix4f toJomlMatrix4f(Object matrix) {
        if (!(matrix instanceof d)) {
            return new Matrix4f();
        }
        d matrix4f = (d) matrix;
        Matrix4f mat = new Matrix4f();
        mat.set(matrix4f.a, matrix4f.e, matrix4f.i, matrix4f.m, matrix4f.b, matrix4f.f, matrix4f.j, matrix4f.n, matrix4f.c, matrix4f.g, matrix4f.k, matrix4f.o, matrix4f.d, matrix4f.h, matrix4f.l, matrix4f.p);
        return mat;
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toMatrix4f(FloatMatrix4 floatMatrix4) {
        return (T) _toMatrix4f(floatMatrix4);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, d] */
    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toMatrix4f(Matrix4f matrix4f) {
        ?? r0 = (T) new d();
        ((d) r0).a = matrix4f.m00();
        ((d) r0).b = matrix4f.m10();
        ((d) r0).c = matrix4f.m20();
        ((d) r0).d = matrix4f.m30();
        ((d) r0).e = matrix4f.m01();
        ((d) r0).f = matrix4f.m11();
        ((d) r0).g = matrix4f.m21();
        ((d) r0).h = matrix4f.m31();
        ((d) r0).i = matrix4f.m02();
        ((d) r0).j = matrix4f.m12();
        ((d) r0).k = matrix4f.m22();
        ((d) r0).l = matrix4f.m32();
        ((d) r0).m = matrix4f.m03();
        ((d) r0).n = matrix4f.m13();
        ((d) r0).o = matrix4f.m23();
        ((d) r0).p = matrix4f.m33();
        return r0;
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public FloatMatrix3 fromMatrix3f(Object matrix) {
        if (!(matrix instanceof c)) {
            return MATRIX3F_IDENTITY;
        }
        c matrix3f = (c) matrix;
        return _fromMatrix3f(matrix3f);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toMatrix3f(FloatMatrix3 floatMatrix3) {
        return (T) _toMatrix3f(floatMatrix3);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyFloatMatrix4(FloatMatrix4 source, Object destination) {
        if (!(destination instanceof d)) {
            return;
        }
        d matrix4f = (d) destination;
        _applyFloatMatrix4(source, matrix4f);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyMatrix4f(Object source, FloatMatrix4 destination) {
        if (!(source instanceof d)) {
            return;
        }
        d matrix4f = (d) source;
        _applyMatrix4f(matrix4f, destination);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyFloatMatrix3(FloatMatrix3 source, Object destination) {
        if (!(destination instanceof c)) {
            return;
        }
        c matrix3f = (c) destination;
        _applyFloatMatrix3(source, matrix3f);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyMatrix3f(Object source, FloatMatrix3 destination) {
        if (!(source instanceof c)) {
            return;
        }
        c matrix3f = (c) source;
        _applyMatrix3f(matrix3f, destination);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public Quaternion fromQuaternion(Object quaternion) {
        if (!(quaternion instanceof g)) {
            return QUATERNION_IDENTITY;
        }
        g gameQuaternion = (g) quaternion;
        return new Quaternion(gameQuaternion.e(), gameQuaternion.f(), gameQuaternion.g(), gameQuaternion.h());
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toQuaternion(Quaternion quaternion) {
        return (T) new g(quaternion.getX(), quaternion.getY(), quaternion.getZ(), quaternion.getW());
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public AxisAlignedBoundingBox fromAABB(Object aabb) {
        if (!(aabb instanceof dmv)) {
            return BOX_IDENTITY;
        }
        dmv gameAABB = (dmv) aabb;
        return new AxisAlignedBoundingBox(gameAABB.a, gameAABB.b, gameAABB.c, gameAABB.d, gameAABB.e, gameAABB.f);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toAABB(AxisAlignedBoundingBox axisAlignedBoundingBox) {
        return (T) new dmv(axisAlignedBoundingBox.getMinX(), axisAlignedBoundingBox.getMinY(), axisAlignedBoundingBox.getMinZ(), axisAlignedBoundingBox.getMaxX(), axisAlignedBoundingBox.getMaxY(), axisAlignedBoundingBox.getMaxZ());
    }

    private d _toMatrix4f(FloatMatrix4 matrix) {
        MOJANG_SHARED_MATRIX4F.b();
        _applyFloatMatrix4(matrix, MOJANG_SHARED_MATRIX4F);
        return MOJANG_SHARED_MATRIX4F;
    }

    private c _toMatrix3f(FloatMatrix3 matrix) {
        MOJANG_SHARED_MATRIX3F.c();
        _applyFloatMatrix3(matrix, MOJANG_SHARED_MATRIX3F);
        return MOJANG_SHARED_MATRIX3F;
    }

    private FloatMatrix4 _fromMatrix4f(d matrix) {
        SHARED_MATRIX4F.identity();
        _applyMatrix4f(matrix, SHARED_MATRIX4F);
        return SHARED_MATRIX4F;
    }

    private FloatMatrix3 _fromMatrix3f(c matrix) {
        SHARED_MATRIX3F.identity();
        _applyMatrix3f(matrix, SHARED_MATRIX3F);
        return SHARED_MATRIX3F;
    }

    private void _applyFloatMatrix3(FloatMatrix3 source, c destination) {
        MATRIX3_BUFFER.rewind();
        source.store(MATRIX3_BUFFER);
        destination.a(MATRIX3_BUFFER);
    }

    private void _applyMatrix3f(c source, FloatMatrix3 destination) {
        MATRIX3_BUFFER.rewind();
        source.c(MATRIX3_BUFFER);
        destination.load(MATRIX3_BUFFER);
    }

    private void _applyMatrix4f(d source, FloatMatrix4 destination) {
        MATRIX4_BUFFER.rewind();
        source.c(MATRIX4_BUFFER);
        destination.load(MATRIX4_BUFFER);
    }

    private void _applyFloatMatrix4(FloatMatrix4 source, d destination) {
        MATRIX4_BUFFER.rewind();
        source.store(MATRIX4_BUFFER);
        destination.a(MATRIX4_BUFFER);
    }
}
