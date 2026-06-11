package net.labymod.v1_20_4.mixins.client.gui.components;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.util.scissor.Scissor;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/gui/components/MixinAbstractSelectionList.class */
@Mixin({exb.class})
public abstract class MixinAbstractSelectionList extends ewy {

    @Shadow
    private boolean r;
    private boolean originalRenderBackground;

    public MixinAbstractSelectionList(int $$0, int $$1, int $$2, int $$3, vf $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }

    @Insert(method = {"renderWidget"}, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderBackground:Z", shift = At.Shift.BEFORE))
    public void labyMod$renderBackground(ewu graphics, int param1, int param2, float param3, InsertInfo ci) {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (!theme.metadata().getBoolean(DefaultThemeVariables.HIDE_DEFAULT_BACKGROUND)) {
            if (this.r != this.originalRenderBackground) {
                this.originalRenderBackground = this.r;
            }
        } else {
            if (this.r == this.originalRenderBackground) {
                return;
            }
            this.r = this.originalRenderBackground;
        }
    }

    @WrapOperation(method = {"renderWidget"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderList(Lnet/minecraft/client/gui/GuiGraphics;IIF)V")})
    public void labyMod$renderList(exb instance, ewu graphics, int mouseX, int mouseY, float partialTicks, Operation<Void> original) {
        ReferenceStorage references = Laby.references();
        ScreenContext screenContext = references.renderEnvironmentContext().screenContext();
        screenContext.runInContext(graphics.c().stack(), mouseX, mouseY, partialTicks, context -> {
            Scissor scissor = screenContext.canvas().scissor();
            scissor.push(C(), u());
            references.laby3D().runWithScissor(screenContext, () -> {
                original.call(new Object[]{instance, graphics, Integer.valueOf(mouseX), Integer.valueOf(mouseY), Float.valueOf(partialTicks)});
            });
            scissor.pop();
        });
    }
}
