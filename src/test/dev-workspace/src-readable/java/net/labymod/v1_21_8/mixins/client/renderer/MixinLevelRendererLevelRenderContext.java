package net.labymod.v1_21_8.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_8.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/renderer/MixinLevelRendererLevelRenderContext.class */
@Mixin(value = {gxh.class}, priority = 900)
public class MixinLevelRendererLevelRenderContext {
    @WrapOperation(method = {"lambda$addMainPass$2"}, at = {@At(value = "NEW", target = "()Lcom/mojang/blaze3d/vertex/PoseStack;")})
    private fod labyMod$storePoseStack(Operation<fod> original) {
        fod poseStack = (fod) original.call(new Object[0]);
        MinecraftUtil.levelRenderContext().setPoseStack(poseStack);
        return poseStack;
    }
}
