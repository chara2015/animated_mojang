package net.labymod.api.util.math.vector;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/vector/Matrix4.class */
public interface Matrix4 {
    void translate(float f, float f2, float f3);

    void rotate(float f, float f2, float f3, float f4);

    void rotateRadians(float f, float f2, float f3, float f4);

    void scale(float f, float f2, float f3);

    void multiply(FloatMatrix4 floatMatrix4);
}
