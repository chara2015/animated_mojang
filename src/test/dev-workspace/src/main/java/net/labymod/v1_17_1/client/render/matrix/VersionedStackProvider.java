package net.labymod.v1_17_1.client.render.matrix;

import net.labymod.api.client.render.matrix.AbstractStackProvider;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.util.math.MathHelper;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/render/matrix/VersionedStackProvider.class */
public final class VersionedStackProvider extends AbstractStackProvider<dql, Matrix3f, Matrix4f> {
    private static final GameMathMapper MAPPER = MathHelper.mapper();

    public VersionedStackProvider(dql poseStack) {
        super(poseStack, () -> {
            return MAPPER.toJomlMatrix3f(poseStack.c().b());
        }, () -> {
            return MAPPER.toJomlMatrix4f(poseStack.c().a());
        });
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void translate(float x, float y, float z) {
        ((dql) this.stack).a(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotate(float angle, float x, float y, float z) {
        ROTATION_VECTOR.set(x, y, z);
        ((dql) this.stack).a((g) MAPPER.toQuaternion(ROTATION_VECTOR.rotationDegrees(angle)));
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void rotateRadians(float radians, float x, float y, float z) {
        ROTATION_VECTOR.set(x, y, z);
        ((dql) this.stack).a((g) MAPPER.toQuaternion(ROTATION_VECTOR.rotation(radians)));
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void scale(float x, float y, float z) {
        ((dql) this.stack).a(x, y, z);
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void mul(Matrix4f matrix) {
        ((dql) this.stack).a((d) MAPPER.toMatrix4f(MAPPER.fromMatrix4f(matrix)));
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void push() {
        ((dql) this.stack).a();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void pop() {
        ((dql) this.stack).b();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public void identity() {
        ((dql) this.stack).e();
    }
}
