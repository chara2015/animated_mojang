package net.labymod.v1_21_11.mixins.client.screen.components.dirtseparator;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.theme.Theme;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/screen/components/dirtseparator/MixinCreateWorldScreenDirtSeparator.class */
@Mixin({gxx.class})
public class MixinCreateWorldScreenDirtSeparator extends gsb {
    protected MixinCreateWorldScreenDirtSeparator(yh $$0) {
        super($$0);
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V", shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$skipDirtSeparator(gir $$0, int $$1, int $$2, float $$3, CallbackInfo ci) {
        Theme currentTheme = Laby.references().themeService().currentTheme();
        if (!((Boolean) currentTheme.metadata().get(DefaultThemeVariables.SHOW_DIRT_SEPARATOR, true)).booleanValue()) {
            ci.cancel();
            super.a($$0, $$1, $$2, $$3);
        }
    }
}
