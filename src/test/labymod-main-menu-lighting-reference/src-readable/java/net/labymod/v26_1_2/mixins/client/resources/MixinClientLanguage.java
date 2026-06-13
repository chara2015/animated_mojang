package net.labymod.v26_1_2.mixins.client.resources;

import net.labymod.api.Laby;
import net.minecraft.client.resources.language.ClientLanguage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/resources/MixinClientLanguage.class */
@Mixin({ClientLanguage.class})
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
