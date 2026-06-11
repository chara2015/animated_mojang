package net.labymod.api.util.color.format;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/color/format/PackedColor.class */
public final class PackedColor extends Record {
    private final ColorFormat colorFormat;
    private final int value;

    public PackedColor(ColorFormat colorFormat, int value) {
        this.colorFormat = colorFormat;
        this.value = value;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PackedColor.class), PackedColor.class, "colorFormat;value", "FIELD:Lnet/labymod/api/util/color/format/PackedColor;->colorFormat:Lnet/labymod/api/util/color/format/ColorFormat;", "FIELD:Lnet/labymod/api/util/color/format/PackedColor;->value:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PackedColor.class), PackedColor.class, "colorFormat;value", "FIELD:Lnet/labymod/api/util/color/format/PackedColor;->colorFormat:Lnet/labymod/api/util/color/format/ColorFormat;", "FIELD:Lnet/labymod/api/util/color/format/PackedColor;->value:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PackedColor.class, Object.class), PackedColor.class, "colorFormat;value", "FIELD:Lnet/labymod/api/util/color/format/PackedColor;->colorFormat:Lnet/labymod/api/util/color/format/ColorFormat;", "FIELD:Lnet/labymod/api/util/color/format/PackedColor;->value:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public ColorFormat colorFormat() {
        return this.colorFormat;
    }

    public int value() {
        return this.value;
    }

    public int red() {
        return this.colorFormat.red(this.value);
    }

    public int green() {
        return this.colorFormat.green(this.value);
    }

    public int blue() {
        return this.colorFormat.blue(this.value);
    }

    public int alpha() {
        return this.colorFormat.alpha(this.value);
    }

    public int packTo(@NotNull ColorFormat destinationFormat) {
        return this.colorFormat.packTo(destinationFormat, this.value);
    }
}
