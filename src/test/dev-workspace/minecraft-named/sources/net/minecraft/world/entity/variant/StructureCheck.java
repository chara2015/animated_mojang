package net.minecraft.world.entity.variant;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/variant/StructureCheck.class */
public final class StructureCheck extends Record implements SpawnCondition {
    private final HolderSet<Structure> requiredStructures;
    public static final MapCodec<StructureCheck> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(RegistryCodecs.homogeneousList(Registries.STRUCTURE).fieldOf("structures").forGetter((v0) -> {
            return v0.requiredStructures();
        })).apply($$0, StructureCheck::new);
    });

    public StructureCheck(HolderSet<Structure> $$0) {
        this.requiredStructures = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, StructureCheck.class), StructureCheck.class, "requiredStructures", "FIELD:Lnet/minecraft/world/entity/variant/StructureCheck;->requiredStructures:Lnet/minecraft/core/HolderSet;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StructureCheck.class), StructureCheck.class, "requiredStructures", "FIELD:Lnet/minecraft/world/entity/variant/StructureCheck;->requiredStructures:Lnet/minecraft/core/HolderSet;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StructureCheck.class, Object.class), StructureCheck.class, "requiredStructures", "FIELD:Lnet/minecraft/world/entity/variant/StructureCheck;->requiredStructures:Lnet/minecraft/core/HolderSet;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public HolderSet<Structure> requiredStructures() {
        return this.requiredStructures;
    }

    @Override // java.util.function.Predicate
    public boolean test(SpawnContext $$0) {
        return $$0.level().getLevel().structureManager().getStructureWithPieceAt($$0.pos(), this.requiredStructures).isValid();
    }

    @Override // net.minecraft.world.entity.variant.SpawnCondition
    public MapCodec<StructureCheck> codec() {
        return MAP_CODEC;
    }
}
