package net.labymod.v1_12_2.mixins.client.renderer;

import net.labymod.core.event.client.render.post.PostProcessingScreenEventCaller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/MixinEntityRendererPostProcessing.class */
@Mixin({buq.class})
public class MixinEntityRendererPostProcessing {
    @Inject(method = {"renderWorldPass"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = At.Shift.BEFORE)})
    private void labyMod$fireBeforeHandProcessingScreenEvent(int lvt_1_1_, float partialTicks, long lvt_3_1_, CallbackInfo ci) {
        PostProcessingScreenEventCaller.callBeforeHand(partialTicks);
    }

    @Inject(method = {"updateCameraAndRender"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;renderEntityOutlineFramebuffer()V", shift = At.Shift.AFTER)})
    private void labyMod$fireWorldPostProcessingScreenEvent(float partialTicks, long lvt_2_1_, CallbackInfo ci) {
        PostProcessingScreenEventCaller.callWorld(partialTicks);
    }
}
