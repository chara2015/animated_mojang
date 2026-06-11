package net.labymod.v1_12_2.mixins.client.renderer.texture;

import java.util.Arrays;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.texture.UpdateLightmapTextureEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/texture/MixinLightTexture_Fullbright.class */
@Mixin({buq.class})
public class MixinLightTexture_Fullbright {

    @Shadow
    @Final
    private cdg H;

    @Shadow
    private boolean K;
    private boolean fullbright$updated = false;

    @Inject(method = {"updateLightmap"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$updateTexture(float partialTicks, CallbackInfo ci) {
        UpdateLightmapTextureEvent event = (UpdateLightmapTextureEvent) Laby.fireEvent(new UpdateLightmapTextureEvent());
        if (event.isCancelled()) {
            if (!this.fullbright$updated) {
                Arrays.fill(this.H.e(), -1);
            }
            this.fullbright$updated = true;
            this.H.d();
            ci.cancel();
            return;
        }
        if (this.fullbright$updated) {
            this.K = true;
        }
        this.fullbright$updated = false;
    }
}
