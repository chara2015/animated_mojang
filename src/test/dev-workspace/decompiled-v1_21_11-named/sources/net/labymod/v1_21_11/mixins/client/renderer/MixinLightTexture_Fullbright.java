package net.labymod.v1_21_11.mixins.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.texture.UpdateLightmapTextureEvent;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/MixinLightTexture_Fullbright.class */
@Mixin({LightTexture.class})
public class MixinLightTexture_Fullbright {

    @Shadow
    @Final
    private GpuTexture f;

    @Inject(method = {"updateLightTexture"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$updateTexture(float partialTicks, CallbackInfo ci) {
        UpdateLightmapTextureEvent event = Laby.fireEvent(new UpdateLightmapTextureEvent());
        if (event.isCancelled()) {
            RenderSystem.getDevice().createCommandEncoder().clearColorTexture(this.f, -1);
            ci.cancel();
        }
    }
}
