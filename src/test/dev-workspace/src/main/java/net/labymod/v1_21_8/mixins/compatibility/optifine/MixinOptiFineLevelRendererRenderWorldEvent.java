package net.labymod.v1_21_8.mixins.compatibility.optifine;

import net.labymod.api.Laby;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.v1_21_8.client.util.MinecraftUtil;
import net.labymod.v1_21_8.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/compatibility/optifine/MixinOptiFineLevelRendererRenderWorldEvent.class */
@DynamicMixin(value = OptiFine.NAMESPACE, applier = OptiFineDynamicMixinApplier.class)
@Mixin({gxh.class})
public class MixinOptiFineLevelRendererRenderWorldEvent {
    @Inject(method = {"lambda$addMainPass$2(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/Camera;Lnet/minecraft/util/profiling/ProfilerFiller;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lnet/minecraft/client/renderer/culling/Frustum;ZLcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;)V"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/renderer/chunk/ChunkSectionLayerGroup;TRIPWIRE:Lnet/minecraft/client/renderer/chunk/ChunkSectionLayerGroup;", shift = At.Shift.AFTER)})
    private void labyMod$fireWorldRenderEvent$transparencyChain(CallbackInfo ci) {
        RenderWorldEventCaller.callPost(MinecraftUtil.levelRenderContext().getPoseStack().stack(), Laby.labyAPI().minecraft().getPartialTicks());
    }
}
