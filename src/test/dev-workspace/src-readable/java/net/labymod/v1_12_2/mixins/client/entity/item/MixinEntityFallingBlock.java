package net.labymod.v1_12_2.mixins.client.entity.item;

import net.labymod.api.client.entity.item.FallingBlock;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.util.math.MathHelper;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import net.labymod.v1_12_2.mixins.client.entity.MixinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/entity/item/MixinEntityFallingBlock.class */
@Mixin({ack.class})
public abstract class MixinEntityFallingBlock extends MixinEntity implements FallingBlock {

    @Shadow
    private awt e;

    @Override // net.labymod.api.client.entity.item.FallingBlock
    public BlockState blockState() {
        return MinecraftUtil.fromMinecraft(this.e, MathHelper.floor(this.p), MathHelper.floor(this.q), MathHelper.floor(this.r));
    }
}
