package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.ResettableOptionWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.level.levelgen.Density;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance.class */
public final class OptionInstance<T> {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Enum<Boolean> BOOLEAN_VALUES = new Enum<>(ImmutableList.of(Boolean.TRUE, Boolean.FALSE), Codec.BOOL);
    public static final CaptionBasedToString<Boolean> BOOLEAN_TO_STRING = ($$0, $$1) -> {
        return $$1.booleanValue() ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF;
    };
    private final TooltipSupplier<T> tooltip;
    final Function<T, Component> toString;
    private final ValueSet<T> values;
    private final Codec<T> codec;
    private final T initialValue;
    private final Consumer<T> onValueUpdate;
    final Component caption;
    private T value;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$CaptionBasedToString.class */
    public interface CaptionBasedToString<T> {
        Component toString(Component component, T t);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$TooltipSupplier.class */
    @FunctionalInterface
    public interface TooltipSupplier<T> {
        Tooltip apply(T t);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$ValueSet.class */
    public interface ValueSet<T> {
        Function<OptionInstance<T>, AbstractWidget> createButton(TooltipSupplier<T> tooltipSupplier, Options options, int i, int i2, int i3, Consumer<T> consumer);

        Optional<T> validateValue(T t);

        Codec<T> codec();
    }

    public static OptionInstance<Boolean> createBoolean(String $$0, boolean $$1, Consumer<Boolean> $$2) {
        return createBoolean($$0, noTooltip(), $$1, $$2);
    }

    public static OptionInstance<Boolean> createBoolean(String $$0, boolean $$1) {
        return createBoolean($$0, noTooltip(), $$1, $$02 -> {
        });
    }

    public static OptionInstance<Boolean> createBoolean(String $$0, TooltipSupplier<Boolean> $$1, boolean $$2) {
        return createBoolean($$0, $$1, $$2, $$02 -> {
        });
    }

    public static OptionInstance<Boolean> createBoolean(String $$0, TooltipSupplier<Boolean> $$1, boolean $$2, Consumer<Boolean> $$3) {
        return createBoolean($$0, $$1, BOOLEAN_TO_STRING, $$2, $$3);
    }

    public static OptionInstance<Boolean> createBoolean(String $$0, TooltipSupplier<Boolean> $$1, CaptionBasedToString<Boolean> $$2, boolean $$3, Consumer<Boolean> $$4) {
        return new OptionInstance<>($$0, $$1, $$2, BOOLEAN_VALUES, Boolean.valueOf($$3), $$4);
    }

    public OptionInstance(String $$0, TooltipSupplier<T> $$1, CaptionBasedToString<T> $$2, ValueSet<T> $$3, T $$4, Consumer<T> $$5) {
        this($$0, $$1, $$2, $$3, $$3.codec(), $$4, $$5);
    }

    public OptionInstance(String $$0, TooltipSupplier<T> $$1, CaptionBasedToString<T> $$2, ValueSet<T> $$3, Codec<T> $$4, T $$5, Consumer<T> $$6) {
        this.caption = Component.translatable($$0);
        this.tooltip = $$1;
        this.toString = $$12 -> {
            return $$2.toString(this.caption, $$12);
        };
        this.values = $$3;
        this.codec = $$4;
        this.initialValue = $$5;
        this.onValueUpdate = $$6;
        this.value = this.initialValue;
    }

    public static <T> TooltipSupplier<T> noTooltip() {
        return $$0 -> {
            return null;
        };
    }

    public static <T> TooltipSupplier<T> cachedConstantTooltip(Component $$0) {
        return $$1 -> {
            return Tooltip.create($$0);
        };
    }

    public AbstractWidget createButton(Options $$0) {
        return createButton($$0, 0, 0, 150);
    }

    public AbstractWidget createButton(Options $$0, int $$1, int $$2, int $$3) {
        return createButton($$0, $$1, $$2, $$3, $$02 -> {
        });
    }

    public AbstractWidget createButton(Options $$0, int $$1, int $$2, int $$3, Consumer<T> $$4) {
        return this.values.createButton(this.tooltip, $$0, $$1, $$2, $$3, $$4).apply(this);
    }

    public T get() {
        return this.value;
    }

    public Codec<T> codec() {
        return this.codec;
    }

    public String toString() {
        return this.caption.getString();
    }

    public void set(T $$0) {
        T $$1 = this.values.validateValue($$0).orElseGet(() -> {
            LOGGER.error("Illegal option value {} for {}", $$0, this.caption.getString());
            return this.initialValue;
        });
        if (!Minecraft.getInstance().isRunning()) {
            this.value = $$1;
        } else if (!Objects.equals(this.value, $$1)) {
            this.value = $$1;
            this.onValueUpdate.accept(this.value);
        }
    }

    public ValueSet<T> values() {
        return this.values;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$SliderableValueSet.class */
    interface SliderableValueSet<T> extends ValueSet<T> {
        double toSliderValue(T t);

        T fromSliderValue(double d);

        default Optional<T> next(T $$0) {
            return Optional.empty();
        }

        default Optional<T> previous(T $$0) {
            return Optional.empty();
        }

        default boolean applyValueImmediately() {
            return true;
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        default Function<OptionInstance<T>, AbstractWidget> createButton(TooltipSupplier<T> $$0, Options $$1, int $$2, int $$3, int $$4, Consumer<T> $$5) {
            return $$6 -> {
                return new OptionInstanceSliderButton($$1, $$2, $$3, $$4, 20, $$6, this, $$0, $$5, applyValueImmediately());
            };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$CycleableValueSet.class */
    interface CycleableValueSet<T> extends ValueSet<T> {

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$CycleableValueSet$ValueSetter.class */
        public interface ValueSetter<T> {
            void set(OptionInstance<T> optionInstance, T t);
        }

        CycleButton.ValueListSupplier<T> valueListSupplier();

        default ValueSetter<T> valueSetter() {
            return (v0, v1) -> {
                v0.set(v1);
            };
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        default Function<OptionInstance<T>, AbstractWidget> createButton(TooltipSupplier<T> $$0, Options $$1, int $$2, int $$3, int $$4, Consumer<T> $$5) {
            return $$6 -> {
                Function<T, Component> function = $$6.toString;
                Objects.requireNonNull($$6);
                return CycleButton.builder((Function) function, $$6::get).withValues(valueListSupplier()).withTooltip($$0).create($$2, $$3, $$4, 20, $$6.caption, ($$32, obj) -> {
                    valueSetter().set($$6, obj);
                    $$1.save();
                    $$5.accept(obj);
                });
            };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$SliderableOrCyclableValueSet.class */
    interface SliderableOrCyclableValueSet<T> extends CycleableValueSet<T>, SliderableValueSet<T> {
        boolean createCycleButton();

        @Override // net.minecraft.client.OptionInstance.CycleableValueSet, net.minecraft.client.OptionInstance.ValueSet
        default Function<OptionInstance<T>, AbstractWidget> createButton(TooltipSupplier<T> $$0, Options $$1, int $$2, int $$3, int $$4, Consumer<T> $$5) {
            if (createCycleButton()) {
                return super.createButton($$0, $$1, $$2, $$3, $$4, $$5);
            }
            return super.createButton($$0, $$1, $$2, $$3, $$4, $$5);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$AltEnum.class */
    public static final class AltEnum<T> extends Record implements CycleableValueSet<T> {
        private final List<T> values;
        private final List<T> altValues;
        private final BooleanSupplier altCondition;
        private final CycleableValueSet.ValueSetter<T> valueSetter;
        private final Codec<T> codec;

        public AltEnum(List<T> $$0, List<T> $$1, BooleanSupplier $$2, CycleableValueSet.ValueSetter<T> $$3, Codec<T> $$4) {
            this.values = $$0;
            this.altValues = $$1;
            this.altCondition = $$2;
            this.valueSetter = $$3;
            this.codec = $$4;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AltEnum.class), AltEnum.class, "values;altValues;altCondition;valueSetter;codec", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->values:Ljava/util/List;", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->altValues:Ljava/util/List;", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->altCondition:Ljava/util/function/BooleanSupplier;", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->valueSetter:Lnet/minecraft/client/OptionInstance$CycleableValueSet$ValueSetter;", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AltEnum.class), AltEnum.class, "values;altValues;altCondition;valueSetter;codec", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->values:Ljava/util/List;", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->altValues:Ljava/util/List;", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->altCondition:Ljava/util/function/BooleanSupplier;", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->valueSetter:Lnet/minecraft/client/OptionInstance$CycleableValueSet$ValueSetter;", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AltEnum.class, Object.class), AltEnum.class, "values;altValues;altCondition;valueSetter;codec", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->values:Ljava/util/List;", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->altValues:Ljava/util/List;", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->altCondition:Ljava/util/function/BooleanSupplier;", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->valueSetter:Lnet/minecraft/client/OptionInstance$CycleableValueSet$ValueSetter;", "FIELD:Lnet/minecraft/client/OptionInstance$AltEnum;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public List<T> values() {
            return this.values;
        }

        public List<T> altValues() {
            return this.altValues;
        }

        public BooleanSupplier altCondition() {
            return this.altCondition;
        }

        @Override // net.minecraft.client.OptionInstance.CycleableValueSet
        public CycleableValueSet.ValueSetter<T> valueSetter() {
            return this.valueSetter;
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Codec<T> codec() {
            return this.codec;
        }

        @Override // net.minecraft.client.OptionInstance.CycleableValueSet
        public CycleButton.ValueListSupplier<T> valueListSupplier() {
            return CycleButton.ValueListSupplier.create(this.altCondition, this.values, this.altValues);
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Optional<T> validateValue(T $$0) {
            return (this.altCondition.getAsBoolean() ? this.altValues : this.values).contains($$0) ? Optional.of($$0) : Optional.empty();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$Enum.class */
    public static final class Enum<T> extends Record implements CycleableValueSet<T> {
        private final List<T> values;
        private final Codec<T> codec;

        public Enum(List<T> $$0, Codec<T> $$1) {
            this.values = $$0;
            this.codec = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Enum.class), Enum.class, "values;codec", "FIELD:Lnet/minecraft/client/OptionInstance$Enum;->values:Ljava/util/List;", "FIELD:Lnet/minecraft/client/OptionInstance$Enum;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Enum.class), Enum.class, "values;codec", "FIELD:Lnet/minecraft/client/OptionInstance$Enum;->values:Ljava/util/List;", "FIELD:Lnet/minecraft/client/OptionInstance$Enum;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Enum.class, Object.class), Enum.class, "values;codec", "FIELD:Lnet/minecraft/client/OptionInstance$Enum;->values:Ljava/util/List;", "FIELD:Lnet/minecraft/client/OptionInstance$Enum;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public List<T> values() {
            return this.values;
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Codec<T> codec() {
            return this.codec;
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Optional<T> validateValue(T $$0) {
            return this.values.contains($$0) ? Optional.of($$0) : Optional.empty();
        }

        @Override // net.minecraft.client.OptionInstance.CycleableValueSet
        public CycleButton.ValueListSupplier<T> valueListSupplier() {
            return CycleButton.ValueListSupplier.create(this.values);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$LazyEnum.class */
    public static final class LazyEnum<T> extends Record implements CycleableValueSet<T> {
        private final Supplier<List<T>> values;
        private final Function<T, Optional<T>> validateValue;
        private final Codec<T> codec;

        public LazyEnum(Supplier<List<T>> $$0, Function<T, Optional<T>> $$1, Codec<T> $$2) {
            this.values = $$0;
            this.validateValue = $$1;
            this.codec = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LazyEnum.class), LazyEnum.class, "values;validateValue;codec", "FIELD:Lnet/minecraft/client/OptionInstance$LazyEnum;->values:Ljava/util/function/Supplier;", "FIELD:Lnet/minecraft/client/OptionInstance$LazyEnum;->validateValue:Ljava/util/function/Function;", "FIELD:Lnet/minecraft/client/OptionInstance$LazyEnum;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LazyEnum.class), LazyEnum.class, "values;validateValue;codec", "FIELD:Lnet/minecraft/client/OptionInstance$LazyEnum;->values:Ljava/util/function/Supplier;", "FIELD:Lnet/minecraft/client/OptionInstance$LazyEnum;->validateValue:Ljava/util/function/Function;", "FIELD:Lnet/minecraft/client/OptionInstance$LazyEnum;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LazyEnum.class, Object.class), LazyEnum.class, "values;validateValue;codec", "FIELD:Lnet/minecraft/client/OptionInstance$LazyEnum;->values:Ljava/util/function/Supplier;", "FIELD:Lnet/minecraft/client/OptionInstance$LazyEnum;->validateValue:Ljava/util/function/Function;", "FIELD:Lnet/minecraft/client/OptionInstance$LazyEnum;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Supplier<List<T>> values() {
            return this.values;
        }

        public Function<T, Optional<T>> validateValue() {
            return this.validateValue;
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Codec<T> codec() {
            return this.codec;
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Optional<T> validateValue(T $$0) {
            return this.validateValue.apply($$0);
        }

        @Override // net.minecraft.client.OptionInstance.CycleableValueSet
        public CycleButton.ValueListSupplier<T> valueListSupplier() {
            return CycleButton.ValueListSupplier.create(this.values.get());
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$OptionInstanceSliderButton.class */
    public static final class OptionInstanceSliderButton<N> extends AbstractOptionSliderButton implements ResettableOptionWidget {
        private final OptionInstance<N> instance;
        private final SliderableValueSet<N> values;
        private final TooltipSupplier<N> tooltipSupplier;
        private final Consumer<N> onValueChanged;
        private Long delayedApplyAt;
        private final boolean applyValueImmediately;

        OptionInstanceSliderButton(Options $$0, int $$1, int $$2, int $$3, int $$4, OptionInstance<N> $$5, SliderableValueSet<N> $$6, TooltipSupplier<N> $$7, Consumer<N> $$8, boolean $$9) {
            super($$0, $$1, $$2, $$3, $$4, $$6.toSliderValue($$5.get()));
            this.instance = $$5;
            this.values = $$6;
            this.tooltipSupplier = $$7;
            this.onValueChanged = $$8;
            this.applyValueImmediately = $$9;
            updateMessage();
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // net.minecraft.client.gui.components.AbstractSliderButton
        protected void updateMessage() {
            setMessage(this.instance.toString.apply((T) this.values.fromSliderValue(this.value)));
            setTooltip(this.tooltipSupplier.apply(this.values.fromSliderValue(this.value)));
        }

        @Override // net.minecraft.client.gui.components.AbstractSliderButton
        protected void applyValue() {
            if (this.applyValueImmediately) {
                applyUnsavedValue();
            } else {
                this.delayedApplyAt = Long.valueOf(Util.getMillis() + 600);
            }
        }

        public void applyUnsavedValue() {
            N nFromSliderValue = this.values.fromSliderValue(this.value);
            if (!Objects.equals(nFromSliderValue, this.instance.get())) {
                this.instance.set(nFromSliderValue);
                this.onValueChanged.accept(this.instance.get());
            }
        }

        @Override // net.minecraft.client.gui.components.ResettableOptionWidget
        public void resetValue() {
            if (this.value != this.values.toSliderValue(this.instance.get())) {
                this.value = this.values.toSliderValue(this.instance.get());
                this.delayedApplyAt = null;
                updateMessage();
            }
        }

        @Override // net.minecraft.client.gui.components.AbstractSliderButton, net.minecraft.client.gui.components.AbstractWidget
        public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
            super.renderWidget($$0, $$1, $$2, $$3);
            if (this.delayedApplyAt != null && Util.getMillis() >= this.delayedApplyAt.longValue()) {
                this.delayedApplyAt = null;
                applyUnsavedValue();
                resetValue();
            }
        }

        @Override // net.minecraft.client.gui.components.AbstractSliderButton, net.minecraft.client.gui.components.AbstractWidget
        public void onRelease(MouseButtonEvent $$0) {
            super.onRelease($$0);
            if (this.applyValueImmediately) {
                resetValue();
            }
        }

        @Override // net.minecraft.client.gui.components.AbstractSliderButton, net.minecraft.client.gui.components.events.GuiEventListener
        public boolean keyPressed(KeyEvent keyEvent) {
            if (keyEvent.isSelection()) {
                this.canChangeValue = !this.canChangeValue;
                return true;
            }
            if (this.canChangeValue) {
                boolean zIsLeft = keyEvent.isLeft();
                boolean zIsRight = keyEvent.isRight();
                if (zIsLeft) {
                    Optional<T> optionalPrevious = this.values.previous(this.values.fromSliderValue(this.value));
                    if (optionalPrevious.isPresent()) {
                        setValue(this.values.toSliderValue(optionalPrevious.get()));
                        return true;
                    }
                }
                if (zIsRight) {
                    Optional<T> next = this.values.next(this.values.fromSliderValue(this.value));
                    if (next.isPresent()) {
                        setValue(this.values.toSliderValue(next.get()));
                        return true;
                    }
                }
                if (zIsLeft || zIsRight) {
                    setValue(this.value + ((double) ((zIsLeft ? -1.0f : 1.0f) / (this.width - 8))));
                    return true;
                }
                return false;
            }
            return false;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$IntRangeBase.class */
    interface IntRangeBase extends SliderableValueSet<Integer> {
        int minInclusive();

        int maxInclusive();

        @Override // net.minecraft.client.OptionInstance.SliderableValueSet
        default Optional<Integer> next(Integer $$0) {
            return Optional.of(Integer.valueOf($$0.intValue() + 1));
        }

        @Override // net.minecraft.client.OptionInstance.SliderableValueSet
        default Optional<Integer> previous(Integer $$0) {
            return Optional.of(Integer.valueOf($$0.intValue() - 1));
        }

        @Override // net.minecraft.client.OptionInstance.SliderableValueSet
        default double toSliderValue(Integer $$0) {
            if ($$0.intValue() == minInclusive()) {
                return Density.SURFACE;
            }
            if ($$0.intValue() == maxInclusive()) {
                return 1.0d;
            }
            return Mth.map(((double) $$0.intValue()) + 0.5d, minInclusive(), ((double) maxInclusive()) + 1.0d, Density.SURFACE, 1.0d);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.minecraft.client.OptionInstance.SliderableValueSet
        default Integer fromSliderValue(double $$0) {
            if ($$0 >= 1.0d) {
                $$0 = 0.9999899864196777d;
            }
            return Integer.valueOf(Mth.floor(Mth.map($$0, Density.SURFACE, 1.0d, minInclusive(), ((double) maxInclusive()) + 1.0d)));
        }

        default <R> SliderableValueSet<R> xmap(final IntFunction<? extends R> $$0, final ToIntFunction<? super R> $$1, final boolean $$2) {
            return new SliderableValueSet<R>() { // from class: net.minecraft.client.OptionInstance.IntRangeBase.1
                @Override // net.minecraft.client.OptionInstance.ValueSet
                public Optional<R> validateValue(R $$02) {
                    Optional optionalValidateValue = IntRangeBase.this.validateValue(Integer.valueOf($$1.applyAsInt($$02)));
                    IntFunction intFunction = $$0;
                    Objects.requireNonNull(intFunction);
                    return optionalValidateValue.map((v1) -> {
                        return r1.apply(v1);
                    });
                }

                @Override // net.minecraft.client.OptionInstance.SliderableValueSet
                public double toSliderValue(R $$02) {
                    return IntRangeBase.this.toSliderValue(Integer.valueOf($$1.applyAsInt($$02)));
                }

                @Override // net.minecraft.client.OptionInstance.SliderableValueSet
                public Optional<R> next(R $$02) {
                    if (!$$2) {
                        return Optional.empty();
                    }
                    int $$12 = $$1.applyAsInt($$02);
                    return Optional.of($$0.apply(IntRangeBase.this.validateValue(Integer.valueOf($$12 + 1)).orElse(Integer.valueOf($$12)).intValue()));
                }

                @Override // net.minecraft.client.OptionInstance.SliderableValueSet
                public Optional<R> previous(R $$02) {
                    if (!$$2) {
                        return Optional.empty();
                    }
                    int $$12 = $$1.applyAsInt($$02);
                    return Optional.of($$0.apply(IntRangeBase.this.validateValue(Integer.valueOf($$12 - 1)).orElse(Integer.valueOf($$12)).intValue()));
                }

                @Override // net.minecraft.client.OptionInstance.SliderableValueSet
                public R fromSliderValue(double d) {
                    return (R) $$0.apply(IntRangeBase.this.fromSliderValue(d).intValue());
                }

                @Override // net.minecraft.client.OptionInstance.ValueSet
                public Codec<R> codec() {
                    Codec<Integer> codec = IntRangeBase.this.codec();
                    IntFunction intFunction = $$0;
                    Objects.requireNonNull(intFunction);
                    Function function = (v1) -> {
                        return r1.apply(v1);
                    };
                    ToIntFunction toIntFunction = $$1;
                    Objects.requireNonNull(toIntFunction);
                    return codec.xmap(function, toIntFunction::applyAsInt);
                }
            };
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$IntRange.class */
    public static final class IntRange extends Record implements IntRangeBase {
        private final int minInclusive;
        private final int maxInclusive;
        private final boolean applyValueImmediately;

        public IntRange(int $$0, int $$1, boolean $$2) {
            this.minInclusive = $$0;
            this.maxInclusive = $$1;
            this.applyValueImmediately = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, IntRange.class), IntRange.class, "minInclusive;maxInclusive;applyValueImmediately", "FIELD:Lnet/minecraft/client/OptionInstance$IntRange;->minInclusive:I", "FIELD:Lnet/minecraft/client/OptionInstance$IntRange;->maxInclusive:I", "FIELD:Lnet/minecraft/client/OptionInstance$IntRange;->applyValueImmediately:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, IntRange.class), IntRange.class, "minInclusive;maxInclusive;applyValueImmediately", "FIELD:Lnet/minecraft/client/OptionInstance$IntRange;->minInclusive:I", "FIELD:Lnet/minecraft/client/OptionInstance$IntRange;->maxInclusive:I", "FIELD:Lnet/minecraft/client/OptionInstance$IntRange;->applyValueImmediately:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, IntRange.class, Object.class), IntRange.class, "minInclusive;maxInclusive;applyValueImmediately", "FIELD:Lnet/minecraft/client/OptionInstance$IntRange;->minInclusive:I", "FIELD:Lnet/minecraft/client/OptionInstance$IntRange;->maxInclusive:I", "FIELD:Lnet/minecraft/client/OptionInstance$IntRange;->applyValueImmediately:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.client.OptionInstance.IntRangeBase
        public int minInclusive() {
            return this.minInclusive;
        }

        @Override // net.minecraft.client.OptionInstance.IntRangeBase
        public int maxInclusive() {
            return this.maxInclusive;
        }

        @Override // net.minecraft.client.OptionInstance.SliderableValueSet
        public boolean applyValueImmediately() {
            return this.applyValueImmediately;
        }

        public IntRange(int $$0, int $$1) {
            this($$0, $$1, true);
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Optional<Integer> validateValue(Integer $$0) {
            return ($$0.compareTo(Integer.valueOf(minInclusive())) < 0 || $$0.compareTo(Integer.valueOf(maxInclusive())) > 0) ? Optional.empty() : Optional.of($$0);
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Codec<Integer> codec() {
            return Codec.intRange(this.minInclusive, this.maxInclusive + 1);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$ClampingLazyMaxIntRange.class */
    public static final class ClampingLazyMaxIntRange extends Record implements IntRangeBase, SliderableOrCyclableValueSet<Integer> {
        private final int minInclusive;
        private final IntSupplier maxSupplier;
        private final int encodableMaxInclusive;

        public ClampingLazyMaxIntRange(int $$0, IntSupplier $$1, int $$2) {
            this.minInclusive = $$0;
            this.maxSupplier = $$1;
            this.encodableMaxInclusive = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClampingLazyMaxIntRange.class), ClampingLazyMaxIntRange.class, "minInclusive;maxSupplier;encodableMaxInclusive", "FIELD:Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange;->minInclusive:I", "FIELD:Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange;->maxSupplier:Ljava/util/function/IntSupplier;", "FIELD:Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange;->encodableMaxInclusive:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClampingLazyMaxIntRange.class), ClampingLazyMaxIntRange.class, "minInclusive;maxSupplier;encodableMaxInclusive", "FIELD:Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange;->minInclusive:I", "FIELD:Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange;->maxSupplier:Ljava/util/function/IntSupplier;", "FIELD:Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange;->encodableMaxInclusive:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClampingLazyMaxIntRange.class, Object.class), ClampingLazyMaxIntRange.class, "minInclusive;maxSupplier;encodableMaxInclusive", "FIELD:Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange;->minInclusive:I", "FIELD:Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange;->maxSupplier:Ljava/util/function/IntSupplier;", "FIELD:Lnet/minecraft/client/OptionInstance$ClampingLazyMaxIntRange;->encodableMaxInclusive:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.client.OptionInstance.IntRangeBase
        public int minInclusive() {
            return this.minInclusive;
        }

        public IntSupplier maxSupplier() {
            return this.maxSupplier;
        }

        public int encodableMaxInclusive() {
            return this.encodableMaxInclusive;
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Optional<Integer> validateValue(Integer $$0) {
            return Optional.of(Integer.valueOf(Mth.clamp($$0.intValue(), minInclusive(), maxInclusive())));
        }

        @Override // net.minecraft.client.OptionInstance.IntRangeBase
        public int maxInclusive() {
            return this.maxSupplier.getAsInt();
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Codec<Integer> codec() {
            return Codec.INT.validate($$0 -> {
                int $$1 = this.encodableMaxInclusive + 1;
                if ($$0.compareTo(Integer.valueOf(this.minInclusive)) >= 0 && $$0.compareTo(Integer.valueOf($$1)) <= 0) {
                    return DataResult.success($$0);
                }
                return DataResult.error(() -> {
                    return "Value " + $$0 + " outside of range [" + this.minInclusive + ":" + $$1 + "]";
                }, $$0);
            });
        }

        @Override // net.minecraft.client.OptionInstance.SliderableOrCyclableValueSet
        public boolean createCycleButton() {
            return true;
        }

        @Override // net.minecraft.client.OptionInstance.CycleableValueSet
        public CycleButton.ValueListSupplier<Integer> valueListSupplier() {
            return CycleButton.ValueListSupplier.create(IntStream.range(this.minInclusive, maxInclusive() + 1).boxed().toList());
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$SliderableEnum.class */
    public static final class SliderableEnum<T> extends Record implements SliderableValueSet<T> {
        private final List<T> values;
        private final Codec<T> codec;

        public SliderableEnum(List<T> $$0, Codec<T> $$1) {
            this.values = $$0;
            this.codec = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SliderableEnum.class), SliderableEnum.class, "values;codec", "FIELD:Lnet/minecraft/client/OptionInstance$SliderableEnum;->values:Ljava/util/List;", "FIELD:Lnet/minecraft/client/OptionInstance$SliderableEnum;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SliderableEnum.class), SliderableEnum.class, "values;codec", "FIELD:Lnet/minecraft/client/OptionInstance$SliderableEnum;->values:Ljava/util/List;", "FIELD:Lnet/minecraft/client/OptionInstance$SliderableEnum;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SliderableEnum.class, Object.class), SliderableEnum.class, "values;codec", "FIELD:Lnet/minecraft/client/OptionInstance$SliderableEnum;->values:Ljava/util/List;", "FIELD:Lnet/minecraft/client/OptionInstance$SliderableEnum;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public List<T> values() {
            return this.values;
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Codec<T> codec() {
            return this.codec;
        }

        @Override // net.minecraft.client.OptionInstance.SliderableValueSet
        public double toSliderValue(T $$0) {
            if ($$0 == this.values.getFirst()) {
                return Density.SURFACE;
            }
            if ($$0 == this.values.getLast()) {
                return 1.0d;
            }
            return Mth.map(this.values.indexOf($$0), Density.SURFACE, this.values.size() - 1, Density.SURFACE, 1.0d);
        }

        @Override // net.minecraft.client.OptionInstance.SliderableValueSet
        public Optional<T> next(T $$0) {
            int $$1 = this.values.indexOf($$0);
            int $$2 = Mth.clamp($$1 + 1, 0, this.values.size() - 1);
            return Optional.of(this.values.get($$2));
        }

        @Override // net.minecraft.client.OptionInstance.SliderableValueSet
        public Optional<T> previous(T $$0) {
            int $$1 = this.values.indexOf($$0);
            int $$2 = Mth.clamp($$1 - 1, 0, this.values.size() - 1);
            return Optional.of(this.values.get($$2));
        }

        @Override // net.minecraft.client.OptionInstance.SliderableValueSet
        public T fromSliderValue(double $$0) {
            if ($$0 >= 1.0d) {
                $$0 = 0.9999899864196777d;
            }
            int $$1 = Mth.floor(Mth.map($$0, Density.SURFACE, 1.0d, Density.SURFACE, this.values.size()));
            return this.values.get(Mth.clamp($$1, 0, this.values.size() - 1));
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Optional<T> validateValue(T $$0) {
            int $$1 = this.values.indexOf($$0);
            return $$1 > -1 ? Optional.of($$0) : Optional.empty();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/OptionInstance$UnitDouble.class */
    public enum UnitDouble implements SliderableValueSet<Double> {
        INSTANCE;

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Optional<Double> validateValue(Double $$0) {
            return ($$0.doubleValue() < Density.SURFACE || $$0.doubleValue() > 1.0d) ? Optional.empty() : Optional.of($$0);
        }

        @Override // net.minecraft.client.OptionInstance.SliderableValueSet
        public double toSliderValue(Double $$0) {
            return $$0.doubleValue();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.minecraft.client.OptionInstance.SliderableValueSet
        public Double fromSliderValue(double $$0) {
            return Double.valueOf($$0);
        }

        public <R> SliderableValueSet<R> xmap(final DoubleFunction<? extends R> $$0, final ToDoubleFunction<? super R> $$1) {
            return new SliderableValueSet<R>() { // from class: net.minecraft.client.OptionInstance.UnitDouble.1
                @Override // net.minecraft.client.OptionInstance.ValueSet
                public Optional<R> validateValue(R r) {
                    Optional<Double> optionalValidateValue = UnitDouble.this.validateValue(Double.valueOf($$1.applyAsDouble(r)));
                    DoubleFunction doubleFunction = $$0;
                    Objects.requireNonNull(doubleFunction);
                    return (Optional<R>) optionalValidateValue.map((v1) -> {
                        return r1.apply(v1);
                    });
                }

                @Override // net.minecraft.client.OptionInstance.SliderableValueSet
                public double toSliderValue(R $$02) {
                    return UnitDouble.this.toSliderValue(Double.valueOf($$1.applyAsDouble($$02)));
                }

                @Override // net.minecraft.client.OptionInstance.SliderableValueSet
                public R fromSliderValue(double d) {
                    return (R) $$0.apply(UnitDouble.this.fromSliderValue(d).doubleValue());
                }

                @Override // net.minecraft.client.OptionInstance.ValueSet
                public Codec<R> codec() {
                    Codec<Double> codec = UnitDouble.this.codec();
                    DoubleFunction doubleFunction = $$0;
                    Objects.requireNonNull(doubleFunction);
                    Function function = (v1) -> {
                        return r1.apply(v1);
                    };
                    ToDoubleFunction toDoubleFunction = $$1;
                    Objects.requireNonNull(toDoubleFunction);
                    return codec.xmap(function, toDoubleFunction::applyAsDouble);
                }
            };
        }

        @Override // net.minecraft.client.OptionInstance.ValueSet
        public Codec<Double> codec() {
            return Codec.withAlternative(Codec.doubleRange(Density.SURFACE, 1.0d), Codec.BOOL, $$0 -> {
                return Double.valueOf($$0.booleanValue() ? 1.0d : Density.SURFACE);
            });
        }
    }
}
