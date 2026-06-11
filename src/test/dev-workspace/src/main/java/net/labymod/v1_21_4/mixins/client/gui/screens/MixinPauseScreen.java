package net.labymod.v1_21_4.mixins.client.gui.screens;

import net.labymod.core.client.gui.screen.accessor.PauseScreenAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/gui/screens/MixinPauseScreen.class */
@Mixin({fuh.class})
public class MixinPauseScreen implements PauseScreenAccessor {

    @Shadow
    @Final
    private boolean J;

    @Override // net.labymod.core.client.gui.screen.accessor.PauseScreenAccessor
    public boolean showPauseMenu() {
        return this.J;
    }
}
