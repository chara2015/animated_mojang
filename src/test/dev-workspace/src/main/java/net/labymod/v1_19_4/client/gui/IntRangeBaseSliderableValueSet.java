package net.labymod.v1_19_4.client.gui;

import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/gui/IntRangeBaseSliderableValueSet.class */
public final class IntRangeBaseSliderableValueSet<R> extends Record implements k<R> {
    private final g base;
    private final IntFunction<? extends R> intFunction;
    private final ToIntFunction<? super R> mapperFunction;

    public IntRangeBaseSliderableValueSet(g base, IntFunction<? extends R> intFunction, ToIntFunction<? super R> mapperFunction) {
        this.base = base;
        this.intFunction = intFunction;
        this.mapperFunction = mapperFunction;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, IntRangeBaseSliderableValueSet.class), IntRangeBaseSliderableValueSet.class, "base;intFunction;mapperFunction", "FIELD:Lnet/labymod/v1_19_4/client/gui/IntRangeBaseSliderableValueSet;->base:Lemk$g;", "FIELD:Lnet/labymod/v1_19_4/client/gui/IntRangeBaseSliderableValueSet;->intFunction:Ljava/util/function/IntFunction;", "FIELD:Lnet/labymod/v1_19_4/client/gui/IntRangeBaseSliderableValueSet;->mapperFunction:Ljava/util/function/ToIntFunction;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, IntRangeBaseSliderableValueSet.class), IntRangeBaseSliderableValueSet.class, "base;intFunction;mapperFunction", "FIELD:Lnet/labymod/v1_19_4/client/gui/IntRangeBaseSliderableValueSet;->base:Lemk$g;", "FIELD:Lnet/labymod/v1_19_4/client/gui/IntRangeBaseSliderableValueSet;->intFunction:Ljava/util/function/IntFunction;", "FIELD:Lnet/labymod/v1_19_4/client/gui/IntRangeBaseSliderableValueSet;->mapperFunction:Ljava/util/function/ToIntFunction;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, IntRangeBaseSliderableValueSet.class, Object.class), IntRangeBaseSliderableValueSet.class, "base;intFunction;mapperFunction", "FIELD:Lnet/labymod/v1_19_4/client/gui/IntRangeBaseSliderableValueSet;->base:Lemk$g;", "FIELD:Lnet/labymod/v1_19_4/client/gui/IntRangeBaseSliderableValueSet;->intFunction:Ljava/util/function/IntFunction;", "FIELD:Lnet/labymod/v1_19_4/client/gui/IntRangeBaseSliderableValueSet;->mapperFunction:Ljava/util/function/ToIntFunction;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public g base() {
        return this.base;
    }

    public IntFunction<? extends R> intFunction() {
        return this.intFunction;
    }

    public ToIntFunction<? super R> mapperFunction() {
        return this.mapperFunction;
    }

    public double b(R value) {
        return this.base.b(Integer.valueOf(this.mapperFunction.applyAsInt(value)));
    }

    public R b(double value) {
        return this.intFunction.apply(this.base.a(value).intValue());
    }

    public Optional<R> a(R value) {
        Optional optionalA = this.base.a(Integer.valueOf(this.mapperFunction.applyAsInt(value)));
        IntFunction<? extends R> intFunction = this.intFunction;
        Objects.requireNonNull(intFunction);
        return optionalA.map((v1) -> {
            return r1.apply(v1);
        });
    }

    public Codec<R> f() {
        Codec codecF = this.base.f();
        IntFunction<? extends R> intFunction = this.intFunction;
        Objects.requireNonNull(intFunction);
        Function function = (v1) -> {
            return r1.apply(v1);
        };
        ToIntFunction<? super R> toIntFunction = this.mapperFunction;
        Objects.requireNonNull(toIntFunction);
        return codecF.xmap(function, toIntFunction::applyAsInt);
    }
}
