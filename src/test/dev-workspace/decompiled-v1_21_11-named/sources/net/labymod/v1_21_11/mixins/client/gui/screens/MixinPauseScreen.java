package net.labymod.v1_21_11.mixins.client.gui.screens;

import net.labymod.core.client.gui.screen.accessor.PauseScreenAccessor;
import net.minecraft.client.gui.screens.PauseScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/gui/screens/MixinPauseScreen.class */
@Mixin({PauseScreen.class})
public class MixinPauseScreen implements PauseScreenAccessor {

    @Shadow
    @Final
    private boolean F;

    public boolean showPauseMenu() {
        return this.F;
    }
}
