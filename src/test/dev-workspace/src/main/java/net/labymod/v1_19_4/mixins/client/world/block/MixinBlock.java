package net.labymod.v1_19_4.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/world/block/MixinBlock.class */
@Mixin({cpi.class})
public abstract class MixinBlock implements Block {
    @Shadow
    protected abstract cpi q();

    @Shadow
    public abstract dbq o();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return ja.f.b(q());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        cpi block = q();
        return block == cpj.a || block == cpj.mY || block == cpj.mX;
    }

    @Override // net.labymod.api.client.world.block.Block
    public BlockState defaultState() {
        return o();
    }
}
