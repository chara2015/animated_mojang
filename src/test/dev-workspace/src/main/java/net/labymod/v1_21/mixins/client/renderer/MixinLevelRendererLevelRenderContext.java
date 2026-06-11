package net.labymod.v1_21.mixins.client.renderer;

import net.labymod.v1_21.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/renderer/MixinLevelRendererLevelRenderContext.class */
@Mixin(value = {gex.class}, priority = 900)
public class MixinLevelRendererLevelRenderContext {
    @Redirect(method = {"renderLevel"}, at = @At(value = "NEW", target = "()Lcom/mojang/blaze3d/vertex/PoseStack;"))
    private fbi labyMod$storePoseStack() {
        fbi poseStack = new fbi();
        MinecraftUtil.levelRenderContext().setPoseStack(poseStack);
        return poseStack;
    }
}
