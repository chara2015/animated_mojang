package net.labymod.v26_1_2.client.render.matrix;

import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.api.client.render.matrix.AbstractStackProvider;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/render/matrix/VersionedStackProvider.class */
public final class VersionedStackProvider extends AbstractStackProvider<PoseStack, Matrix3f, Matrix4f> {
    public VersionedStackProvider(PoseStack poseStack) {
        super(poseStack, () -> {
            return poseStack.last().normal();
        }, () -> {
            return poseStack.last().pose();
        });
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void translate(float x, float y, float z) {
        ((PoseStack) this.stack).translate(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotate(float angle, float x, float y, float z) {
        ROTATION_VECTOR.set(x, y, z);
        ((PoseStack) this.stack).mulPose((Quaternionf) MAPPER.toQuaternion(ROTATION_VECTOR.rotationDegrees(angle)));
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotateRadians(float radians, float x, float y, float z) {
        ROTATION_VECTOR.set(x, y, z);
        ((PoseStack) this.stack).mulPose((Quaternionf) MAPPER.toQuaternion(ROTATION_VECTOR.rotation(radians)));
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void scale(float x, float y, float z) {
        ((PoseStack) this.stack).scale(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void mul(Matrix4f matrix) {
        ((PoseStack) this.stack).mulPose(matrix);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void push() {
        ((PoseStack) this.stack).pushPose();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void pop() {
        ((PoseStack) this.stack).popPose();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void identity() {
        ((PoseStack) this.stack).setIdentity();
    }
}
