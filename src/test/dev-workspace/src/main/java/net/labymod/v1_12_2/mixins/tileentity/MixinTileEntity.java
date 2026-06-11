package net.labymod.v1_12_2.mixins.tileentity;

import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.world.block.BlockPosition;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/tileentity/MixinTileEntity.class */
@Mixin({avj.class})
public abstract class MixinTileEntity implements BlockEntity {
    @Shadow
    public abstract et w();

    @Shadow
    public abstract boolean y();

    @Override // net.labymod.api.client.blockentity.BlockEntity
    @NotNull
    public BlockPosition position() {
        return w();
    }

    @Override // net.labymod.api.client.blockentity.BlockEntity
    public boolean isRemoved() {
        return y();
    }
}
