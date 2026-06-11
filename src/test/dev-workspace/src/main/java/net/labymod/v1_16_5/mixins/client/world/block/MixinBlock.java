package net.labymod.v1_16_5.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/world/block/MixinBlock.class */
@Mixin({buo.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract buo p();

    @Shadow
    public abstract ceh n();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return gm.Q.b(p());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        buo block = p();
        return block == bup.a || block == bup.lb || block == bup.la;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return n();
    }
}
