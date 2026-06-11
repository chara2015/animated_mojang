package net.minecraft.server.packs;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.server.packs.repository.Pack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/PackSelectionConfig.class */
public final class PackSelectionConfig extends Record {
    private final boolean required;
    private final Pack.Position defaultPosition;
    private final boolean fixedPosition;

    public PackSelectionConfig(boolean $$0, Pack.Position $$1, boolean $$2) {
        this.required = $$0;
        this.defaultPosition = $$1;
        this.fixedPosition = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PackSelectionConfig.class), PackSelectionConfig.class, "required;defaultPosition;fixedPosition", "FIELD:Lnet/minecraft/server/packs/PackSelectionConfig;->required:Z", "FIELD:Lnet/minecraft/server/packs/PackSelectionConfig;->defaultPosition:Lnet/minecraft/server/packs/repository/Pack$Position;", "FIELD:Lnet/minecraft/server/packs/PackSelectionConfig;->fixedPosition:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PackSelectionConfig.class), PackSelectionConfig.class, "required;defaultPosition;fixedPosition", "FIELD:Lnet/minecraft/server/packs/PackSelectionConfig;->required:Z", "FIELD:Lnet/minecraft/server/packs/PackSelectionConfig;->defaultPosition:Lnet/minecraft/server/packs/repository/Pack$Position;", "FIELD:Lnet/minecraft/server/packs/PackSelectionConfig;->fixedPosition:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PackSelectionConfig.class, Object.class), PackSelectionConfig.class, "required;defaultPosition;fixedPosition", "FIELD:Lnet/minecraft/server/packs/PackSelectionConfig;->required:Z", "FIELD:Lnet/minecraft/server/packs/PackSelectionConfig;->defaultPosition:Lnet/minecraft/server/packs/repository/Pack$Position;", "FIELD:Lnet/minecraft/server/packs/PackSelectionConfig;->fixedPosition:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public boolean required() {
        return this.required;
    }

    public Pack.Position defaultPosition() {
        return this.defaultPosition;
    }

    public boolean fixedPosition() {
        return this.fixedPosition;
    }
}
