package net.labymod.v1_21_1.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/MixinMinecraftSinglePlayerLoading.class */
@Mixin({fgo.class})
public class MixinMinecraftSinglePlayerLoading {
    @Inject(method = {"doWorldLoad"}, at = {@At("HEAD")})
    private void labyMod$loadSinglePlayerWorld(c $$1, atp $$2, alp $$3, boolean $$4, CallbackInfo ci) {
        fzf level = fgo.Q().r;
        if (level != null) {
            level.Y();
        }
    }
}
