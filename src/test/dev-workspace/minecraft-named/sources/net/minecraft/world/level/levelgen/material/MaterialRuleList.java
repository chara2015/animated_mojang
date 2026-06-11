package net.minecraft.world.level.levelgen.material;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseChunk;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/material/MaterialRuleList.class */
public final class MaterialRuleList extends Record implements NoiseChunk.BlockStateFiller {
    private final NoiseChunk.BlockStateFiller[] materialRuleList;

    public MaterialRuleList(NoiseChunk.BlockStateFiller[] $$0) {
        this.materialRuleList = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MaterialRuleList.class), MaterialRuleList.class, "materialRuleList", "FIELD:Lnet/minecraft/world/level/levelgen/material/MaterialRuleList;->materialRuleList:[Lnet/minecraft/world/level/levelgen/NoiseChunk$BlockStateFiller;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MaterialRuleList.class), MaterialRuleList.class, "materialRuleList", "FIELD:Lnet/minecraft/world/level/levelgen/material/MaterialRuleList;->materialRuleList:[Lnet/minecraft/world/level/levelgen/NoiseChunk$BlockStateFiller;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MaterialRuleList.class, Object.class), MaterialRuleList.class, "materialRuleList", "FIELD:Lnet/minecraft/world/level/levelgen/material/MaterialRuleList;->materialRuleList:[Lnet/minecraft/world/level/levelgen/NoiseChunk$BlockStateFiller;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public NoiseChunk.BlockStateFiller[] materialRuleList() {
        return this.materialRuleList;
    }

    @Override // net.minecraft.world.level.levelgen.NoiseChunk.BlockStateFiller
    public BlockState calculate(DensityFunction.FunctionContext $$0) {
        for (NoiseChunk.BlockStateFiller $$1 : this.materialRuleList) {
            BlockState $$2 = $$1.calculate($$0);
            if ($$2 != null) {
                return $$2;
            }
        }
        return null;
    }
}
