package net.labymod.v1_21_10.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/world/block/MixinBlock.class */
@Mixin({duv.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract duv o();

    @Shadow
    public abstract ejm m();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return mo.e.b(o());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        duv block = o();
        return block == dux.a || block == dux.nZ || block == dux.nY;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return m();
    }
}
