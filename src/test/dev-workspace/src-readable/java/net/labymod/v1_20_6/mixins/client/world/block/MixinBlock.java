package net.labymod.v1_20_6.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/world/block/MixinBlock.class */
@Mixin({dfb.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract dfb q();

    @Shadow
    public abstract dse o();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return lp.e.b(q());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        dfb block = q();
        return block == dfd.a || block == dfd.nc || block == dfd.nb;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return o();
    }
}
