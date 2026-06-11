package net.labymod.v26_1.mixins.client.window;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/window/MixinWindowVideoMode.class */
@Mixin({VideoSettingsScreen.class})
public class MixinWindowVideoMode {
    @Inject(at = {@At("HEAD")}, method = {"removed"})
    private void screenRemoved(CallbackInfo ci) {
        Minecraft.getInstance().getWindow().changeFullscreenVideoMode();
    }
}
