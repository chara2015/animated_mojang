package net.labymod.v26_1_1.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/world/block/MixinBlock.class */
@Mixin({Block.class})
public abstract class MixinBlock implements net.labymod.api.client.world.block.Block {
    @Shadow
    protected abstract Block asBlock();

    @Shadow
    public abstract BlockState defaultBlockState();

    @Override // net.labymod.api.client.world.block.Block
    public ResourceLocation id() {
        return BuiltInRegistries.BLOCK.getKey(asBlock());
    }

    @Override // net.labymod.api.client.world.block.Block
    public boolean isAir() {
        Block block = asBlock();
        return block == Blocks.AIR || block == Blocks.CAVE_AIR || block == Blocks.VOID_AIR;
    }

    @Override // net.labymod.api.client.world.block.Block
    public net.labymod.api.client.world.block.BlockState defaultState() {
        return defaultBlockState();
    }
}
