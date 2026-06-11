package net.labymod.v26_1_1.client.resources.pack;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.Pack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/resources/pack/FixedResourcesSupplier.class */
public final class FixedResourcesSupplier extends Record implements Pack.ResourcesSupplier {
    private final PackResources packResources;

    public FixedResourcesSupplier(PackResources packResources) {
        this.packResources = packResources;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FixedResourcesSupplier.class), FixedResourcesSupplier.class, "packResources", "FIELD:Lnet/labymod/v26_1_1/client/resources/pack/FixedResourcesSupplier;->packResources:Lnet/minecraft/server/packs/PackResources;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FixedResourcesSupplier.class), FixedResourcesSupplier.class, "packResources", "FIELD:Lnet/labymod/v26_1_1/client/resources/pack/FixedResourcesSupplier;->packResources:Lnet/minecraft/server/packs/PackResources;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FixedResourcesSupplier.class, Object.class), FixedResourcesSupplier.class, "packResources", "FIELD:Lnet/labymod/v26_1_1/client/resources/pack/FixedResourcesSupplier;->packResources:Lnet/minecraft/server/packs/PackResources;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public PackResources packResources() {
        return this.packResources;
    }

    public PackResources openPrimary(PackLocationInfo var1) {
        return this.packResources;
    }

    public PackResources openFull(PackLocationInfo var1, Pack.Metadata var2) {
        return this.packResources;
    }
}
