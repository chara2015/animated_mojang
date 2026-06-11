package net.labymod.api.util.math.vector;

import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/vector/IntVector3.class */
public class IntVector3 {
    public static final IntVector3 ZERO = new IntVector3();
    private int x;
    private int y;
    private int z;

    public IntVector3() {
        this(0, 0, 0);
    }

    public IntVector3(FloatVector3 other) {
        this(MathHelper.floor(other.getX()), MathHelper.floor(other.getY()), MathHelper.floor(other.getZ()));
    }

    public IntVector3(DoubleVector3 other) {
        this(MathHelper.floor(other.getX()), MathHelper.floor(other.getY()), MathHelper.floor(other.getZ()));
    }

    public IntVector3(IntVector3 other) {
        this(other.getX(), other.getY(), other.getZ());
    }

    public IntVector3(float x, float y, float z) {
        this(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }

    public IntVector3(double x, double y, double z) {
        this(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }

    public IntVector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void set(int x, int y, int z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    public void add(int x, int y, int z) {
        setX(getX() + x);
        setY(getY() + y);
        setZ(getZ() + z);
    }

    public void sub(int x, int y, int z) {
        setX(getX() - x);
        setY(getY() - y);
        setZ(getZ() - z);
    }

    public void mul(int scalar) {
        mul(scalar, scalar, scalar);
    }

    public void mul(int x, int y, int z) {
        setX(getX() * x);
        setY(getY() * y);
        setZ(getZ() * z);
    }

    public void div(int scalar) {
        div(scalar, scalar, scalar);
    }

    public void div(int x, int y, int z) {
        setX(getX() / x);
        setY(getY() / y);
        setZ(getZ() / z);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IntVector3 that = (IntVector3) o;
        return this.x == that.x && this.y == that.y && this.z == that.z;
    }

    public int hashCode() {
        int result = this.x;
        return (31 * ((31 * result) + this.y)) + this.z;
    }

    public String toString() {
        return "IntVector3[x=" + getX() + ", y=" + getY() + ", z=" + getZ() + "]";
    }
}
