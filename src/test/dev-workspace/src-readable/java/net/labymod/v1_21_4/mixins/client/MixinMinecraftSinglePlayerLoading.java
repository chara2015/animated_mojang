package net.labymod.v1_21_4.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/MixinMinecraftSinglePlayerLoading.class */
@Mixin({flk.class})
public class MixinMinecraftSinglePlayerLoading {
    @Inject(method = {"doWorldLoad"}, at = {@At("HEAD")})
    private void labyMod$loadSinglePlayerWorld(c $$1, aua $$2, alu $$3, boolean $$4, CallbackInfo ci) {
        gga level = flk.Q().s;
        if (level != null) {
            level.ac();
        }
    }
}
