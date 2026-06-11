package net.labymod.v1_20_1.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/MixinMinecraftSinglePlayerLoading.class */
@Mixin({enn.class})
public class MixinMinecraftSinglePlayerLoading {
    @Inject(method = {"doWorldLoad"}, at = {@At("HEAD")})
    private void labyMod$loadSinglePlayerWorld(String $$0, c $$1, aki $$2, adk $$3, boolean $$4, CallbackInfo ci) {
        few level = enn.N().s;
        if (level != null) {
            level.U();
        }
    }
}
