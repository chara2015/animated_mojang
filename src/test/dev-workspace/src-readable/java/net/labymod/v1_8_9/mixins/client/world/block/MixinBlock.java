package net.labymod.v1_8_9.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/world/block/MixinBlock.class */
@Mixin({afh.class})
public abstract class MixinBlock implements Block {
    @Shadow
    public abstract alz Q();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return (ResourceLocation) afh.c.c((afh) this);
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        return this == afi.a;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return Q();
    }
}
