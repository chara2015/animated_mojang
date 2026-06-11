package net.labymod.api.util.math.vector;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/vector/FloatVector4.class */
public class FloatVector4 {
    public static final FloatVector4 ZERO = new FloatVector4(0.0f, 0.0f, 0.0f, 0.0f);
    private float x;
    private float y;
    private float z;
    private float w;

    public FloatVector4() {
    }

    public FloatVector4(FloatVector4 vector) {
        this(vector.x, vector.y, vector.z, vector.w);
    }

    public FloatVector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void transform(FloatMatrix4 matrix) {
        float x = this.x;
        float y = this.y;
        float z = this.z;
        float w = this.w;
        this.x = (matrix.getM00() * x) + (matrix.getM01() * y) + (matrix.getM02() * z) + (matrix.getM03() * w);
        this.y = (matrix.getM10() * x) + (matrix.getM11() * y) + (matrix.getM12() * z) + (matrix.getM13() * w);
        this.z = (matrix.getM20() * x) + (matrix.getM21() * y) + (matrix.getM22() * z) + (matrix.getM23() * w);
        this.w = (matrix.getM30() * x) + (matrix.getM31() * y) + (matrix.getM32() * z) + (matrix.getM33() * w);
    }

    public void transform(FloatVector3 other, FloatMatrix4 matrix, float scale) {
        float x = other.getX() * scale;
        float y = other.getY() * scale;
        float z = other.getZ() * scale;
        this.x = (matrix.getM00() * x) + (matrix.getM01() * y) + (matrix.getM02() * z) + matrix.getM03();
        this.y = (matrix.getM10() * x) + (matrix.getM11() * y) + (matrix.getM12() * z) + matrix.getM13();
        this.z = (matrix.getM20() * x) + (matrix.getM21() * y) + (matrix.getM22() * z) + matrix.getM23();
        this.w = (matrix.getM30() * x) + (matrix.getM31() * y) + (matrix.getM32() * z) + matrix.getM33();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public float getW() {
        return this.w;
    }

    public void set(float x, float y, float z, float w) {
        setX(x);
        setY(y);
        setZ(z);
        setW(w);
    }

    public void set(FloatVector4 other) {
        set(other.getX(), other.getY(), other.getZ(), other.getW());
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setW(float w) {
        this.w = w;
    }
}
