package net.labymod.v1_20_6.mixins.client.gui.screens;

import net.labymod.core.client.gui.screen.accessor.PauseScreenAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/gui/screens/MixinPauseScreen.class */
@Mixin({fna.class})
public class MixinPauseScreen implements PauseScreenAccessor {

    @Shadow
    @Final
    private boolean G;

    @Override // net.labymod.core.client.gui.screen.accessor.PauseScreenAccessor
    public boolean showPauseMenu() {
        return this.G;
    }
}
