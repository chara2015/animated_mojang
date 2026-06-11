package net.labymod.v1_21.mixins.client.resources;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/resources/MixinClientLanguage.class */
@Mixin({grp.class})
public class MixinClientLanguage {
    @Inject(method = {"getOrDefault"}, at = {@At("HEAD")}, cancellable = true)
    public void getOrDefault(String key, String fallback, CallbackInfoReturnable<String> cir) {
        String translation = Laby.labyAPI().internationalization().getRawTranslation(key);
        if (!translation.equals(key)) {
            cir.setReturnValue(translation);
        }
    }

    @Inject(method = {"has"}, at = {@At("HEAD")}, cancellable = true)
    public void has(String key, CallbackInfoReturnable<Boolean> cir) {
        if (Laby.labyAPI().internationalization().has(key)) {
            cir.setReturnValue(true);
        }
    }
}
