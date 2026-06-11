package net.minecraft.world.item.enchantment;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/LevelBasedValue.class */
public interface LevelBasedValue {
    public static final Codec<LevelBasedValue> DISPATCH_CODEC = BuiltInRegistries.ENCHANTMENT_LEVEL_BASED_VALUE_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.codec();
    }, $$0 -> {
        return $$0;
    });
    public static final Codec<LevelBasedValue> CODEC = Codec.either(Constant.CODEC, DISPATCH_CODEC).xmap($$0 -> {
        return (LevelBasedValue) $$0.map($$0 -> {
            return $$0;
        }, $$02 -> {
            return $$02;
        });
    }, $$02 -> {
        if (!($$02 instanceof Constant)) {
            return Either.right($$02);
        }
        Constant $$1 = (Constant) $$02;
        return Either.left($$1);
    });

    float calculate(int i);

    MapCodec<? extends LevelBasedValue> codec();

    static MapCodec<? extends LevelBasedValue> bootstrap(Registry<MapCodec<? extends LevelBasedValue>> $$0) {
        Registry.register((Registry<? super MapCodec<Clamped>>) $$0, "clamped", Clamped.CODEC);
        Registry.register((Registry<? super MapCodec<Fraction>>) $$0, "fraction", Fraction.CODEC);
        Registry.register((Registry<? super MapCodec<LevelsSquared>>) $$0, "levels_squared", LevelsSquared.CODEC);
        Registry.register((Registry<? super MapCodec<Linear>>) $$0, "linear", Linear.CODEC);
        Registry.register((Registry<? super MapCodec<Exponent>>) $$0, "exponent", Exponent.CODEC);
        return (MapCodec) Registry.register((Registry<? super MapCodec<Lookup>>) $$0, "lookup", Lookup.CODEC);
    }

    static Constant constant(float $$0) {
        return new Constant($$0);
    }

    static Linear perLevel(float $$0, float $$1) {
        return new Linear($$0, $$1);
    }

    static Linear perLevel(float $$0) {
        return perLevel($$0, $$0);
    }

    static Lookup lookup(List<Float> $$0, LevelBasedValue $$1) {
        return new Lookup($$0, $$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/LevelBasedValue$Constant.class */
    public static final class Constant extends Record implements LevelBasedValue {
        private final float value;
        public static final Codec<Constant> CODEC = Codec.FLOAT.xmap((v1) -> {
            return new Constant(v1);
        }, (v0) -> {
            return v0.value();
        });
        public static final MapCodec<Constant> TYPED_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Codec.FLOAT.fieldOf("value").forGetter((v0) -> {
                return v0.value();
            })).apply($$0, (v1) -> {
                return new Constant(v1);
            });
        });

        public Constant(float $$0) {
            this.value = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Constant.class), Constant.class, "value", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Constant;->value:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Constant.class), Constant.class, "value", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Constant;->value:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Constant.class, Object.class), Constant.class, "value", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Constant;->value:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public float value() {
            return this.value;
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public float calculate(int $$0) {
            return this.value;
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public MapCodec<Constant> codec() {
            return TYPED_CODEC;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/LevelBasedValue$Lookup.class */
    public static final class Lookup extends Record implements LevelBasedValue {
        private final List<Float> values;
        private final LevelBasedValue fallback;
        public static final MapCodec<Lookup> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Codec.FLOAT.listOf().fieldOf("values").forGetter((v0) -> {
                return v0.values();
            }), LevelBasedValue.CODEC.fieldOf("fallback").forGetter((v0) -> {
                return v0.fallback();
            })).apply($$0, Lookup::new);
        });

        public Lookup(List<Float> $$0, LevelBasedValue $$1) {
            this.values = $$0;
            this.fallback = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Lookup.class), Lookup.class, "values;fallback", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Lookup;->values:Ljava/util/List;", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Lookup;->fallback:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Lookup.class), Lookup.class, "values;fallback", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Lookup;->values:Ljava/util/List;", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Lookup;->fallback:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Lookup.class, Object.class), Lookup.class, "values;fallback", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Lookup;->values:Ljava/util/List;", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Lookup;->fallback:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public List<Float> values() {
            return this.values;
        }

        public LevelBasedValue fallback() {
            return this.fallback;
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public float calculate(int $$0) {
            return $$0 <= this.values.size() ? this.values.get($$0 - 1).floatValue() : this.fallback.calculate($$0);
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public MapCodec<Lookup> codec() {
            return CODEC;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/LevelBasedValue$Linear.class */
    public static final class Linear extends Record implements LevelBasedValue {
        private final float base;
        private final float perLevelAboveFirst;
        public static final MapCodec<Linear> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Codec.FLOAT.fieldOf("base").forGetter((v0) -> {
                return v0.base();
            }), Codec.FLOAT.fieldOf("per_level_above_first").forGetter((v0) -> {
                return v0.perLevelAboveFirst();
            })).apply($$0, (v1, v2) -> {
                return new Linear(v1, v2);
            });
        });

        public Linear(float $$0, float $$1) {
            this.base = $$0;
            this.perLevelAboveFirst = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Linear.class), Linear.class, "base;perLevelAboveFirst", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Linear;->base:F", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Linear;->perLevelAboveFirst:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Linear.class), Linear.class, "base;perLevelAboveFirst", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Linear;->base:F", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Linear;->perLevelAboveFirst:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Linear.class, Object.class), Linear.class, "base;perLevelAboveFirst", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Linear;->base:F", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Linear;->perLevelAboveFirst:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public float base() {
            return this.base;
        }

        public float perLevelAboveFirst() {
            return this.perLevelAboveFirst;
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public float calculate(int $$0) {
            return this.base + (this.perLevelAboveFirst * ($$0 - 1));
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public MapCodec<Linear> codec() {
            return CODEC;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/LevelBasedValue$Clamped.class */
    public static final class Clamped extends Record implements LevelBasedValue {
        private final LevelBasedValue value;
        private final float min;
        private final float max;
        public static final MapCodec<Clamped> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(LevelBasedValue.CODEC.fieldOf("value").forGetter((v0) -> {
                return v0.value();
            }), Codec.FLOAT.fieldOf("min").forGetter((v0) -> {
                return v0.min();
            }), Codec.FLOAT.fieldOf("max").forGetter((v0) -> {
                return v0.max();
            })).apply($$0, (v1, v2, v3) -> {
                return new Clamped(v1, v2, v3);
            });
        }).validate($$02 -> {
            if ($$02.max <= $$02.min) {
                return DataResult.error(() -> {
                    return "Max must be larger than min, min: " + $$02.min + ", max: " + $$02.max;
                });
            }
            return DataResult.success($$02);
        });

        public Clamped(LevelBasedValue $$0, float $$1, float $$2) {
            this.value = $$0;
            this.min = $$1;
            this.max = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Clamped.class), Clamped.class, "value;min;max", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Clamped;->value:Lnet/minecraft/world/item/enchantment/LevelBasedValue;", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Clamped;->min:F", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Clamped;->max:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Clamped.class), Clamped.class, "value;min;max", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Clamped;->value:Lnet/minecraft/world/item/enchantment/LevelBasedValue;", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Clamped;->min:F", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Clamped;->max:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Clamped.class, Object.class), Clamped.class, "value;min;max", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Clamped;->value:Lnet/minecraft/world/item/enchantment/LevelBasedValue;", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Clamped;->min:F", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Clamped;->max:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public LevelBasedValue value() {
            return this.value;
        }

        public float min() {
            return this.min;
        }

        public float max() {
            return this.max;
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public float calculate(int $$0) {
            return Mth.clamp(this.value.calculate($$0), this.min, this.max);
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public MapCodec<Clamped> codec() {
            return CODEC;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/LevelBasedValue$Fraction.class */
    public static final class Fraction extends Record implements LevelBasedValue {
        private final LevelBasedValue numerator;
        private final LevelBasedValue denominator;
        public static final MapCodec<Fraction> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(LevelBasedValue.CODEC.fieldOf("numerator").forGetter((v0) -> {
                return v0.numerator();
            }), LevelBasedValue.CODEC.fieldOf("denominator").forGetter((v0) -> {
                return v0.denominator();
            })).apply($$0, Fraction::new);
        });

        public Fraction(LevelBasedValue $$0, LevelBasedValue $$1) {
            this.numerator = $$0;
            this.denominator = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Fraction.class), Fraction.class, "numerator;denominator", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Fraction;->numerator:Lnet/minecraft/world/item/enchantment/LevelBasedValue;", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Fraction;->denominator:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Fraction.class), Fraction.class, "numerator;denominator", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Fraction;->numerator:Lnet/minecraft/world/item/enchantment/LevelBasedValue;", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Fraction;->denominator:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Fraction.class, Object.class), Fraction.class, "numerator;denominator", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Fraction;->numerator:Lnet/minecraft/world/item/enchantment/LevelBasedValue;", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Fraction;->denominator:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public LevelBasedValue numerator() {
            return this.numerator;
        }

        public LevelBasedValue denominator() {
            return this.denominator;
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public float calculate(int $$0) {
            float $$1 = this.denominator.calculate($$0);
            if ($$1 == 0.0f) {
                return 0.0f;
            }
            return this.numerator.calculate($$0) / $$1;
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public MapCodec<Fraction> codec() {
            return CODEC;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/LevelBasedValue$Exponent.class */
    public static final class Exponent extends Record implements LevelBasedValue {
        private final LevelBasedValue base;
        private final LevelBasedValue power;
        public static final MapCodec<Exponent> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(LevelBasedValue.CODEC.fieldOf("base").forGetter((v0) -> {
                return v0.base();
            }), LevelBasedValue.CODEC.fieldOf("power").forGetter((v0) -> {
                return v0.power();
            })).apply($$0, Exponent::new);
        });

        public Exponent(LevelBasedValue $$0, LevelBasedValue $$1) {
            this.base = $$0;
            this.power = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Exponent.class), Exponent.class, "base;power", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Exponent;->base:Lnet/minecraft/world/item/enchantment/LevelBasedValue;", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Exponent;->power:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Exponent.class), Exponent.class, "base;power", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Exponent;->base:Lnet/minecraft/world/item/enchantment/LevelBasedValue;", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Exponent;->power:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Exponent.class, Object.class), Exponent.class, "base;power", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Exponent;->base:Lnet/minecraft/world/item/enchantment/LevelBasedValue;", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$Exponent;->power:Lnet/minecraft/world/item/enchantment/LevelBasedValue;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public LevelBasedValue base() {
            return this.base;
        }

        public LevelBasedValue power() {
            return this.power;
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public float calculate(int $$0) {
            return (float) Math.pow(this.base.calculate($$0), this.power.calculate($$0));
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public MapCodec<Exponent> codec() {
            return CODEC;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/LevelBasedValue$LevelsSquared.class */
    public static final class LevelsSquared extends Record implements LevelBasedValue {
        private final float added;
        public static final MapCodec<LevelsSquared> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Codec.FLOAT.fieldOf("added").forGetter((v0) -> {
                return v0.added();
            })).apply($$0, (v1) -> {
                return new LevelsSquared(v1);
            });
        });

        public LevelsSquared(float $$0) {
            this.added = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LevelsSquared.class), LevelsSquared.class, "added", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$LevelsSquared;->added:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LevelsSquared.class), LevelsSquared.class, "added", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$LevelsSquared;->added:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LevelsSquared.class, Object.class), LevelsSquared.class, "added", "FIELD:Lnet/minecraft/world/item/enchantment/LevelBasedValue$LevelsSquared;->added:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public float added() {
            return this.added;
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public float calculate(int $$0) {
            return Mth.square($$0) + this.added;
        }

        @Override // net.minecraft.world.item.enchantment.LevelBasedValue
        public MapCodec<LevelsSquared> codec() {
            return CODEC;
        }
    }
}
