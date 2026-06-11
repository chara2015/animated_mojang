package net.minecraft.gizmos;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.OptionalDouble;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gizmos/TextGizmo.class */
public final class TextGizmo extends Record implements Gizmo {
    private final Vec3 pos;
    private final String text;
    private final Style style;

    public TextGizmo(Vec3 $$0, String $$1, Style $$2) {
        this.pos = $$0;
        this.text = $$1;
        this.style = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TextGizmo.class), TextGizmo.class, "pos;text;style", "FIELD:Lnet/minecraft/gizmos/TextGizmo;->pos:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/TextGizmo;->text:Ljava/lang/String;", "FIELD:Lnet/minecraft/gizmos/TextGizmo;->style:Lnet/minecraft/gizmos/TextGizmo$Style;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TextGizmo.class), TextGizmo.class, "pos;text;style", "FIELD:Lnet/minecraft/gizmos/TextGizmo;->pos:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/TextGizmo;->text:Ljava/lang/String;", "FIELD:Lnet/minecraft/gizmos/TextGizmo;->style:Lnet/minecraft/gizmos/TextGizmo$Style;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TextGizmo.class, Object.class), TextGizmo.class, "pos;text;style", "FIELD:Lnet/minecraft/gizmos/TextGizmo;->pos:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/gizmos/TextGizmo;->text:Ljava/lang/String;", "FIELD:Lnet/minecraft/gizmos/TextGizmo;->style:Lnet/minecraft/gizmos/TextGizmo$Style;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Vec3 pos() {
        return this.pos;
    }

    public String text() {
        return this.text;
    }

    public Style style() {
        return this.style;
    }

    @Override // net.minecraft.gizmos.Gizmo
    public void emit(GizmoPrimitives $$0, float $$1) {
        Style $$3;
        if ($$1 < 1.0f) {
            $$3 = new Style(ARGB.multiplyAlpha(this.style.color, $$1), this.style.scale, this.style.adjustLeft);
        } else {
            $$3 = this.style;
        }
        $$0.addText(this.pos, this.text, $$3);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gizmos/TextGizmo$Style.class */
    public static final class Style extends Record {
        private final int color;
        private final float scale;
        private final OptionalDouble adjustLeft;
        public static final float DEFAULT_SCALE = 0.32f;

        public Style(int $$0, float $$1, OptionalDouble $$2) {
            this.color = $$0;
            this.scale = $$1;
            this.adjustLeft = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Style.class), Style.class, "color;scale;adjustLeft", "FIELD:Lnet/minecraft/gizmos/TextGizmo$Style;->color:I", "FIELD:Lnet/minecraft/gizmos/TextGizmo$Style;->scale:F", "FIELD:Lnet/minecraft/gizmos/TextGizmo$Style;->adjustLeft:Ljava/util/OptionalDouble;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Style.class), Style.class, "color;scale;adjustLeft", "FIELD:Lnet/minecraft/gizmos/TextGizmo$Style;->color:I", "FIELD:Lnet/minecraft/gizmos/TextGizmo$Style;->scale:F", "FIELD:Lnet/minecraft/gizmos/TextGizmo$Style;->adjustLeft:Ljava/util/OptionalDouble;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Style.class, Object.class), Style.class, "color;scale;adjustLeft", "FIELD:Lnet/minecraft/gizmos/TextGizmo$Style;->color:I", "FIELD:Lnet/minecraft/gizmos/TextGizmo$Style;->scale:F", "FIELD:Lnet/minecraft/gizmos/TextGizmo$Style;->adjustLeft:Ljava/util/OptionalDouble;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int color() {
            return this.color;
        }

        public float scale() {
            return this.scale;
        }

        public OptionalDouble adjustLeft() {
            return this.adjustLeft;
        }

        public static Style whiteAndCentered() {
            return new Style(-1, 0.32f, OptionalDouble.empty());
        }

        public static Style forColorAndCentered(int $$0) {
            return new Style($$0, 0.32f, OptionalDouble.empty());
        }

        public static Style forColor(int $$0) {
            return new Style($$0, 0.32f, OptionalDouble.of(Density.SURFACE));
        }

        public Style withScale(float $$0) {
            return new Style(this.color, $$0, this.adjustLeft);
        }

        public Style withLeftAlignment(float $$0) {
            return new Style(this.color, this.scale, OptionalDouble.of($$0));
        }
    }
}
