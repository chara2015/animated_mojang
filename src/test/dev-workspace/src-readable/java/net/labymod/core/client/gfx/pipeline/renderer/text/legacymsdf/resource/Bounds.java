package net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf.resource;

import com.google.gson.annotations.SerializedName;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds.class */
public final class Bounds extends Record {

    @SerializedName("left")
    private final float left;

    @SerializedName("top")
    private final float top;

    @SerializedName("right")
    private final float right;

    @SerializedName("bottom")
    private final float bottom;

    public Bounds(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Bounds.class), Bounds.class, "left;top;right;bottom", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;->left:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;->top:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;->right:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;->bottom:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Bounds.class), Bounds.class, "left;top;right;bottom", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;->left:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;->top:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;->right:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;->bottom:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Bounds.class, Object.class), Bounds.class, "left;top;right;bottom", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;->left:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;->top:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;->right:F", "FIELD:Lnet/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/resource/Bounds;->bottom:F").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @SerializedName("left")
    public float left() {
        return this.left;
    }

    @SerializedName("top")
    public float top() {
        return this.top;
    }

    @SerializedName("right")
    public float right() {
        return this.right;
    }

    @SerializedName("bottom")
    public float bottom() {
        return this.bottom;
    }
}
