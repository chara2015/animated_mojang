package net.labymod.v26_1_2.mixins.client.gui.components;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.util.scissor.Scissor;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.v26_1_2.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/gui/components/MixinAbstractSelectionList.class */
@Mixin({AbstractSelectionList.class})
public abstract class MixinAbstractSelectionList extends AbstractContainerWidget {

    @Shadow
    @Final
    protected Minecraft minecraft;

    public MixinAbstractSelectionList(int x, int y, int width, int height, Component message, AbstractScrollArea.ScrollbarSettings scrollbarSettings) {
        super(x, y, width, height, message, scrollbarSettings);
    }

    @Inject(method = {"extractListSeparators"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$hideListSeparators(CallbackInfo info) {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (theme.metadata().getBoolean(DefaultThemeVariables.HIDE_LIST_SEPARATORS)) {
            info.cancel();
        }
    }

    @WrapOperation(method = {"extractWidgetRenderState"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;extractListBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V")})
    private void labyMod$renderBackground(AbstractSelectionList instance, GuiGraphicsExtractor guiGraphics, Operation<Void> original) {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (theme.metadata().getBoolean(DefaultThemeVariables.HIDE_DEFAULT_BACKGROUND)) {
            return;
        }
        original.call(new Object[]{instance, guiGraphics});
    }

    @WrapOperation(method = {"extractWidgetRenderState"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;extractListItems(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V")})
    public void labyMod$renderList(AbstractSelectionList instance, GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks, Operation<Void> original) {
        ReferenceStorage references = Laby.references();
        ScreenContext screenContext = references.renderEnvironmentContext().screenContext();
        screenContext.runInContext(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), mouseX, mouseY, partialTicks, context -> {
            Scissor scissor = screenContext.canvas().scissor();
            scissor.push(getY(), getHeight());
            references.laby3D().runWithScissor(screenContext, () -> {
                original.call(new Object[]{instance, graphics, Integer.valueOf(mouseX), Integer.valueOf(mouseY), Float.valueOf(partialTicks)});
            });
            scissor.pop();
        });
    }
}
