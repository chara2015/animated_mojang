package net.labymod.api.util.math.vector;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.position.Position;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/vector/DoubleVector3.class */
public class DoubleVector3 {
    private double x;
    private double y;
    private double z;

    public DoubleVector3() {
        this(0.0d, 0.0d, 0.0d);
    }

    public DoubleVector3(@NotNull FloatVector3 vector) {
        this(vector.getX(), vector.getY(), vector.getZ());
    }

    public DoubleVector3(@NotNull DoubleVector3 vector) {
        this(vector.getX(), vector.getY(), vector.getZ());
    }

    public DoubleVector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @NotNull
    public DoubleVector3 add(@NotNull FloatVector3 v) {
        return add(v, this);
    }

    @NotNull
    public DoubleVector3 add(@NotNull FloatVector3 v, @NotNull DoubleVector3 dest) {
        return add(v.getX(), v.getY(), v.getZ(), dest);
    }

    @NotNull
    public DoubleVector3 add(@NotNull DoubleVector3 v) {
        return add(v, this);
    }

    @NotNull
    public DoubleVector3 add(@NotNull DoubleVector3 v, @NotNull DoubleVector3 dest) {
        return add(v.getX(), v.getY(), v.getZ(), dest);
    }

    @NotNull
    public DoubleVector3 add(double x, double y, double z) {
        return add(x, y, z, this);
    }

    @NotNull
    public DoubleVector3 add(double x, double y, double z, @NotNull DoubleVector3 dest) {
        dest.set(getX() + x, getY() + y, getZ() + z);
        return dest;
    }

    @NotNull
    public DoubleVector3 sub(@NotNull DoubleVector3 v) {
        return sub(v, this);
    }

    @NotNull
    public DoubleVector3 sub(@NotNull DoubleVector3 v, @NotNull DoubleVector3 dest) {
        return sub(v.getX(), v.getY(), v.getZ(), dest);
    }

    @NotNull
    public DoubleVector3 sub(double x, double y, double z) {
        return sub(x, y, z, this);
    }

    @NotNull
    public DoubleVector3 sub(double x, double y, double z, @NotNull DoubleVector3 dest) {
        dest.set(getX() - x, getY() - y, getZ() - z);
        return dest;
    }

    @NotNull
    public DoubleVector3 multiply(double x, double y, double z) {
        return multiply(x, y, z, this);
    }

    @NotNull
    public DoubleVector3 multiply(double x, double y, double z, @NotNull DoubleVector3 dest) {
        dest.set(getX() * x, getY() * y, getZ() * z);
        return dest;
    }

    @NotNull
    public DoubleVector3 multiply(double scalar) {
        return multiply(scalar, this);
    }

    @NotNull
    public DoubleVector3 multiply(double scalar, @NotNull DoubleVector3 dest) {
        return multiply(scalar, scalar, scalar, dest);
    }

    public void set(@NotNull Position position) {
        set(position.getX(), position.getY(), position.getZ());
    }

    public void set(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    public void set(@NotNull DoubleVector3 other) {
        set(other.getX(), other.getY(), other.getZ());
    }

    @NotNull
    public DoubleVector3 normalize() {
        return normalize(this);
    }

    @NotNull
    public DoubleVector3 normalize(@NotNull DoubleVector3 dest) {
        double x = getX();
        double y = getY();
        double z = getZ();
        double sqrt = Math.sqrt((x * x) + (y * y) + (z * z));
        if (sqrt < 1.0E-4d) {
            dest.set(0.0d, 0.0d, 0.0d);
        } else {
            dest.set(x / sqrt, y / sqrt, z / sqrt);
        }
        return this;
    }

    public double lerpX(@NotNull DoubleVector3 other, float delta) {
        return MathHelper.lerp(getX(), other.getX(), delta);
    }

    public double lerpY(@NotNull DoubleVector3 other, float delta) {
        return MathHelper.lerp(getY(), other.getY(), delta);
    }

    public double lerpZ(@NotNull DoubleVector3 other, float delta) {
        return MathHelper.lerp(getZ(), other.getZ(), delta);
    }

    public double distanceSquared(@NotNull DoubleVector3 other) {
        return square(getX() - other.getX()) + square(getY() - other.getY()) + square(getZ() - other.getZ());
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return square(getX()) + square(getY()) + square(getZ());
    }

    public double horizontalDistanceSquared() {
        return square(getX()) + square(getZ());
    }

    public float distance(@NotNull DoubleVector3 vector) {
        return (float) Math.sqrt(distanceSquared(vector));
    }

    @NotNull
    public DoubleVector3 copy() {
        return new DoubleVector3(getX(), getY(), getZ());
    }

    @NotNull
    public DoubleVector3 transform(@NotNull Quaternion quaternion) {
        return transform(quaternion, this);
    }

    @NotNull
    public DoubleVector3 transform(@NotNull Quaternionf quaternion) {
        return transform(quaternion, this);
    }

    @NotNull
    public DoubleVector3 transform(@NotNull Quaternionf quaternion, @NotNull DoubleVector3 dest) {
        Quaternionf quaternion2 = new Quaternionf(quaternion);
        quaternion.mul(new Quaternionf(getX(), getY(), getZ(), 0.0d));
        Quaternionf quaternion3 = new Quaternionf(quaternion);
        quaternion3.conjugate();
        quaternion2.mul(quaternion3);
        dest.set(quaternion2.x(), quaternion2.y(), quaternion2.z());
        return dest;
    }

    @NotNull
    public DoubleVector3 transform(@NotNull Quaternion quaternion, @NotNull DoubleVector3 dest) {
        Quaternion quaternion2 = new Quaternion(quaternion);
        quaternion2.multiply(new Quaternion((float) getX(), (float) getY(), (float) getZ(), 0.0f));
        Quaternion quaternion3 = new Quaternion(quaternion);
        quaternion3.conj();
        quaternion2.multiply(quaternion3);
        dest.set(quaternion2.getX(), quaternion2.getY(), quaternion2.getZ());
        return dest;
    }

    @NotNull
    public String toString() {
        double x = getX();
        double y = getY();
        getZ();
        return "DoubleVector3[x=" + x + ", y=" + x + ", z=" + y + "]";
    }

    private double square(double v) {
        return v * v;
    }
}
