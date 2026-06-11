package net.labymod.v1_21_11.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/world/block/MixinBlock.class */
@Mixin({dzq.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract dzq o();

    @Shadow
    public abstract eoh m();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return mi.e.b(o());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        dzq block = o();
        return block == dzs.a || block == dzs.nZ || block == dzs.nY;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return m();
    }
}
