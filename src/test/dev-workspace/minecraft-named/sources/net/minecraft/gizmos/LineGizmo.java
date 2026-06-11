package net.minecraft.gizmos;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gizmos/LineGizmo.class */
public final class LineGizmo extends Record implements Gizmo {
    private final Vec3 start;
    private final Vec3 end;
    private final int color;
    private final float width;
    public static final float DEFAULT_WIDTH = 3.0f;

    public LineGizmo(Vec3 $$0, Vec3 $$1, int $$2, float $$3) {
        this.start = $$0;
        this.end = $$1;
        this.color = $$2;
        this.width = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LineGizmo.class), LineGizmo.class, "start;end;color;width", "FIELD:Lnet/minecraft/gizmos/LineGizmo;->start:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/LineGizmo;->end:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/LineGizmo;->color:I", "FIELD:Lnet/minecraft/gizmos/LineGizmo;->width:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LineGizmo.class), LineGizmo.class, "start;end;color;width", "FIELD:Lnet/minecraft/gizmos/LineGizmo;->start:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/LineGizmo;->end:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/LineGizmo;->color:I", "FIELD:Lnet/minecraft/gizmos/LineGizmo;->width:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LineGizmo.class, Object.class), LineGizmo.class, "start;end;color;width", "FIELD:Lnet/minecraft/gizmos/LineGizmo;->start:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/LineGizmo;->end:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/LineGizmo;->color:I", "FIELD:Lnet/minecraft/gizmos/LineGizmo;->width:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Vec3 start() {
        return this.start;
    }

    public Vec3 end() {
        return this.end;
    }

    public int color() {
        return this.color;
    }

    public float width() {
        return this.width;
    }

    @Override // net.minecraft.gizmos.Gizmo
    public void emit(GizmoPrimitives $$0, float $$1) {
        $$0.addLine(this.start, this.end, ARGB.multiplyAlpha(this.color, $$1), this.width);
    }
}
