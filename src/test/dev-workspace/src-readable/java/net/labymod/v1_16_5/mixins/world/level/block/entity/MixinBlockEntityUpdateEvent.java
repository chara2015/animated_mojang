package net.labymod.v1_16_5.mixins.world.level.block.entity;

import javax.annotation.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.event.client.blockentity.BlockEntityPreLoadEvent;
import net.labymod.api.event.client.blockentity.BlockEntityUpdateEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.ThreadSafe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/world/level/block/entity/MixinBlockEntityUpdateEvent.class */
@Mixin({ccj.class})
public class MixinBlockEntityUpdateEvent {

    @Shadow
    @Nullable
    protected brx d;

    @Shadow
    protected fx e;

    /* JADX WARN: Multi-variable type inference failed */
    @Inject(method = {"load"}, at = {@At("HEAD")})
    private void labyMod$fireTileEntityPreLoadEvent(ceh state, md tag, CallbackInfo ci) {
        if (ThreadSafe.isRenderThread()) {
            Laby.fireEvent(new BlockEntityPreLoadEvent((BlockEntity) this, (NBTTagCompound) CastUtil.cast(tag)));
        }
    }

    @Inject(method = {"setChanged"}, at = {@At("TAIL")})
    private void labyMod$fireTileEntityUpdateEvent(CallbackInfo ci) {
        if (this.d == null) {
            return;
        }
        BlockEntity blockEntityC = this.d.c(this.e);
        if (this.d.s_() && (blockEntityC instanceof BlockEntity)) {
            Laby.fireEvent(new BlockEntityUpdateEvent(blockEntityC));
        }
    }
}
