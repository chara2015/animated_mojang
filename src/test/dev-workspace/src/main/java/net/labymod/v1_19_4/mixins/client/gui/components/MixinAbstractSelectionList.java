package net.labymod.v1_19_4.mixins.client.gui.components;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.util.scissor.Scissor;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/gui/components/MixinAbstractSelectionList.class */
@Mixin({enw.class})
public abstract class MixinAbstractSelectionList {

    @Shadow
    private boolean u;
    private boolean originalRenderBackground;

    @Shadow
    private boolean v;

    @Shadow
    protected int k;

    @Shadow
    protected int f;
    private boolean originalRenderTopAndBottom;

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V"}, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderBackground:Z", shift = At.Shift.BEFORE))
    public void labyMod$renderBackground(ehe stack, int param1, int param2, float param3, InsertInfo ci) {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (!theme.metadata().getBoolean(DefaultThemeVariables.HIDE_DEFAULT_BACKGROUND)) {
            if (this.u != this.originalRenderBackground) {
                this.originalRenderBackground = this.u;
            }
        } else {
            if (this.u == this.originalRenderBackground) {
                return;
            }
            this.u = this.originalRenderBackground;
        }
    }

    @Insert(method = {"render"}, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderTopAndBottom:Z", shift = At.Shift.BEFORE))
    public void labyMod$renderTopAndBottom(ehe stack, int param1, int param2, float param3, InsertInfo ci) {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (!theme.metadata().getBoolean(DefaultThemeVariables.HIDE_DEFAULT_BACKGROUND)) {
            if (this.v != this.originalRenderTopAndBottom) {
                this.originalRenderTopAndBottom = this.v;
            }
        } else {
            if (this.v == this.originalRenderTopAndBottom) {
                return;
            }
            this.v = this.originalRenderTopAndBottom;
        }
    }

    @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderList(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V")})
    public void labyMod$renderList(enw instance, ehe stack, int mouseX, int mouseY, float partialTicks, Operation<Void> original) {
        ReferenceStorage references = Laby.references();
        ScreenContext screenContext = references.renderEnvironmentContext().screenContext();
        screenContext.runInContext(((VanillaStackAccessor) stack).stack(), mouseX, mouseY, partialTicks, context -> {
            Scissor scissor = screenContext.canvas().scissor();
            scissor.push(this.f, this.k - this.f);
            references.laby3D().runWithScissor(screenContext, () -> {
                original.call(new Object[]{instance, stack, Integer.valueOf(mouseX), Integer.valueOf(mouseY), Float.valueOf(partialTicks)});
            });
            scissor.pop();
        });
    }
}
