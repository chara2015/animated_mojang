package net.labymod.v1_21_4.mixins.client.renderer;

import java.io.IOException;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/renderer/MixinGameRendererPreloadUIShaders.class */
@Mixin({glq.class})
public abstract class MixinGameRendererPreloadUIShaders {

    @Shadow
    @Final
    private flk i;

    @Inject(method = {"preloadUiShader"}, at = {@At("TAIL")})
    private void labyMod$preloadUiShader(aus provider, CallbackInfo ci) {
        try {
            this.i.ab().a(provider, new gmr[]{glk.g});
        } catch (b | IOException e) {
            throw new RuntimeException("Could not preload shaders for loading UI (labymod)", e);
        }
    }
}
