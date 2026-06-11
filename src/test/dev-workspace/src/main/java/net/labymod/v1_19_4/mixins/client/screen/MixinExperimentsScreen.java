package net.labymod.v1_19_4.mixins.client.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/screen/MixinExperimentsScreen.class */
@Mixin({exq.class})
public class MixinExperimentsScreen {
    @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/ExperimentsScreen;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIFFIIII)V"))
    private void labyMod$disableDirtBackground(ehe poseStack, int i0, int i1, float v2, float v3, int i4, int i5, int i6, int i7) {
        ThemeService themeService = Laby.references().themeService();
        Theme theme = themeService.currentTheme();
        if (!theme.metadata().getBoolean(DefaultThemeVariables.HIDE_DEFAULT_BACKGROUND)) {
            enq.a(poseStack, i0, i1, v2, v3, i4, i5, i6, i7);
        }
    }
}
