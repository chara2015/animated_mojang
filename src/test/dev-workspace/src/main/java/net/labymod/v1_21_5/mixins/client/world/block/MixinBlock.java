package net.labymod.v1_21_5.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/world/block/MixinBlock.class */
@Mixin({dno.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract dno o();

    @Shadow
    public abstract ebq m();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return mh.e.b(o());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        dno block = o();
        return block == dnq.a || block == dnq.nI || block == dnq.nH;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return m();
    }
}
