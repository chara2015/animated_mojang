package net.minecraft.server.dialog.input;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.server.dialog.Dialog;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Display;
import net.minecraft.world.level.levelgen.Density;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/input/NumberRangeInput.class */
public final class NumberRangeInput extends Record implements InputControl {
    private final int width;
    private final Component label;
    private final String labelFormat;
    private final RangeInfo rangeInfo;
    public static final MapCodec<NumberRangeInput> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Dialog.WIDTH_CODEC.optionalFieldOf(Display.TAG_WIDTH, 200).forGetter((v0) -> {
            return v0.width();
        }), ComponentSerialization.CODEC.fieldOf("label").forGetter((v0) -> {
            return v0.label();
        }), Codec.STRING.optionalFieldOf("label_format", "options.generic_value").forGetter((v0) -> {
            return v0.labelFormat();
        }), RangeInfo.MAP_CODEC.forGetter((v0) -> {
            return v0.rangeInfo();
        })).apply($$0, (v1, v2, v3, v4) -> {
            return new NumberRangeInput(v1, v2, v3, v4);
        });
    });

    public NumberRangeInput(int $$0, Component $$1, String $$2, RangeInfo $$3) {
        this.width = $$0;
        this.label = $$1;
        this.labelFormat = $$2;
        this.rangeInfo = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, NumberRangeInput.class), NumberRangeInput.class, "width;label;labelFormat;rangeInfo", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput;->width:I", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput;->label:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput;->labelFormat:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput;->rangeInfo:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, NumberRangeInput.class), NumberRangeInput.class, "width;label;labelFormat;rangeInfo", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput;->width:I", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput;->label:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput;->labelFormat:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput;->rangeInfo:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, NumberRangeInput.class, Object.class), NumberRangeInput.class, "width;label;labelFormat;rangeInfo", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput;->width:I", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput;->label:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput;->labelFormat:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput;->rangeInfo:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int width() {
        return this.width;
    }

    public Component label() {
        return this.label;
    }

    public String labelFormat() {
        return this.labelFormat;
    }

    public RangeInfo rangeInfo() {
        return this.rangeInfo;
    }

    @Override // net.minecraft.server.dialog.input.InputControl
    public MapCodec<NumberRangeInput> mapCodec() {
        return MAP_CODEC;
    }

    public Component computeLabel(String $$0) {
        return Component.translatable(this.labelFormat, this.label, $$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/input/NumberRangeInput$RangeInfo.class */
    public static final class RangeInfo extends Record {
        private final float start;
        private final float end;
        private final Optional<Float> initial;
        private final Optional<Float> step;
        public static final MapCodec<RangeInfo> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Codec.FLOAT.fieldOf("start").forGetter((v0) -> {
                return v0.start();
            }), Codec.FLOAT.fieldOf("end").forGetter((v0) -> {
                return v0.end();
            }), Codec.FLOAT.optionalFieldOf("initial").forGetter((v0) -> {
                return v0.initial();
            }), ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("step").forGetter((v0) -> {
                return v0.step();
            })).apply($$0, (v1, v2, v3, v4) -> {
                return new RangeInfo(v1, v2, v3, v4);
            });
        }).validate($$02 -> {
            if ($$02.initial.isPresent()) {
                double $$1 = $$02.initial.get().floatValue();
                double $$2 = Math.min($$02.start, $$02.end);
                double $$3 = Math.max($$02.start, $$02.end);
                if ($$1 < $$2 || $$1 > $$3) {
                    return DataResult.error(() -> {
                        return "Initial value " + $$1 + " is outside of range [" + $$1 + ", " + $$2 + "]";
                    });
                }
            }
            return DataResult.success($$02);
        });

        public RangeInfo(float $$0, float $$1, Optional<Float> $$2, Optional<Float> $$3) {
            this.start = $$0;
            this.end = $$1;
            this.initial = $$2;
            this.step = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RangeInfo.class), RangeInfo.class, "start;end;initial;step", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;->start:F", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;->end:F", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;->initial:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;->step:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RangeInfo.class), RangeInfo.class, "start;end;initial;step", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;->start:F", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;->end:F", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;->initial:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;->step:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RangeInfo.class, Object.class), RangeInfo.class, "start;end;initial;step", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;->start:F", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;->end:F", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;->initial:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/input/NumberRangeInput$RangeInfo;->step:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public float start() {
            return this.start;
        }

        public float end() {
            return this.end;
        }

        public Optional<Float> initial() {
            return this.initial;
        }

        public Optional<Float> step() {
            return this.step;
        }

        public float computeScaledValue(float $$0) {
            float $$1 = Mth.lerp($$0, this.start, this.end);
            if (this.step.isEmpty()) {
                return $$1;
            }
            float $$2 = this.step.get().floatValue();
            float $$3 = initialScaledValue();
            float $$4 = $$1 - $$3;
            int $$5 = Math.round($$4 / $$2);
            float $$6 = $$3 + ($$5 * $$2);
            if (!isOutOfRange($$6)) {
                return $$6;
            }
            int $$7 = $$5 - Mth.sign($$5);
            return $$3 + ($$7 * $$2);
        }

        private boolean isOutOfRange(float $$0) {
            float $$1 = scaledValueToSlider($$0);
            return ((double) $$1) < Density.SURFACE || ((double) $$1) > 1.0d;
        }

        private float initialScaledValue() {
            if (this.initial.isPresent()) {
                return this.initial.get().floatValue();
            }
            return (this.start + this.end) / 2.0f;
        }

        public float initialSliderValue() {
            float $$0 = initialScaledValue();
            return scaledValueToSlider($$0);
        }

        private float scaledValueToSlider(float $$0) {
            if (this.start == this.end) {
                return 0.5f;
            }
            return Mth.inverseLerp($$0, this.start, this.end);
        }
    }
}
