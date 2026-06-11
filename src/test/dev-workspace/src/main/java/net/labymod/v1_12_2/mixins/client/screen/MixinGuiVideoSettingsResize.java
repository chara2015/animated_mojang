package net.labymod.v1_12_2.mixins.client.screen;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/screen/MixinGuiVideoSettingsResize.class */
@Mixin({bls.class})
public class MixinGuiVideoSettingsResize {
    @Redirect(method = {"actionPerformed(Lnet/minecraft/client/gui/GuiButton;I)V"}, require = 0, expect = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiVideoSettings;setWorldAndResolution(Lnet/minecraft/client/Minecraft;II)V"))
    @Dynamic
    private void labyMod$optifine$fixResize(bls instance, bib minecraft, int width, int height) {
        labyMod$resizeParentScreen(minecraft, width, height);
    }

    @Redirect(method = {"mouseClicked", "mouseReleased"}, require = 0, expect = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiVideoSettings;setWorldAndResolution(Lnet/minecraft/client/Minecraft;II)V"))
    @Dynamic
    private void labyMod$fixResize(bls instance, bib minecraft, int width, int height) {
        labyMod$resizeParentScreen(minecraft, width, height);
    }

    @Unique
    private void labyMod$resizeParentScreen(bib minecraft, int width, int height) {
        blk screen = minecraft.m;
        if (screen == null) {
            return;
        }
        screen.a(minecraft, width, height);
    }
}
