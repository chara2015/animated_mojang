package net.labymod.v1_16_5.mixins.client.gui.screens.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.v1_16_5.client.LabyModKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/gui/screens/controls/MixinKeyBindsScreen.class */
@Mixin({dpl.class})
public class MixinKeyBindsScreen {
    @Redirect(method = {"render"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private djw[] labyMod$checkKeys(dkd instance) {
        List<djw> mappings = new ArrayList<>(Arrays.asList(instance.aF));
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (djw[]) mappings.toArray(new djw[0]);
    }

    @Redirect(method = {"lambda$init$1"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private djw[] labyMod$resetKeys(dkd instance) {
        List<djw> mappings = new ArrayList<>(Arrays.asList(instance.aF));
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (djw[]) mappings.toArray(new djw[0]);
    }
}
