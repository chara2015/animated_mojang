package net.labymod.api.client.gfx.pipeline.util;

import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.render.matrix.ArrayStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.util.logging.Logging;
import net.labymod.laby3d.api.opengl.GlRenderDevice;
import net.labymod.util.property.SystemProperties;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/util/EmulatedStack.class */
public class EmulatedStack {
    private static final Logging LOGGER = Logging.getLogger();
    private static final float PRECISION_THRESHOLD = 0.01f;
    private final GlRenderDevice renderDevice;
    private final Type type;
    private final String name;
    private final Stack stack;

    public EmulatedStack(GlRenderDevice renderDevice, Type type, String name) {
        this.renderDevice = renderDevice;
        this.type = type;
        this.name = name;
        int stackSize = renderDevice.functions().getInt(type.getMaxDepthTarget());
        LOGGER.info("[OpenGL/{}] Size of \"{}\" stack: {}", name, name, Integer.valueOf(stackSize));
        this.stack = Stack.create((StackProvider) new ArrayStackProvider(stackSize));
    }

    public void push() {
        this.stack.push();
        checkDepthAndPoseConsistency();
    }

    public void pop() {
        this.stack.pop();
        checkDepthAndPoseConsistency();
    }

    public void mul(Matrix4f matrix) {
        this.stack.mul(matrix);
        checkDepthAndPoseConsistency();
    }

    public void loadIdentity() {
        this.stack.identity();
        checkDepthAndPoseConsistency();
    }

    public Matrix4f pose() {
        return this.stack.getProvider().getPose();
    }

    public int index() {
        return this.stack.index();
    }

    public Type getType() {
        return this.type;
    }

    public void checkDepthAndPoseConsistency() {
        if (!SystemProperties.RENDER_DEVICE_VERITY_OPENGL_EMULATION.get().booleanValue()) {
            return;
        }
        String name = getName();
        int currentDepth = this.stack.index();
        int targetDepth = this.renderDevice.functions().getInt(getType().getDepthTarget());
        Matrix4f pose = JomlMath.extractMatrix(pose());
        float[] oglPoseBuffer = new float[16];
        GL11.glGetFloatv(this.type.getMatrixTarget(), oglPoseBuffer);
        Matrix4f oglPose = new Matrix4f();
        oglPose.set(oglPoseBuffer);
        boolean mismatchDepth = currentDepth != targetDepth;
        boolean mismatchPose = !pose.equals(oglPose, PRECISION_THRESHOLD);
        if (mismatchDepth) {
            LOGGER.error("[{}] Mismatching matrix depths: {} != {}", name, Integer.valueOf(currentDepth), Integer.valueOf(targetDepth));
        }
        if (mismatchPose) {
            LOGGER.error("[{}] Mismatching matrices", name);
        }
    }

    public Stack stack() {
        return this.stack;
    }

    String getName() {
        return this.name;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/util/EmulatedStack$Type.class */
    public enum Type {
        MODELVIEW(GlConst.GL_MODELVIEW, GlConst.GL_MODELVIEW_MATRIX, GlConst.GL_MODELVIEW_STACK_DEPTH, GlConst.GL_MAX_MODELVIEW_STACK_DEPTH),
        PROJECTION(GlConst.GL_PROJECTION, GlConst.GL_PROJECTION_MATRIX, GlConst.GL_PROJECTION_STACK_DEPTH, GlConst.GL_MAX_PROJECTION_STACK_DEPTH),
        TEXTURE(GlConst.GL_TEXTURE, GlConst.GL_TEXTURE_MATRIX, GlConst.GL_TEXTURE_STACK_DEPTH, GlConst.GL_MAX_TEXTURE_STACK_DEPTH);

        private static final Type[] VALUES = values();
        private final int target;
        private final int matrixTarget;
        private final int depthTarget;
        private final int maxDepthTarget;

        Type(int target, int matrixTarget, int depthTarget, int maxDepthTarget) {
            this.target = target;
            this.matrixTarget = matrixTarget;
            this.depthTarget = depthTarget;
            this.maxDepthTarget = maxDepthTarget;
        }

        public static Type byOpenGlTarget(int target) {
            for (Type type : VALUES) {
                if (type.getTarget() == target) {
                    return type;
                }
            }
            return MODELVIEW;
        }

        public int getTarget() {
            return this.target;
        }

        public int getMatrixTarget() {
            return this.matrixTarget;
        }

        public int getDepthTarget() {
            return this.depthTarget;
        }

        public int getMaxDepthTarget() {
            return this.maxDepthTarget;
        }
    }
}
