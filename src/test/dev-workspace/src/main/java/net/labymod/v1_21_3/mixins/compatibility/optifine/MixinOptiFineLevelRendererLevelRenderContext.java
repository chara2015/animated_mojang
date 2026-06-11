package net.labymod.v1_21_3.mixins.compatibility.optifine;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v1_21_3.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/compatibility/optifine/MixinOptiFineLevelRendererLevelRenderContext.class */
@Pseudo
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin(value = {glh.class}, priority = 900)
public class MixinOptiFineLevelRendererLevelRenderContext {
    @WrapOperation(method = {"lambda$addMainPass$1(Lnet/minecraft/client/renderer/FogParameters;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/Camera;Lnet/minecraft/util/profiling/ProfilerFiller;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lnet/minecraft/client/renderer/culling/Frustum;ZLcom/mojang/blaze3d/resource/ResourceHandle;Lnet/minecraft/client/renderer/LightTexture;)V"}, at = {@At(value = "NEW", target = "()Lcom/mojang/blaze3d/vertex/PoseStack;")})
    @Dynamic
    private fgs labyMod$storePoseStack(Operation<fgs> original) {
        fgs poseStack = (fgs) original.call(new Object[0]);
        MinecraftUtil.levelRenderContext().setPoseStack(poseStack);
        return poseStack;
    }
}
