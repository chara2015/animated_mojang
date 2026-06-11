package net.labymod.v26_2_snapshot_8.mixins.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.texture.UpdateLightmapTextureEvent;
import net.minecraft.client.renderer.Lightmap;
import net.minecraft.client.renderer.state.LightmapRenderState;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/renderer/MixinLightTexture_Fullbright.class */
@Mixin({Lightmap.class})
public class MixinLightTexture_Fullbright {

    @Shadow
    @Final
    private GpuTexture texture;

    @Inject(method = {"render"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$updateTexture(LightmapRenderState renderState, CallbackInfo ci) {
        UpdateLightmapTextureEvent event = (UpdateLightmapTextureEvent) Laby.fireEvent(new UpdateLightmapTextureEvent());
        if (event.isCancelled()) {
            RenderSystem.getDevice().createCommandEncoder().clearColorTexture(this.texture, new Vector4f(1.0f));
            ci.cancel();
        }
    }
}
