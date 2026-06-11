package net.labymod.api.util.math.vector;

import java.nio.FloatBuffer;
import java.util.function.Function;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Quaternion;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/vector/FloatVector3.class */
public class FloatVector3 {
    public static final FloatVector3 XN = new FloatVector3(-1.0f, 0.0f, 0.0f);
    public static final FloatVector3 XP = new FloatVector3(1.0f, 0.0f, 0.0f);
    public static final FloatVector3 YN = new FloatVector3(0.0f, -1.0f, 0.0f);
    public static final FloatVector3 YP = new FloatVector3(0.0f, 1.0f, 0.0f);
    public static final FloatVector3 ZN = new FloatVector3(0.0f, 0.0f, -1.0f);
    public static final FloatVector3 ZP = new FloatVector3(0.0f, 0.0f, 1.0f);
    public static final FloatVector3 ZERO = new FloatVector3(0.0f, 0.0f, 0.0f);
    public static final FloatVector3 ONE = new FloatVector3(1.0f, 1.0f, 1.0f);
    private static final Quaternion ROTATION_QUATERNION = new Quaternion();
    private static final Quaternion ROTATION_DEGREES_QUATERNION = new Quaternion();
    protected float x;
    protected float y;
    protected float z;

    public FloatVector3() {
        this(0.0f, 0.0f, 0.0f);
    }

    public FloatVector3(FloatVector3 vector) {
        this(vector.getX(), vector.getY(), vector.getZ());
    }

    public FloatVector3(float value) {
        this(value, value, value);
    }

    public FloatVector3(DoubleVector3 vector) {
        this((float) vector.getX(), (float) vector.getY(), (float) vector.getZ());
    }

    public FloatVector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void set(float x, float y, float z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    public void set(FloatVector3 vector) {
        set(vector.getX(), vector.getY(), vector.getZ());
    }

    public void transform(FloatMatrix3 matrix) {
        float x = getX();
        float y = getY();
        float z = getZ();
        setX((matrix.getM00() * x) + (matrix.getM01() * y) + (matrix.getM02() * z));
        setY((matrix.getM10() * x) + (matrix.getM11() * y) + (matrix.getM12() * z));
        setZ((matrix.getM20() * x) + (matrix.getM21() * y) + (matrix.getM22() * z));
    }

    public void transform(FloatVector3 other, FloatMatrix3 matrix) {
        float x = other.getX();
        float y = other.getY();
        float z = other.getZ();
        setX((matrix.getM00() * x) + (matrix.getM01() * y) + (matrix.getM02() * z));
        setY((matrix.getM10() * x) + (matrix.getM11() * y) + (matrix.getM12() * z));
        setZ((matrix.getM20() * x) + (matrix.getM21() * y) + (matrix.getM22() * z));
    }

    public void transform(Quaternion quaternion) {
        Quaternion quaternion2 = new Quaternion(quaternion);
        quaternion2.multiply(new Quaternion(getX(), getY(), getZ(), 0.0f));
        Quaternion quaternion3 = new Quaternion(quaternion);
        quaternion3.conj();
        quaternion2.multiply(quaternion3);
        set(quaternion2.getX(), quaternion2.getY(), quaternion2.getZ());
    }

    public FloatVector3 subReverse(FloatVector3 vector) {
        set(vector.getX() - getX(), vector.getY() - getY(), vector.getZ() - getZ());
        return this;
    }

    public FloatVector3 add(FloatVector3 vector) {
        return add(vector.getX(), vector.getY(), vector.getZ());
    }

    public FloatVector3 add(float x, float y, float z) {
        set(getX() + x, getY() + y, getZ() + z);
        return this;
    }

    public FloatVector3 sub(FloatVector3 vector) {
        return sub(vector.getX(), vector.getY(), vector.getZ());
    }

    public FloatVector3 sub(float x, float y, float z) {
        return add(-x, -y, -z);
    }

    public FloatVector3 multiply(float x, float y, float z) {
        set(getX() * x, getY() * y, getZ() * z);
        return this;
    }

    public FloatVector3 multiply(FloatMatrix4 matrix) {
        float x = this.x;
        float y = this.y;
        float z = this.z;
        this.x = MathHelper.fma(matrix.getM00(), x, MathHelper.fma(matrix.getM10(), y, MathHelper.fma(matrix.getM20(), z, matrix.getM30())));
        this.y = MathHelper.fma(matrix.getM01(), x, MathHelper.fma(matrix.getM11(), y, MathHelper.fma(matrix.getM21(), z, matrix.getM31())));
        this.z = MathHelper.fma(matrix.getM02(), x, MathHelper.fma(matrix.getM12(), y, MathHelper.fma(matrix.getM22(), z, matrix.getM32())));
        return this;
    }

    public FloatVector3 multiply(float scalar) {
        return multiply(scalar, scalar, scalar);
    }

    public FloatVector3 divide(float scalar) {
        return divide(scalar, scalar, scalar);
    }

    public FloatVector3 divide(float x, float y, float z) {
        set(getX() / x, getY() / y, getZ() / z);
        return this;
    }

    public FloatVector3 map(Function<Float, Float> mapper) {
        set(mapper.apply(Float.valueOf(getX())).floatValue(), mapper.apply(Float.valueOf(getY())).floatValue(), mapper.apply(Float.valueOf(getZ())).floatValue());
        return this;
    }

    public FloatVector3 cross(FloatVector3 vector) {
        float x = getX();
        float y = getY();
        float z = getZ();
        float vecX = vector.getX();
        float vecY = vector.getY();
        float vecZ = vector.getZ();
        set((y * vecZ) - (z * vecY), (z * vecX) - (x * vecZ), (x * vecY) - (y * vecX));
        return this;
    }

    public FloatVector3 normalize() {
        float x = getX();
        float y = getY();
        float z = getZ();
        double sqrt = Math.sqrt((x * x) + (y * y) + (z * z));
        if (sqrt < 1.0E-4d) {
            set(0.0f, 0.0f, 0.0f);
        } else {
            set(x / ((float) sqrt), y / ((float) sqrt), z / ((float) sqrt));
        }
        return this;
    }

    public FloatVector3 copy() {
        return new FloatVector3(getX(), getY(), getZ());
    }

    public float distance(FloatVector3 vector) {
        return (float) Math.sqrt(distanceSquared(vector));
    }

    public float distance(IntVector3 vector) {
        return (float) Math.sqrt(distanceSquared(vector));
    }

    public float distanceSquared(FloatVector3 vector) {
        return square(getX() - vector.getX()) + square(getY() - vector.getY()) + square(getZ() - vector.getZ());
    }

    public float distanceSquared(IntVector3 vector) {
        return square(getX() - vector.getX()) + square(getY() - vector.getY()) + square(getZ() - vector.getZ());
    }

    private float square(float number) {
        return number * number;
    }

    public Quaternion rotation(float rotationAngle) {
        return rotation(rotationAngle, true);
    }

    public Quaternion rotation(float rotationAngle, boolean newObject) {
        if (newObject) {
            return new Quaternion(this, rotationAngle, false);
        }
        ROTATION_QUATERNION.setRotation(this, rotationAngle, false);
        return ROTATION_QUATERNION;
    }

    public Quaternion rotationDegrees(float rotationAngle) {
        return rotationDegrees(rotationAngle, true);
    }

    public Quaternion rotationDegrees(float rotationAngle, boolean newObject) {
        if (newObject) {
            return new Quaternion(this, rotationAngle, true);
        }
        ROTATION_DEGREES_QUATERNION.setRotation(this, rotationAngle, true);
        return ROTATION_DEGREES_QUATERNION;
    }

    public void store(FloatBuffer buffer) {
        buffer.position(0);
        buffer.put(0, getX());
        buffer.put(1, getY());
        buffer.put(2, getZ());
    }

    public String toString() {
        return "FloatVector3[x=" + getX() + ", y=" + getY() + ", z=" + getZ() + "]";
    }

    @NotNull
    public static FloatVector3 calculateViewVector(float xRot, float yRot) {
        float var0 = xRot * 0.017453292f;
        float var1 = (-yRot) * 0.017453292f;
        float var2 = (float) Math.cos(var1);
        float var3 = (float) Math.sin(var1);
        float var4 = (float) Math.cos(var0);
        float var5 = (float) Math.sin(var0);
        return new FloatVector3(var3 * var4, -var5, var2 * var4);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return square(getX()) + square(getY()) + square(getZ());
    }

    public double horizontalDistance() {
        return Math.sqrt(horizontalDistanceSquared());
    }

    public double horizontalDistanceSquared() {
        return (getX() * getX()) + (getZ() * getZ());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FloatVector3 that = (FloatVector3) o;
        return Float.compare(that.getX(), getX()) == 0 && Float.compare(that.getY(), getY()) == 0 && Float.compare(that.getZ(), getZ()) == 0;
    }

    public int hashCode() {
        int result = getX() != 0.0f ? Float.floatToIntBits(getX()) : 0;
        return (31 * ((31 * result) + (getY() != 0.0f ? Float.floatToIntBits(getY()) : 0))) + (getZ() != 0.0f ? Float.floatToIntBits(getZ()) : 0);
    }
}
