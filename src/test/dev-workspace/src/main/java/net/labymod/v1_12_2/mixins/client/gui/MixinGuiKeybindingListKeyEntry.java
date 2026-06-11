package net.labymod.v1_12_2.mixins.client.gui;

import net.labymod.api.client.options.MinecraftInputMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiKeybindingListKeyEntry.class */
@Mixin({b.class})
public class MixinGuiKeybindingListKeyEntry {
    @Redirect(method = {"drawEntry"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;getKeyCode()I"))
    private int labyMod$removeRedColor(bhy instance) {
        if (MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping) instance)) {
            return Integer.MIN_VALUE;
        }
        return instance.j();
    }
}
