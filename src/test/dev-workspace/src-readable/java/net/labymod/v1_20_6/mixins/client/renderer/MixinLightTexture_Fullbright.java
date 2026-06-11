package net.labymod.v1_20_6.mixins.client.renderer;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.texture.UpdateLightmapTextureEvent;
import net.labymod.v1_20_6.client.resources.texture.NativeImageUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/renderer/MixinLightTexture_Fullbright.class */
@Mixin({gdp.class})
public class MixinLightTexture_Fullbright {

    @Shadow
    private boolean g;

    @Shadow
    @Final
    private ezb e;

    @Shadow
    @Final
    private goo d;
    private boolean labyMod$updated = false;

    @Inject(method = {"updateLightTexture"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$updateTexture(float partialTicks, CallbackInfo ci) {
        UpdateLightmapTextureEvent event = (UpdateLightmapTextureEvent) Laby.fireEvent(new UpdateLightmapTextureEvent());
        if (event.isCancelled()) {
            if (!this.labyMod$updated) {
                ezb image = this.e;
                NativeImageUtils.fill(image, image.a(), image.b(), -1);
            }
            this.labyMod$updated = true;
            this.d.d();
            ci.cancel();
            return;
        }
        if (this.labyMod$updated) {
            this.g = true;
        }
        this.labyMod$updated = false;
    }
}
