package net.labymod.api.client.gui.screen.state;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/ClearInfo.class */
public final class ClearInfo extends Record {
    private final int mask;
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;
    private final float depth;
    private final int stencil;
    public static final int CLEAR_COLOR = 1;
    public static final int CLEAR_DEPTH = 2;
    public static final int CLEAR_STENCIL = 4;

    public ClearInfo(int mask, float red, float green, float blue, float alpha, float depth, int stencil) {
        this.mask = mask;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.depth = depth;
        this.stencil = stencil;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClearInfo.class), ClearInfo.class, "mask;red;green;blue;alpha;depth;stencil", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->mask:I", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->red:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->green:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->blue:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->alpha:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->depth:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->stencil:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClearInfo.class), ClearInfo.class, "mask;red;green;blue;alpha;depth;stencil", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->mask:I", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->red:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->green:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->blue:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->alpha:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->depth:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->stencil:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClearInfo.class, Object.class), ClearInfo.class, "mask;red;green;blue;alpha;depth;stencil", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->mask:I", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->red:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->green:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->blue:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->alpha:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->depth:F", "FIELD:Lnet/labymod/api/client/gui/screen/state/ClearInfo;->stencil:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public int mask() {
        return this.mask;
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

    public float alpha() {
        return this.alpha;
    }

    public float depth() {
        return this.depth;
    }

    public int stencil() {
        return this.stencil;
    }

    public boolean isClearColor() {
        return (this.mask & 1) != 0;
    }

    public boolean isClearDepth() {
        return (this.mask & 2) != 0;
    }

    public boolean isClearStencil() {
        return (this.mask & 4) != 0;
    }
}
