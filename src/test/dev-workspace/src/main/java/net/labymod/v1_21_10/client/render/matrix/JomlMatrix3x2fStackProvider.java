package net.labymod.v1_21_10.client.render.matrix;

import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/render/matrix/JomlMatrix3x2fStackProvider.class */
public class JomlMatrix3x2fStackProvider implements StackProvider {
    private static final Matrix3f NORMAL_IDENTITY = new Matrix3f();
    private final Matrix3x2fStack stack;

    public JomlMatrix3x2fStackProvider(Matrix3x2fStack stack) {
        this.stack = stack;
    }

    public static JomlMatrix3x2fStackProvider fromGuiGraphics(gdd graphics) {
        return new JomlMatrix3x2fStackProvider(graphics.e());
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void translate(float x, float y, float z) {
        this.stack.translate(x, y);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotate(float angle, float x, float y, float z) {
        this.stack.rotate(MathHelper.toRadiansFloat(angle));
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotateRadians(float radians, float x, float y, float z) {
        this.stack.rotate(radians);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void scale(float x, float y, float z) {
        this.stack.scale(x, y);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void mul(Matrix4f matrix) {
        Matrix3x2f mat = new Matrix3x2f();
        mat.set(matrix.m00(), matrix.m01(), matrix.m10(), matrix.m11(), matrix.m20(), matrix.m21());
        this.stack.mul(mat);
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
        Matrix4f mat = new Matrix4f();
        MathHelper.setMat3x2(this.stack, mat);
        return mat;
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
