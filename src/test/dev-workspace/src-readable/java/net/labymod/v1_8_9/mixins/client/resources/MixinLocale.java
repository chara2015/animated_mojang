package net.labymod.v1_8_9.mixins.client.resources;

import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.v1_8_9.client.resources.VersionedLocale;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/resources/MixinLocale.class */
@Mixin({bnt.class})
public abstract class MixinLocale implements VersionedLocale {

    @Shadow
    Map<String, String> a;

    @Shadow
    protected abstract String b(String str);

    @Inject(method = {"translateKeyPrivate"}, at = {@At("HEAD")}, cancellable = true)
    public void injectLabyTranslations(String translationKey, CallbackInfoReturnable<String> cir) {
        String translation = Laby.labyAPI().internationalization().getRawTranslation(translationKey);
        if (!translation.equals(translationKey)) {
            cir.setReturnValue(translation);
        }
    }

    @Override // net.labymod.v1_8_9.client.resources.VersionedLocale
    public String getTranslation(String translationKey) {
        return b(translationKey);
    }

    @Override // net.labymod.v1_8_9.client.resources.VersionedLocale
    public boolean hasTranslation(String translationKey) {
        return this.a.containsKey(translationKey) || Laby.labyAPI().internationalization().has(translationKey);
    }
}
