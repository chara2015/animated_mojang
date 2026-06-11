package net.labymod.v1_12_2.mixins.compatibility.optifine;

import java.util.List;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/compatibility/optifine/MixinOptiFineTooltipManager.class */
@Pseudo
@Mixin(targets = {"net.optifine.gui.TooltipManager"})
public class MixinOptiFineTooltipManager {
    @Inject(method = {"drawTooltips"}, at = {@At("HEAD")}, remap = false)
    @Dynamic
    private void labyMod$preTranslateTooltip(int x, int y, List buttonList, CallbackInfo ci) {
        bus.c(0.0f, 0.0f, 100.0f);
    }

    @Inject(method = {"drawTooltips"}, at = {@At("TAIL")}, remap = false)
    @Dynamic
    private void labyMod$postTranslateTooltip(int x, int y, List buttonList, CallbackInfo ci) {
        bus.c(0.0f, 0.0f, -100.0f);
    }
}
