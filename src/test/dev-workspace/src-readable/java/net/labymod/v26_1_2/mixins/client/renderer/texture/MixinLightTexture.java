package net.labymod.v26_1_2.mixins.client.renderer.texture;

import net.labymod.core.client.gfx.texture.overlay.DynamicOverlayTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Lightmap;
import net.minecraft.client.renderer.state.LightmapRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/renderer/texture/MixinLightTexture.class */
@Mixin(value = {Lightmap.class}, priority = 900)
public class MixinLightTexture {
    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$updateOverlayTexture(LightmapRenderState renderState, CallbackInfo ci) {
        DynamicOverlayTexture texture = Minecraft.getInstance().gameRenderer.overlayTexture().dynamicTexture();
        texture.setColorAndUpdate();
    }
}
