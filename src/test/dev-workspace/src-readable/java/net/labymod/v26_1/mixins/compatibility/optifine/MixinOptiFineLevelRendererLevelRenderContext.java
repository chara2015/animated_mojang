package net.labymod.v26_1.mixins.compatibility.optifine;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v26_1.client.util.MinecraftUtil;
import net.labymod.v26_1.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/compatibility/optifine/MixinOptiFineLevelRendererLevelRenderContext.class */
@DynamicMixin(value = OptiFine.NAMESPACE, applier = OptiFineDynamicMixinApplier.class)
@Mixin(value = {LevelRenderer.class}, priority = 900)
public class MixinOptiFineLevelRendererLevelRenderContext {
    @WrapOperation(method = {"lambda$addMainPass$1(Lnet/minecraft/client/renderer/FogParameters;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/Camera;Lnet/minecraft/util/profiling/ProfilerFiller;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;ZLnet/minecraft/client/renderer/culling/Frustum;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;)V"}, at = {@At(value = "NEW", target = "()Lcom/mojang/blaze3d/vertex/PoseStack;")})
    private PoseStack labyMod$storePoseStack(Operation<PoseStack> original) {
        PoseStack poseStack = (PoseStack) original.call(new Object[0]);
        MinecraftUtil.levelRenderContext().setPoseStack(poseStack);
        return poseStack;
    }
}
