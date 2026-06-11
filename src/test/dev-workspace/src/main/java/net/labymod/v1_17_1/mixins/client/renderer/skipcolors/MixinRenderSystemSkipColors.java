package net.labymod.v1_17_1.mixins.client.renderer.skipcolors;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/renderer/skipcolors/MixinRenderSystemSkipColors.class */
@Mixin({RenderSystem.class})
public class MixinRenderSystemSkipColors {
    @Inject(method = {"setShaderColor"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$skipColors(float $$0, float $$1, float $$2, float $$3, CallbackInfo ci) {
        if (GlColorAlphaModifier.isModifiedAlpha()) {
            ci.cancel();
        }
    }
}
