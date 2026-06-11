package net.labymod.v1_20_1.mixins.client.renderer;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/renderer/MixinGameRendererPreloadUIShaders.class */
@Mixin({fjq.class})
public abstract class MixinGameRendererPreloadUIShaders {

    @Shadow
    @Nullable
    private static fki W;

    @Shadow
    protected abstract fki a(ala alaVar, String str, eio eioVar);

    @Inject(method = {"preloadUiShader"}, at = {@At("TAIL")})
    private void labyMod$preloadUiShader(ala provider, CallbackInfo ci) {
        W = a(provider, "position_color_tex_lightmap", eih.t);
    }
}
