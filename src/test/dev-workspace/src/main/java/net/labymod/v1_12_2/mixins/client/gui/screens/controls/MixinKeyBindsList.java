package net.labymod.v1_12_2.mixins.client.gui.screens.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.v1_12_2.client.LabyModKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/screens/controls/MixinKeyBindsList.class */
@Mixin({bmd.class})
public abstract class MixinKeyBindsList {
    @Redirect(method = {"<init>"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;keyBindings:[Lnet/minecraft/client/settings/KeyBinding;"))
    private bhy[] labyMod$addCustomKeybindings(bid instance) {
        List<bhy> keyMappings = new ArrayList<>(Arrays.asList(instance.as));
        keyMappings.removeIf(mapping -> {
            return ((MinecraftInputMapping) mapping).isHidden();
        });
        for (Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            keyMappings.add(LabyModKeyMapping.create(hotkey));
        }
        return (bhy[]) keyMappings.toArray(new bhy[0]);
    }
}
