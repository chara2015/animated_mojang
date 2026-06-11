package net.labymod.v1_16_5.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.core.client.render.batch.DefaultRenderContexts;
import net.labymod.v1_16_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/MixinGameRendererScreen.class */
@Mixin({dzz.class})
public class MixinGameRendererScreen {
    private final DefaultRenderContexts renderContexts = (DefaultRenderContexts) Laby.references().renderContexts();

    @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V")})
    private void labyMod$storeStack(dot instance, dfm poseStack, int mouseX, int mouseY, float tickDelta, Operation<Void> original) {
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
        screenCustomFontStack.push(instance);
        this.renderContexts.setCurrentStack(stack);
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, mouseX, mouseY, tickDelta, screenContext -> {
            ScreenUtil.wrapRender(screenContext, () -> {
                original.call(new Object[]{instance, poseStack, Integer.valueOf(mouseX), Integer.valueOf(mouseY), Float.valueOf(tickDelta)});
            });
        });
        this.renderContexts.setCurrentStack(null);
        screenCustomFontStack.pop(instance);
    }
}
