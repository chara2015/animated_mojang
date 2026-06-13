package net.labymod.api.laby3d.util;

import net.labymod.api.Laby;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.laby3d.GameTransformations;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.NotNull;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/util/Frustum.class */
public class Frustum {
    private static final boolean USE_MODEL_VIEW_MATRIX = MinecraftVersions.V1_16_5.orOlder();
    private final FrustumIntersection intersection = new FrustumIntersection();
    private final Matrix4f matrix = new Matrix4f();
    private Vector4f viewVector;
    private double camX;
    private double camY;
    private double camZ;

    public Frustum(@NotNull Matrix4f viewMatrix, @NotNull Matrix4f projectionMatrix) {
        calculateFrustum(viewMatrix, projectionMatrix);
    }

    public Frustum(@NotNull Frustum frustum) {
        this.intersection.set(frustum.matrix);
        this.matrix.set(frustum.matrix);
        this.camX = frustum.camX;
        this.camY = frustum.camY;
        this.camZ = frustum.camZ;
        this.viewVector = frustum.viewVector;
    }

    @NotNull
    public static Frustum createGameFrustum() {
        Matrix4f matrix4fViewMatrix;
        GameTransformations transformations = Laby.references().gameTransformations();
        if (USE_MODEL_VIEW_MATRIX) {
            matrix4fViewMatrix = transformations.modelViewMatrix();
        } else {
            matrix4fViewMatrix = transformations.viewMatrix();
        }
        Frustum frustum = new Frustum(JomlMath.cloneMatrix(matrix4fViewMatrix), JomlMath.cloneMatrix(transformations.projectionMatrix()));
        MinecraftCamera camera = Laby.labyAPI().minecraft().getCamera();
        if (camera != null) {
            DoubleVector3 position = camera.renderPosition();
            frustum.prepare(position.getX(), position.getY(), position.getZ());
        }
        return frustum;
    }

    public void prepare(double camX, double camY, double camZ) {
        this.camX = camX;
        this.camY = camY;
        this.camZ = camZ;
    }

    public boolean isVisible(@NotNull FloatVector3 point) {
        return isVisible(point.getX(), point.getY(), point.getZ());
    }

    public boolean isVisible(@NotNull DoubleVector3 point) {
        return isVisible(point.getX(), point.getY(), point.getZ());
    }

    public boolean isVisible(double x, double y, double z) {
        float relativeX = (float) (x - this.camX);
        float relativeY = (float) (y - this.camY);
        float relativeZ = (float) (z - this.camZ);
        return this.intersection.testPoint(relativeX, relativeY, relativeZ);
    }

    public boolean isVisible(@NotNull AxisAlignedBoundingBox aabb) {
        return isVisible(aabb.getMinX(), aabb.getMinY(), aabb.getMinZ(), aabb.getMaxX(), aabb.getMaxY(), aabb.getMaxZ());
    }

    public boolean isVisible(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        int result = cubeInFrustum(minX, minY, minZ, maxX, maxY, maxZ);
        return result == -2 || result == -1;
    }

    public int cubeInFrustum(@NotNull AxisAlignedBoundingBox aabb) {
        return cubeInFrustum(aabb.getMinX(), aabb.getMinY(), aabb.getMinZ(), aabb.getMaxX(), aabb.getMaxY(), aabb.getMaxZ());
    }

    public int cubeInFrustum(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        float relativeMinX = (float) (minX - this.camX);
        float relativeMinY = (float) (minY - this.camY);
        float relativeMinZ = (float) (minZ - this.camZ);
        float relativeMaxX = (float) (maxX - this.camX);
        float relativeMaxY = (float) (maxY - this.camY);
        float relativeMaxZ = (float) (maxZ - this.camZ);
        return this.intersection.intersectAab(relativeMinX, relativeMinY, relativeMinZ, relativeMaxX, relativeMaxY, relativeMaxZ);
    }

    public Matrix4f matrix() {
        return this.matrix;
    }

    public double camX() {
        return this.camX;
    }

    public double camY() {
        return this.camY;
    }

    public double camZ() {
        return this.camZ;
    }

    private void calculateFrustum(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        projectionMatrix.mul(viewMatrix, this.matrix);
        this.intersection.set(this.matrix);
        this.viewVector = this.matrix.transformTranspose(new Vector4f(0.0f, 0.0f, 1.0f, 0.0f));
    }
}
