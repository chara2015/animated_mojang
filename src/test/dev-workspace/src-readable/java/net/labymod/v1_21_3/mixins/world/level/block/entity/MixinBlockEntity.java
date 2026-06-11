package net.labymod.v1_21_3.mixins.world.level.block.entity;

import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.world.block.BlockPosition;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/world/level/block/entity/MixinBlockEntity.class */
@Mixin({dux.class})
@Implements({@Interface(prefix = "labyMod$", iface = BlockEntity.class, remap = Interface.Remap.NONE)})
public abstract class MixinBlockEntity implements BlockEntity {

    @Shadow
    protected boolean q;

    @Shadow
    public abstract jh aB_();

    @Shadow
    public abstract boolean n();

    @Shadow
    public abstract dxv m();

    @Override // net.labymod.api.client.blockentity.BlockEntity
    @NotNull
    public BlockPosition position() {
        return aB_();
    }

    @Intrinsic
    public boolean labyMod$isRemoved() {
        return n();
    }
}
