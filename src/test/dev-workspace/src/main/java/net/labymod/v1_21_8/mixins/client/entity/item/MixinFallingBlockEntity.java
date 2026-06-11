package net.labymod.v1_21_8.mixins.client.entity.item;

import net.labymod.api.client.entity.item.FallingBlock;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.v1_21_8.mixins.client.entity.MixinEntity;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/entity/item/MixinFallingBlockEntity.class */
@Mixin({cqy.class})
@Implements({@Interface(iface = FallingBlock.class, prefix = "fallingBlock$", remap = Interface.Remap.NONE)})
public abstract class MixinFallingBlockEntity extends MixinEntity implements FallingBlock {

    @Shadow
    private eeb m;

    @Intrinsic
    public BlockState fallingBlock$blockState() {
        return this.m;
    }
}
