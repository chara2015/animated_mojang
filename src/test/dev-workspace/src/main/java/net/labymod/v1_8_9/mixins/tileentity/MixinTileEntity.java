package net.labymod.v1_8_9.mixins.tileentity;

import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.world.block.BlockPosition;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/tileentity/MixinTileEntity.class */
@Mixin({akw.class})
public abstract class MixinTileEntity implements BlockEntity {
    @Shadow
    public abstract cj v();

    @Shadow
    public abstract boolean x();

    @Override // net.labymod.api.client.blockentity.BlockEntity
    @NotNull
    public BlockPosition position() {
        return v();
    }

    @Override // net.labymod.api.client.blockentity.BlockEntity
    public boolean isRemoved() {
        return x();
    }
}
