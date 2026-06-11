package net.labymod.v1_20_6.mixins.client.renderer;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/renderer/MixinGameRendererPreloadUIShaders.class */
@Mixin({gdj.class})
public abstract class MixinGameRendererPreloadUIShaders {

    @Shadow
    @Nullable
    private static gee W;

    @Shadow
    protected abstract gee a(aus ausVar, String str, faf fafVar);

    @Inject(method = {"preloadUiShader"}, at = {@At("TAIL")})
    private void labyMod$preloadUiShader(aus provider, CallbackInfo ci) {
        W = a(provider, "position_color_tex_lightmap", ezy.t);
    }
}
