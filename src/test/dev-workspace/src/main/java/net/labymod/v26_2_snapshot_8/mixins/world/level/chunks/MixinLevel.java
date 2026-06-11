package net.labymod.v26_2_snapshot_8.mixins.world.level.chunks;

import net.labymod.api.Laby;
import net.labymod.api.event.client.world.chunk.BlockUpdateEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/world/level/chunks/MixinLevel.class */
@Mixin({Level.class})
public abstract class MixinLevel {
    @Shadow
    public abstract boolean isClientSide();

    @Inject(method = {"setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z"}, at = {@At("RETURN")})
    private void labyMod$fireChunkBlockUpdateEvent(BlockPos pos, BlockState newState, int flags, int recursionLeft, CallbackInfoReturnable<Boolean> cir) {
        if (!isClientSide()) {
            return;
        }
        net.labymod.api.client.world.block.BlockState state = (net.labymod.api.client.world.block.BlockState) newState;
        state.setCoordinates(pos.getX(), pos.getY(), pos.getZ());
        Laby.fireEvent(new BlockUpdateEvent(state, flags));
    }
}
