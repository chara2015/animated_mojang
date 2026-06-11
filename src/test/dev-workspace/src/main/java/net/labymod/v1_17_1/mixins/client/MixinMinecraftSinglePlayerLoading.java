package net.labymod.v1_17_1.mixins.client;

import com.mojang.datafixers.util.Function4;
import java.util.function.Function;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/MixinMinecraftSinglePlayerLoading.class */
@Mixin({dvp.class})
public class MixinMinecraftSinglePlayerLoading {
    @Inject(method = {"doLoadLevel"}, at = {@At("HEAD")})
    public void labyMod$loadSinglePlayerWorld(String param0, b param1, Function<a, bwd> param2, Function4<a, b, adt, bwd, dii> param3, boolean param4, b param5, CallbackInfo ci) {
        eji level = dvp.C().r;
        if (level != null) {
            level.U();
        }
    }
}
