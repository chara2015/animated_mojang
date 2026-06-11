package net.minecraft.client.model.geom;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/geom/PartPose.class */
public final class PartPose extends Record {
    private final float x;
    private final float y;
    private final float z;
    private final float xRot;
    private final float yRot;
    private final float zRot;
    private final float xScale;
    private final float yScale;
    private final float zScale;
    public static final PartPose ZERO = offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

    public PartPose(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8) {
        this.x = $$0;
        this.y = $$1;
        this.z = $$2;
        this.xRot = $$3;
        this.yRot = $$4;
        this.zRot = $$5;
        this.xScale = $$6;
        this.yScale = $$7;
        this.zScale = $$8;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PartPose.class), PartPose.class, "x;y;z;xRot;yRot;zRot;xScale;yScale;zScale", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->x:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->y:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->z:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->xRot:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->yRot:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->zRot:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->xScale:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->yScale:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->zScale:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PartPose.class), PartPose.class, "x;y;z;xRot;yRot;zRot;xScale;yScale;zScale", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->x:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->y:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->z:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->xRot:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->yRot:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->zRot:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->xScale:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->yScale:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->zScale:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PartPose.class, Object.class), PartPose.class, "x;y;z;xRot;yRot;zRot;xScale;yScale;zScale", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->x:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->y:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->z:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->xRot:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->yRot:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->zRot:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->xScale:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->yScale:F", "FIELD:Lnet/minecraft/client/model/geom/PartPose;->zScale:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public float z() {
        return this.z;
    }

    public float xRot() {
        return this.xRot;
    }

    public float yRot() {
        return this.yRot;
    }

    public float zRot() {
        return this.zRot;
    }

    public float xScale() {
        return this.xScale;
    }

    public float yScale() {
        return this.yScale;
    }

    public float zScale() {
        return this.zScale;
    }

    public static PartPose offset(float $$0, float $$1, float $$2) {
        return offsetAndRotation($$0, $$1, $$2, 0.0f, 0.0f, 0.0f);
    }

    public static PartPose rotation(float $$0, float $$1, float $$2) {
        return offsetAndRotation(0.0f, 0.0f, 0.0f, $$0, $$1, $$2);
    }

    public static PartPose offsetAndRotation(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
        return new PartPose($$0, $$1, $$2, $$3, $$4, $$5, 1.0f, 1.0f, 1.0f);
    }

    public PartPose translated(float $$0, float $$1, float $$2) {
        return new PartPose(this.x + $$0, this.y + $$1, this.z + $$2, this.xRot, this.yRot, this.zRot, this.xScale, this.yScale, this.zScale);
    }

    public PartPose withScale(float $$0) {
        return new PartPose(this.x, this.y, this.z, this.xRot, this.yRot, this.zRot, $$0, $$0, $$0);
    }

    public PartPose scaled(float $$0) {
        if ($$0 == 1.0f) {
            return this;
        }
        return scaled($$0, $$0, $$0);
    }

    public PartPose scaled(float $$0, float $$1, float $$2) {
        return new PartPose(this.x * $$0, this.y * $$1, this.z * $$2, this.xRot, this.yRot, this.zRot, this.xScale * $$0, this.yScale * $$1, this.zScale * $$2);
    }
}
