package net.labymod.v1_19_4.mixins.client.gui.screens;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.core.client.render.batch.DefaultRenderContexts;
import net.labymod.v1_19_4.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/gui/screens/MixinLoadingOverlay.class */
@Mixin({esq.class})
public class MixinLoadingOverlay {
    @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V")})
    private void labyMod$renderScreen(etd screen, ehe poseStack, int mouseX, int mouseY, float partialTicks, Operation<Void> original) {
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        DefaultRenderContexts renderContexts = (DefaultRenderContexts) Laby.labyAPI().renderPipeline().renderContexts();
        renderContexts.setCurrentStack(stack);
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, mouseX, mouseY, partialTicks, screenContext -> {
            ScreenUtil.wrapRender(screenContext, () -> {
                original.call(new Object[]{screen, poseStack, Integer.valueOf(mouseX), Integer.valueOf(mouseY), Float.valueOf(partialTicks)});
            });
        });
        renderContexts.setCurrentStack(null);
    }
}
