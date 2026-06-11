package net.labymod.v1_12_2.mixins.client.resources;

import net.labymod.v1_12_2.client.resources.VersionedI18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/resources/MixinI18n.class */
@Mixin({cey.class})
public class MixinI18n {
    @Inject(method = {"setLocale"}, at = {@At("TAIL")})
    private static void setLocale(cfb locale, CallbackInfo callback) {
        VersionedI18n.setLocale(locale);
    }
}
