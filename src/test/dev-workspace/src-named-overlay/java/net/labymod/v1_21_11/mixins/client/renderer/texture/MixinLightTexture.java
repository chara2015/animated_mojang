package net.labymod.v1_21_11.mixins.client.renderer.texture;

import net.labymod.core.client.gfx.texture.overlay.DynamicOverlayTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/texture/MixinLightTexture.class */
@Mixin(value = {LightTexture.class}, priority = 900)
public class MixinLightTexture {
    @Inject(method = {"updateLightTexture"}, at = {@At("HEAD")})
    private void labyMod$updateOverlayTexture(float partialTicks, CallbackInfo ci) {
        DynamicOverlayTexture texture = Minecraft.getInstance().gameRenderer.overlayTexture().dynamicTexture();
        texture.setColorAndUpdate();
    }
}
