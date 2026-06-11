package net.labymod.v1_12_2.mixins.client.entity;

import net.labymod.core.util.LegacyEntityTypeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/entity/MixinEntityList.class */
@Mixin({vi.class})
public class MixinEntityList {
    @Inject(method = {"init"}, at = {@At("HEAD")})
    private static void labyMod$registerPlayer(CallbackInfo ci) {
        LegacyEntityTypeRegistry.register(aed.class, "player");
        LegacyEntityTypeRegistry.register(bud.class, "player");
        LegacyEntityTypeRegistry.register(oq.class, "player");
        LegacyEntityTypeRegistry.register(bue.class, "player");
    }
}
