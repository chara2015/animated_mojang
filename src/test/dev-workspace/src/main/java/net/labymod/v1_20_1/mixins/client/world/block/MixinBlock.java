package net.labymod.v1_20_1.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/world/block/MixinBlock.class */
@Mixin({cpn.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract cpn p();

    @Shadow
    public abstract dcb n();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return jb.f.b(p());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        cpn block = p();
        return block == cpo.a || block == cpo.nc || block == cpo.nb;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return n();
    }
}
