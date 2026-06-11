package net.labymod.v1_16_5.mixins.client.gui.screens.worldselection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/gui/screens/worldselection/MixinWorldSelectionList.class */
@Mixin({dsm.class})
public class MixinWorldSelectionList {
    @Redirect(method = {"refreshList"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V", ordinal = 1))
    public void labyMod$openCreateWorldScreenNextTick(djz minecraft, dot screen) {
        minecraft.h(() -> {
            minecraft.a(screen);
        });
    }
}
