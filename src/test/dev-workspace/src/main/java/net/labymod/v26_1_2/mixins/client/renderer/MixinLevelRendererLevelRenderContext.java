package net.labymod.v26_1_2.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.v26_1_2.client.util.MinecraftUtil;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/renderer/MixinLevelRendererLevelRenderContext.class */
@Mixin(value = {LevelRenderer.class}, priority = 900)
public class MixinLevelRendererLevelRenderContext {
    @WrapOperation(method = {"lambda$addMainPass$0"}, at = {@At(value = "NEW", target = "()Lcom/mojang/blaze3d/vertex/PoseStack;")})
    private PoseStack labyMod$storePoseStack(Operation<PoseStack> original) {
        PoseStack poseStack = (PoseStack) original.call(new Object[0]);
        MinecraftUtil.levelRenderContext().setPoseStack(poseStack);
        return poseStack;
    }
}
