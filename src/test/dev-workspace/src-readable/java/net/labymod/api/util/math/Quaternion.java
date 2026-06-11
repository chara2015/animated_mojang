package net.labymod.api.util.math;

import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/Quaternion.class */
public class Quaternion {
    public static final Quaternion ONE = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
    private float x;
    private float y;
    private float z;
    private float w;

    public Quaternion() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 1.0f;
    }

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(FloatVector3 axis, float rotationAngle, boolean degrees) {
        setRotation(axis, rotationAngle, degrees);
    }

    public Quaternion(float x, float y, float z, boolean degrees) {
        if (degrees) {
            x *= 0.017453292f;
            y *= 0.017453292f;
            z *= 0.017453292f;
        }
        float sinX = sin(0.5f * x);
        float cosX = cos(0.5f * x);
        float sinY = sin(0.5f * y);
        float cosY = cos(0.5f * y);
        float sinZ = sin(0.5f * z);
        float cosZ = cos(0.5f * z);
        this.x = (sinX * cosY * cosZ) + (cosX * sinY * sinZ);
        this.y = ((cosX * sinY) * cosZ) - ((sinX * cosY) * sinZ);
        this.z = (sinX * sinY * cosZ) + (cosX * cosY * sinZ);
        this.w = ((cosX * cosY) * cosZ) - ((sinX * sinY) * sinZ);
    }

    public Quaternion(Quaternion other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.w = other.w;
    }

    public static Quaternion fromYXZ(float x, float y, float z) {
        Quaternion one = ONE.copy();
        one.multiply(new Quaternion(0.0f, sin(x / 2.0f), 0.0f, cos(x / 2.0f)));
        one.multiply(new Quaternion(sin(y / 2.0f), 0.0f, 0.0f, cos(y / 2.0f)));
        one.multiply(new Quaternion(0.0f, 0.0f, sin(z / 2.0f), cos(z / 2.0f)));
        return one;
    }

    public static Quaternion fromXYZDegrees(FloatVector3 axis) {
        return fromXYZ((float) Math.toRadians(axis.getX()), (float) Math.toRadians(axis.getY()), (float) Math.toRadians(axis.getZ()));
    }

    public static Quaternion fromXYZ(FloatVector3 axis) {
        return fromXYZ(axis.getX(), axis.getY(), axis.getZ());
    }

    public static Quaternion fromXYZ(float x, float y, float z) {
        Quaternion one = ONE.copy();
        one.multiply(new Quaternion(sin(x / 2.0f), 0.0f, 0.0f, cos(x / 2.0f)));
        one.multiply(new Quaternion(0.0f, sin(y / 2.0f), 0.0f, cos(y / 2.0f)));
        one.multiply(new Quaternion(0.0f, 0.0f, sin(z / 2.0f), cos(z / 2.0f)));
        return one;
    }

    public static Quaternion fromEuler(float yaw, float pitch, float roll) {
        float yaw2 = yaw / 2.0f;
        float pitch2 = pitch / 2.0f;
        float roll2 = roll / 2.0f;
        double qx = ((sin(roll2) * cos(pitch2)) * cos(yaw2)) - ((cos(roll2) * sin(pitch2)) * sin(yaw2));
        double qy = (cos(roll2) * sin(pitch2) * cos(yaw2)) + (sin(roll2) * cos(pitch2) * sin(yaw2));
        double qz = ((cos(roll2) * cos(pitch2)) * sin(yaw2)) - ((sin(roll2) * sin(pitch2)) * cos(yaw2));
        double qw = (cos(roll2) * cos(pitch2) * cos(yaw2)) + (sin(roll2) * sin(pitch2) * sin(yaw2));
        return new Quaternion((float) qx, (float) qy, (float) qz, (float) qw);
    }

    public void setRotation(FloatVector3 axis, float rotationAngle, boolean degrees) {
        if (degrees) {
            rotationAngle *= 0.017453292f;
        }
        float sinRotationAngle = sin(rotationAngle / 2.0f);
        this.x = axis.getX() * sinRotationAngle;
        this.y = axis.getY() * sinRotationAngle;
        this.z = axis.getZ() * sinRotationAngle;
        this.w = cos(rotationAngle / 2.0f);
    }

    public FloatVector3 toXYZ() {
        float squareW = getW() * getW();
        float squareX = getX() * getX();
        float squareY = getY() * getY();
        float squareZ = getZ() * getZ();
        float sum = squareW + squareX + squareY + squareZ;
        float value = ((2.0f * getW()) * getX()) - ((2.0f * getY()) * getZ());
        float arcSin = (float) Math.asin(value / sum);
        if (Math.abs(value) > 0.999f * sum) {
            return new FloatVector3(2.0f * ((float) Math.atan2(getX(), getW())), arcSin, 0.0f);
        }
        return new FloatVector3((float) Math.atan2((2.0f * getY() * getZ()) + (2.0f * getX() * getW()), ((squareW - squareX) - squareY) + squareZ), arcSin, (float) Math.atan2((2.0f * getX() * getY()) + (2.0f * getW() * getZ()), ((squareW + squareX) - squareY) - squareZ));
    }

    public FloatVector3 toXYZDegrees() {
        FloatVector3 axis = toXYZ();
        return new FloatVector3((float) Math.toDegrees(axis.getX()), (float) Math.toDegrees(axis.getY()), (float) Math.toDegrees(axis.getZ()));
    }

    public FloatVector3 toYXZ() {
        float squareW = getW() * getW();
        float squareX = getX() * getX();
        float squareY = getY() * getY();
        float squareZ = getZ() * getZ();
        float sum = squareW + squareX + squareY + squareZ;
        float value = ((2.0f * getW()) * getX()) - ((2.0f * getY()) * getZ());
        float arcSin = (float) Math.asin(value / sum);
        if (Math.abs(value) > 0.999f * sum) {
            return new FloatVector3(arcSin, 2.0f * ((float) Math.atan2(getY(), getW())), 0.0f);
        }
        return new FloatVector3(arcSin, (float) Math.atan2((2.0f * getX() * getZ()) + (2.0f * getY() * getW()), ((squareW - squareX) - squareY) + squareZ), (float) Math.atan2((2.0f * getX() * getY()) + (2.0f * getW() * getZ()), ((squareW - squareX) + squareY) - squareZ));
    }

    public FloatVector3 toYXZDegrees() {
        FloatVector3 axis = toYXZ();
        return new FloatVector3((float) Math.toDegrees(axis.getX()), (float) Math.toDegrees(axis.getY()), (float) Math.toDegrees(axis.getZ()));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Quaternion quaternion = (Quaternion) obj;
            return Float.compare(quaternion.x, this.x) == 0 && Float.compare(quaternion.y, this.y) == 0 && Float.compare(quaternion.z, this.z) == 0 && Float.compare(quaternion.w, this.w) == 0;
        }
        return false;
    }

    public int hashCode() {
        int bits = Float.floatToIntBits(this.x);
        return (31 * ((31 * ((31 * bits) + Float.floatToIntBits(this.y))) + Float.floatToIntBits(this.z))) + Float.floatToIntBits(this.w);
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

    public float getW() {
        return this.w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public void multiply(Quaternion other) {
        multiply(other, this);
    }

    public Quaternion multiply(Quaternion q, Quaternion dest) {
        dest.set(Math.fma(getW(), q.getX(), Math.fma(getX(), q.getW(), Math.fma(getY(), q.getZ(), (-getZ()) * q.getY()))), Math.fma(getW(), q.getY(), Math.fma(-getX(), q.getZ(), Math.fma(getY(), q.getY(), getZ() * q.getX()))), Math.fma(getW(), q.getZ(), Math.fma(getX(), q.getY(), Math.fma(-getY(), q.getX(), getZ() * q.getW()))), Math.fma(getW(), q.getW(), Math.fma(-getX(), q.getX(), Math.fma(-getY(), q.getY(), (-getZ()) * q.getZ()))));
        return dest;
    }

    public Quaternion rotationYXZ(float angleY, float angleX, float angleZ) {
        float sinX = sin(0.5f * angleX);
        float cosX = cos(0.5f * angleX);
        float sinY = sin(0.5f * angleY);
        float cosY = cos(0.5f * angleY);
        float sinZ = sin(0.5f * angleZ);
        float cosZ = cos(0.5f * angleZ);
        float x = cosY * sinX;
        float y = sinY * cosX;
        float z = sinY * sinX;
        float w = cosY * cosX;
        setX((x * cosZ) + (y * sinZ));
        setY((y * cosZ) - (x * sinZ));
        setZ((w * sinZ) - (z * cosZ));
        setW((w * cosZ) + (z * sinZ));
        return this;
    }

    public void multiply(float factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
        this.w *= factor;
    }

    public Quaternion conj() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void set(Quaternion quaternion) {
        this.x = quaternion.x;
        this.y = quaternion.y;
        this.z = quaternion.z;
        this.w = quaternion.w;
    }

    private static float cos(float value) {
        return MathHelper.cos(value);
    }

    private static float sin(float value) {
        return MathHelper.sin(value);
    }

    public float getYaw() {
        return (float) (-Math.toDegrees(Math.atan2(2.0f * ((this.y * this.w) - (this.x * this.z)), 1.0f - (2.0f * ((this.y * this.y) + (this.z * this.z))))));
    }

    public float getPitch() {
        return (float) Math.toDegrees(Math.asin(MathHelper.clamp(2.0f * ((this.x * this.w) - (this.y * this.z)), -1.0f, 1.0f)));
    }

    public float getRoll() {
        return (float) Math.toDegrees(Math.atan2(2.0f * ((this.x * this.y) + (this.z * this.w)), 1.0f - (2.0f * ((this.x * this.x) + (this.z * this.z)))));
    }

    public void normalize() {
        float product = (getX() * getX()) + (getY() * getY()) + (getZ() * getZ()) + (getW() * getW());
        if (product > 1.0E-6f) {
            float inverseSqrt = MathHelper.fastInverseSqrt(product);
            this.x *= inverseSqrt;
            this.y *= inverseSqrt;
            this.z *= inverseSqrt;
            this.w *= inverseSqrt;
            return;
        }
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 0.0f;
    }

    public boolean hasNoRotation() {
        return this.x == 0.0f && this.y == 0.0f && this.z == 0.0f;
    }

    public Quaternion copy() {
        return new Quaternion(this);
    }
}
