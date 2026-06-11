package net.labymod.core.laby3d.renderer;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.laby3d.util.Frustum;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/renderer/FrustumDebugRenderer.class */
public class FrustumDebugRenderer {
    private static final int CORNER_NBL = 0;
    private static final int CORNER_NBR = 1;
    private static final int CORNER_NTR = 2;
    private static final int CORNER_NTL = 3;
    private static final int CORNER_FBL = 4;
    private static final int CORNER_FBR = 5;
    private static final int CORNER_FTR = 6;
    private static final int CORNER_FTL = 7;
    private Frustum frozenFrustum;
    private double[] frozenCorners;
    private static final int COLOR_NEAR = ColorFormat.ARGB32.pack(1.0f, 0.2f, 0.2f, 0.3f);
    private static final int COLOR_FAR = ColorFormat.ARGB32.pack(0.2f, 1.0f, 0.2f, 0.3f);
    private static final int COLOR_LEFT = ColorFormat.ARGB32.pack(0.2f, 0.5f, 1.0f, 0.3f);
    private static final int COLOR_RIGHT = ColorFormat.ARGB32.pack(1.0f, 1.0f, 0.2f, 0.3f);
    private static final int COLOR_BOTTOM = ColorFormat.ARGB32.pack(1.0f, 0.5f, 0.0f, 0.3f);
    private static final int COLOR_TOP = ColorFormat.ARGB32.pack(0.8f, 0.2f, 1.0f, 0.3f);
    private static final float[] NDC_X = {-1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f};
    private static final float[] NDC_Y = {-1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f};
    private static final float[] NDC_Z = {-1.0f, -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f};

    public void freeze(@NotNull Frustum frustum) {
        if (this.frozenFrustum != null) {
            return;
        }
        this.frozenFrustum = new Frustum(frustum);
        this.frozenCorners = extractWorldCorners(frustum);
    }

    public void clear() {
        this.frozenFrustum = null;
        this.frozenCorners = null;
    }

    @Nullable
    public Frustum frozenFrustum() {
        return this.frozenFrustum;
    }

    public boolean isFrozen() {
        return this.frozenFrustum != null;
    }

    public void submit(@NotNull Stack stack, @NotNull SubmissionCollector collector, double camX, double camY, double camZ) {
        if (this.frozenCorners == null) {
            return;
        }
        float[] c = toCameraRelative(this.frozenCorners, camX, camY, camZ);
        Material material = LevelMaterial.builder(RenderStates.DEBUG_FRUSTUM).setTexture(0, Textures.WHITE).build();
        collector.order(SubmissionOrders.DEBUG).submitCustomGeometry(stack, material, (pose, consumer) -> {
            emitQuad(consumer, pose, c, 0, 1, 2, 3, COLOR_NEAR);
            emitQuad(consumer, pose, c, 4, 7, 6, 5, COLOR_FAR);
            emitQuad(consumer, pose, c, 0, 3, 7, 4, COLOR_LEFT);
            emitQuad(consumer, pose, c, 1, 5, 6, 2, COLOR_RIGHT);
            emitQuad(consumer, pose, c, 0, 4, 5, 1, COLOR_BOTTOM);
            emitQuad(consumer, pose, c, 3, 2, 6, 7, COLOR_TOP);
        });
    }

    private static double[] extractWorldCorners(Frustum frustum) {
        Matrix4f inverse = Laby.references().laby3D().matrices().acquire().set(frustum.matrix()).invert();
        double[] corners = new double[24];
        Vector3f temp = new Vector3f();
        for (int i = 0; i < 8; i++) {
            temp.set(NDC_X[i], NDC_Y[i], NDC_Z[i]);
            inverse.transformProject(temp);
            corners[i * 3] = ((double) temp.x) + frustum.camX();
            corners[(i * 3) + 1] = ((double) temp.y) + frustum.camY();
            corners[(i * 3) + 2] = ((double) temp.z) + frustum.camZ();
        }
        return corners;
    }

    private static float[] toCameraRelative(double[] worldCorners, double camX, double camY, double camZ) {
        float[] relative = new float[24];
        for (int i = 0; i < 8; i++) {
            relative[i * 3] = (float) (worldCorners[i * 3] - camX);
            relative[(i * 3) + 1] = (float) (worldCorners[(i * 3) + 1] - camY);
            relative[(i * 3) + 2] = (float) (worldCorners[(i * 3) + 2] - camZ);
        }
        return relative;
    }

    private static void emitQuad(VertexConsumer consumer, Matrix4f pose, float[] corners, int a, int b, int c, int d, int color) {
        consumer.addVertex(pose, corners[a * 3], corners[(a * 3) + 1], corners[(a * 3) + 2]).setColor(color);
        consumer.addVertex(pose, corners[b * 3], corners[(b * 3) + 1], corners[(b * 3) + 2]).setColor(color);
        consumer.addVertex(pose, corners[c * 3], corners[(c * 3) + 1], corners[(c * 3) + 2]).setColor(color);
        consumer.addVertex(pose, corners[d * 3], corners[(d * 3) + 1], corners[(d * 3) + 2]).setColor(color);
    }
}
