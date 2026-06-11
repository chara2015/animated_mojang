package net.labymod.v1_21_1.mixins.client.renderer;

import net.labymod.core.event.client.render.post.PostProcessingScreenEventCaller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/renderer/MixinGameRendererPostProcessing.class */
@Mixin({ges.class})
public class MixinGameRendererPostProcessing {
    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V", shift = At.Shift.AFTER)})
    private void labyMod$fireBeforeHandPostProcessingScreenEvent(fgf tracker, CallbackInfo ci) {
        PostProcessingScreenEventCaller.callBeforeHand(tracker.a(false));
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER)})
    private void labyMod$fireWorldPostProcessingScreenEvent(fgf tracker, boolean $$1, CallbackInfo ci) {
        PostProcessingScreenEventCaller.callWorld(tracker.a(false));
    }
}
