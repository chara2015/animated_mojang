package net.labymod.v26_1_1.mixins.client.entity.item;

import net.labymod.api.client.entity.item.FallingBlock;
import net.labymod.v26_1_1.mixins.client.entity.MixinEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/entity/item/MixinFallingBlockEntity.class */
@Mixin({FallingBlockEntity.class})
@Implements({@Interface(iface = FallingBlock.class, prefix = "fallingBlock$", remap = Interface.Remap.NONE)})
public abstract class MixinFallingBlockEntity extends MixinEntity implements FallingBlock {

    @Shadow
    private BlockState blockState;

    @Intrinsic
    public net.labymod.api.client.world.block.BlockState fallingBlock$blockState() {
        return this.blockState;
    }
}
