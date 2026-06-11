package net.labymod.v1_16_5.mixins.client.renderer;

import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/MixinLevelRendererRenderWorldEvent.class */
@Mixin({eae.class})
public class MixinLevelRendererRenderWorldEvent {

    @Shadow
    @Nullable
    private eaj K;

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderStateShard$OutputStateShard;clearRenderState()V", ordinal = 0, shift = At.Shift.AFTER)})
    private void labyMod$fireWorldRenderEvent$transparencyChain(dfm poseStack, float partialTicks, long lvt_3_1_, boolean lvt_5_1_, djk lvt_6_1_, dzz lvt_7_1_, eaf lvt_8_1_, b lvt_9_1_, CallbackInfo ci) {
        RenderWorldEventCaller.callPost(((VanillaStackAccessor) poseStack).stack(), partialTicks);
    }

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;waterMask()Lnet/minecraft/client/renderer/RenderType;", shift = At.Shift.BEFORE)})
    private void labyMod$fireWorldRenderEvent(dfm poseStack, float partialTicks, long lvt_3_1_, boolean lvt_5_1_, djk lvt_6_1_, dzz lvt_7_1_, eaf lvt_8_1_, b lvt_9_1_, CallbackInfo ci) {
        if (this.K == null) {
            RenderWorldEventCaller.callPost(((VanillaStackAccessor) poseStack).stack(), partialTicks);
        }
    }
}
