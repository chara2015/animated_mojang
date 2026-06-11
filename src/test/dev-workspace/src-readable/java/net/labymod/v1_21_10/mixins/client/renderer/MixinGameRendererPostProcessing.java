package net.labymod.v1_21_10.mixins.client.renderer;

import net.labymod.core.event.client.render.post.PostProcessingScreenEventCaller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/MixinGameRendererPostProcessing.class */
@Mixin({hfk.class})
public class MixinGameRendererPostProcessing {
    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/Camera;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;Z)V", shift = At.Shift.AFTER)})
    private void labyMod$fireBeforeHandPostProcessingScreenEvent(fzp tracker, CallbackInfo ci) {
        PostProcessingScreenEventCaller.callBeforeHand(tracker.a(false));
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER)})
    private void labyMod$fireWorldPostProcessingScreenEvent(fzp tracker, boolean $$1, CallbackInfo ci) {
        PostProcessingScreenEventCaller.callWorld(tracker.a(false));
    }
}
