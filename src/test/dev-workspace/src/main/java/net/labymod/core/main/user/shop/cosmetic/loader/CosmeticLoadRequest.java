package net.labymod.core.main.user.shop.cosmetic.loader;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/loader/CosmeticLoadRequest.class */
public final class CosmeticLoadRequest extends Record implements Comparable<CosmeticLoadRequest> {
    private final CosmeticDefinition definition;
    private final double distanceSquared;

    public CosmeticLoadRequest(CosmeticDefinition definition, double distanceSquared) {
        this.definition = definition;
        this.distanceSquared = distanceSquared;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CosmeticLoadRequest.class), CosmeticLoadRequest.class, "definition;distanceSquared", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/loader/CosmeticLoadRequest;->definition:Lnet/labymod/core/main/user/shop/cosmetic/CosmeticDefinition;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/loader/CosmeticLoadRequest;->distanceSquared:D").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CosmeticLoadRequest.class), CosmeticLoadRequest.class, "definition;distanceSquared", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/loader/CosmeticLoadRequest;->definition:Lnet/labymod/core/main/user/shop/cosmetic/CosmeticDefinition;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/loader/CosmeticLoadRequest;->distanceSquared:D").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CosmeticLoadRequest.class, Object.class), CosmeticLoadRequest.class, "definition;distanceSquared", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/loader/CosmeticLoadRequest;->definition:Lnet/labymod/core/main/user/shop/cosmetic/CosmeticDefinition;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/loader/CosmeticLoadRequest;->distanceSquared:D").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public CosmeticDefinition definition() {
        return this.definition;
    }

    public double distanceSquared() {
        return this.distanceSquared;
    }

    @Override // java.lang.Comparable
    public int compareTo(CosmeticLoadRequest other) {
        return Double.compare(this.distanceSquared, other.distanceSquared);
    }
}
