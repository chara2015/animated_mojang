package net.minecraft.gizmos;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.ARGB;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gizmos/GizmoStyle.class */
public final class GizmoStyle extends Record {
    private final int stroke;
    private final float strokeWidth;
    private final int fill;
    private static final float DEFAULT_WIDTH = 2.5f;

    public GizmoStyle(int $$0, float $$1, int $$2) {
        this.stroke = $$0;
        this.strokeWidth = $$1;
        this.fill = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GizmoStyle.class), GizmoStyle.class, "stroke;strokeWidth;fill", "FIELD:Lnet/minecraft/gizmos/GizmoStyle;->stroke:I", "FIELD:Lnet/minecraft/gizmos/GizmoStyle;->strokeWidth:F", "FIELD:Lnet/minecraft/gizmos/GizmoStyle;->fill:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GizmoStyle.class), GizmoStyle.class, "stroke;strokeWidth;fill", "FIELD:Lnet/minecraft/gizmos/GizmoStyle;->stroke:I", "FIELD:Lnet/minecraft/gizmos/GizmoStyle;->strokeWidth:F", "FIELD:Lnet/minecraft/gizmos/GizmoStyle;->fill:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GizmoStyle.class, Object.class), GizmoStyle.class, "stroke;strokeWidth;fill", "FIELD:Lnet/minecraft/gizmos/GizmoStyle;->stroke:I", "FIELD:Lnet/minecraft/gizmos/GizmoStyle;->strokeWidth:F", "FIELD:Lnet/minecraft/gizmos/GizmoStyle;->fill:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int stroke() {
        return this.stroke;
    }

    public float strokeWidth() {
        return this.strokeWidth;
    }

    public int fill() {
        return this.fill;
    }

    public static GizmoStyle stroke(int $$0) {
        return new GizmoStyle($$0, 2.5f, 0);
    }

    public static GizmoStyle stroke(int $$0, float $$1) {
        return new GizmoStyle($$0, $$1, 0);
    }

    public static GizmoStyle fill(int $$0) {
        return new GizmoStyle(0, 0.0f, $$0);
    }

    public static GizmoStyle strokeAndFill(int $$0, float $$1, int $$2) {
        return new GizmoStyle($$0, $$1, $$2);
    }

    public boolean hasFill() {
        return this.fill != 0;
    }

    public boolean hasStroke() {
        return this.stroke != 0 && this.strokeWidth > 0.0f;
    }

    public int multipliedStroke(float $$0) {
        return ARGB.multiplyAlpha(this.stroke, $$0);
    }

    public int multipliedFill(float $$0) {
        return ARGB.multiplyAlpha(this.fill, $$0);
    }
}
