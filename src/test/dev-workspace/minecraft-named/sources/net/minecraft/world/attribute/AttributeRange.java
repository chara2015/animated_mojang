package net.minecraft.world.attribute;

import com.mojang.serialization.DataResult;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/AttributeRange.class */
public interface AttributeRange<Value> {
    public static final AttributeRange<Float> UNIT_FLOAT = ofFloat(0.0f, 1.0f);
    public static final AttributeRange<Float> NON_NEGATIVE_FLOAT = ofFloat(0.0f, Float.POSITIVE_INFINITY);

    DataResult<Value> validate(Value value);

    Value sanitize(Value value);

    static <Value> AttributeRange<Value> any() {
        return new AttributeRange<Value>() { // from class: net.minecraft.world.attribute.AttributeRange.1
            @Override // net.minecraft.world.attribute.AttributeRange
            public DataResult<Value> validate(Value $$0) {
                return DataResult.success($$0);
            }

            @Override // net.minecraft.world.attribute.AttributeRange
            public Value sanitize(Value $$0) {
                return $$0;
            }
        };
    }

    static AttributeRange<Float> ofFloat(final float $$0, final float $$1) {
        return new AttributeRange<Float>() { // from class: net.minecraft.world.attribute.AttributeRange.2
            @Override // net.minecraft.world.attribute.AttributeRange
            public DataResult<Float> validate(Float $$02) {
                if ($$02.floatValue() >= $$0 && $$02.floatValue() <= $$1) {
                    return DataResult.success($$02);
                }
                float f = $$0;
                float f2 = $$1;
                return DataResult.error(() -> {
                    return $$02 + " is not in range [" + f + "; " + f2 + "]";
                });
            }

            @Override // net.minecraft.world.attribute.AttributeRange
            public Float sanitize(Float $$02) {
                if ($$02.floatValue() >= $$0 && $$02.floatValue() <= $$1) {
                    return $$02;
                }
                return Float.valueOf(Mth.clamp($$02.floatValue(), $$0, $$1));
            }
        };
    }
}
