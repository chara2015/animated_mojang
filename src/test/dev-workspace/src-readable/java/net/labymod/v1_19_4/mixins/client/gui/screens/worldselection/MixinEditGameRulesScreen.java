package net.labymod.v1_19_4.mixins.client.gui.screens.worldselection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/gui/screens/worldselection/MixinEditGameRulesScreen.class */
@Mixin({exo.class})
public abstract class MixinEditGameRulesScreen extends etd {
    protected MixinEditGameRulesScreen(tj param0) {
        super(param0);
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    public void labyMod$renderBackground(ehe param0, int param1, int param2, float param3, CallbackInfo ci) {
        a(param0);
    }
}
