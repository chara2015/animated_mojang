package net.minecraft.world.damagesource;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/damagesource/CombatEntry.class */
public final class CombatEntry extends Record {
    private final DamageSource source;
    private final float damage;
    private final FallLocation fallLocation;
    private final float fallDistance;

    public CombatEntry(DamageSource $$0, float $$1, FallLocation $$2, float $$3) {
        this.source = $$0;
        this.damage = $$1;
        this.fallLocation = $$2;
        this.fallDistance = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CombatEntry.class), CombatEntry.class, "source;damage;fallLocation;fallDistance", "FIELD:Lnet/minecraft/world/damagesource/CombatEntry;->source:Lnet/minecraft/world/damagesource/DamageSource;", "FIELD:Lnet/minecraft/world/damagesource/CombatEntry;->damage:F", "FIELD:Lnet/minecraft/world/damagesource/CombatEntry;->fallLocation:Lnet/minecraft/world/damagesource/FallLocation;", "FIELD:Lnet/minecraft/world/damagesource/CombatEntry;->fallDistance:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CombatEntry.class), CombatEntry.class, "source;damage;fallLocation;fallDistance", "FIELD:Lnet/minecraft/world/damagesource/CombatEntry;->source:Lnet/minecraft/world/damagesource/DamageSource;", "FIELD:Lnet/minecraft/world/damagesource/CombatEntry;->damage:F", "FIELD:Lnet/minecraft/world/damagesource/CombatEntry;->fallLocation:Lnet/minecraft/world/damagesource/FallLocation;", "FIELD:Lnet/minecraft/world/damagesource/CombatEntry;->fallDistance:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CombatEntry.class, Object.class), CombatEntry.class, "source;damage;fallLocation;fallDistance", "FIELD:Lnet/minecraft/world/damagesource/CombatEntry;->source:Lnet/minecraft/world/damagesource/DamageSource;", "FIELD:Lnet/minecraft/world/damagesource/CombatEntry;->damage:F", "FIELD:Lnet/minecraft/world/damagesource/CombatEntry;->fallLocation:Lnet/minecraft/world/damagesource/FallLocation;", "FIELD:Lnet/minecraft/world/damagesource/CombatEntry;->fallDistance:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public DamageSource source() {
        return this.source;
    }

    public float damage() {
        return this.damage;
    }

    public FallLocation fallLocation() {
        return this.fallLocation;
    }

    public float fallDistance() {
        return this.fallDistance;
    }
}
