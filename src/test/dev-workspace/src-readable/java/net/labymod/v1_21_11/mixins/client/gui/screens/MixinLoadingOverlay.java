package net.labymod.v1_21_11.mixins.client.gui.screens;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.client.render.batch.DefaultRenderContexts;
import net.labymod.v1_21_11.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/gui/screens/MixinLoadingOverlay.class */
@Mixin({LoadingOverlay.class})
public class MixinLoadingOverlay {
    @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderWithTooltipAndSubtitles(Lnet/minecraft/client/gui/GuiGraphics;IIF)V")})
    private void labyMod$renderScreen(Screen screen, GuiGraphics graphics, int mouseX, int mouseY, float tickDelta, Operation<Void> original) {
        Stack stack = Stack.create(JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics));
        DefaultRenderContexts renderContexts = Laby.labyAPI().renderPipeline().renderContexts();
        renderContexts.setCurrentStack(stack);
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, mouseX, mouseY, tickDelta, screenContext -> {
            ScreenUtil.wrapRender(screenContext, () -> {
                original.call(new Object[]{screen, graphics, Integer.valueOf(mouseX), Integer.valueOf(mouseY), Float.valueOf(tickDelta)});
            });
        });
        renderContexts.setCurrentStack((Stack) null);
    }
}
