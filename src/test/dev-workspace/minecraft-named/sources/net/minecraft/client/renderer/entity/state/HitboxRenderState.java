package net.minecraft.client.renderer.entity.state;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/HitboxRenderState.class */
public final class HitboxRenderState extends Record {
    private final double x0;
    private final double y0;
    private final double z0;
    private final double x1;
    private final double y1;
    private final double z1;
    private final float offsetX;
    private final float offsetY;
    private final float offsetZ;
    private final float red;
    private final float green;
    private final float blue;

    public HitboxRenderState(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5, float $$6, float $$7, float $$8, float $$9, float $$10, float $$11) {
        this.x0 = $$0;
        this.y0 = $$1;
        this.z0 = $$2;
        this.x1 = $$3;
        this.y1 = $$4;
        this.z1 = $$5;
        this.offsetX = $$6;
        this.offsetY = $$7;
        this.offsetZ = $$8;
        this.red = $$9;
        this.green = $$10;
        this.blue = $$11;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, HitboxRenderState.class), HitboxRenderState.class, "x0;y0;z0;x1;y1;z1;offsetX;offsetY;offsetZ;red;green;blue", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->x0:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->y0:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->z0:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->x1:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->y1:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->z1:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->offsetX:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->offsetY:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->offsetZ:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->red:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->green:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->blue:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, HitboxRenderState.class), HitboxRenderState.class, "x0;y0;z0;x1;y1;z1;offsetX;offsetY;offsetZ;red;green;blue", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->x0:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->y0:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->z0:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->x1:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->y1:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->z1:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->offsetX:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->offsetY:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->offsetZ:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->red:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->green:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->blue:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, HitboxRenderState.class, Object.class), HitboxRenderState.class, "x0;y0;z0;x1;y1;z1;offsetX;offsetY;offsetZ;red;green;blue", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->x0:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->y0:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->z0:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->x1:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->y1:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->z1:D", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->offsetX:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->offsetY:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->offsetZ:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->red:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->green:F", "FIELD:Lnet/minecraft/client/renderer/entity/state/HitboxRenderState;->blue:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public double x0() {
        return this.x0;
    }

    public double y0() {
        return this.y0;
    }

    public double z0() {
        return this.z0;
    }

    public double x1() {
        return this.x1;
    }

    public double y1() {
        return this.y1;
    }

    public double z1() {
        return this.z1;
    }

    public float offsetX() {
        return this.offsetX;
    }

    public float offsetY() {
        return this.offsetY;
    }

    public float offsetZ() {
        return this.offsetZ;
    }

    public float red() {
        return this.red;
    }

    public float green() {
        return this.green;
    }

    public float blue() {
        return this.blue;
    }

    public HitboxRenderState(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5, float $$6, float $$7, float $$8) {
        this($$0, $$1, $$2, $$3, $$4, $$5, 0.0f, 0.0f, 0.0f, $$6, $$7, $$8);
    }
}
