package net.labymod.v1_8_9.mixins.tileentity;

import net.labymod.api.Laby;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.event.client.blockentity.BlockEntityPreLoadEvent;
import net.labymod.api.event.client.blockentity.BlockEntityUpdateEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.util.ThreadSafe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/tileentity/MixinTileEntityUpdateEvent.class */
@Mixin({akw.class})
public class MixinTileEntityUpdateEvent {
    /* JADX WARN: Multi-variable type inference failed */
    @Inject(method = {"readFromNBT"}, at = {@At("HEAD")})
    private void labyMod$fireTileEntityPreLoadEvent(dn tag, CallbackInfo ci) {
        if (ThreadSafe.isRenderThread()) {
            Laby.fireEvent(new BlockEntityPreLoadEvent((BlockEntity) this, (NBTTagCompound) tag));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Inject(method = {"markDirty"}, at = {@At("TAIL")})
    private void labyMod$fireTileEntityUpdateEvent(CallbackInfo ci) {
        if (ThreadSafe.isRenderThread()) {
            Laby.fireEvent(new BlockEntityUpdateEvent((BlockEntity) this));
        }
    }
}
