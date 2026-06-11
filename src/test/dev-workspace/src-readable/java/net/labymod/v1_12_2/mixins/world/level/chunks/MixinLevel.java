package net.labymod.v1_12_2.mixins.world.level.chunks;

import net.labymod.api.Laby;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.event.client.world.chunk.BlockUpdateEvent;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/world/level/chunks/MixinLevel.class */
@Mixin({amu.class})
public class MixinLevel {

    @Shadow
    @Final
    public boolean G;

    @Inject(method = {"setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z"}, at = {@At("RETURN")})
    private void labyMod$fireChunkBlockUpdateEvent(et pos, awt newState, int flags, CallbackInfoReturnable<Boolean> cir) {
        if (!this.G) {
            return;
        }
        BlockState state = MinecraftUtil.fromMinecraft(newState, pos.p(), pos.q(), pos.r());
        Laby.fireEvent(new BlockUpdateEvent(state, flags));
    }
}
