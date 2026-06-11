package net.minecraft.gizmos;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gizmos/PointGizmo.class */
public final class PointGizmo extends Record implements Gizmo {
    private final Vec3 pos;
    private final int color;
    private final float size;

    public PointGizmo(Vec3 $$0, int $$1, float $$2) {
        this.pos = $$0;
        this.color = $$1;
        this.size = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PointGizmo.class), PointGizmo.class, "pos;color;size", "FIELD:Lnet/minecraft/gizmos/PointGizmo;->pos:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/PointGizmo;->color:I", "FIELD:Lnet/minecraft/gizmos/PointGizmo;->size:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PointGizmo.class), PointGizmo.class, "pos;color;size", "FIELD:Lnet/minecraft/gizmos/PointGizmo;->pos:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/PointGizmo;->color:I", "FIELD:Lnet/minecraft/gizmos/PointGizmo;->size:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PointGizmo.class, Object.class), PointGizmo.class, "pos;color;size", "FIELD:Lnet/minecraft/gizmos/PointGizmo;->pos:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/PointGizmo;->color:I", "FIELD:Lnet/minecraft/gizmos/PointGizmo;->size:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Vec3 pos() {
        return this.pos;
    }

    public int color() {
        return this.color;
    }

    public float size() {
        return this.size;
    }

    @Override // net.minecraft.gizmos.Gizmo
    public void emit(GizmoPrimitives $$0, float $$1) {
        $$0.addPoint(this.pos, ARGB.multiplyAlpha(this.color, $$1), this.size);
    }
}
