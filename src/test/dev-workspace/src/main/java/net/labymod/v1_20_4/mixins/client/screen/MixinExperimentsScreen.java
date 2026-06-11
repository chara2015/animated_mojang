package net.labymod.v1_20_4.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/screen/MixinExperimentsScreen.class */
@Mixin({fhu.class})
public class MixinExperimentsScreen {
    @Redirect(method = {"renderBackground"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private void labyMod$disableDirtBackground(ewu instance, ahg $$0, int $$1, int $$2, float $$3, float $$4, int $$5, int $$6, int $$7, int $$8) {
        ThemeService themeService = Laby.references().themeService();
        Theme theme = themeService.currentTheme();
        if (!theme.metadata().getBoolean(DefaultThemeVariables.HIDE_DEFAULT_BACKGROUND)) {
            instance.a($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
        }
    }
}
