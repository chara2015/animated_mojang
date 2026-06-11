package net.labymod.v1_19_4.mixins.client.renderer;

import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/renderer/MixinLevelRendererRenderWorldEvent.class */
@Mixin({fie.class})
public class MixinLevelRendererRenderWorldEvent {

    @Shadow
    @Nullable
    private fij V;

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderStateShard$OutputStateShard;clearRenderState()V", ordinal = 0, shift = At.Shift.AFTER)})
    private void labyMod$fireWorldRenderEvent$transparencyChain(ehe poseStack, float partialTicks, long lvt_3_1_, boolean lvt_5_1_, elt lvt_6_1_, fhz lvt_7_1_, fif lvt_8_1_, Matrix4f lvt_9_1_, CallbackInfo ci) {
        RenderWorldEventCaller.callPost(((VanillaStackAccessor) poseStack).stack(), partialTicks);
    }

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;waterMask()Lnet/minecraft/client/renderer/RenderType;", shift = At.Shift.BEFORE)})
    private void labyMod$fireWorldRenderEvent(ehe poseStack, float partialTicks, long lvt_3_1_, boolean lvt_5_1_, elt lvt_6_1_, fhz lvt_7_1_, fif lvt_8_1_, Matrix4f lvt_9_1_, CallbackInfo ci) {
        if (this.V == null) {
            RenderWorldEventCaller.callPost(((VanillaStackAccessor) poseStack).stack(), partialTicks);
        }
    }
}
