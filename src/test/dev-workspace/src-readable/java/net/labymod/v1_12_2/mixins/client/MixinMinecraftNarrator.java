package net.labymod.v1_12_2.mixins.client;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/MixinMinecraftNarrator.class */
@Mixin({bib.class})
public class MixinMinecraftNarrator {
    @Redirect(method = {"dispatchKeypresses"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/GameSettings;setOptionValue(Lnet/minecraft/client/settings/GameSettings$Options;I)V"))
    private void labyMod$disableNarrator(bid settings, a options, int value) {
        if (Laby.labyAPI().config().hotkeys().disableNarratorHotkey().get().booleanValue()) {
            return;
        }
        settings.a(options, value);
    }
}
