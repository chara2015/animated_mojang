package net.labymod.v1_21_10.client.render.matrix;

import net.labymod.api.client.render.matrix.AbstractStackProvider;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/render/matrix/VersionedStackProvider.class */
public final class VersionedStackProvider extends AbstractStackProvider<fua, Matrix3f, Matrix4f> {
    public VersionedStackProvider(fua poseStack) {
        super(poseStack, () -> {
            return poseStack.c().b();
        }, () -> {
            return poseStack.c().a();
        });
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void translate(float x, float y, float z) {
        ((fua) this.stack).a(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotate(float angle, float x, float y, float z) {
        ROTATION_VECTOR.set(x, y, z);
        ((fua) this.stack).a((Quaternionf) MAPPER.toQuaternion(ROTATION_VECTOR.rotationDegrees(angle)));
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotateRadians(float radians, float x, float y, float z) {
        ROTATION_VECTOR.set(x, y, z);
        ((fua) this.stack).a((Quaternionf) MAPPER.toQuaternion(ROTATION_VECTOR.rotation(radians)));
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void scale(float x, float y, float z) {
        ((fua) this.stack).b(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void mul(Matrix4f matrix) {
        ((fua) this.stack).a(matrix);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void push() {
        ((fua) this.stack).a();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void pop() {
        ((fua) this.stack).b();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void identity() {
        ((fua) this.stack).e();
    }
}
