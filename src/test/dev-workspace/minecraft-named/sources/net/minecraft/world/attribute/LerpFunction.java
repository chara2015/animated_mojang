package net.minecraft.world.attribute;

import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/LerpFunction.class */
public interface LerpFunction<T> {
    T apply(float f, T t, T t2);

    static LerpFunction<Float> ofFloat() {
        return (v0, v1, v2) -> {
            return Mth.lerp(v0, v1, v2);
        };
    }

    static LerpFunction<Float> ofDegrees(float $$0) {
        return ($$1, $$2, $$3) -> {
            float $$4 = Mth.wrapDegrees($$3.floatValue() - $$2.floatValue());
            if (Math.abs($$4) >= $$0) {
                return $$3;
            }
            return Float.valueOf($$2.floatValue() + ($$1 * $$4));
        };
    }

    static <T> LerpFunction<T> ofConstant() {
        return ($$0, $$1, $$2) -> {
            return $$1;
        };
    }

    static <T> LerpFunction<T> ofStep(float $$0) {
        return ($$1, $$2, $$3) -> {
            return $$1 >= $$0 ? $$3 : $$2;
        };
    }

    static LerpFunction<Integer> ofColor() {
        return (v0, v1, v2) -> {
            return ARGB.srgbLerp(v0, v1, v2);
        };
    }
}
