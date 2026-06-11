package net.labymod.api.util.math.vector;

import java.nio.FloatBuffer;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Quaternion;
import org.joml.Matrix3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/vector/FloatMatrix3.class */
public class FloatMatrix3 {
    private static final FloatMatrix3 SCALE_MATRIX = new FloatMatrix3();
    private static final FloatMatrix3 QUATERNION_MATRIX = new FloatMatrix3();
    private float m00;
    private float m01;
    private float m02;
    private float m10;
    private float m11;
    private float m12;
    private float m20;
    private float m21;
    private float m22;

    public FloatMatrix3() {
    }

    public FloatMatrix3(FloatMatrix3 matrix) {
        this.m00 = matrix.m00;
        this.m01 = matrix.m01;
        this.m02 = matrix.m02;
        this.m10 = matrix.m10;
        this.m11 = matrix.m11;
        this.m12 = matrix.m12;
        this.m20 = matrix.m20;
        this.m21 = matrix.m21;
        this.m22 = matrix.m22;
    }

    public FloatMatrix3(Quaternion quaternion) {
        setQuaternion(quaternion);
    }

    public static FloatMatrix3 scaleMatrix(float x, float y, float z) {
        SCALE_MATRIX.identity();
        SCALE_MATRIX.m00 = x;
        SCALE_MATRIX.m11 = y;
        SCALE_MATRIX.m22 = z;
        return SCALE_MATRIX;
    }

    public float getM00() {
        return this.m00;
    }

    public void setM00(float m00) {
        this.m00 = m00;
    }

    public float getM01() {
        return this.m01;
    }

    public void setM01(float m01) {
        this.m01 = m01;
    }

    public float getM02() {
        return this.m02;
    }

    public void setM02(float m02) {
        this.m02 = m02;
    }

    public float getM10() {
        return this.m10;
    }

    public void setM10(float m10) {
        this.m10 = m10;
    }

    public float getM11() {
        return this.m11;
    }

    public void setM11(float m11) {
        this.m11 = m11;
    }

    public float getM12() {
        return this.m12;
    }

    public void setM12(float m12) {
        this.m12 = m12;
    }

    public float getM20() {
        return this.m20;
    }

    public void setM20(float m20) {
        this.m20 = m20;
    }

    public float getM21() {
        return this.m21;
    }

    public void setM21(float m21) {
        this.m21 = m21;
    }

    public float getM22() {
        return this.m22;
    }

    public void setM22(float m22) {
        this.m22 = m22;
    }

    public float transformVectorX(float x, float y, float z) {
        return (this.m00 * x) + (this.m01 * y) + (this.m02 * z);
    }

    public float transformVectorX(FloatVector3 vector) {
        return transformVectorX(vector.getX(), vector.getY(), vector.getZ());
    }

    public float transformVectorY(float x, float y, float z) {
        return (this.m10 * x) + (this.m11 * y) + (this.m12 * z);
    }

    public float transformVectorY(FloatVector3 vector) {
        return transformVectorY(vector.getX(), vector.getY(), vector.getZ());
    }

    public float transformVectorZ(float x, float y, float z) {
        return (this.m20 * x) + (this.m21 * y) + (this.m22 * z);
    }

    public float transformVectorZ(FloatVector3 vector) {
        return transformVectorZ(vector.getX(), vector.getY(), vector.getZ());
    }

    public void setQuaternion(Quaternion quaternion) {
        float x = quaternion.getX();
        float y = quaternion.getY();
        float z = quaternion.getZ();
        float w = quaternion.getW();
        float scaledX = 2.0f * x * x;
        float scaledY = 2.0f * y * y;
        float scaledZ = 2.0f * z * z;
        this.m00 = (1.0f - scaledY) - scaledZ;
        this.m11 = (1.0f - scaledZ) - scaledX;
        this.m22 = (1.0f - scaledX) - scaledY;
        float xy = x * y;
        float yz = y * z;
        float zx = z * x;
        float xw = x * w;
        float yw = y * w;
        float zw = z * w;
        this.m10 = 2.0f * (xy + zw);
        this.m01 = 2.0f * (xy - zw);
        this.m20 = 2.0f * (zx - yw);
        this.m02 = 2.0f * (zx + yw);
        this.m21 = 2.0f * (yz + xw);
        this.m12 = 2.0f * (yz - xw);
    }

    public FloatMatrix3 copy() {
        return new FloatMatrix3(this);
    }

    public void multiply(Quaternion quaternion) {
        if (quaternion.hasNoRotation()) {
            return;
        }
        QUATERNION_MATRIX.setQuaternion(quaternion);
        multiply(QUATERNION_MATRIX);
    }

    public void set(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    public void multiply(FloatMatrix3 value) {
        float m00 = MathHelper.fma(this.m00, value.m00, MathHelper.fma(this.m01, value.m10, this.m02 * value.m20));
        float m01 = MathHelper.fma(this.m00, value.m01, MathHelper.fma(this.m01, value.m11, this.m02 * value.m21));
        float m02 = MathHelper.fma(this.m00, value.m02, MathHelper.fma(this.m01, value.m12, this.m02 * value.m22));
        float m10 = MathHelper.fma(this.m10, value.m00, MathHelper.fma(this.m11, value.m10, this.m12 * value.m20));
        float m11 = MathHelper.fma(this.m10, value.m01, MathHelper.fma(this.m11, value.m11, this.m12 * value.m21));
        float m12 = MathHelper.fma(this.m10, value.m02, MathHelper.fma(this.m11, value.m12, this.m12 * value.m22));
        float m20 = MathHelper.fma(this.m20, value.m00, MathHelper.fma(this.m21, value.m10, this.m22 * value.m20));
        float m21 = MathHelper.fma(this.m20, value.m01, MathHelper.fma(this.m21, value.m11, this.m22 * value.m21));
        float m22 = MathHelper.fma(this.m20, value.m02, MathHelper.fma(this.m21, value.m12, this.m22 * value.m22));
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    public void multiply(float factor) {
        this.m00 *= factor;
        this.m01 *= factor;
        this.m02 *= factor;
        this.m10 *= factor;
        this.m11 *= factor;
        this.m12 *= factor;
        this.m20 *= factor;
        this.m21 *= factor;
        this.m22 *= factor;
    }

    public FloatMatrix3 identity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
        return this;
    }

    public void load(FloatBuffer buffer) {
        this.m00 = buffer.get(0);
        this.m01 = buffer.get(1);
        this.m02 = buffer.get(2);
        this.m10 = buffer.get(3);
        this.m11 = buffer.get(4);
        this.m12 = buffer.get(5);
        this.m20 = buffer.get(6);
        this.m21 = buffer.get(7);
        this.m22 = buffer.get(8);
    }

    public void store(FloatBuffer buffer) {
        buffer.put(0, this.m00);
        buffer.put(3, this.m01);
        buffer.put(6, this.m02);
        buffer.put(1, this.m10);
        buffer.put(4, this.m11);
        buffer.put(7, this.m12);
        buffer.put(2, this.m20);
        buffer.put(5, this.m21);
        buffer.put(8, this.m22);
    }

    public void store(FloatMatrix3 other) {
        this.m00 = other.m00;
        this.m01 = other.m01;
        this.m02 = other.m02;
        this.m10 = other.m10;
        this.m11 = other.m11;
        this.m12 = other.m12;
        this.m20 = other.m20;
        this.m21 = other.m21;
        this.m22 = other.m22;
    }

    public Matrix3f toMatrix3f() {
        return new Matrix3f(this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22);
    }

    public String toString() {
        return "FloatMatrix3:\n" + this.m00 + " " + this.m01 + " " + this.m02 + "\n" + this.m10 + " " + this.m11 + " " + this.m12 + "\n" + this.m20 + " " + this.m21 + " " + this.m22;
    }
}
