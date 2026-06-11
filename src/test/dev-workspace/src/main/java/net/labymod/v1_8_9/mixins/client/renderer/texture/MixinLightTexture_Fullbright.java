package net.labymod.v1_8_9.mixins.client.renderer.texture;

import java.util.Arrays;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.texture.UpdateLightmapTextureEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/texture/MixinLightTexture_Fullbright.class */
@Mixin({bfk.class})
public class MixinLightTexture_Fullbright {

    @Shadow
    @Final
    private blz G;

    @Shadow
    private boolean J;
    private boolean fullbright$updated = false;

    @Inject(method = {"updateLightmap"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$updateTexture(float partialTicks, CallbackInfo ci) {
        UpdateLightmapTextureEvent event = (UpdateLightmapTextureEvent) Laby.fireEvent(new UpdateLightmapTextureEvent());
        if (event.isCancelled()) {
            if (!this.fullbright$updated) {
                Arrays.fill(this.G.e(), -1);
            }
            this.fullbright$updated = true;
            this.G.d();
            ci.cancel();
            return;
        }
        if (this.fullbright$updated) {
            this.J = true;
        }
        this.fullbright$updated = false;
    }
}
