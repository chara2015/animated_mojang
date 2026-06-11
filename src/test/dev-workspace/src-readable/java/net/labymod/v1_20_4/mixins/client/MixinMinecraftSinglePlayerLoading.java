package net.labymod.v1_20_4.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/MixinMinecraftSinglePlayerLoading.class */
@Mixin({evi.class})
public class MixinMinecraftSinglePlayerLoading {
    @Inject(method = {"doWorldLoad"}, at = {@At("HEAD")})
    private void labyMod$loadSinglePlayerWorld(c $$1, apt $$2, aic $$3, boolean $$4, CallbackInfo ci) {
        fns level = evi.O().r;
        if (level != null) {
            level.W();
        }
    }
}
