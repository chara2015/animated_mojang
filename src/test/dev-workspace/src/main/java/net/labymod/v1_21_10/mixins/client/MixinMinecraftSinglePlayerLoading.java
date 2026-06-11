package net.labymod.v1_21_10.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/MixinMinecraftSinglePlayerLoading.class */
@Mixin({fzz.class})
public class MixinMinecraftSinglePlayerLoading {
    @Inject(method = {"doWorldLoad"}, at = {@At("HEAD")})
    private void labyMod$loadSinglePlayerWorld(c $$1, baa $$2, ani $$3, boolean $$4, CallbackInfo ci) {
        gzn level = fzz.W().r;
        if (level != null) {
            level.a(gzn.a);
        }
    }
}
