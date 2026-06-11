package net.labymod.v1_18_2.mixins.client.gui.screens.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.v1_18_2.client.LabyModKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/gui/screens/controls/MixinKeyBindsScreen.class */
@Mixin({eep.class})
public class MixinKeyBindsScreen {
    @Redirect(method = {"render"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private dyo[] labyMod$checkKeys(dyv instance) {
        List<dyo> mappings = new ArrayList<>(Arrays.asList(instance.aT));
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (dyo[]) mappings.toArray(new dyo[0]);
    }

    @Redirect(method = {"lambda$init$0"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private dyo[] labyMod$resetKeys(dyv instance) {
        List<dyo> mappings = new ArrayList<>(Arrays.asList(instance.aT));
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (dyo[]) mappings.toArray(new dyo[0]);
    }
}
