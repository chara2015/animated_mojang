package net.labymod.v1_16_5.mixins.client.gui.components;

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
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/gui/components/MixinAbstractSelectionList.class */
@Mixin({dlf.class})
public abstract class MixinAbstractSelectionList {

    @Shadow
    private boolean t;
    private boolean originalRenderBackground;

    @Shadow
    private boolean u;

    @Shadow
    protected int j;

    @Shadow
    protected int i;

    @Shadow
    protected int e;

    @Shadow
    protected int d;
    private boolean originalRenderTopAndBottom;
    private dfm labyMod$poseStack;

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V"}, at = @At("HEAD"))
    private void labyMod$getStack(dfm stack, int mouseX, int mouseY, float partialTicks, InsertInfo ci) {
        this.labyMod$poseStack = stack;
    }

    @Redirect(method = {"render", "renderList"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;vertex(DDD)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private dfq labyMod$addStack(dfh instance, double x, double y, double z) {
        return instance.a(this.labyMod$poseStack.c().a(), (float) x, (float) y, (float) z);
    }

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V"}, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderBackground:Z", shift = At.Shift.BEFORE))
    public void labyMod$renderBackground(dfm stack, int param1, int param2, float param3, InsertInfo ci) {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (!theme.metadata().getBoolean(DefaultThemeVariables.HIDE_DEFAULT_BACKGROUND)) {
            if (this.t != this.originalRenderBackground) {
                this.originalRenderBackground = this.t;
            }
        } else {
            if (this.t == this.originalRenderBackground) {
                return;
            }
            this.t = this.originalRenderBackground;
        }
    }

    @Insert(method = {"render"}, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderTopAndBottom:Z", shift = At.Shift.BEFORE))
    public void labyMod$renderTopAndBottom(dfm stack, int param1, int param2, float param3, InsertInfo ci) {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (!theme.metadata().getBoolean(DefaultThemeVariables.HIDE_DEFAULT_BACKGROUND)) {
            if (this.u != this.originalRenderTopAndBottom) {
                this.originalRenderTopAndBottom = this.u;
            }
        } else {
            if (this.u == this.originalRenderTopAndBottom) {
                return;
            }
            this.u = this.originalRenderTopAndBottom;
        }
    }

    @WrapOperation(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;renderList(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIF)V")})
    public void labyMod$renderList(dlf instance, dfm stack, int left, int top, int mouseX, int mouseY, float partialTicks, Operation<Void> original) {
        ReferenceStorage references = Laby.references();
        ScreenContext screenContext = references.renderEnvironmentContext().screenContext();
        screenContext.runInContext(((VanillaStackAccessor) stack).stack(), mouseX, mouseY, partialTicks, context -> {
            Scissor scissor = screenContext.canvas().scissor();
            scissor.push(this.i, this.j - this.i);
            references.laby3D().runWithScissor(screenContext, () -> {
                original.call(new Object[]{instance, stack, Integer.valueOf(left), Integer.valueOf(top), Integer.valueOf(mouseX), Integer.valueOf(mouseY), Float.valueOf(partialTicks)});
            });
            scissor.pop();
        });
    }
}
