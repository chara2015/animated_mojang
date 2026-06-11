package net.labymod.v1_21_10.mixins.client.renderer.texture;

import net.labymod.core.client.gfx.texture.overlay.DynamicOverlayTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/texture/MixinLightTexture.class */
@Mixin(value = {hfs.class}, priority = 900)
public class MixinLightTexture {
    @Inject(method = {"updateLightTexture"}, at = {@At("HEAD")})
    private void labyMod$updateOverlayTexture(float partialTicks, CallbackInfo ci) {
        DynamicOverlayTexture texture = fzz.W().i.q().dynamicTexture();
        texture.setColorAndUpdate();
    }
}
