package net.minecraft.world.level;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/BlockEventData.class */
public final class BlockEventData extends Record {
    private final BlockPos pos;
    private final Block block;
    private final int paramA;
    private final int paramB;

    public BlockEventData(BlockPos $$0, Block $$1, int $$2, int $$3) {
        this.pos = $$0;
        this.block = $$1;
        this.paramA = $$2;
        this.paramB = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BlockEventData.class), BlockEventData.class, "pos;block;paramA;paramB", "FIELD:Lnet/minecraft/world/level/BlockEventData;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/BlockEventData;->block:Lnet/minecraft/world/level/block/Block;", "FIELD:Lnet/minecraft/world/level/BlockEventData;->paramA:I", "FIELD:Lnet/minecraft/world/level/BlockEventData;->paramB:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BlockEventData.class), BlockEventData.class, "pos;block;paramA;paramB", "FIELD:Lnet/minecraft/world/level/BlockEventData;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/BlockEventData;->block:Lnet/minecraft/world/level/block/Block;", "FIELD:Lnet/minecraft/world/level/BlockEventData;->paramA:I", "FIELD:Lnet/minecraft/world/level/BlockEventData;->paramB:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BlockEventData.class, Object.class), BlockEventData.class, "pos;block;paramA;paramB", "FIELD:Lnet/minecraft/world/level/BlockEventData;->pos:Lnet/minecraft/core/BlockPos;", "FIELD:Lnet/minecraft/world/level/BlockEventData;->block:Lnet/minecraft/world/level/block/Block;", "FIELD:Lnet/minecraft/world/level/BlockEventData;->paramA:I", "FIELD:Lnet/minecraft/world/level/BlockEventData;->paramB:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public BlockPos pos() {
        return this.pos;
    }

    public Block block() {
        return this.block;
    }

    public int paramA() {
        return this.paramA;
    }

    public int paramB() {
        return this.paramB;
    }
}
