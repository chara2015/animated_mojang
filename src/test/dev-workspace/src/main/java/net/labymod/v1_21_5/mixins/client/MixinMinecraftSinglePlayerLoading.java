package net.labymod.v1_21_5.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/MixinMinecraftSinglePlayerLoading.class */
@Mixin({fqq.class})
public class MixinMinecraftSinglePlayerLoading {
    @Inject(method = {"doWorldLoad"}, at = {@At("HEAD")})
    private void labyMod$loadSinglePlayerWorld(c $$1, auz $$2, amq $$3, boolean $$4, CallbackInfo ci) {
        glo level = fqq.Q().s;
        if (level != null) {
            level.ad();
        }
    }
}
