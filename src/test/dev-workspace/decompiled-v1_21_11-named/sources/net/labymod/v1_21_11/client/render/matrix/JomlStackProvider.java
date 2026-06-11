package net.labymod.v1_21_11.client.render.matrix;

import net.labymod.api.client.render.matrix.StackProvider;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/render/matrix/JomlStackProvider.class */
public class JomlStackProvider implements StackProvider {
    private static final Matrix3f NORMAL_IDENTITY = new Matrix3f();
    private final Matrix4fStack stack;

    public JomlStackProvider(Matrix4fStack stack) {
        this.stack = stack;
    }

    public void translate(float x, float y, float z) {
        this.stack.translate(x, y, z);
    }

    public void rotate(float angle, float x, float y, float z) {
        this.stack.rotate(angle, x, y, z);
    }

    public void rotateRadians(float radians, float x, float y, float z) {
        this.stack.rotate(radians, x, y, z);
    }

    public void scale(float x, float y, float z) {
        this.stack.scale(x, y, z);
    }

    public void mul(Matrix4f matrix) {
        this.stack.mul(matrix);
    }

    public void push() {
        this.stack.pushMatrix();
    }

    public void pop() {
        this.stack.popMatrix();
    }

    public void identity() {
        this.stack.identity();
    }

    public Matrix4f getPose() {
        return this.stack;
    }

    public Matrix3f getNormal() {
        return NORMAL_IDENTITY;
    }

    @Nullable
    public Object getPoseStack() {
        return this.stack;
    }

    public int index() {
        return this.stack.getCurrentIndex();
    }
}
