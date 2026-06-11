package net.labymod.core.main.user.shop.cosmetic.texture;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/texture/UVTransform.class */
public final class UVTransform extends Record {
    private final float offsetU;
    private final float offsetV;
    private final float scaleU;
    private final float scaleV;
    public static final UVTransform IDENTITY = new UVTransform(0.0f, 0.0f, 1.0f, 1.0f);

    public UVTransform(float offsetU, float offsetV, float scaleU, float scaleV) {
        this.offsetU = offsetU;
        this.offsetV = offsetV;
        this.scaleU = scaleU;
        this.scaleV = scaleV;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, UVTransform.class), UVTransform.class, "offsetU;offsetV;scaleU;scaleV", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/UVTransform;->offsetU:F", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/UVTransform;->offsetV:F", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/UVTransform;->scaleU:F", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/UVTransform;->scaleV:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, UVTransform.class), UVTransform.class, "offsetU;offsetV;scaleU;scaleV", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/UVTransform;->offsetU:F", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/UVTransform;->offsetV:F", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/UVTransform;->scaleU:F", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/UVTransform;->scaleV:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, UVTransform.class, Object.class), UVTransform.class, "offsetU;offsetV;scaleU;scaleV", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/UVTransform;->offsetU:F", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/UVTransform;->offsetV:F", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/UVTransform;->scaleU:F", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/UVTransform;->scaleV:F").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public float offsetU() {
        return this.offsetU;
    }

    public float offsetV() {
        return this.offsetV;
    }

    public float scaleU() {
        return this.scaleU;
    }

    public float scaleV() {
        return this.scaleV;
    }

    public boolean isIdentity() {
        return this.offsetU == 0.0f && this.offsetV == 0.0f && this.scaleU == 1.0f && this.scaleV == 1.0f;
    }

    public float transformU(float u) {
        return this.offsetU + (u * this.scaleU);
    }

    public float transformV(float v) {
        return this.offsetV + (v * this.scaleV);
    }
}
