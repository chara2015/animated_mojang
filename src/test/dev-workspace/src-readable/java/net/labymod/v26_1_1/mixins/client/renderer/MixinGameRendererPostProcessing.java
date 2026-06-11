package net.labymod.v26_1_1.mixins.client.renderer;

import net.labymod.core.event.client.render.post.PostProcessingScreenEventCaller;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/renderer/MixinGameRendererPostProcessing.class */
@Mixin({GameRenderer.class})
public class MixinGameRendererPostProcessing {
    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/renderer/state/level/CameraRenderState;Lorg/joml/Matrix4fc;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;ZLnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;)V", shift = At.Shift.AFTER)})
    private void labyMod$fireBeforeHandPostProcessingScreenEvent(DeltaTracker tracker, CallbackInfo ci) {
        PostProcessingScreenEventCaller.callBeforeHand(tracker.getGameTimeDeltaPartialTick(false));
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER)})
    private void labyMod$fireWorldPostProcessingScreenEvent(DeltaTracker tracker, boolean $$1, CallbackInfo ci) {
        PostProcessingScreenEventCaller.callWorld(tracker.getGameTimeDeltaPartialTick(false));
    }
}
