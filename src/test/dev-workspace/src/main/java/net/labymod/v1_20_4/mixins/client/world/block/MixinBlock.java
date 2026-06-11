package net.labymod.v1_20_4.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/world/block/MixinBlock.class */
@Mixin({cwq.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract cwq q();

    @Shadow
    public abstract djh o();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return kd.e.b(q());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        cwq block = q();
        return block == cws.a || block == cws.nc || block == cws.nb;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return o();
    }
}
