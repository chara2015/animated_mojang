package net.labymod.v26_2_snapshot_8.mixins.client.screen.components.dirtseparator;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/screen/components/dirtseparator/MixinCreateWorldScreenDirtSeparator.class */
@Mixin({CreateWorldScreen.class})
public class MixinCreateWorldScreenDirtSeparator extends Screen {
    protected MixinCreateWorldScreenDirtSeparator(Component $$0) {
        super($$0);
    }

    @Inject(method = {"extractRenderState"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V", shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$skipDirtSeparator(GuiGraphicsExtractor $$0, int $$1, int $$2, float $$3, CallbackInfo ci) {
        Theme currentTheme = Laby.references().themeService().currentTheme();
        if (!((Boolean) currentTheme.metadata().get(DefaultThemeVariables.SHOW_DIRT_SEPARATOR, true)).booleanValue()) {
            ci.cancel();
            super.extractRenderState($$0, $$1, $$2, $$3);
        }
    }
}
