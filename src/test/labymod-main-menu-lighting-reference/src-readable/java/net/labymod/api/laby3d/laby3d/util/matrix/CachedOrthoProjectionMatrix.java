package net.labymod.api.laby3d.util.matrix;

import net.labymod.laby3d.api.pipeline.DepthConventionHolder;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/util/matrix/CachedOrthoProjectionMatrix.class */
public abstract class CachedOrthoProjectionMatrix<T> implements AutoCloseable {
    private final float zNear;
    private final float zFar;
    private final boolean invertY;
    protected float width;
    protected float height;

    public abstract T getCached(float f, float f2);

    protected CachedOrthoProjectionMatrix(float zNear, float zFar, boolean invertY) {
        this.zNear = zNear;
        this.zFar = zFar;
        this.invertY = invertY;
    }

    public static CachedOrthoProjectionMatrix<Matrix4f> simple(float zNear, float zFar, boolean invertY) {
        return new Matrix4fOrthoProjectionCache(zNear, zFar, invertY);
    }

    protected Matrix4f createProjectionMatrix(float width, float height) {
        float near = this.zNear;
        float far = this.zFar;
        boolean zZeroToOne = false;
        if (DepthConventionHolder.get().zZeroToOne()) {
            near = this.zFar;
            far = this.zNear;
            zZeroToOne = true;
        }
        return new Matrix4f().setOrtho(0.0f, width, this.invertY ? height : 0.0f, this.invertY ? 0.0f : height, near, far, zZeroToOne);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/util/matrix/CachedOrthoProjectionMatrix$Matrix4fOrthoProjectionCache.class */
    public static class Matrix4fOrthoProjectionCache extends CachedOrthoProjectionMatrix<Matrix4f> {
        private Matrix4f projectionMatrix;

        public Matrix4fOrthoProjectionCache(float zNear, float zFar, boolean invertY) {
            super(zNear, zFar, invertY);
            this.projectionMatrix = new Matrix4f();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.labymod.api.laby3d.util.matrix.CachedOrthoProjectionMatrix
        public Matrix4f getCached(float width, float height) {
            if (this.width != width || this.height != height) {
                this.projectionMatrix = createProjectionMatrix(width, height);
                this.width = width;
                this.height = height;
            }
            return this.projectionMatrix;
        }
    }
}
