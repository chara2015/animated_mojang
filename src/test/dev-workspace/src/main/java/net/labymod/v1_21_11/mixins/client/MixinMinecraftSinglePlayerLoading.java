package net.labymod.v1_21_11.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/MixinMinecraftSinglePlayerLoading.class */
@Mixin({gfj.class})
public class MixinMinecraftSinglePlayerLoading {
    @Inject(method = {"doWorldLoad"}, at = {@At("HEAD")})
    private void labyMod$loadSinglePlayerWorld(c $$1, bak $$2, ans $$3, boolean $$4, CallbackInfo ci) {
        hif level = gfj.V().r;
        if (level != null) {
            level.a(hif.a);
        }
    }
}
