package net.labymod.v1_8_9.mixins.client.entity;

import net.labymod.core.util.LegacyEntityTypeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/entity/MixinEntityList.class */
@Mixin({pm.class})
public class MixinEntityList {
    @Inject(method = {"<clinit>"}, at = {@At("HEAD")})
    private static void labyMod$registerPlayer(CallbackInfo ci) {
        LegacyEntityTypeRegistry.register(wn.class, "player");
        LegacyEntityTypeRegistry.register(bew.class, "player");
        LegacyEntityTypeRegistry.register(lf.class, "player");
        LegacyEntityTypeRegistry.register(bex.class, "player");
    }
}
