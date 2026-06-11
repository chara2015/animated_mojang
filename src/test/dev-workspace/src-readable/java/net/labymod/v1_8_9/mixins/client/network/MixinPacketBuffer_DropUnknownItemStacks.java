package net.labymod.v1_8_9.mixins.client.network;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/network/MixinPacketBuffer_DropUnknownItemStacks.class */
@Mixin({em.class})
public class MixinPacketBuffer_DropUnknownItemStacks {
    @Inject(method = {"readItemStackFromBuffer"}, at = {@At("RETURN")}, cancellable = true)
    private void labyMod$dropNullItemStacks(CallbackInfoReturnable<zx> cir) {
        zx stack = (zx) cir.getReturnValue();
        if (stack != null && stack.b() == null) {
            cir.setReturnValue((Object) null);
        }
    }
}
