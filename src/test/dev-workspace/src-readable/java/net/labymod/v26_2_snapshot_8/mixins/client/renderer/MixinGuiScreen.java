package net.labymod.v26_2_snapshot_8.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.core.client.render.batch.DefaultRenderContexts;
import net.labymod.v26_2_snapshot_8.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.labymod.v26_2_snapshot_8.client.util.MinecraftUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/renderer/MixinGuiScreen.class */
@Mixin({Gui.class})
public class MixinGuiScreen {
    private final DefaultRenderContexts renderContexts = (DefaultRenderContexts) Laby.references().renderContexts();

    @WrapOperation(method = {"extractRenderState"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;extractRenderStateWithTooltipAndSubtitles(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V")})
    private void labyMod$storeStack(Screen instance, GuiGraphicsExtractor graphics, int mouseX, int mouseY, float tickDelta, Operation<Void> original) {
        Stack stack = Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics));
        ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
        screenCustomFontStack.push(instance);
        this.renderContexts.setCurrentStack(stack);
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, mouseX, mouseY, tickDelta, screenContext -> {
            ScreenUtil.wrapRender(screenContext, () -> {
                original.call(new Object[]{instance, graphics, Integer.valueOf(mouseX), Integer.valueOf(mouseY), Float.valueOf(tickDelta)});
            });
        });
        graphics.extractDeferredElements(mouseX, mouseY, tickDelta);
        this.renderContexts.setCurrentStack(null);
        screenCustomFontStack.pop(instance);
    }
}
