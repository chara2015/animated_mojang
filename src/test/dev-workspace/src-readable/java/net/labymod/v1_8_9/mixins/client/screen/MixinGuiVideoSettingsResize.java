package net.labymod.v1_8_9.mixins.client.screen;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/screen/MixinGuiVideoSettingsResize.class */
@Mixin({ayb.class})
public class MixinGuiVideoSettingsResize {
    @Redirect(method = {"actionPerformed(Lnet/minecraft/client/gui/GuiButton;I)V"}, require = 0, expect = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiVideoSettings;setWorldAndResolution(Lnet/minecraft/client/Minecraft;II)V"))
    @Dynamic
    private void labyMod$optifine$fixResize(ayb instance, ave minecraft, int width, int height) {
        labyMod$resizeParentScreen(minecraft, width, height);
    }

    @Redirect(method = {"mouseClicked", "mouseReleased"}, require = 0, expect = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiVideoSettings;setWorldAndResolution(Lnet/minecraft/client/Minecraft;II)V"))
    @Dynamic
    private void labyMod$fixResize(ayb instance, ave minecraft, int width, int height) {
        labyMod$resizeParentScreen(minecraft, width, height);
    }

    @Unique
    private void labyMod$resizeParentScreen(ave minecraft, int width, int height) {
        axu screen = minecraft.m;
        if (screen == null) {
            return;
        }
        screen.a(minecraft, width, height);
    }
}
