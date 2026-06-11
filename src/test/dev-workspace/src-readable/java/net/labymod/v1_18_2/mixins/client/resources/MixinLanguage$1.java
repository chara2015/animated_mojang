package net.labymod.v1_18_2.mixins.client.resources;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/resources/MixinLanguage$1.class */
@Mixin(targets = {"net.minecraft.locale.Language$1"})
public class MixinLanguage$1 {
    @Inject(method = {"getOrDefault(Ljava/lang/String;)Ljava/lang/String;"}, at = {@At("HEAD")}, cancellable = true)
    public void getOrDefault(String key, CallbackInfoReturnable<String> cir) {
        String translation = Laby.labyAPI().internationalization().getRawTranslation(key);
        if (!translation.equals(key)) {
            cir.setReturnValue(translation);
        }
    }

    @Inject(method = {"has(Ljava/lang/String;)Z"}, at = {@At("HEAD")}, cancellable = true)
    public void has(String key, CallbackInfoReturnable<Boolean> cir) {
        if (Laby.labyAPI().internationalization().has(key)) {
            cir.setReturnValue(true);
        }
    }
}
