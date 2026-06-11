package net.minecraft.world.attribute.modifier;

import com.mojang.serialization.Codec;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.LerpFunction;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/modifier/FloatModifier.class */
public interface FloatModifier<Argument> extends AttributeModifier<Float, Argument> {
    public static final FloatModifier<FloatWithAlpha> ALPHA_BLEND = new FloatModifier<FloatWithAlpha>() { // from class: net.minecraft.world.attribute.modifier.FloatModifier.1
        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        public Float apply(Float $$0, FloatWithAlpha $$1) {
            return Float.valueOf(Mth.lerp($$1.alpha(), $$0.floatValue(), $$1.value()));
        }

        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        public Codec<FloatWithAlpha> argumentCodec(EnvironmentAttribute<Float> $$0) {
            return FloatWithAlpha.CODEC;
        }

        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        public LerpFunction<FloatWithAlpha> argumentKeyframeLerp(EnvironmentAttribute<Float> $$0) {
            return ($$02, $$1, $$2) -> {
                return new FloatWithAlpha(Mth.lerp($$02, $$1.value(), $$2.value()), Mth.lerp($$02, $$1.alpha(), $$2.alpha()));
            };
        }
    };
    public static final FloatModifier<Float> ADD = (v0, v1) -> {
        return Float.sum(v0, v1);
    };
    public static final FloatModifier<Float> SUBTRACT = ($$0, $$1) -> {
        return Float.valueOf($$0.floatValue() - $$1.floatValue());
    };
    public static final FloatModifier<Float> MULTIPLY = ($$0, $$1) -> {
        return Float.valueOf($$0.floatValue() * $$1.floatValue());
    };
    public static final FloatModifier<Float> MINIMUM = (v0, v1) -> {
        return Math.min(v0, v1);
    };
    public static final FloatModifier<Float> MAXIMUM = (v0, v1) -> {
        return Math.max(v0, v1);
    };

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/modifier/FloatModifier$Simple.class */
    @FunctionalInterface
    public interface Simple extends FloatModifier<Float> {
        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        default Codec<Float> argumentCodec(EnvironmentAttribute<Float> $$0) {
            return Codec.FLOAT;
        }

        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        default LerpFunction<Float> argumentKeyframeLerp(EnvironmentAttribute<Float> $$0) {
            return LerpFunction.ofFloat();
        }
    }
}
