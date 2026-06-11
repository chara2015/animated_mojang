package net.labymod.v1_21_8.mixins.client.gui.components;

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
import net.labymod.v1_21_8.client.render.matrix.JomlMatrix3x2fStackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/gui/components/MixinAbstractSelectionList.class */
@Mixin({fxi.class})
public abstract class MixinAbstractSelectionList extends fxf {

    @Shadow
    @Final
    protected fue c;

    public MixinAbstractSelectionList(int $$0, int $$1, int $$2, int $$3, xo $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }

    @Inject(method = {"renderListSeparators"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$hideListSeparators(CallbackInfo info) {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (theme.metadata().getBoolean(DefaultThemeVariables.HIDE_LIST_SEPARATORS)) {
            info.cancel();
        }
    }

    @WrapOperation(method = {"renderWidget"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderListBackground(Lnet/minecraft/client/gui/GuiGraphics;)V")})
    private void labyMod$renderBackground(fxi instance, fxb guiGraphics, Operation<Void> original) {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (theme.metadata().getBoolean(DefaultThemeVariables.HIDE_DEFAULT_BACKGROUND)) {
            return;
        }
        original.call(new Object[]{instance, guiGraphics});
    }

    @WrapOperation(method = {"renderWidget"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderListItems(Lnet/minecraft/client/gui/GuiGraphics;IIF)V")})
    public void labyMod$renderList(fxi instance, fxb graphics, int mouseX, int mouseY, float partialTicks, Operation<Void> original) {
        ReferenceStorage references = Laby.references();
        ScreenContext screenContext = references.renderEnvironmentContext().screenContext();
        screenContext.runInContext(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), mouseX, mouseY, partialTicks, context -> {
            Scissor scissor = screenContext.canvas().scissor();
            scissor.push(F(), y());
            references.laby3D().runWithScissor(screenContext, () -> {
                original.call(new Object[]{instance, graphics, Integer.valueOf(mouseX), Integer.valueOf(mouseY), Float.valueOf(partialTicks)});
            });
            scissor.pop();
        });
    }
}
