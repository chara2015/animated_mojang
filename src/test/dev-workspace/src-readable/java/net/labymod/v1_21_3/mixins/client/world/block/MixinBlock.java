package net.labymod.v1_21_3.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/world/block/MixinBlock.class */
@Mixin({dkm.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract dkm o();

    @Shadow
    public abstract dxv m();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return ma.e.b(o());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        dkm block = o();
        return block == dko.a || block == dko.nx || block == dko.nw;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return m();
    }
}
