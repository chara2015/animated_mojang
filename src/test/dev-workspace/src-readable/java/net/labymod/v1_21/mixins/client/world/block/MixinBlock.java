package net.labymod.v1_21.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/world/block/MixinBlock.class */
@Mixin({dfy.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract dfy q();

    @Shadow
    public abstract dtc o();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return lt.e.b(q());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        dfy block = q();
        return block == dga.a || block == dga.nc || block == dga.nb;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return o();
    }
}
