package net.labymod.v1_18_2.mixins.client;

import java.util.function.Function;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/MixinMinecraftSinglePlayerLoading.class */
@Mixin({dyr.class})
public class MixinMinecraftSinglePlayerLoading {
    @Inject(method = {"doLoadLevel"}, at = {@At("HEAD")})
    public void labyMod$loadSinglePlayerWorld(String $$0, Function<a, a> $$1, Function<a, c> $$2, boolean $$3, b $$4, CallbackInfo ci) {
        ems level = dyr.D().r;
        if (level != null) {
            level.T();
        }
    }
}
