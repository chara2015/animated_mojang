package net.labymod.v1_21_3.mixins.client.renderer;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.texture.UpdateLightmapTextureEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/renderer/MixinLightTexture_Fullbright.class */
@Mixin({glj.class})
public class MixinLightTexture_Fullbright {

    @Shadow
    @Final
    private ffd e;

    @Inject(method = {"updateLightTexture"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$updateTexture(float partialTicks, CallbackInfo ci) {
        UpdateLightmapTextureEvent event = (UpdateLightmapTextureEvent) Laby.fireEvent(new UpdateLightmapTextureEvent());
        if (event.isCancelled()) {
            this.e.f();
            ci.cancel();
        }
    }
}
