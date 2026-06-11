package net.labymod.v1_16_5.mixins.client;

import com.mojang.datafixers.util.Function4;
import java.util.function.Function;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/MixinMinecraftSinglePlayerLoading.class */
@Mixin({djz.class})
public class MixinMinecraftSinglePlayerLoading {
    @Inject(method = {"doLoadLevel"}, at = {@At("HEAD")})
    public void labyMod$loadSinglePlayerWorld(String param0, b param1, Function<a, brk> param2, Function4<a, b, ach, brk, cyn> param3, boolean param4, a param5, CallbackInfo ci) {
        dwt level = djz.C().r;
        if (level != null) {
            level.S();
        }
    }
}
