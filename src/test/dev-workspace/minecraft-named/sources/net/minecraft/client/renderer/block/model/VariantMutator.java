package net.minecraft.client.renderer.block.model;

import com.mojang.math.Quadrant;
import java.util.function.UnaryOperator;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/VariantMutator.class */
@FunctionalInterface
public interface VariantMutator extends UnaryOperator<Variant> {
    public static final VariantProperty<Quadrant> X_ROT = (v0, v1) -> {
        return v0.withXRot(v1);
    };
    public static final VariantProperty<Quadrant> Y_ROT = (v0, v1) -> {
        return v0.withYRot(v1);
    };
    public static final VariantProperty<Quadrant> Z_ROT = (v0, v1) -> {
        return v0.withZRot(v1);
    };
    public static final VariantProperty<Identifier> MODEL = (v0, v1) -> {
        return v0.withModel(v1);
    };
    public static final VariantProperty<Boolean> UV_LOCK = (v0, v1) -> {
        return v0.withUvLock(v1);
    };

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/VariantMutator$VariantProperty.class */
    @FunctionalInterface
    public interface VariantProperty<T> {
        Variant apply(Variant variant, T t);

        default VariantMutator withValue(T $$0) {
            return $$1 -> {
                return apply($$1, $$0);
            };
        }
    }

    default VariantMutator then(VariantMutator $$0) {
        return $$1 -> {
            return (Variant) $$0.apply((Variant) apply($$1));
        };
    }
}
