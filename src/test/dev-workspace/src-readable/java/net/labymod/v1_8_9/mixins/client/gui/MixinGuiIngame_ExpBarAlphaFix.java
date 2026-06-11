package net.labymod.v1_8_9.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinGuiIngame_ExpBarAlphaFix.class */
@Mixin({avo.class})
public class MixinGuiIngame_ExpBarAlphaFix {
    @Inject(method = {"renderGameOverlay(F)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderBossHealth()V")})
    private void labyMod$restoreAlphaTestAfterCrosshair(float partialTicks, CallbackInfo ci) {
        bfl.d();
    }
}
