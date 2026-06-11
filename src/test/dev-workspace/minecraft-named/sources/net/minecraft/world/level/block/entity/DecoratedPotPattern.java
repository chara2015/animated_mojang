package net.minecraft.world.level.block.entity;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/DecoratedPotPattern.class */
public final class DecoratedPotPattern extends Record {
    private final Identifier assetId;

    public DecoratedPotPattern(Identifier $$0) {
        this.assetId = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DecoratedPotPattern.class), DecoratedPotPattern.class, "assetId", "FIELD:Lnet/minecraft/world/level/block/entity/DecoratedPotPattern;->assetId:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DecoratedPotPattern.class), DecoratedPotPattern.class, "assetId", "FIELD:Lnet/minecraft/world/level/block/entity/DecoratedPotPattern;->assetId:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DecoratedPotPattern.class, Object.class), DecoratedPotPattern.class, "assetId", "FIELD:Lnet/minecraft/world/level/block/entity/DecoratedPotPattern;->assetId:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Identifier assetId() {
        return this.assetId;
    }
}
