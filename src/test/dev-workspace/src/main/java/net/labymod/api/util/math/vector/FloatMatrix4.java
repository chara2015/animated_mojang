package net.labymod.api.util.math.vector;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Quaternion;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/vector/FloatMatrix4.class */
public class FloatMatrix4 {
    private static final FloatVector3 ROTATION_VECTOR = new FloatVector3(0.0f, 0.0f, 0.0f);
    private static final FloatMatrix4 SCALE_MATRIX = newIdentity();
    private static final FloatMatrix4 QUATERNION_MATRIX = newIdentity();
    private float m00;
    private float m01;
    private float m02;
    private float m03;
    private float m10;
    private float m11;
    private float m12;
    private float m13;
    private float m20;
    private float m21;
    private float m22;
    private float m23;
    private float m30;
    private float m31;
    private float m32;
    private float m33;

    public FloatMatrix4() {
    }

    public FloatMatrix4(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    public FloatMatrix4(FloatMatrix4 matrix) {
        this.m00 = matrix.m00;
        this.m01 = matrix.m01;
        this.m02 = matrix.m02;
        this.m03 = matrix.m03;
        this.m10 = matrix.m10;
        this.m11 = matrix.m11;
        this.m12 = matrix.m12;
        this.m13 = matrix.m13;
        this.m20 = matrix.m20;
        this.m21 = matrix.m21;
        this.m22 = matrix.m22;
        this.m23 = matrix.m23;
        this.m30 = matrix.m30;
        this.m31 = matrix.m31;
        this.m32 = matrix.m32;
        this.m33 = matrix.m33;
    }

    public FloatMatrix4(Matrix4f matrix) {
        this.m00 = matrix.m00();
        this.m01 = matrix.m10();
        this.m02 = matrix.m20();
        this.m03 = matrix.m30();
        this.m10 = matrix.m01();
        this.m11 = matrix.m11();
        this.m12 = matrix.m21();
        this.m13 = matrix.m31();
        this.m20 = matrix.m02();
        this.m21 = matrix.m12();
        this.m22 = matrix.m22();
        this.m23 = matrix.m32();
        this.m30 = matrix.m03();
        this.m31 = matrix.m13();
        this.m32 = matrix.m23();
        this.m33 = matrix.m33();
    }

    public FloatMatrix4(Quaternion quaternion) {
        setQuaternion(quaternion);
    }

    public static FloatMatrix4 newIdentity() {
        return new FloatMatrix4().identity();
    }

    public void store(FloatBuffer buffer) {
        store(0, buffer);
    }

    public void store(ByteBuffer buffer) {
        store(0, buffer);
    }

    public void store(int position, FloatBuffer buffer) {
        buffer.put(position, this.m00);
        buffer.put(position + 4, this.m01);
        buffer.put(position + 8, this.m02);
        buffer.put(position + 12, this.m03);
        buffer.put(position + 1, this.m10);
        buffer.put(position + 5, this.m11);
        buffer.put(position + 9, this.m12);
        buffer.put(position + 13, this.m13);
        buffer.put(position + 2, this.m20);
        buffer.put(position + 6, this.m21);
        buffer.put(position + 10, this.m22);
        buffer.put(position + 14, this.m23);
        buffer.put(position + 3, this.m30);
        buffer.put(position + 7, this.m31);
        buffer.put(position + 11, this.m32);
        buffer.put(position + 15, this.m33);
    }

    public void store(int position, ByteBuffer buffer) {
        buffer.putFloat(position, this.m00);
        buffer.putFloat(position + 16, this.m01);
        buffer.putFloat(position + 32, this.m02);
        buffer.putFloat(position + 48, this.m03);
        buffer.putFloat(position + 4, this.m10);
        buffer.putFloat(position + 20, this.m11);
        buffer.putFloat(position + 36, this.m12);
        buffer.putFloat(position + 52, this.m13);
        buffer.putFloat(position + 8, this.m20);
        buffer.putFloat(position + 24, this.m21);
        buffer.putFloat(position + 40, this.m22);
        buffer.putFloat(position + 56, this.m23);
        buffer.putFloat(position + 12, this.m30);
        buffer.putFloat(position + 28, this.m31);
        buffer.putFloat(position + 44, this.m32);
        buffer.putFloat(position + 60, this.m33);
    }

    public void store(float[] buffer) {
        store(0, buffer);
    }

    public void store(int position, float[] buffer) {
        buffer[position] = this.m00;
        buffer[position + 4] = this.m01;
        buffer[position + 8] = this.m02;
        buffer[position + 12] = this.m03;
        buffer[position + 1] = this.m10;
        buffer[position + 5] = this.m11;
        buffer[position + 9] = this.m12;
        buffer[position + 13] = this.m13;
        buffer[position + 2] = this.m20;
        buffer[position + 6] = this.m21;
        buffer[position + 10] = this.m22;
        buffer[position + 14] = this.m23;
        buffer[position + 3] = this.m30;
        buffer[position + 7] = this.m31;
        buffer[position + 11] = this.m32;
        buffer[position + 15] = this.m33;
    }

    public void load(FloatBuffer buffer) {
        load(0, buffer);
    }

    public void load(int position, FloatBuffer buffer) {
        this.m00 = buffer.get(position);
        this.m01 = buffer.get(position + 4);
        this.m02 = buffer.get(position + 8);
        this.m03 = buffer.get(position + 12);
        this.m10 = buffer.get(position + 1);
        this.m11 = buffer.get(position + 5);
        this.m12 = buffer.get(position + 9);
        this.m13 = buffer.get(position + 13);
        this.m20 = buffer.get(position + 2);
        this.m21 = buffer.get(position + 6);
        this.m22 = buffer.get(position + 10);
        this.m23 = buffer.get(position + 14);
        this.m30 = buffer.get(position + 3);
        this.m31 = buffer.get(position + 7);
        this.m32 = buffer.get(position + 11);
        this.m33 = buffer.get(position + 15);
    }

    public void load(DoubleBuffer buffer) {
        this.m00 = (float) buffer.get(0);
        this.m01 = (float) buffer.get(4);
        this.m02 = (float) buffer.get(8);
        this.m03 = (float) buffer.get(12);
        this.m10 = (float) buffer.get(1);
        this.m11 = (float) buffer.get(5);
        this.m12 = (float) buffer.get(9);
        this.m13 = (float) buffer.get(13);
        this.m20 = (float) buffer.get(2);
        this.m21 = (float) buffer.get(6);
        this.m22 = (float) buffer.get(10);
        this.m23 = (float) buffer.get(14);
        this.m30 = (float) buffer.get(3);
        this.m31 = (float) buffer.get(7);
        this.m32 = (float) buffer.get(11);
        this.m33 = (float) buffer.get(15);
    }

    public void setScale(FloatVector3 scaleVector) {
        this.m00 = scaleVector.getX();
        this.m11 = scaleVector.getY();
        this.m22 = scaleVector.getZ();
        this.m33 = 1.0f;
    }

    public void setScale(float x, float y, float z) {
        this.m00 = x;
        this.m11 = y;
        this.m22 = z;
        this.m33 = 1.0f;
    }

    public void scale(float x, float y, float z) {
        SCALE_MATRIX.identity();
        SCALE_MATRIX.m00 = x;
        SCALE_MATRIX.m11 = y;
        SCALE_MATRIX.m22 = z;
        multiply(SCALE_MATRIX);
    }

    public static FloatMatrix4 scaleMatrix(float scaleFactor) {
        return scaleMatrix(scaleFactor, scaleFactor, scaleFactor);
    }

    public static FloatMatrix4 scaleMatrix(float scaleX, float scaleY, float scaleZ) {
        SCALE_MATRIX.identity();
        SCALE_MATRIX.m00 = scaleX;
        SCALE_MATRIX.m11 = scaleY;
        SCALE_MATRIX.m22 = scaleZ;
        return SCALE_MATRIX;
    }

    public static FloatMatrix4 createTranslateMatrix(float x, float y, float z) {
        FloatMatrix4 translateMatrix = new FloatMatrix4();
        translateMatrix.m00 = 1.0f;
        translateMatrix.m11 = 1.0f;
        translateMatrix.m22 = 1.0f;
        translateMatrix.m33 = 1.0f;
        translateMatrix.m03 = x;
        translateMatrix.m13 = y;
        translateMatrix.m23 = z;
        return translateMatrix;
    }

    public static FloatMatrix4 orthographic(float width, float height, float zNear, float zFar) {
        FloatMatrix4 var0 = new FloatMatrix4();
        var0.m00 = 2.0f / width;
        var0.m11 = 2.0f / height;
        float var1 = zFar - zNear;
        var0.m22 = (-2.0f) / var1;
        var0.m33 = 1.0f;
        var0.m03 = -1.0f;
        var0.m13 = 1.0f;
        var0.m23 = (-(zFar + zNear)) / var1;
        return var0;
    }

    public static FloatMatrix4 orthographic(float left, float right, float bottom, float top, float zNear, float zFar) {
        FloatMatrix4 matrix = new FloatMatrix4();
        float var1 = right - left;
        float var2 = bottom - top;
        float var3 = zFar - zNear;
        matrix.m00 = 2.0f / var1;
        matrix.m11 = 2.0f / var2;
        matrix.m22 = (-2.0f) / var3;
        matrix.m03 = (-(right + left)) / var1;
        matrix.m13 = (-(bottom + top)) / var2;
        matrix.m23 = (-(zFar + zNear)) / var3;
        matrix.m33 = 1.0f;
        return matrix;
    }

    public FloatMatrix4 setOrthographic(float width, float height, float zNear, float zFar) {
        this.m00 = 2.0f / width;
        this.m11 = 2.0f / height;
        float var1 = zFar - zNear;
        this.m22 = (-2.0f) / var1;
        this.m33 = 1.0f;
        this.m03 = -1.0f;
        this.m13 = 1.0f;
        this.m23 = (-(zFar + zNear)) / var1;
        return this;
    }

    public FloatMatrix4 setOrthographic(float left, float right, float bottom, float top, float zNear, float zFar) {
        float var1 = right - left;
        float var2 = -(bottom - top);
        float var3 = zFar - zNear;
        this.m00 = 2.0f / var1;
        this.m11 = 2.0f / var2;
        this.m22 = (-2.0f) / var3;
        this.m03 = (-(right + left)) / var1;
        this.m13 = (-(bottom + top)) / var2;
        this.m23 = (-(zFar + zNear)) / var3;
        this.m33 = 1.0f;
        return this;
    }

    public void setPerspective(double fov, float aspect, float nearFar, float depthFar) {
        float var0 = (float) (1.0d / Math.tan((fov * 0.01745329238474369d) / 2.0d));
        this.m00 = var0 / aspect;
        this.m11 = var0;
        this.m22 = (depthFar + nearFar) / (nearFar - depthFar);
        this.m32 = -1.0f;
        this.m23 = ((2.0f * depthFar) * nearFar) / (nearFar - depthFar);
    }

    public FloatMatrix4 copy() {
        return new FloatMatrix4(this);
    }

    public void translate(float x, float y, float z) {
        this.m03 += x;
        this.m13 += y;
        this.m23 += z;
    }

    public void rotate(float angle, float x, float y, float z) {
        ROTATION_VECTOR.set(x, y, z);
        multiply(ROTATION_VECTOR.rotationDegrees(angle));
    }

    public void rotateRadians(float radians, float x, float y, float z) {
        ROTATION_VECTOR.set(x, y, z);
        multiply(ROTATION_VECTOR.rotation(radians, false));
    }

    public void translate(FloatVector3 vector) {
        translate(vector.getX(), vector.getY(), vector.getZ());
    }

    public void multiply(FloatMatrix4 matrix) {
        float m00 = MathHelper.fma(this.m00, matrix.m00, MathHelper.fma(this.m01, matrix.m10, MathHelper.fma(this.m02, matrix.m20, this.m03 * matrix.m30)));
        float m01 = MathHelper.fma(this.m00, matrix.m01, MathHelper.fma(this.m01, matrix.m11, MathHelper.fma(this.m02, matrix.m21, this.m03 * matrix.m31)));
        float m02 = MathHelper.fma(this.m00, matrix.m02, MathHelper.fma(this.m01, matrix.m12, MathHelper.fma(this.m02, matrix.m22, this.m03 * matrix.m32)));
        float m03 = MathHelper.fma(this.m00, matrix.m03, MathHelper.fma(this.m01, matrix.m13, MathHelper.fma(this.m02, matrix.m23, this.m03 * matrix.m33)));
        float m10 = MathHelper.fma(this.m10, matrix.m00, MathHelper.fma(this.m11, matrix.m10, MathHelper.fma(this.m12, matrix.m20, this.m13 * matrix.m30)));
        float m11 = MathHelper.fma(this.m10, matrix.m01, MathHelper.fma(this.m11, matrix.m11, MathHelper.fma(this.m12, matrix.m21, this.m13 * matrix.m31)));
        float m12 = MathHelper.fma(this.m10, matrix.m02, MathHelper.fma(this.m11, matrix.m12, MathHelper.fma(this.m12, matrix.m22, this.m13 * matrix.m32)));
        float m13 = MathHelper.fma(this.m10, matrix.m03, MathHelper.fma(this.m11, matrix.m13, MathHelper.fma(this.m12, matrix.m23, this.m13 * matrix.m33)));
        float m20 = MathHelper.fma(this.m20, matrix.m00, MathHelper.fma(this.m21, matrix.m10, MathHelper.fma(this.m22, matrix.m20, this.m23 * matrix.m30)));
        float m21 = MathHelper.fma(this.m20, matrix.m01, MathHelper.fma(this.m21, matrix.m11, MathHelper.fma(this.m22, matrix.m21, this.m23 * matrix.m31)));
        float m22 = MathHelper.fma(this.m20, matrix.m02, MathHelper.fma(this.m21, matrix.m12, MathHelper.fma(this.m22, matrix.m22, this.m23 * matrix.m32)));
        float m23 = MathHelper.fma(this.m20, matrix.m03, MathHelper.fma(this.m21, matrix.m13, MathHelper.fma(this.m22, matrix.m23, this.m23 * matrix.m33)));
        float m30 = MathHelper.fma(this.m30, matrix.m00, MathHelper.fma(this.m31, matrix.m10, MathHelper.fma(this.m32, matrix.m20, this.m33 * matrix.m30)));
        float m31 = MathHelper.fma(this.m30, matrix.m01, MathHelper.fma(this.m31, matrix.m11, MathHelper.fma(this.m32, matrix.m21, this.m33 * matrix.m31)));
        float m32 = MathHelper.fma(this.m30, matrix.m02, MathHelper.fma(this.m31, matrix.m12, MathHelper.fma(this.m32, matrix.m22, this.m33 * matrix.m32)));
        float m33 = MathHelper.fma(this.m30, matrix.m03, MathHelper.fma(this.m31, matrix.m13, MathHelper.fma(this.m32, matrix.m23, this.m33 * matrix.m33)));
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    public void set(FloatMatrix4 matrix4) {
        this.m00 = matrix4.m00;
        this.m01 = matrix4.m01;
        this.m02 = matrix4.m02;
        this.m03 = matrix4.m03;
        this.m10 = matrix4.m10;
        this.m11 = matrix4.m11;
        this.m12 = matrix4.m12;
        this.m13 = matrix4.m13;
        this.m20 = matrix4.m20;
        this.m21 = matrix4.m21;
        this.m22 = matrix4.m22;
        this.m23 = matrix4.m23;
        this.m30 = matrix4.m30;
        this.m31 = matrix4.m31;
        this.m32 = matrix4.m32;
        this.m33 = matrix4.m33;
    }

    public void rotation(Quaternion quaternion, boolean degrees) {
        float x = quaternion.getX();
        float y = quaternion.getY();
        float z = quaternion.getZ();
        FloatVector3 xRotation = FloatVector3.XP;
        FloatVector3 yRotation = FloatVector3.YP;
        FloatVector3 zRotation = FloatVector3.ZP;
        if (degrees) {
            multiply(zRotation.rotationDegrees(z));
            multiply(yRotation.rotationDegrees(y));
            multiply(xRotation.rotationDegrees(x));
        } else {
            multiply(zRotation.rotation(z, false));
            multiply(yRotation.rotation(y, false));
            multiply(xRotation.rotation(x, false));
        }
    }

    public void set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
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
        this.m33 = 1.0f;
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

    public float getM03() {
        return this.m03;
    }

    public void setM03(float m03) {
        this.m03 = m03;
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

    public float getM13() {
        return this.m13;
    }

    public void setM13(float m13) {
        this.m13 = m13;
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

    public float getM23() {
        return this.m23;
    }

    public void setM23(float m23) {
        this.m23 = m23;
    }

    public float getM30() {
        return this.m30;
    }

    public void setM30(float m30) {
        this.m30 = m30;
    }

    public float getM31() {
        return this.m31;
    }

    public void setM31(float m31) {
        this.m31 = m31;
    }

    public float getM32() {
        return this.m32;
    }

    public void setM32(float m32) {
        this.m32 = m32;
    }

    public float getM33() {
        return this.m33;
    }

    public void setM33(float m33) {
        this.m33 = m33;
    }

    public void multiplyWithTranslation(float x, float y, float z) {
        this.m03 += fma(this.m00, x, fma(this.m01, y, this.m02 * z));
        this.m13 += fma(this.m10, x, fma(this.m11, y, this.m12 * z));
        this.m23 += fma(this.m20, x, fma(this.m21, y, this.m22 * z));
        this.m33 += fma(this.m30, x, fma(this.m31, y, this.m32 * z));
    }

    public void fastTranslate(float x, float y, float z) {
        this.m03 = fma(this.m00, x, fma(this.m01, y, fma(this.m02, z, this.m03)));
        this.m13 = fma(this.m10, x, fma(this.m11, y, fma(this.m12, z, this.m13)));
        this.m23 = fma(this.m20, x, fma(this.m21, y, fma(this.m22, z, this.m23)));
        this.m33 = fma(this.m30, x, fma(this.m31, y, fma(this.m32, z, this.m33)));
    }

    public void fastTranslate(FloatVector3 vector) {
        fastTranslate(vector.getX(), vector.getY(), vector.getZ());
    }

    public float transformVectorX(float x, float y, float z) {
        return (this.m00 * x) + (this.m01 * y) + (this.m02 * z) + this.m03;
    }

    public float transformVectorY(float x, float y, float z) {
        return (this.m10 * x) + (this.m11 * y) + (this.m12 * z) + this.m13;
    }

    public float transformVectorZ(float x, float y, float z) {
        return (this.m20 * x) + (this.m21 * y) + (this.m22 * z) + this.m23;
    }

    public void multiply(Quaternion quaternion) {
        if (quaternion.hasNoRotation()) {
            return;
        }
        QUATERNION_MATRIX.setQuaternion(quaternion);
        multiply(QUATERNION_MATRIX);
    }

    public FloatMatrix4 identity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
        return this;
    }

    public FloatMatrix4 invert() {
        float a = (this.m00 * this.m11) - (this.m01 * this.m10);
        float b = (this.m00 * this.m12) - (this.m02 * this.m10);
        float c = (this.m00 * this.m13) - (this.m03 * this.m10);
        float d = (this.m01 * this.m12) - (this.m02 * this.m11);
        float e = (this.m01 * this.m13) - (this.m03 * this.m11);
        float f = (this.m02 * this.m13) - (this.m03 * this.m12);
        float g = (this.m20 * this.m31) - (this.m21 * this.m30);
        float h = (this.m20 * this.m32) - (this.m22 * this.m30);
        float i = (this.m20 * this.m33) - (this.m23 * this.m30);
        float j = (this.m21 * this.m32) - (this.m22 * this.m31);
        float k = (this.m21 * this.m33) - (this.m23 * this.m31);
        float l = (this.m22 * this.m33) - (this.m23 * this.m32);
        float det = 1.0f / ((((((a * l) - (b * k)) + (c * j)) + (d * i)) - (e * h)) + (f * g));
        float nm00 = fma(this.m11, l, fma(-this.m12, k, this.m13 * j)) * det;
        float nm01 = fma(-this.m01, l, fma(this.m02, k, (-this.m03) * j)) * det;
        float nm02 = fma(this.m31, f, fma(-this.m32, e, this.m33 * d)) * det;
        float nm03 = fma(-this.m21, f, fma(this.m22, e, (-this.m23) * d)) * det;
        float nm10 = fma(-this.m10, l, fma(this.m12, i, (-this.m13) * h)) * det;
        float nm11 = fma(this.m00, l, fma(-this.m02, i, this.m03 * h)) * det;
        float nm12 = fma(-this.m30, f, fma(this.m32, c, (-this.m33) * b)) * det;
        float nm13 = fma(this.m20, f, fma(-this.m22, c, this.m23 * b)) * det;
        float nm20 = fma(this.m10, k, fma(-this.m11, i, this.m13 * g)) * det;
        float nm21 = fma(-this.m00, k, fma(this.m01, i, (-this.m03) * g)) * det;
        float nm22 = fma(this.m30, e, fma(-this.m31, c, this.m33 * a)) * det;
        float nm23 = fma(-this.m20, e, fma(this.m21, c, (-this.m23) * a)) * det;
        float nm30 = fma(-this.m10, j, fma(this.m11, h, (-this.m12) * g)) * det;
        float nm31 = fma(this.m00, j, fma(-this.m01, h, this.m02 * g)) * det;
        float nm32 = fma(-this.m30, d, fma(this.m31, b, (-this.m32) * a)) * det;
        float nm33 = fma(this.m20, d, fma(-this.m21, b, this.m22 * a)) * det;
        return new FloatMatrix4(nm00, nm01, nm02, nm03, nm10, nm11, nm12, nm13, nm20, nm21, nm22, nm23, nm30, nm31, nm32, nm33);
    }

    public FloatVector3 transformPosition(float x, float y, float z, FloatVector3 destination) {
        destination.set(x, y, z);
        destination.multiply(this);
        return destination;
    }

    private float fma(float a, float b, float c) {
        return MathHelper.fma(a, b, c);
    }

    public Matrix4f toMatrix4f() {
        Matrix4f dest = new Matrix4f();
        dest.set(getM00(), getM10(), getM20(), getM30(), getM01(), getM11(), getM21(), getM31(), getM02(), getM12(), getM22(), getM32(), getM03(), getM13(), getM23(), getM33());
        return dest;
    }

    public String toString() {
        return "FloatMatrix4:\n" + this.m00 + " " + this.m01 + " " + this.m02 + " " + this.m03 + "\n" + this.m10 + " " + this.m11 + " " + this.m12 + " " + this.m13 + "\n" + this.m20 + " " + this.m21 + " " + this.m22 + " " + this.m23 + "\n" + this.m30 + " " + this.m31 + " " + this.m32 + " " + this.m33 + "\n";
    }
}
