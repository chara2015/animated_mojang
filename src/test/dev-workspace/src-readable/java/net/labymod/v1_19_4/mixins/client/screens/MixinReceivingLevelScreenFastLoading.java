package net.labymod.v1_19_4.mixins.client.screens;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/screens/MixinReceivingLevelScreenFastLoading.class */
@Mixin({etc.class})
public class MixinReceivingLevelScreenFastLoading {

    @Shadow
    private boolean o;

    @Inject(method = {"loadingPacketsReceived"}, at = {@At("HEAD")})
    private void labyMod$fastLoading(CallbackInfo ci) {
        this.o = true;
    }
}
