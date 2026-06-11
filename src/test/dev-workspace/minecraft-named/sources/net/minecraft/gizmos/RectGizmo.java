package net.minecraft.gizmos;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gizmos/RectGizmo.class */
public final class RectGizmo extends Record implements Gizmo {
    private final Vec3 a;
    private final Vec3 b;
    private final Vec3 c;
    private final Vec3 d;
    private final GizmoStyle style;

    public RectGizmo(Vec3 $$0, Vec3 $$1, Vec3 $$2, Vec3 $$3, GizmoStyle $$4) {
        this.a = $$0;
        this.b = $$1;
        this.c = $$2;
        this.d = $$3;
        this.style = $$4;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RectGizmo.class), RectGizmo.class, "a;b;c;d;style", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->a:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->b:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->c:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->d:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->style:Lnet/minecraft/gizmos/GizmoStyle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RectGizmo.class), RectGizmo.class, "a;b;c;d;style", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->a:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->b:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->c:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->d:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->style:Lnet/minecraft/gizmos/GizmoStyle;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RectGizmo.class, Object.class), RectGizmo.class, "a;b;c;d;style", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->a:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->b:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->c:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->d:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/RectGizmo;->style:Lnet/minecraft/gizmos/GizmoStyle;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Vec3 a() {
        return this.a;
    }

    public Vec3 b() {
        return this.b;
    }

    public Vec3 c() {
        return this.c;
    }

    public Vec3 d() {
        return this.d;
    }

    public GizmoStyle style() {
        return this.style;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static RectGizmo fromCuboidFace(Vec3 $$0, Vec3 $$1, Direction $$2, GizmoStyle $$3) throws MatchException {
        switch ($$2) {
            case DOWN:
                return new RectGizmo(new Vec3($$0.x, $$0.y, $$0.z), new Vec3($$1.x, $$0.y, $$0.z), new Vec3($$1.x, $$0.y, $$1.z), new Vec3($$0.x, $$0.y, $$1.z), $$3);
            case UP:
                return new RectGizmo(new Vec3($$0.x, $$1.y, $$0.z), new Vec3($$0.x, $$1.y, $$1.z), new Vec3($$1.x, $$1.y, $$1.z), new Vec3($$1.x, $$1.y, $$0.z), $$3);
            case NORTH:
                return new RectGizmo(new Vec3($$0.x, $$0.y, $$0.z), new Vec3($$0.x, $$1.y, $$0.z), new Vec3($$1.x, $$1.y, $$0.z), new Vec3($$1.x, $$0.y, $$0.z), $$3);
            case SOUTH:
                return new RectGizmo(new Vec3($$0.x, $$0.y, $$1.z), new Vec3($$1.x, $$0.y, $$1.z), new Vec3($$1.x, $$1.y, $$1.z), new Vec3($$0.x, $$1.y, $$1.z), $$3);
            case WEST:
                return new RectGizmo(new Vec3($$0.x, $$0.y, $$0.z), new Vec3($$0.x, $$0.y, $$1.z), new Vec3($$0.x, $$1.y, $$1.z), new Vec3($$0.x, $$1.y, $$0.z), $$3);
            case EAST:
                return new RectGizmo(new Vec3($$1.x, $$0.y, $$0.z), new Vec3($$1.x, $$1.y, $$0.z), new Vec3($$1.x, $$1.y, $$1.z), new Vec3($$1.x, $$0.y, $$1.z), $$3);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Override // net.minecraft.gizmos.Gizmo
    public void emit(GizmoPrimitives $$0, float $$1) {
        if (this.style.hasFill()) {
            int $$2 = this.style.multipliedFill($$1);
            $$0.addQuad(this.a, this.b, this.c, this.d, $$2);
        }
        if (this.style.hasStroke()) {
            int $$3 = this.style.multipliedStroke($$1);
            $$0.addLine(this.a, this.b, $$3, this.style.strokeWidth());
            $$0.addLine(this.b, this.c, $$3, this.style.strokeWidth());
            $$0.addLine(this.c, this.d, $$3, this.style.strokeWidth());
            $$0.addLine(this.d, this.a, $$3, this.style.strokeWidth());
        }
    }
}
