package net.labymod.v1_8_9.mixins.util;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/util/MixinStringTranslate.class */
@Mixin({dj.class})
public abstract class MixinStringTranslate {
    @Inject(method = {"tryTranslateKey"}, at = {@At("HEAD")}, cancellable = true)
    public void injectLabyTranslations(String translationKey, CallbackInfoReturnable<String> cir) {
        if (Laby.labyAPI() == null || Laby.labyAPI().internationalization() == null) {
            return;
        }
        String translation = Laby.labyAPI().internationalization().getRawTranslation(translationKey);
        if (!translation.equals(translationKey)) {
            cir.setReturnValue(translation);
        }
    }

    @Inject(method = {"isKeyTranslated"}, at = {@At("HEAD")}, cancellable = true)
    public void has(String key, CallbackInfoReturnable<Boolean> cir) {
        if (Laby.labyAPI() != null && Laby.labyAPI().internationalization() != null && Laby.labyAPI().internationalization().has(key)) {
            cir.setReturnValue(true);
        }
    }
}
