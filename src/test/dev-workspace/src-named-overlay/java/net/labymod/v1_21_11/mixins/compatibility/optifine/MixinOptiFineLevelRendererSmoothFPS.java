package net.labymod.v1_21_11.mixins.compatibility.optifine;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.v1_21_11.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/compatibility/optifine/MixinOptiFineLevelRendererSmoothFPS.class */
@Pseudo
@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin({LevelRenderer.class})
public class MixinOptiFineLevelRendererSmoothFPS {
    @WrapOperation(method = {"lambda$addMainPass$1(Lnet/minecraft/client/renderer/FogParameters;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/Camera;Lnet/minecraft/util/profiling/ProfilerFiller;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lnet/minecraft/client/renderer/culling/Frustum;ZLcom/mojang/blaze3d/resource/ResourceHandle;)V"}, at = {@At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glFinish()V", remap = false)})
    @Dynamic
    private void labyMod$disableSmoothFPS(Operation<Void> original) {
    }
}
