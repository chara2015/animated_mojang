package net.labymod.v1_21.mixins.compatibility.optifine;

import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.v1_21.client.util.MinecraftUtil;
import net.labymod.v1_21.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/compatibility/optifine/MixinOptiFineLevelRendererRenderWorldEvent.class */
@DynamicMixin(value = OptiFine.NAMESPACE, applier = OptiFineDynamicMixinApplier.class)
@Mixin({gex.class})
public class MixinOptiFineLevelRendererRenderWorldEvent {

    @Shadow
    @Nullable
    private gfc Q;

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderStateShard$OutputStateShard;clearRenderState()V", ordinal = 0, shift = At.Shift.AFTER)})
    private void labyMod$fireWorldRenderEvent$transparencyChain(fgf $$0, boolean $$1, ffy $$2, ges $$3, gey $$4, Matrix4f $$5, Matrix4f $$6, CallbackInfo ci) {
        RenderWorldEventCaller.callPost(MinecraftUtil.levelRenderContext().getPoseStack().stack(), $$0.a(false));
    }

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;waterMask()Lnet/minecraft/client/renderer/RenderType;", shift = At.Shift.BEFORE)})
    @Dynamic
    private void labyMod$fireWorldRenderEvent(fgf $$0, boolean $$1, ffy $$2, ges $$3, gey $$4, Matrix4f $$5, Matrix4f $$6, CallbackInfo ci) {
        if (this.Q == null) {
            RenderWorldEventCaller.callPost(MinecraftUtil.levelRenderContext().getPoseStack().stack(), $$0.a(false));
        }
    }
}
