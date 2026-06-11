package net.labymod.v1_21_11.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.client.render.batch.DefaultRenderContexts;
import net.labymod.v1_21_11.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/MixinGameRendererScreen.class */
@Mixin({GameRenderer.class})
public class MixinGameRendererScreen {
    private final DefaultRenderContexts renderContexts = Laby.references().renderContexts();

    @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderWithTooltipAndSubtitles(Lnet/minecraft/client/gui/GuiGraphics;IIF)V")})
    private void labyMod$storeStack(Screen instance, GuiGraphics graphics, int mouseX, int mouseY, float tickDelta, Operation<Void> original) {
        Stack stack = Stack.create(JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics));
        ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
        screenCustomFontStack.push(instance);
        this.renderContexts.setCurrentStack(stack);
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, mouseX, mouseY, tickDelta, screenContext -> {
            ScreenUtil.wrapRender(screenContext, () -> {
                original.call(new Object[]{instance, graphics, Integer.valueOf(mouseX), Integer.valueOf(mouseY), Float.valueOf(tickDelta)});
            });
        });
        graphics.renderDeferredElements();
        this.renderContexts.setCurrentStack((Stack) null);
        screenCustomFontStack.pop(instance);
    }
}
