package net.labymod.api.client.render.matrix;

import net.labymod.api.Laby;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.vector.FloatVector4;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/matrix/Stack.class */
public class Stack {
    private static final FloatVector3 TRANSFORM_VEC3 = new FloatVector3(0.0f, 0.0f, 0.0f);
    private static final FloatVector4 TRANSFORM_VEC4 = new FloatVector4(0.0f, 0.0f, 0.0f, 0.0f);
    private static final Logging LOGGER = Logging.create((Class<?>) Stack.class);
    protected final StackProvider provider;
    protected Object multiBufferSource;

    protected Stack(StackProvider provider) {
        this(provider, null);
    }

    protected Stack(StackProvider provider, Object multiBufferSource) {
        this.provider = provider;
        this.multiBufferSource = multiBufferSource;
    }

    public static Stack getDefaultEmptyStack() {
        return EmptyStackHolder.EMPTY_STACK.create();
    }

    @Contract("_ -> new")
    @ApiStatus.Internal
    @NotNull
    public static Stack create(StackProvider provider) {
        return new Stack(provider, null);
    }

    @NotNull
    public static Stack create(Object poseStack) {
        return Laby.references().stackProviderFactory().create(poseStack);
    }

    @NotNull
    public static Stack create(Object poseStack, Object multiBufferSource) {
        return Laby.references().stackProviderFactory().create(poseStack, multiBufferSource);
    }

    public StackProvider getProvider() {
        return this.provider;
    }

    public Stack multiBufferSource(Object source) {
        this.multiBufferSource = source;
        return this;
    }

    public Object getMultiBufferSource() {
        return this.multiBufferSource;
    }

    public void push() {
        this.provider.push();
    }

    public void pop() {
        this.provider.pop();
    }

    @ApiStatus.Experimental
    public void pushAndPop(Runnable runnable) {
        push();
        try {
            runnable.run();
        } catch (Throwable throwable) {
            LOGGER.error("The last entry in the stack could not be popped normally. Please report this error to \"https://www.labymod.net/ideas\".", throwable);
        }
        pop();
    }

    public void translate(double x, double y, double z) {
        translate((float) x, (float) y, (float) z);
    }

    public void translate(float x, float y, float z) {
        this.provider.translate(x, y, z);
    }

    public void rotate(float angle, float x, float y, float z) {
        this.provider.rotate(angle, x, y, z);
    }

    public void rotateRadians(float radians, float x, float y, float z) {
        this.provider.rotateRadians(radians, x, y, z);
    }

    public void scale(float scale) {
        scale(scale, scale, scale);
    }

    public void scale(float x, float y, float z) {
        this.provider.scale(x, y, z);
    }

    public void mul(Matrix4f matrix) {
        this.provider.mul(matrix);
    }

    public void identity() {
        this.provider.identity();
    }

    public void recoverStackDepth(int diff) {
        if (diff > 0) {
            for (int i = 0; i < diff; i++) {
                pop();
            }
            return;
        }
        if (diff < 0) {
            for (int i2 = 0; i2 < (-diff); i2++) {
                push();
            }
        }
    }

    public FloatVector3 transformVector(float x, float y, float z) {
        FloatMatrix4 positionMatrix = getProvider().getPosition();
        float translatedX = positionMatrix.transformVectorX(x, y, z) - x;
        float translatedY = positionMatrix.transformVectorY(x, y, z) - y;
        float translatedZ = positionMatrix.transformVectorZ(x, y, z) - z;
        TRANSFORM_VEC3.set(translatedX, translatedY, translatedZ);
        return TRANSFORM_VEC3;
    }

    public FloatVector4 transformVector(Rectangle rectangle) {
        return transformVector(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom());
    }

    public FloatVector4 transformVector(float left, float top, float right, float bottom) {
        FloatMatrix4 matrix = getProvider().getPosition();
        float leftTranslated = matrix.transformVectorX(left, top, 0.0f) - left;
        float topTranslated = matrix.transformVectorY(left, top, 0.0f) - top;
        float rightTranslated = matrix.transformVectorX(right, bottom, 0.0f) - right;
        float bottomTranslated = matrix.transformVectorY(right, bottom, 0.0f) - bottom;
        TRANSFORM_VEC4.set(leftTranslated, topTranslated, rightTranslated, bottomTranslated);
        return TRANSFORM_VEC4;
    }

    public Stack copy() {
        return new Stack(this.provider, this.multiBufferSource);
    }

    public int index() {
        return this.provider.index();
    }
}
