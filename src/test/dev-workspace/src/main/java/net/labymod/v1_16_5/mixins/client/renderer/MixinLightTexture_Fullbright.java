package net.labymod.v1_16_5.mixins.client.renderer;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.texture.UpdateLightmapTextureEvent;
import net.labymod.v1_16_5.client.resources.texture.NativeImageUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/MixinLightTexture_Fullbright.class */
@Mixin({eaf.class})
public class MixinLightTexture_Fullbright {

    @Shadow
    private boolean d;

    @Shadow
    @Final
    private det b;

    @Shadow
    @Final
    private ejs a;
    private boolean labyMod$updated = false;

    @Inject(method = {"updateLightTexture"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$updateTexture(float partialTicks, CallbackInfo ci) {
        UpdateLightmapTextureEvent event = (UpdateLightmapTextureEvent) Laby.fireEvent(new UpdateLightmapTextureEvent());
        if (event.isCancelled()) {
            if (!this.labyMod$updated) {
                det image = this.b;
                NativeImageUtils.fill(image, image.a(), image.b(), -1);
            }
            this.labyMod$updated = true;
            this.a.a();
            ci.cancel();
            return;
        }
        if (this.labyMod$updated) {
            this.d = true;
        }
        this.labyMod$updated = false;
    }
}
