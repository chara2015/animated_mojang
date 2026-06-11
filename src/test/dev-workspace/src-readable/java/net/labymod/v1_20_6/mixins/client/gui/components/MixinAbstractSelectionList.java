package net.labymod.v1_20_6.mixins.client.gui.components;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.util.scissor.Scissor;
import net.labymod.api.generated.ReferenceStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/gui/components/MixinAbstractSelectionList.class */
@Mixin({fhb.class})
public abstract class MixinAbstractSelectionList extends fgy {

    @Shadow
    @Final
    protected ffh c;

    public MixinAbstractSelectionList(int $$0, int $$1, int $$2, int $$3, xp $$4) {
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
    private void labyMod$renderBackground(fhb instance, fgt guiGraphics, Operation<Void> original) {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (theme.metadata().getBoolean(DefaultThemeVariables.HIDE_DEFAULT_BACKGROUND)) {
            return;
        }
        original.call(new Object[]{instance, guiGraphics});
    }

    @WrapOperation(method = {"renderWidget"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderListItems(Lnet/minecraft/client/gui/GuiGraphics;IIF)V")})
    public void labyMod$renderList(fhb instance, fgt graphics, int mouseX, int mouseY, float partialTicks, Operation<Void> original) {
        ReferenceStorage references = Laby.references();
        ScreenContext screenContext = references.renderEnvironmentContext().screenContext();
        screenContext.runInContext(graphics.c().stack(), mouseX, mouseY, partialTicks, context -> {
            Scissor scissor = screenContext.canvas().scissor();
            scissor.push(D(), v());
            references.laby3D().runWithScissor(screenContext, () -> {
                original.call(new Object[]{instance, graphics, Integer.valueOf(mouseX), Integer.valueOf(mouseY), Float.valueOf(partialTicks)});
            });
            scissor.pop();
        });
    }
}
