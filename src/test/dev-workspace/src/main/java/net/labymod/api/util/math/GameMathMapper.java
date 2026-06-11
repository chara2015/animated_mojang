package net.labymod.api.util.math;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix4;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/GameMathMapper.class */
@Referenceable
public interface GameMathMapper {
    FloatMatrix4 fromMatrix4f(Object obj);

    <T> T toMatrix4f(FloatMatrix4 floatMatrix4);

    FloatMatrix3 fromMatrix3f(Object obj);

    <T> T toMatrix3f(FloatMatrix3 floatMatrix3);

    void applyFloatMatrix4(FloatMatrix4 floatMatrix4, Object obj);

    void applyMatrix4f(Object obj, FloatMatrix4 floatMatrix4);

    void applyFloatMatrix3(FloatMatrix3 floatMatrix3, Object obj);

    void applyMatrix3f(Object obj, FloatMatrix3 floatMatrix3);

    Quaternion fromQuaternion(Object obj);

    <T> T toQuaternion(Quaternion quaternion);

    AxisAlignedBoundingBox fromAABB(Object obj);

    <T> T toAABB(AxisAlignedBoundingBox axisAlignedBoundingBox);

    default Matrix3f toJomlMatrix3f(Object matrix) {
        return (Matrix3f) CastUtil.requireInstanceOf(matrix, Matrix3f.class);
    }

    default Matrix4f toJomlMatrix4f(Object matrix) {
        return (Matrix4f) CastUtil.requireInstanceOf(matrix, Matrix4f.class);
    }

    /* JADX WARN: Multi-variable type inference failed */
    default <T> T toMatrix4f(Matrix4f matrix4f) {
        return matrix4f;
    }
}
