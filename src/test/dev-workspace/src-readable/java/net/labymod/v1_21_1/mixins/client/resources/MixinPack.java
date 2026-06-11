package net.labymod.v1_21_1.mixins.client.resources;

import net.labymod.v1_21_1.client.resources.pack.WrappedPackResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/resources/MixinPack.class */
@Mixin({atm.class})
public class MixinPack {
    @Inject(method = {"open"}, at = {@At("RETURN")}, cancellable = true)
    private void labyMod$wrapPackResources(CallbackInfoReturnable<asq> cir) {
        cir.setReturnValue(new WrappedPackResources((asq) cir.getReturnValue()));
    }
}
