package net.minecraft.client.renderer.culling;

import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector4f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/culling/Frustum.class */
public class Frustum {
    public static final int OFFSET_STEP = 4;
    private final FrustumIntersection intersection = new FrustumIntersection();
    private final Matrix4f matrix = new Matrix4f();
    private Vector4f viewVector;
    private double camX;
    private double camY;
    private double camZ;

    public Frustum(Matrix4f $$0, Matrix4f $$1) {
        calculateFrustum($$0, $$1);
    }

    public Frustum(Frustum $$0) {
        this.intersection.set($$0.matrix);
        this.matrix.set($$0.matrix);
        this.camX = $$0.camX;
        this.camY = $$0.camY;
        this.camZ = $$0.camZ;
        this.viewVector = $$0.viewVector;
    }

    public Frustum offset(float $$0) {
        this.camX += (double) (this.viewVector.x * $$0);
        this.camY += (double) (this.viewVector.y * $$0);
        this.camZ += (double) (this.viewVector.z * $$0);
        return this;
    }

    public Frustum offsetToFullyIncludeCameraCube(int $$0) {
        double $$1 = Math.floor(this.camX / ((double) $$0)) * ((double) $$0);
        double $$2 = Math.floor(this.camY / ((double) $$0)) * ((double) $$0);
        double $$3 = Math.floor(this.camZ / ((double) $$0)) * ((double) $$0);
        double $$4 = Math.ceil(this.camX / ((double) $$0)) * ((double) $$0);
        double $$5 = Math.ceil(this.camY / ((double) $$0)) * ((double) $$0);
        double $$6 = Math.ceil(this.camZ / ((double) $$0)) * ((double) $$0);
        while (this.intersection.intersectAab((float) ($$1 - this.camX), (float) ($$2 - this.camY), (float) ($$3 - this.camZ), (float) ($$4 - this.camX), (float) ($$5 - this.camY), (float) ($$6 - this.camZ)) != -2) {
            this.camX -= (double) (this.viewVector.x() * 4.0f);
            this.camY -= (double) (this.viewVector.y() * 4.0f);
            this.camZ -= (double) (this.viewVector.z() * 4.0f);
        }
        return this;
    }

    public void prepare(double $$0, double $$1, double $$2) {
        this.camX = $$0;
        this.camY = $$1;
        this.camZ = $$2;
    }

    private void calculateFrustum(Matrix4f $$0, Matrix4f $$1) {
        $$1.mul($$0, this.matrix);
        this.intersection.set(this.matrix);
        this.viewVector = this.matrix.transformTranspose(new Vector4f(0.0f, 0.0f, 1.0f, 0.0f));
    }

    public boolean isVisible(AABB $$0) {
        int $$1 = cubeInFrustum($$0.minX, $$0.minY, $$0.minZ, $$0.maxX, $$0.maxY, $$0.maxZ);
        return $$1 == -2 || $$1 == -1;
    }

    public int cubeInFrustum(BoundingBox $$0) {
        return cubeInFrustum($$0.minX(), $$0.minY(), $$0.minZ(), $$0.maxX() + 1, $$0.maxY() + 1, $$0.maxZ() + 1);
    }

    private int cubeInFrustum(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
        float $$6 = (float) ($$0 - this.camX);
        float $$7 = (float) ($$1 - this.camY);
        float $$8 = (float) ($$2 - this.camZ);
        float $$9 = (float) ($$3 - this.camX);
        float $$10 = (float) ($$4 - this.camY);
        float $$11 = (float) ($$5 - this.camZ);
        return this.intersection.intersectAab($$6, $$7, $$8, $$9, $$10, $$11);
    }

    public boolean pointInFrustum(double $$0, double $$1, double $$2) {
        return this.intersection.testPoint((float) ($$0 - this.camX), (float) ($$1 - this.camY), (float) ($$2 - this.camZ));
    }

    public Vector4f[] getFrustumPoints() {
        Vector4f[] $$0 = {new Vector4f(-1.0f, -1.0f, -1.0f, 1.0f), new Vector4f(1.0f, -1.0f, -1.0f, 1.0f), new Vector4f(1.0f, 1.0f, -1.0f, 1.0f), new Vector4f(-1.0f, 1.0f, -1.0f, 1.0f), new Vector4f(-1.0f, -1.0f, 1.0f, 1.0f), new Vector4f(1.0f, -1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(-1.0f, 1.0f, 1.0f, 1.0f)};
        Matrix4f $$1 = this.matrix.invert(new Matrix4f());
        for (int $$2 = 0; $$2 < 8; $$2++) {
            $$1.transform($$0[$$2]);
            $$0[$$2].div($$0[$$2].w());
        }
        return $$0;
    }

    public double getCamX() {
        return this.camX;
    }

    public double getCamY() {
        return this.camY;
    }

    public double getCamZ() {
        return this.camZ;
    }
}
