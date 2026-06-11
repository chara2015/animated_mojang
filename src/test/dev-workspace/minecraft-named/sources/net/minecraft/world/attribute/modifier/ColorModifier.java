package net.minecraft.world.attribute.modifier;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.LerpFunction;
import net.minecraft.world.entity.Display;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/modifier/ColorModifier.class */
public interface ColorModifier<Argument> extends AttributeModifier<Integer, Argument> {
    public static final ColorModifier<Integer> ALPHA_BLEND = new ColorModifier<Integer>() { // from class: net.minecraft.world.attribute.modifier.ColorModifier.1
        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        public Integer apply(Integer $$0, Integer $$1) {
            return Integer.valueOf(ARGB.alphaBlend($$0.intValue(), $$1.intValue()));
        }

        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        public Codec<Integer> argumentCodec(EnvironmentAttribute<Integer> $$0) {
            return ExtraCodecs.STRING_ARGB_COLOR;
        }

        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        public LerpFunction<Integer> argumentKeyframeLerp(EnvironmentAttribute<Integer> $$0) {
            return LerpFunction.ofColor();
        }
    };
    public static final ColorModifier<Integer> ADD = (v0, v1) -> {
        return ARGB.addRgb(v0, v1);
    };
    public static final ColorModifier<Integer> SUBTRACT = (v0, v1) -> {
        return ARGB.subtractRgb(v0, v1);
    };
    public static final ColorModifier<Integer> MULTIPLY_RGB = (v0, v1) -> {
        return ARGB.multiply(v0, v1);
    };
    public static final ColorModifier<Integer> MULTIPLY_ARGB = (v0, v1) -> {
        return ARGB.multiply(v0, v1);
    };
    public static final ColorModifier<BlendToGray> BLEND_TO_GRAY = new ColorModifier<BlendToGray>() { // from class: net.minecraft.world.attribute.modifier.ColorModifier.2
        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        public Integer apply(Integer $$0, BlendToGray $$1) {
            int $$2 = ARGB.scaleRGB(ARGB.greyscale($$0.intValue()), $$1.brightness);
            return Integer.valueOf(ARGB.srgbLerp($$1.factor, $$0.intValue(), $$2));
        }

        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        public Codec<BlendToGray> argumentCodec(EnvironmentAttribute<Integer> $$0) {
            return BlendToGray.CODEC;
        }

        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        public LerpFunction<BlendToGray> argumentKeyframeLerp(EnvironmentAttribute<Integer> $$0) {
            return ($$02, $$1, $$2) -> {
                return new BlendToGray(Mth.lerp($$02, $$1.brightness, $$2.brightness), Mth.lerp($$02, $$1.factor, $$2.factor));
            };
        }
    };

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/modifier/ColorModifier$RgbModifier.class */
    @FunctionalInterface
    public interface RgbModifier extends ColorModifier<Integer> {
        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        default Codec<Integer> argumentCodec(EnvironmentAttribute<Integer> $$0) {
            return ExtraCodecs.STRING_RGB_COLOR;
        }

        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        default LerpFunction<Integer> argumentKeyframeLerp(EnvironmentAttribute<Integer> $$0) {
            return LerpFunction.ofColor();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/modifier/ColorModifier$ArgbModifier.class */
    @FunctionalInterface
    public interface ArgbModifier extends ColorModifier<Integer> {
        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        default Codec<Integer> argumentCodec(EnvironmentAttribute<Integer> $$0) {
            return Codec.either(ExtraCodecs.STRING_ARGB_COLOR, ExtraCodecs.RGB_COLOR_CODEC).xmap(Either::unwrap, $$02 -> {
                return ARGB.alpha($$02.intValue()) == 255 ? Either.right($$02) : Either.left($$02);
            });
        }

        @Override // net.minecraft.world.attribute.modifier.AttributeModifier
        default LerpFunction<Integer> argumentKeyframeLerp(EnvironmentAttribute<Integer> $$0) {
            return LerpFunction.ofColor();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/modifier/ColorModifier$BlendToGray.class */
    public static final class BlendToGray extends Record {
        private final float brightness;
        private final float factor;
        public static final Codec<BlendToGray> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(Codec.floatRange(0.0f, 1.0f).fieldOf(Display.TAG_BRIGHTNESS).forGetter((v0) -> {
                return v0.brightness();
            }), Codec.floatRange(0.0f, 1.0f).fieldOf("factor").forGetter((v0) -> {
                return v0.factor();
            })).apply($$0, (v1, v2) -> {
                return new BlendToGray(v1, v2);
            });
        });

        public BlendToGray(float $$0, float $$1) {
            this.brightness = $$0;
            this.factor = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BlendToGray.class), BlendToGray.class, "brightness;factor", "FIELD:Lnet/minecraft/world/attribute/modifier/ColorModifier$BlendToGray;->brightness:F", "FIELD:Lnet/minecraft/world/attribute/modifier/ColorModifier$BlendToGray;->factor:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BlendToGray.class), BlendToGray.class, "brightness;factor", "FIELD:Lnet/minecraft/world/attribute/modifier/ColorModifier$BlendToGray;->brightness:F", "FIELD:Lnet/minecraft/world/attribute/modifier/ColorModifier$BlendToGray;->factor:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BlendToGray.class, Object.class), BlendToGray.class, "brightness;factor", "FIELD:Lnet/minecraft/world/attribute/modifier/ColorModifier$BlendToGray;->brightness:F", "FIELD:Lnet/minecraft/world/attribute/modifier/ColorModifier$BlendToGray;->factor:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public float brightness() {
            return this.brightness;
        }

        public float factor() {
            return this.factor;
        }
    }
}
