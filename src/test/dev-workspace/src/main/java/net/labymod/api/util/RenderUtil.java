package net.labymod.api.util;

import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.laby3d.shaders.block.CosmeticsUniformBlockData;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/RenderUtil.class */
public final class RenderUtil {
    private static RenderTarget offscreenTarget;

    @Nullable
    private static CosmeticsUniformBlockData cosmeticsData;

    @Nullable
    private static Matrix4f offscreenProjectionMatrix;

    @Nullable
    private static Matrix4f offscreenModelViewMatrix;
    private static TagType nameTagType;
    private static float lastElementLayer;

    private RenderUtil() {
    }

    @NotNull
    public static TagType getNameTagType() {
        return nameTagType;
    }

    public static void setNameTagType(@NotNull TagType nameTagType2) {
        nameTagType = nameTagType2;
    }

    public static RenderTarget getOffscreenTarget() {
        return offscreenTarget;
    }

    public static Vector3f getTranslation() {
        return new Vector3f(0.0f, 0.0f, -10000.0f);
    }

    public static void setOffscreenTarget(RenderTarget offscreenTarget2) {
        offscreenTarget = offscreenTarget2;
    }

    @Nullable
    public static Matrix4f getOffscreenProjectionMatrix() {
        return offscreenProjectionMatrix;
    }

    public static void setOffscreenProjectionMatrix(@Nullable Matrix4f offscreenProjectionMatrix2) {
        offscreenProjectionMatrix = offscreenProjectionMatrix2;
    }

    @Nullable
    public static Matrix4f getOffscreenModelViewMatrix() {
        return offscreenModelViewMatrix;
    }

    public static void setOffscreenModelViewMatrix(@Nullable Matrix4f offscreenModelViewMatrix2) {
        offscreenModelViewMatrix = offscreenModelViewMatrix2;
    }

    public static OffscreenScope pushOffscreen(@Nullable RenderTarget target, @Nullable Matrix4f projection, @Nullable Matrix4f modelView) {
        OffscreenScope scope = new OffscreenScope(offscreenTarget, offscreenProjectionMatrix, offscreenModelViewMatrix);
        offscreenTarget = target;
        offscreenProjectionMatrix = projection;
        offscreenModelViewMatrix = modelView;
        return scope;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/RenderUtil$OffscreenScope.class */
    public static final class OffscreenScope implements AutoCloseable {

        @Nullable
        private final RenderTarget previousTarget;

        @Nullable
        private final Matrix4f previousProjection;

        @Nullable
        private final Matrix4f previousModelView;

        private OffscreenScope(@Nullable RenderTarget previousTarget, @Nullable Matrix4f previousProjection, @Nullable Matrix4f previousModelView) {
            this.previousTarget = previousTarget;
            this.previousProjection = previousProjection;
            this.previousModelView = previousModelView;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            RenderUtil.offscreenTarget = this.previousTarget;
            RenderUtil.offscreenProjectionMatrix = this.previousProjection;
            RenderUtil.offscreenModelViewMatrix = this.previousModelView;
        }
    }

    @Nullable
    public static CosmeticsUniformBlockData getCosmeticsData() {
        return cosmeticsData;
    }

    public static void setCosmeticsData(@Nullable CosmeticsUniformBlockData cosmeticsData2) {
        cosmeticsData = cosmeticsData2;
    }

    public static float getLastElementLayer() {
        return lastElementLayer;
    }

    public static void setLastElementLayer(float lastElementLayer2) {
        lastElementLayer = lastElementLayer2;
    }
}
