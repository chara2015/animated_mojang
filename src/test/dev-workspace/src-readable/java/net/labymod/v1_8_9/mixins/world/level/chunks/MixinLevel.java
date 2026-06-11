package net.labymod.v1_8_9.mixins.world.level.chunks;

import net.labymod.api.Laby;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.event.client.world.chunk.BlockUpdateEvent;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/world/level/chunks/MixinLevel.class */
@Mixin({adm.class})
public class MixinLevel {

    @Shadow
    @Final
    public boolean D;

    @Inject(method = {"setBlockState(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z"}, at = {@At("RETURN")})
    private void labyMod$fireChunkBlockUpdateEvent(cj pos, alz newState, int flags, CallbackInfoReturnable<Boolean> cir) {
        if (!this.D) {
            return;
        }
        BlockState state = MinecraftUtil.fromMinecraft(newState, pos.n(), pos.o(), pos.p());
        Laby.fireEvent(new BlockUpdateEvent(state, flags));
    }
}
