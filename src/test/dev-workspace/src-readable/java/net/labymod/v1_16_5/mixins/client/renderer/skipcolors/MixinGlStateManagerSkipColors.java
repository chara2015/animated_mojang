package net.labymod.v1_16_5.mixins.client.renderer.skipcolors;

import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/skipcolors/MixinGlStateManagerSkipColors.class */
@Mixin({dem.class})
public class MixinGlStateManagerSkipColors {
    @Inject(method = {"_color4f"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$skipColors(float lvt_0_1_, float lvt_1_1_, float lvt_2_1_, float lvt_3_1_, CallbackInfo ci) {
        if (GlColorAlphaModifier.isModifiedAlpha()) {
            ci.cancel();
        }
    }
}
