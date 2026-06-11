package net.labymod.v1_20_4.mixins.compatibility.optifine;

import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.v1_20_4.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/compatibility/optifine/MixinOptiFineLevelRendererRenderWorldEvent.class */
@DynamicMixin(value = OptiFine.NAMESPACE, applier = OptiFineDynamicMixinApplier.class)
@Mixin({ftf.class})
public class MixinOptiFineLevelRendererRenderWorldEvent {

    @Shadow
    @Nullable
    private ftk Q;

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderStateShard$OutputStateShard;clearRenderState()V", ordinal = 0, shift = At.Shift.AFTER)})
    private void labyMod$fireWorldRenderEvent$transparencyChain(eqb poseStack, float partialTicks, long lvt_3_1_, boolean lvt_5_1_, eut lvt_6_1_, fta lvt_7_1_, ftg lvt_8_1_, Matrix4f lvt_9_1_, CallbackInfo ci) {
        RenderWorldEventCaller.callPost(((VanillaStackAccessor) poseStack).stack(), partialTicks);
    }

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;waterMask()Lnet/minecraft/client/renderer/RenderType;", shift = At.Shift.BEFORE)})
    @Dynamic
    private void labyMod$fireWorldRenderEvent(eqb poseStack, float partialTicks, long lvt_3_1_, boolean lvt_5_1_, eut lvt_6_1_, fta lvt_7_1_, ftg lvt_8_1_, Matrix4f lvt_9_1_, CallbackInfo ci) {
        if (this.Q == null) {
            RenderWorldEventCaller.callPost(((VanillaStackAccessor) poseStack).stack(), partialTicks);
        }
    }
}
