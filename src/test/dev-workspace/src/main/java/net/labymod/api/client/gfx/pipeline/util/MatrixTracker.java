package net.labymod.api.client.gfx.pipeline.util;

import java.nio.FloatBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gfx.pipeline.util.EmulatedStack;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.util.math.MathHelper;
import net.labymod.laby3d.api.opengl.GlRenderDevice;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/util/MatrixTracker.class */
public final class MatrixTracker {
    public static final EmulatedStack MODEL_VIEW_MATRIX = createStack(EmulatedStack.Type.MODELVIEW, "ModelView");
    public static final EmulatedStack PROJECTION_MATRIX = createStack(EmulatedStack.Type.PROJECTION, "Projection");
    private static final List<EmulatedStack> TEXTURE_MATRICES = Lists.newArrayListWithCapacity(32);
    private static EmulatedStack currentStack = null;

    public static void beginTracking(int matrixType) {
        EmulatedStack currentTextureMatrix;
        switch (matrixType) {
            case GlConst.GL_MODELVIEW /* 5888 */:
                currentTextureMatrix = MODEL_VIEW_MATRIX;
                break;
            case GlConst.GL_PROJECTION /* 5889 */:
                currentTextureMatrix = PROJECTION_MATRIX;
                break;
            case GlConst.GL_TEXTURE /* 5890 */:
                currentTextureMatrix = getCurrentTextureMatrix();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + matrixType);
        }
        currentStack = currentTextureMatrix;
        currentStack.checkDepthAndPoseConsistency();
    }

    public static void push() {
        if (currentStack == null) {
            return;
        }
        currentStack.push();
    }

    public static void translate(float x, float y, float z) {
        if (currentStack == null) {
            return;
        }
        Matrix4f translationMatrix = MatrixPool.get().translation(x, y, z);
        currentStack.mul(translationMatrix);
        MatrixPool.release(translationMatrix);
    }

    public static void rotate(float angle, float x, float y, float z) {
        if (currentStack == null) {
            return;
        }
        Vector3f axis = Vector3fPool.get().set(x, y, z).normalize();
        Matrix4f rotationMatrix = MatrixPool.get();
        rotationMatrix.rotate(MathHelper.toRadiansFloat(angle), axis);
        currentStack.mul(rotationMatrix);
        Vector3fPool.release(axis);
        MatrixPool.release(rotationMatrix);
    }

    public static void scale(float x, float y, float z) {
        if (currentStack == null) {
            return;
        }
        Matrix4f scaleMatrix = MatrixPool.get().scaling(x, y, z);
        currentStack.mul(scaleMatrix);
        MatrixPool.release(scaleMatrix);
    }

    public static void loadIdentity() {
        if (currentStack == null) {
            return;
        }
        currentStack.loadIdentity();
    }

    public static void ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
        if (currentStack == null) {
            return;
        }
        Matrix4f mat = MatrixPool.get();
        currentStack.mul(mat.setOrtho(left, right, bottom, top, zNear, zFar));
        MatrixPool.release(mat);
    }

    public static void multiply(FloatBuffer buffer) {
        if (currentStack == null) {
            return;
        }
        Matrix4f mat = MatrixPool.get();
        currentStack.mul(mat.set(buffer));
        MatrixPool.release(mat);
    }

    public static void pop() {
        if (currentStack == null) {
            return;
        }
        currentStack.pop();
    }

    public static EmulatedStack getCurrentTextureMatrix() {
        return getTextureMatrix(getActiveTextureUnit());
    }

    public static EmulatedStack getTextureMatrix(int unit) {
        int index = unit - GlConst.GL_TEXTURE0;
        while (TEXTURE_MATRICES.size() <= index) {
            TEXTURE_MATRICES.add(null);
        }
        EmulatedStack stack = TEXTURE_MATRICES.get(index);
        if (stack == null) {
            stack = createStack(EmulatedStack.Type.TEXTURE, "Texture[" + unit + "]");
            TEXTURE_MATRICES.set(index, stack);
        }
        return stack;
    }

    private static EmulatedStack createStack(EmulatedStack.Type type, String name) {
        return new EmulatedStack((GlRenderDevice) CastUtil.requireInstanceOf(Laby.references().laby3D().renderDevice(), GlRenderDevice.class), type, name);
    }

    private static int getActiveTextureUnit() {
        return ((GlRenderDevice) CastUtil.requireInstanceOf(Laby.references().laby3D().renderDevice(), GlRenderDevice.class)).functions().activeTexture();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/util/MatrixTracker$MatrixPool.class */
    static final class MatrixPool {
        private static final Deque<Matrix4f> POOL = new ArrayDeque();

        MatrixPool() {
        }

        public static Matrix4f get() {
            Matrix4f polled = POOL.pollFirst();
            return polled != null ? polled.identity() : new Matrix4f();
        }

        public static void release(Matrix4f matrix) {
            if (POOL.size() < 256) {
                POOL.offerFirst(matrix);
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/util/MatrixTracker$Vector3fPool.class */
    static final class Vector3fPool {
        private static final Deque<Vector3f> POOL = new ArrayDeque();

        Vector3fPool() {
        }

        public static Vector3f get() {
            Vector3f polled = POOL.pollFirst();
            return polled != null ? polled.zero() : new Vector3f();
        }

        public static void release(Vector3f vector) {
            if (POOL.size() < 256) {
                POOL.offerFirst(vector);
            }
        }
    }
}
