package net.labymod.v1_19_4.mixins.client.resources;

import net.labymod.v1_19_4.client.resources.pack.WrappedPackResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/resources/MixinPack.class */
@Mixin({akq.class})
public class MixinPack {
    @Inject(method = {"open"}, at = {@At("RETURN")}, cancellable = true)
    private void labyMod$wrapPackResources(CallbackInfoReturnable<ajv> cir) {
        cir.setReturnValue(new WrappedPackResources((ajv) cir.getReturnValue()));
    }
}
