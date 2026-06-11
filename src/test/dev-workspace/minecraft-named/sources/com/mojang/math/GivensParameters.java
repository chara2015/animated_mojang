package com.mojang.math;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import org.joml.Math;
import org.joml.Matrix3f;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/math/GivensParameters.class */
public final class GivensParameters extends Record {
    private final float sinHalf;
    private final float cosHalf;

    public GivensParameters(float $$0, float $$1) {
        this.sinHalf = $$0;
        this.cosHalf = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GivensParameters.class), GivensParameters.class, "sinHalf;cosHalf", "FIELD:Lcom/mojang/math/GivensParameters;->sinHalf:F", "FIELD:Lcom/mojang/math/GivensParameters;->cosHalf:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GivensParameters.class), GivensParameters.class, "sinHalf;cosHalf", "FIELD:Lcom/mojang/math/GivensParameters;->sinHalf:F", "FIELD:Lcom/mojang/math/GivensParameters;->cosHalf:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GivensParameters.class, Object.class), GivensParameters.class, "sinHalf;cosHalf", "FIELD:Lcom/mojang/math/GivensParameters;->sinHalf:F", "FIELD:Lcom/mojang/math/GivensParameters;->cosHalf:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public float sinHalf() {
        return this.sinHalf;
    }

    public float cosHalf() {
        return this.cosHalf;
    }

    public static GivensParameters fromUnnormalized(float $$0, float $$1) {
        float $$2 = Math.invsqrt(($$0 * $$0) + ($$1 * $$1));
        return new GivensParameters($$2 * $$0, $$2 * $$1);
    }

    public static GivensParameters fromPositiveAngle(float $$0) {
        float $$1 = Math.sin($$0 / 2.0f);
        float $$2 = Math.cosFromSin($$1, $$0 / 2.0f);
        return new GivensParameters($$1, $$2);
    }

    public GivensParameters inverse() {
        return new GivensParameters(-this.sinHalf, this.cosHalf);
    }

    public Quaternionf aroundX(Quaternionf $$0) {
        return $$0.set(this.sinHalf, 0.0f, 0.0f, this.cosHalf);
    }

    public Quaternionf aroundY(Quaternionf $$0) {
        return $$0.set(0.0f, this.sinHalf, 0.0f, this.cosHalf);
    }

    public Quaternionf aroundZ(Quaternionf $$0) {
        return $$0.set(0.0f, 0.0f, this.sinHalf, this.cosHalf);
    }

    public float cos() {
        return (this.cosHalf * this.cosHalf) - (this.sinHalf * this.sinHalf);
    }

    public float sin() {
        return 2.0f * this.sinHalf * this.cosHalf;
    }

    public Matrix3f aroundX(Matrix3f $$0) {
        $$0.m01 = 0.0f;
        $$0.m02 = 0.0f;
        $$0.m10 = 0.0f;
        $$0.m20 = 0.0f;
        float $$1 = cos();
        float $$2 = sin();
        $$0.m11 = $$1;
        $$0.m22 = $$1;
        $$0.m12 = $$2;
        $$0.m21 = -$$2;
        $$0.m00 = 1.0f;
        return $$0;
    }

    public Matrix3f aroundY(Matrix3f $$0) {
        $$0.m01 = 0.0f;
        $$0.m10 = 0.0f;
        $$0.m12 = 0.0f;
        $$0.m21 = 0.0f;
        float $$1 = cos();
        float $$2 = sin();
        $$0.m00 = $$1;
        $$0.m22 = $$1;
        $$0.m02 = -$$2;
        $$0.m20 = $$2;
        $$0.m11 = 1.0f;
        return $$0;
    }

    public Matrix3f aroundZ(Matrix3f $$0) {
        $$0.m02 = 0.0f;
        $$0.m12 = 0.0f;
        $$0.m20 = 0.0f;
        $$0.m21 = 0.0f;
        float $$1 = cos();
        float $$2 = sin();
        $$0.m00 = $$1;
        $$0.m11 = $$1;
        $$0.m01 = $$2;
        $$0.m10 = -$$2;
        $$0.m22 = 1.0f;
        return $$0;
    }
}
