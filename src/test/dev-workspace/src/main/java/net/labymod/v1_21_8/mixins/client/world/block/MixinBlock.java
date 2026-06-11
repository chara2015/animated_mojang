package net.labymod.v1_21_8.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/world/block/MixinBlock.class */
@Mixin({dpz.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract dpz o();

    @Shadow
    public abstract eeb m();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return mm.e.b(o());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        dpz block = o();
        return block == dqb.a || block == dqb.nJ || block == dqb.nI;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return m();
    }
}
