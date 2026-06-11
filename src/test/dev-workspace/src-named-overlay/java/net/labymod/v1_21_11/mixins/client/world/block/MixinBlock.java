package net.labymod.v1_21_11.mixins.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/world/block/MixinBlock.class */
@Mixin({Block.class})
public abstract class MixinBlock implements net.labymod.api.client.world.block.Block {
    @Shadow
    protected abstract Block asBlock();

    @Shadow
    public abstract BlockState defaultBlockState();

    public ResourceLocation id() {
        return BuiltInRegistries.BLOCK.getKey(asBlock());
    }

    public boolean isAir() {
        Block block = asBlock();
        return block == Blocks.AIR || block == Blocks.CAVE_AIR || block == Blocks.VOID_AIR;
    }

    public net.labymod.api.client.world.block.BlockState defaultState() {
        return defaultBlockState();
    }
}

