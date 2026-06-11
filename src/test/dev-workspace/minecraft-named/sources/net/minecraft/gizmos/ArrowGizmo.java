package net.minecraft.gizmos;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gizmos/ArrowGizmo.class */
public final class ArrowGizmo extends Record implements Gizmo {
    private final Vec3 start;
    private final Vec3 end;
    private final int color;
    private final float width;
    public static final float DEFAULT_WIDTH = 2.5f;

    public ArrowGizmo(Vec3 $$0, Vec3 $$1, int $$2, float $$3) {
        this.start = $$0;
        this.end = $$1;
        this.color = $$2;
        this.width = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ArrowGizmo.class), ArrowGizmo.class, "start;end;color;width", "FIELD:Lnet/minecraft/gizmos/ArrowGizmo;->start:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/ArrowGizmo;->end:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/ArrowGizmo;->color:I", "FIELD:Lnet/minecraft/gizmos/ArrowGizmo;->width:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ArrowGizmo.class), ArrowGizmo.class, "start;end;color;width", "FIELD:Lnet/minecraft/gizmos/ArrowGizmo;->start:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/ArrowGizmo;->end:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/ArrowGizmo;->color:I", "FIELD:Lnet/minecraft/gizmos/ArrowGizmo;->width:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ArrowGizmo.class, Object.class), ArrowGizmo.class, "start;end;color;width", "FIELD:Lnet/minecraft/gizmos/ArrowGizmo;->start:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/ArrowGizmo;->end:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/ArrowGizmo;->color:I", "FIELD:Lnet/minecraft/gizmos/ArrowGizmo;->width:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
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
        int $$2 = ARGB.multiplyAlpha(this.color, $$1);
        $$0.addLine(this.start, this.end, $$2, this.width);
        Quaternionf $$3 = new Quaternionf().rotationTo(new Vector3f(1.0f, 0.0f, 0.0f), this.end.subtract(this.start).toVector3f().normalize());
        float $$4 = (float) Mth.clamp(this.end.distanceTo(this.start) * 0.10000000149011612d, 0.10000000149011612d, 1.0d);
        Vector3f[] $$5 = {$$3.transform(-$$4, $$4, 0.0f, new Vector3f()), $$3.transform(-$$4, 0.0f, $$4, new Vector3f()), $$3.transform(-$$4, -$$4, 0.0f, new Vector3f()), $$3.transform(-$$4, 0.0f, -$$4, new Vector3f())};
        for (Vector3f $$6 : $$5) {
            $$0.addLine(this.end.add($$6.x, $$6.y, $$6.z), this.end, $$2, this.width);
        }
    }
}
