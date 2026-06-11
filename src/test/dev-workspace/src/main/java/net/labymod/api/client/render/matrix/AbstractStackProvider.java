package net.labymod.api.client.render.matrix;

import java.util.function.Supplier;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/matrix/AbstractStackProvider.class */
public abstract class AbstractStackProvider<S, M3, M4> implements StackProvider {
    protected static final GameMathMapper MAPPER = MathHelper.mapper();
    protected static final FloatVector3 ROTATION_VECTOR = new FloatVector3();
    protected final S stack;
    private final Supplier<M3> normalSupplier;
    private final Supplier<M4> poseSupplier;

    protected AbstractStackProvider(S stack, Supplier<M3> normalSupplier, Supplier<M4> poseSupplier) {
        this.stack = stack;
        this.normalSupplier = normalSupplier;
        this.poseSupplier = poseSupplier;
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public Matrix3f getNormal() {
        return (Matrix3f) this.normalSupplier.get();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public Matrix4f getPose() {
        return (Matrix4f) this.poseSupplier.get();
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    @Nullable
    public Object getPoseStack() {
        return this.stack;
    }

    @Override // net.labymod.api.client.render.matrix.StackProvider
    public int index() {
        return ((VanillaStackAccessor) this.stack).index();
    }
}
