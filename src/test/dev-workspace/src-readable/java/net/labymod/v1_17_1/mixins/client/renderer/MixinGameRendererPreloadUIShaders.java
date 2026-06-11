package net.labymod.v1_17_1.mixins.client.renderer;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/renderer/MixinGameRendererPreloadUIShaders.class */
@Mixin({enb.class})
public abstract class MixinGameRendererPreloadUIShaders {

    @Shadow
    @Nullable
    private static ent X;

    @Shadow
    protected abstract ent a(adv advVar, String str, dqq dqqVar);

    @Inject(method = {"preloadUiShader"}, at = {@At("TAIL")})
    private void labyMod$preloadUiShader(adv provider, CallbackInfo ci) {
        X = a(provider, "position_color_tex_lightmap", dqj.t);
    }
}
