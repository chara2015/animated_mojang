package net.labymod.v1_21.mixins.world.level.chunks;

import net.labymod.api.Laby;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.event.client.world.chunk.BlockUpdateEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/world/level/chunks/MixinLevel.class */
@Mixin({dcw.class})
public abstract class MixinLevel {
    @Shadow
    public abstract boolean x_();

    @Inject(method = {"setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z"}, at = {@At("RETURN")})
    private void labyMod$fireChunkBlockUpdateEvent(jd pos, dtc newState, int flags, int recursionLeft, CallbackInfoReturnable<Boolean> cir) {
        if (!x_()) {
            return;
        }
        BlockState state = (BlockState) newState;
        state.setCoordinates(pos.u(), pos.v(), pos.w());
        Laby.fireEvent(new BlockUpdateEvent(state, flags));
    }
}
