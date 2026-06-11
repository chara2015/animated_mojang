package net.labymod.v1_21_8.client.render.matrix;

import net.labymod.api.client.render.matrix.StackProvider;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/render/matrix/JomlStackProvider.class */
public class JomlStackProvider implements StackProvider {
    private static final Matrix3f NORMAL_IDENTITY = new Matrix3f();
    private final Matrix4fStack stack;

    public JomlStackProvider(Matrix4fStack stack) {
        this.stack = stack;
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void translate(float x, float y, float z) {
        this.stack.translate(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotate(float angle, float x, float y, float z) {
        this.stack.rotate(angle, x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotateRadians(float radians, float x, float y, float z) {
        this.stack.rotate(radians, x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void scale(float x, float y, float z) {
        this.stack.scale(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void mul(Matrix4f matrix) {
        this.stack.mul(matrix);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void push() {
        this.stack.pushMatrix();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void pop() {
        this.stack.popMatrix();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void identity() {
        this.stack.identity();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public Matrix4f getPose() {
        return this.stack;
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public Matrix3f getNormal() {
        return NORMAL_IDENTITY;
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    @Nullable
    public Object getPoseStack() {
        return this.stack;
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public int index() {
        return this.stack.getCurrentIndex();
    }
}
