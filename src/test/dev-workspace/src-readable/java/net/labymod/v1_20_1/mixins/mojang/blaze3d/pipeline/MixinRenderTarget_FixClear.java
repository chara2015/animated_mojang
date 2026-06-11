package net.labymod.v1_20_1.mixins.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.platform.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/mojang/blaze3d/pipeline/MixinRenderTarget_FixClear.class */
@Mixin({egv.class})
public class MixinRenderTarget_FixClear {
    @Inject(method = {"clear"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_clearColor(FFFF)V", shift = At.Shift.BEFORE)})
    private void labyMod$enableColorMask(CallbackInfo ci) {
        GlStateManager._colorMask(true, true, true, true);
    }

    @Inject(method = {"clear"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_clearDepth(D)V", shift = At.Shift.BEFORE)})
    private void labyMod$enableDepthWrite(CallbackInfo ci) {
        GlStateManager._depthMask(true);
    }
}
