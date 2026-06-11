package net.labymod.v1_20_1.mixins.client.renderer;

import net.labymod.core.event.client.render.post.PostProcessingScreenEventCaller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/renderer/MixinGameRendererPostProcessing.class */
@Mixin({fjq.class})
public class MixinGameRendererPostProcessing {
    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lorg/joml/Matrix4f;)V", shift = At.Shift.AFTER)})
    private void labyMod$fireBeforeHandPostProcessingScreenEvent(float partialTicks, long $$1, eij $$2, CallbackInfo ci) {
        PostProcessingScreenEventCaller.callBeforeHand(partialTicks);
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER)})
    private void labyMod$fireWorldPostProcessingScreenEvent(float partialTicks, long $$1, boolean $$2, CallbackInfo ci) {
        PostProcessingScreenEventCaller.callWorld(partialTicks);
    }
}
