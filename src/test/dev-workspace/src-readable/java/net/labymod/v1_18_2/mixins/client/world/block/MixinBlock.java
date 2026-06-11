package net.labymod.v1_18_2.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/world/block/MixinBlock.class */
@Mixin({cdq.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract cdq p();

    @Shadow
    public abstract cov n();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return hb.U.b(p());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        cdq block = p();
        return block == cdr.a || block == cdr.lp || block == cdr.lo;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return n();
    }
}
