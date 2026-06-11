package net.labymod.v1_17_1.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/world/block/MixinBlock.class */
@Mixin({bzp.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract bzp p();

    @Shadow
    public abstract ckt n();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return gw.W.b(p());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        bzp block = p();
        return block == bzq.a || block == bzq.lp || block == bzq.lo;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return n();
    }
}
