package net.labymod.v1_12_2.mixins.client.gui.screens.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.v1_12_2.client.LabyModKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/screens/controls/MixinKeyBindsScreen.class */
@Mixin({bme.class})
public class MixinKeyBindsScreen {
    @Redirect(method = {"drawScreen"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;keyBindings:[Lnet/minecraft/client/settings/KeyBinding;"))
    private bhy[] labyMod$checkKeys(bid instance) {
        List<bhy> mappings = new ArrayList<>(Arrays.asList(instance.as));
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (bhy[]) mappings.toArray(new bhy[0]);
    }

    @Redirect(method = {"actionPerformed"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;keyBindings:[Lnet/minecraft/client/settings/KeyBinding;"))
    private bhy[] labyMod$resetKeys(bid instance) {
        List<bhy> mappings = new ArrayList<>(Arrays.asList(instance.as));
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (bhy[]) mappings.toArray(new bhy[0]);
    }
}
