package net.labymod.v1_16_5.client.render.matrix;

import java.nio.FloatBuffer;
import net.labymod.api.client.gfx.pipeline.util.MatrixTracker;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/render/matrix/OpenGLStackProvider.class */
public class OpenGLStackProvider implements StackProvider {
    private static final FloatBuffer MATRIX_BUFFER = BufferUtils.createFloatBuffer(16);
    public static final Stack DEFAULT_STACK = createStack();
    private static final Matrix3f IDENTITY_NORMAL = new Matrix3f();

    @NotNull
    private static Stack createStack() {
        return Stack.create((StackProvider) new OpenGLStackProvider());
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void translate(float x, float y, float z) {
        dem.c(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotate(float angle, float x, float y, float z) {
        dem.c(angle, x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotateRadians(float radians, float x, float y, float z) {
        dem.c((float) Math.toDegrees(radians), x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void scale(float x, float y, float z) {
        dem.b(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void mul(Matrix4f matrix) {
        matrix.get(MATRIX_BUFFER);
        dem.a(MATRIX_BUFFER);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void push() {
        dem.Q();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void pop() {
        dem.R();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void identity() {
        dem.P();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public Matrix3f getNormal() {
        return IDENTITY_NORMAL;
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    @Nullable
    public Object getPoseStack() {
        return null;
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public Matrix4f getPose() {
        return MatrixTracker.MODEL_VIEW_MATRIX.pose();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public int index() {
        return MatrixTracker.MODEL_VIEW_MATRIX.index();
    }
}
