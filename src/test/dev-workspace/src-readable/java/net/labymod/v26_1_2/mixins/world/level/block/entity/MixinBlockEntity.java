package net.labymod.v26_1_2.mixins.world.level.block.entity;

import net.labymod.api.client.world.block.BlockPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/world/level/block/entity/MixinBlockEntity.class */
@Mixin({BlockEntity.class})
@Implements({@Interface(prefix = "labyMod$", iface = net.labymod.api.client.blockentity.BlockEntity.class, remap = Interface.Remap.NONE)})
public abstract class MixinBlockEntity implements net.labymod.api.client.blockentity.BlockEntity {

    @Shadow
    protected boolean remove;

    @Shadow
    public abstract BlockPos getBlockPos();

    @Override // net.labymod.api.client.blockentity.BlockEntity
    @Shadow
    public abstract boolean isRemoved();

    @Shadow
    public abstract BlockState getBlockState();

    @Override // net.labymod.api.client.blockentity.BlockEntity
    @NotNull
    public BlockPosition position() {
        return getBlockPos();
    }

    @Intrinsic
    public boolean labyMod$isRemoved() {
        return isRemoved();
    }
}
