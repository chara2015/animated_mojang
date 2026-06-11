package net.labymod.v1_20_4.mixins.client.renderer;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/renderer/MixinGameRendererPreloadUIShaders.class */
@Mixin({fta.class})
public abstract class MixinGameRendererPreloadUIShaders {

    @Shadow
    @Nullable
    private static ftv X;

    @Shadow
    protected abstract ftv a(aql aqlVar, String str, eqg eqgVar);

    @Inject(method = {"preloadUiShader"}, at = {@At("TAIL")})
    private void labyMod$preloadUiShader(aql provider, CallbackInfo ci) {
        X = a(provider, "position_color_tex_lightmap", epz.t);
    }
}
