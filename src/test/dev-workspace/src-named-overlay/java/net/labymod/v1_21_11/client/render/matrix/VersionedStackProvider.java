package net.labymod.v1_21_11.client.render.matrix;

import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.api.client.render.matrix.AbstractStackProvider;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/render/matrix/VersionedStackProvider.class */
public final class VersionedStackProvider extends AbstractStackProvider<PoseStack, Matrix3f, Matrix4f> {
    public VersionedStackProvider(PoseStack poseStack) {
        super(poseStack, () -> {
            return poseStack.last().normal();
        }, () -> {
            return poseStack.last().pose();
        });
    }

    public void translate(float x, float y, float z) {
        ((PoseStack) this.stack).translate(x, y, z);
    }

    public void rotate(float angle, float x, float y, float z) {
        ROTATION_VECTOR.set(x, y, z);
        ((PoseStack) this.stack).mulPose((Quaternionf) MAPPER.toQuaternion(ROTATION_VECTOR.rotationDegrees(angle)));
    }

    public void rotateRadians(float radians, float x, float y, float z) {
        ROTATION_VECTOR.set(x, y, z);
        ((PoseStack) this.stack).mulPose((Quaternionf) MAPPER.toQuaternion(ROTATION_VECTOR.rotation(radians)));
    }

    public void scale(float x, float y, float z) {
        ((PoseStack) this.stack).scale(x, y, z);
    }

    public void mul(Matrix4f matrix) {
        ((PoseStack) this.stack).mulPose(matrix);
    }

    public void push() {
        ((PoseStack) this.stack).pushPose();
    }

    public void pop() {
        ((PoseStack) this.stack).popPose();
    }

    public void identity() {
        ((PoseStack) this.stack).setIdentity();
    }
}
