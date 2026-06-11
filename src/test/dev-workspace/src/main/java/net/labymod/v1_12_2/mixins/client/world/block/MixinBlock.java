package net.labymod.v1_12_2.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/world/block/MixinBlock.class */
@Mixin({aow.class})
public abstract class MixinBlock implements Block {
    @Shadow
    public abstract awt t();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return (ResourceLocation) aow.h.b((aow) this);
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        return this == aox.a;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return t();
    }
}
