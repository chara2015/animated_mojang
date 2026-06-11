package net.labymod.v1_21_8.mixins.world.level.block.entity;

import net.labymod.api.Laby;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.event.client.blockentity.BlockEntityPreLoadEvent;
import net.labymod.api.event.client.blockentity.BlockEntityUpdateEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.ThreadSafe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/world/level/block/entity/MixinBlockEntityUpdateEvent.class */
@Mixin({eaz.class})
public class MixinBlockEntityUpdateEvent {
    /* JADX WARN: Multi-variable type inference failed */
    @Inject(method = {"loadAdditional"}, at = {@At("HEAD")})
    private void labyMod$fireTileEntityPreLoadEvent(fda valueInput, CallbackInfo ci) {
        if (ThreadSafe.isRenderThread() && (valueInput instanceof fcy)) {
            ui tag = ((fcy) valueInput).tag();
            Laby.fireEvent(new BlockEntityPreLoadEvent((BlockEntity) this, (NBTTagCompound) CastUtil.cast(tag)));
        }
    }

    @Inject(method = {"setChanged(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"}, at = {@At("TAIL")})
    private static void labyMod$fireTileEntityUpdateEvent(dmu level, jb pos, eeb state, CallbackInfo ci) {
        BlockEntity blockEntityC_ = level.c_(pos);
        if (level.B_() && (blockEntityC_ instanceof BlockEntity)) {
            Laby.fireEvent(new BlockEntityUpdateEvent(blockEntityC_));
        }
    }
}
