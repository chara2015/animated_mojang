package net.labymod.v26_1_2.mixins.compatibility.optifine;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v26_1_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/compatibility/optifine/MixinOptiFineShadersRender.class */
@Pseudo
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin(targets = {"net.optifine.shaders.ShadersRender"})
public class MixinOptiFineShadersRender {
    @WrapOperation(method = {"renderShadowMap"}, at = {@At(value = "NEW", target = "()Lcom/mojang/blaze3d/vertex/PoseStack;")})
    private static PoseStack labyMod$store(Operation<PoseStack> original) {
        PoseStack pose = (PoseStack) original.call(new Object[0]);
        MinecraftUtil.levelRenderContext().setPoseStack(pose);
        return pose;
    }
}
