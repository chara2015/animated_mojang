package net.labymod.v1_21_4.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/world/block/MixinBlock.class */
@Mixin({djn.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract djn o();

    @Shadow
    public abstract dwy m();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return mb.e.b(o());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        djn block = o();
        return block == djp.a || block == djp.nE || block == djp.nD;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return m();
    }
}
