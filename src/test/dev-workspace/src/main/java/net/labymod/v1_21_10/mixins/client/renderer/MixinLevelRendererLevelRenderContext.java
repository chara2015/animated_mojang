package net.labymod.v1_21_10.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_10.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/MixinLevelRendererLevelRenderContext.class */
@Mixin(value = {hfq.class}, priority = 900)
public class MixinLevelRendererLevelRenderContext {
    @WrapOperation(method = {"lambda$addMainPass$1"}, at = {@At(value = "NEW", target = "()Lcom/mojang/blaze3d/vertex/PoseStack;")})
    private fua labyMod$storePoseStack(Operation<fua> original) {
        fua poseStack = (fua) original.call(new Object[0]);
        MinecraftUtil.levelRenderContext().setPoseStack(poseStack);
        return poseStack;
    }
}
