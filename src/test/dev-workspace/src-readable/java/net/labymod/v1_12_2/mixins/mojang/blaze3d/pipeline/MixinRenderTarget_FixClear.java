package net.labymod.v1_12_2.mixins.mojang.blaze3d.pipeline;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/mojang/blaze3d/pipeline/MixinRenderTarget_FixClear.class */
@Mixin({bvd.class})
public class MixinRenderTarget_FixClear {
    @Inject(method = {"framebufferClear"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;clearColor(FFFF)V", shift = At.Shift.BEFORE)})
    private void labyMod$enableColorMask(CallbackInfo ci) {
        bus.a(true, true, true, true);
    }

    @Inject(method = {"framebufferClear"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;clearDepth(D)V", shift = At.Shift.BEFORE)})
    private void labyMod$enableDepthWrite(CallbackInfo ci) {
        bus.a(true);
    }
}
