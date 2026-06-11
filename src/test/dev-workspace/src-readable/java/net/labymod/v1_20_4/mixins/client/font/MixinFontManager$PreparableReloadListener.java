package net.labymod.v1_20_4.mixins.client.font;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/font/MixinFontManager$PreparableReloadListener.class */
@Mixin({ezu.class})
public class MixinFontManager$PreparableReloadListener {
    @Inject(method = {"apply"}, at = {@At("TAIL")})
    private void labyMod$onFontApply(d $$0, bgs $$1, CallbackInfo ci) {
        Laby.labyAPI().renderPipeline().componentRenderer().invalidate();
    }
}
