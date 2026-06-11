package net.labymod.v1_16_5.client.util.math;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/util/math/VersionedGameMathMapper.class */
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
    private static final a MOJANG_SHARED_MATRIX3F = new a();
    private static final b MOJANG_SHARED_MATRIX4F = new b();

    @Override // net.labymod.api.util.math.GameMathMapper
    public FloatMatrix4 fromMatrix4f(Object matrix) {
        if (matrix instanceof b) {
            b mojangMat = (b) matrix;
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
        if (!(matrix instanceof a)) {
            return new Matrix3f();
        }
        a matrix3f = (a) matrix;
        Matrix3f mat = new Matrix3f();
        mat.set(matrix3f.a, matrix3f.d, matrix3f.g, matrix3f.b, matrix3f.e, matrix3f.h, matrix3f.c, matrix3f.f, matrix3f.i);
        return mat;
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public Matrix4f toJomlMatrix4f(Object matrix) {
        if (!(matrix instanceof b)) {
            return new Matrix4f();
        }
        b matrix4f = (b) matrix;
        Matrix4f mat = new Matrix4f();
        mat.set(matrix4f.a, matrix4f.e, matrix4f.i, matrix4f.m, matrix4f.b, matrix4f.f, matrix4f.j, matrix4f.n, matrix4f.c, matrix4f.g, matrix4f.k, matrix4f.o, matrix4f.d, matrix4f.h, matrix4f.l, matrix4f.p);
        return mat;
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toMatrix4f(FloatMatrix4 floatMatrix4) {
        return (T) _toMatrix4f(floatMatrix4);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, b] */
    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toMatrix4f(Matrix4f matrix4f) {
        ?? r0 = (T) new b();
        ((b) r0).a = matrix4f.m00();
        ((b) r0).b = matrix4f.m10();
        ((b) r0).c = matrix4f.m20();
        ((b) r0).d = matrix4f.m30();
        ((b) r0).e = matrix4f.m01();
        ((b) r0).f = matrix4f.m11();
        ((b) r0).g = matrix4f.m21();
        ((b) r0).h = matrix4f.m31();
        ((b) r0).i = matrix4f.m02();
        ((b) r0).j = matrix4f.m12();
        ((b) r0).k = matrix4f.m22();
        ((b) r0).l = matrix4f.m32();
        ((b) r0).m = matrix4f.m03();
        ((b) r0).n = matrix4f.m13();
        ((b) r0).o = matrix4f.m23();
        ((b) r0).p = matrix4f.m33();
        return r0;
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public FloatMatrix3 fromMatrix3f(Object matrix) {
        if (!(matrix instanceof a)) {
            return MATRIX3F_IDENTITY;
        }
        a matrix3f = (a) matrix;
        return _fromMatrix3f(matrix3f);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toMatrix3f(FloatMatrix3 floatMatrix3) {
        return (T) _toMatrix3f(floatMatrix3);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyFloatMatrix4(FloatMatrix4 source, Object destination) {
        if (!(destination instanceof b)) {
            return;
        }
        b matrix4f = (b) destination;
        _applyFloatMatrix4(source, matrix4f);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyMatrix4f(Object source, FloatMatrix4 destination) {
        if (!(source instanceof b)) {
            return;
        }
        b matrix4f = (b) source;
        _applyMatrix4f(matrix4f, destination);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyFloatMatrix3(FloatMatrix3 source, Object destination) {
        if (!(destination instanceof a)) {
            return;
        }
        a matrix3f = (a) destination;
        _applyFloatMatrix3(source, matrix3f);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public void applyMatrix3f(Object source, FloatMatrix3 destination) {
        if (!(source instanceof a)) {
            return;
        }
        a matrix3f = (a) source;
        _applyMatrix3f(matrix3f, destination);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public Quaternion fromQuaternion(Object quaternion) {
        if (!(quaternion instanceof d)) {
            return QUATERNION_IDENTITY;
        }
        d gameQuaternion = (d) quaternion;
        return new Quaternion(gameQuaternion.a(), gameQuaternion.b(), gameQuaternion.c(), gameQuaternion.d());
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toQuaternion(Quaternion quaternion) {
        return (T) new d(quaternion.getX(), quaternion.getY(), quaternion.getZ(), quaternion.getW());
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public AxisAlignedBoundingBox fromAABB(Object aabb) {
        if (!(aabb instanceof dci)) {
            return BOX_IDENTITY;
        }
        dci gameAABB = (dci) aabb;
        return new AxisAlignedBoundingBox(gameAABB.a, gameAABB.b, gameAABB.c, gameAABB.d, gameAABB.e, gameAABB.f);
    }

    @Override // net.labymod.api.util.math.GameMathMapper
    public <T> T toAABB(AxisAlignedBoundingBox axisAlignedBoundingBox) {
        return (T) new dci(axisAlignedBoundingBox.getMinX(), axisAlignedBoundingBox.getMinY(), axisAlignedBoundingBox.getMinZ(), axisAlignedBoundingBox.getMaxX(), axisAlignedBoundingBox.getMaxY(), axisAlignedBoundingBox.getMaxZ());
    }

    private b _toMatrix4f(FloatMatrix4 matrix) {
        MOJANG_SHARED_MATRIX4F.a();
        _applyFloatMatrix4(matrix, MOJANG_SHARED_MATRIX4F);
        return MOJANG_SHARED_MATRIX4F;
    }

    private a _toMatrix3f(FloatMatrix3 matrix) {
        MOJANG_SHARED_MATRIX3F.c();
        _applyFloatMatrix3(matrix, MOJANG_SHARED_MATRIX3F);
        return MOJANG_SHARED_MATRIX3F;
    }

    private FloatMatrix4 _fromMatrix4f(b matrix) {
        SHARED_MATRIX4F.identity();
        _applyMatrix4f(matrix, SHARED_MATRIX4F);
        return SHARED_MATRIX4F;
    }

    private FloatMatrix3 _fromMatrix3f(a matrix) {
        SHARED_MATRIX3F.identity();
        _applyMatrix3f(matrix, SHARED_MATRIX3F);
        return SHARED_MATRIX3F;
    }

    private void _applyFloatMatrix3(FloatMatrix3 source, a destination) {
        MATRIX3_BUFFER.rewind();
        source.store(MATRIX3_BUFFER);
        load(MATRIX3_BUFFER, destination);
    }

    private void _applyMatrix3f(a source, FloatMatrix3 destination) {
        MATRIX3_BUFFER.rewind();
        store(MATRIX3_BUFFER, source);
        destination.load(MATRIX3_BUFFER);
    }

    private void _applyMatrix4f(b source, FloatMatrix4 destination) {
        MATRIX4_BUFFER.rewind();
        source.a(MATRIX4_BUFFER);
        destination.load(MATRIX4_BUFFER);
    }

    private void _applyFloatMatrix4(FloatMatrix4 source, b destination) {
        MATRIX4_BUFFER.rewind();
        source.store(MATRIX4_BUFFER);
        load(MATRIX4_BUFFER, destination);
    }

    public void store(FloatBuffer buffer, a matrix3f) {
        buffer.put(bufferIndex(0, 0, 3), matrix3f.a);
        buffer.put(bufferIndex(0, 1, 3), matrix3f.b);
        buffer.put(bufferIndex(0, 2, 3), matrix3f.c);
        buffer.put(bufferIndex(1, 0, 3), matrix3f.d);
        buffer.put(bufferIndex(1, 1, 3), matrix3f.e);
        buffer.put(bufferIndex(1, 2, 3), matrix3f.f);
        buffer.put(bufferIndex(2, 0, 3), matrix3f.g);
        buffer.put(bufferIndex(2, 1, 3), matrix3f.h);
        buffer.put(bufferIndex(2, 2, 3), matrix3f.i);
    }

    public void load(FloatBuffer buffer, a matrix3f) {
        matrix3f.a = buffer.get(bufferIndex(0, 0, 3));
        matrix3f.b = buffer.get(bufferIndex(0, 1, 3));
        matrix3f.c = buffer.get(bufferIndex(0, 2, 3));
        matrix3f.d = buffer.get(bufferIndex(1, 0, 3));
        matrix3f.e = buffer.get(bufferIndex(1, 1, 3));
        matrix3f.f = buffer.get(bufferIndex(1, 2, 3));
        matrix3f.g = buffer.get(bufferIndex(2, 0, 3));
        matrix3f.h = buffer.get(bufferIndex(2, 1, 3));
        matrix3f.i = buffer.get(bufferIndex(2, 2, 3));
    }

    public void load(FloatBuffer buffer, b matrix4f) {
        matrix4f.a = buffer.get(bufferIndex(0, 0, 4));
        matrix4f.b = buffer.get(bufferIndex(0, 1, 4));
        matrix4f.c = buffer.get(bufferIndex(0, 2, 4));
        matrix4f.d = buffer.get(bufferIndex(0, 3, 4));
        matrix4f.e = buffer.get(bufferIndex(1, 0, 4));
        matrix4f.f = buffer.get(bufferIndex(1, 1, 4));
        matrix4f.g = buffer.get(bufferIndex(1, 2, 4));
        matrix4f.h = buffer.get(bufferIndex(1, 3, 4));
        matrix4f.i = buffer.get(bufferIndex(2, 0, 4));
        matrix4f.j = buffer.get(bufferIndex(2, 1, 4));
        matrix4f.k = buffer.get(bufferIndex(2, 2, 4));
        matrix4f.l = buffer.get(bufferIndex(2, 3, 4));
        matrix4f.m = buffer.get(bufferIndex(3, 0, 4));
        matrix4f.n = buffer.get(bufferIndex(3, 1, 4));
        matrix4f.o = buffer.get(bufferIndex(3, 2, 4));
        matrix4f.p = buffer.get(bufferIndex(3, 3, 4));
    }

    private static int bufferIndex(int i, int j, int v) {
        return (j * v) + i;
    }
}
