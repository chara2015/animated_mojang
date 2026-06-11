package net.labymod.v1_8_9.client.render.matrix;

import java.nio.FloatBuffer;
import net.labymod.api.client.gfx.pipeline.util.MatrixTracker;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/render/matrix/VersionedStackProvider.class */
public class VersionedStackProvider implements StackProvider {
    private static final FloatBuffer MATRIX_BUFFER = BufferUtils.createFloatBuffer(16);
    public static final Stack DEFAULT_STACK = createStack();
    private static final Matrix3f IDENTITY_NORMAL = new Matrix3f();

    @NotNull
    private static Stack createStack() {
        return Stack.create((StackProvider) new VersionedStackProvider());
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void translate(float x, float y, float z) {
        bfl.b(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotate(float angle, float x, float y, float z) {
        bfl.b(angle, x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotateRadians(float radians, float x, float y, float z) {
        bfl.b((float) Math.toDegrees(radians), x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void scale(float x, float y, float z) {
        bfl.a(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void mul(Matrix4f matrix) {
        matrix.get(MATRIX_BUFFER);
        bfl.a(MATRIX_BUFFER);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void push() {
        bfl.E();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void pop() {
        bfl.F();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void identity() {
        bfl.D();
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
    public boolean isOpenGlStack() {
        return true;
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public int index() {
        return MatrixTracker.MODEL_VIEW_MATRIX.index();
    }
}
