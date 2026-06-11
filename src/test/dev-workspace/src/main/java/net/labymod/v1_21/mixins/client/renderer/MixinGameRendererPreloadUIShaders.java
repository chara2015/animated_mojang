package net.labymod.v1_21.mixins.client.renderer;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/renderer/MixinGameRendererPreloadUIShaders.class */
@Mixin({ges.class})
public abstract class MixinGameRendererPreloadUIShaders {

    @Shadow
    @Nullable
    private static gfn V;

    @Shadow
    protected abstract gfn a(auh auhVar, String str, fbn fbnVar);

    @Inject(method = {"preloadUiShader"}, at = {@At("TAIL")})
    private void labyMod$preloadUiShader(auh provider, CallbackInfo ci) {
        V = a(provider, "position_color_tex_lightmap", fbg.k);
    }
}
