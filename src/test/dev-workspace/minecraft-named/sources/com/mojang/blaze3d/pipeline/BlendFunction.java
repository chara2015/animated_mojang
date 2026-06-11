package com.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/pipeline/BlendFunction.class */
@DontObfuscate
public final class BlendFunction extends Record {
    private final SourceFactor sourceColor;
    private final DestFactor destColor;
    private final SourceFactor sourceAlpha;
    private final DestFactor destAlpha;
    public static final BlendFunction LIGHTNING = new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE);
    public static final BlendFunction GLINT = new BlendFunction(SourceFactor.SRC_COLOR, DestFactor.ONE, SourceFactor.ZERO, DestFactor.ONE);
    public static final BlendFunction OVERLAY = new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
    public static final BlendFunction TRANSLUCENT = new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
    public static final BlendFunction TRANSLUCENT_PREMULTIPLIED_ALPHA = new BlendFunction(SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
    public static final BlendFunction ADDITIVE = new BlendFunction(SourceFactor.ONE, DestFactor.ONE);
    public static final BlendFunction ENTITY_OUTLINE_BLIT = new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ZERO, DestFactor.ONE);
    public static final BlendFunction INVERT = new BlendFunction(SourceFactor.ONE_MINUS_DST_COLOR, DestFactor.ONE_MINUS_SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO);

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BlendFunction.class), BlendFunction.class, "sourceColor;destColor;sourceAlpha;destAlpha", "FIELD:Lcom/mojang/blaze3d/pipeline/BlendFunction;->sourceColor:Lcom/mojang/blaze3d/platform/SourceFactor;", "FIELD:Lcom/mojang/blaze3d/pipeline/BlendFunction;->destColor:Lcom/mojang/blaze3d/platform/DestFactor;", "FIELD:Lcom/mojang/blaze3d/pipeline/BlendFunction;->sourceAlpha:Lcom/mojang/blaze3d/platform/SourceFactor;", "FIELD:Lcom/mojang/blaze3d/pipeline/BlendFunction;->destAlpha:Lcom/mojang/blaze3d/platform/DestFactor;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BlendFunction.class), BlendFunction.class, "sourceColor;destColor;sourceAlpha;destAlpha", "FIELD:Lcom/mojang/blaze3d/pipeline/BlendFunction;->sourceColor:Lcom/mojang/blaze3d/platform/SourceFactor;", "FIELD:Lcom/mojang/blaze3d/pipeline/BlendFunction;->destColor:Lcom/mojang/blaze3d/platform/DestFactor;", "FIELD:Lcom/mojang/blaze3d/pipeline/BlendFunction;->sourceAlpha:Lcom/mojang/blaze3d/platform/SourceFactor;", "FIELD:Lcom/mojang/blaze3d/pipeline/BlendFunction;->destAlpha:Lcom/mojang/blaze3d/platform/DestFactor;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BlendFunction.class, Object.class), BlendFunction.class, "sourceColor;destColor;sourceAlpha;destAlpha", "FIELD:Lcom/mojang/blaze3d/pipeline/BlendFunction;->sourceColor:Lcom/mojang/blaze3d/platform/SourceFactor;", "FIELD:Lcom/mojang/blaze3d/pipeline/BlendFunction;->destColor:Lcom/mojang/blaze3d/platform/DestFactor;", "FIELD:Lcom/mojang/blaze3d/pipeline/BlendFunction;->sourceAlpha:Lcom/mojang/blaze3d/platform/SourceFactor;", "FIELD:Lcom/mojang/blaze3d/pipeline/BlendFunction;->destAlpha:Lcom/mojang/blaze3d/platform/DestFactor;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public SourceFactor sourceColor() {
        return this.sourceColor;
    }

    public DestFactor destColor() {
        return this.destColor;
    }

    public SourceFactor sourceAlpha() {
        return this.sourceAlpha;
    }

    public DestFactor destAlpha() {
        return this.destAlpha;
    }

    public BlendFunction(SourceFactor $$0, DestFactor $$1, SourceFactor $$2, DestFactor $$3) {
        this.sourceColor = $$0;
        this.destColor = $$1;
        this.sourceAlpha = $$2;
        this.destAlpha = $$3;
    }

    public BlendFunction(SourceFactor $$0, DestFactor $$1) {
        this($$0, $$1, $$0, $$1);
    }
}
